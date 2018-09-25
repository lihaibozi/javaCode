package com.kin207.zn;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import common.utils.AppUtil;
import common.utils.StringUtil;
import jodd.io.FileUtil;


/**
 * 本地文件服务器存储服务 
 */
public class FileService {

	private static FileService service;

	private String fileServerDomain;
	private String fileServerPath;
	private String appName;
	
	private FileService(){}
	
	/**
	 * 初始化  系统产生的文件  在文件服务器中的保存路径
	 * @param webAppAbsolutePath 文件服务器所在文件夹
	 * @param appName 应用的名称
	 */
	public static void init(String fileServerHostAndPort, String webAppAbsolutePath, String appName)
	{
		if(service == null)
		{
			service = new FileService();
			service.fileServerDomain = fileServerHostAndPort;
			service.fileServerPath = webAppAbsolutePath;
			service.appName = appName;
			
			File appFolder = new File(service.getFileServerPath() + "/" + service.getAppName());
			if (!appFolder.exists()) 
			{
				appFolder.mkdir();
			}
		}
	}
	
	public static String getFileKey(SavedFile savedFile)
	{
		return getFileKey(savedFile.getAbsolutePath());
	}
	
	
	/**
	 * @return folder/name
	 */
	private static String getFileKey(String filePath)
	{
		String serverPath = null;
		if (service.getFileServerPath().endsWith("/")) 
		{
			serverPath = service.getFileServerPath() + service.getAppName();
		}else 
		{
			serverPath = service.getFileServerPath() + "/" + service.getAppName();
		}
		String key = filePath.replace(serverPath, "");
		return key.startsWith("/") ? key.replaceFirst("/", "") : key;
	}
	
	
	/**
	 * 将文件移动到文件服务器中
	 * @param saveFolder
	 * @param srcFile
	 * @return 下载链接
	 */
	public static SavedFile newUploadTo(String saveFolder, MultipartFile file)
	{
		File srcFile = AppUtil.MultipartFilToFile(file);
		String str =file.getOriginalFilename();
		String photoType= str.substring(str.indexOf("."),str.length());
		//创建保存目录
		String savePath = getFolderSavePath(saveFolder);
		
		if(srcFile == null || !srcFile.exists())
		{
			return null;
		}
		//生成新名称
		String destFilePath = savePath + generateNewName(srcFile.getName());
		//文件名称已存在，生成新的名称
		boolean fileExisted = true;
		File destFile = null;
		while (fileExisted) 
		{
			destFile = new File(destFilePath);
			if(destFile.exists())
			{
				destFilePath = savePath + generateNewName(srcFile.getName());
			}else {
				fileExisted = false;
			}
		}
		//移动文件
		try {
			FileUtil.move(srcFile, destFile);
			String relativeLink = generateRelativeLink(saveFolder, destFile.getName());
			
			String imagePath = destFile.getAbsolutePath();
			String path = imagePath.substring(0,imagePath.lastIndexOf(".")) + photoType;
			  FileInputStream is = new FileInputStream(new File(imagePath));  
	          FileOutputStream os = new FileOutputStream(new File(path));  
	  
	            byte[] buf = new byte[1024];  
	  
	            while (is.read(buf) != -1) {  
	            	os.write(buf);  
	            }  
			
			  SavedFile image = new SavedFile(getService().getFileServerDomain(), relativeLink,path);
			  
	            
			  return image;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	/**
	 * 将文件移动到文件服务器中
	 * @param saveFolder
	 * @param srcFile
	 * @return 下载链接
	 */
	public static SavedFile uploadTo(String saveFolder, MultipartFile file)
	{
		//创建保存目录

		File srcFile = AppUtil.MultipartFilToFile(file);
		String savePath = getFolderSavePath(saveFolder);
		
		if(srcFile == null || !srcFile.exists())
		{
			return null;
		}
		//生成新名称
		String destFilePath = savePath + generateNewName(srcFile.getName());
		//文件名称已存在，生成新的名称
		boolean fileExisted = true;
		File destFile = null;
		while (fileExisted) 
		{
			destFile = new File(destFilePath);
			if(destFile.exists())
			{
				destFilePath = savePath + generateNewName(srcFile.getName());
			}else {
				fileExisted = false;
			}
		}
		//移动文件
		try {
			FileUtil.move(srcFile, destFile);
			String relativeLink = generateRelativeLink(saveFolder, destFile.getName());
			return new SavedFile(getService().getFileServerDomain(), relativeLink, destFile.getAbsolutePath());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	/**
	 * @param fileExt  .jpg/jpg
	 */
	public static SavedFile gemerateSavedFile(String saveFolder, String fileExt) 
	{
		//创建保存目录
		String savePath = getFolderSavePath(saveFolder);
		fileExt = fileExt.startsWith(".") ? fileExt : "." + fileExt;
		String destFilePath = savePath + generateNewName(fileExt);
		
		//文件名称已存在，生成新的名称
		boolean fileExisted = true;
		File destFile = null;
		while (fileExisted) 
		{
			destFile = new File(destFilePath);
			if(destFile.exists())
			{
				destFilePath = savePath + generateNewName(fileExt);
			}else {
				fileExisted = false;
			}
		}
		String relativeLink = generateRelativeLink(saveFolder, destFile.getName());
		return new SavedFile(getService().getFileServerDomain(), relativeLink, destFile.getAbsolutePath());
	}
	
	public static String getFolderSavePath(String saveFolder) 
	{
		String savePath = new StringBuilder().append(getService().getFileServerPath())
											 .append("/")
											 .append(getService().getAppName())
											 .append("/")
											 .append(saveFolder)
											 .append("/")
											 .toString();
		File saveDir = new File(savePath);
		if(!saveDir.exists())
		{
			saveDir.mkdirs();
		}
		return savePath;
	}
	
	private static FileService getService()
	{
		if (service == null) 
		{
			try {
				throw new Exception("FileSaverService 尚未初始化，请先调用 init 方法进行初始化");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return service;
	}
	
	
	private static String generateRelativeLink(String saveFolder, String name) 
	{
		String link = "/" + getService().getAppName() + "/" + saveFolder + "/" + name;
		link = link.replace("\\","/");
		link = link.replaceAll("//", "/");
		return link;
	}
	
	
	private static String generateNewName(String fileName) 
	{
		String newName = StringUtil.uniqueStrWithDateTime();
		int index = fileName.lastIndexOf(".");
		String extName = fileName.substring(index);
		 
		return newName + extName;
	}
	
	
	public String getFileServerPath() {
		return fileServerPath;
	}
	
	public String getAppName() {
		return appName;
	}
	
	public String getFileServerDomain() {
		return fileServerDomain;
	}
	
	public static void delete(String saveFolderName, File soft) 
	{
		File folder = getSaveFolder(saveFolderName);
		File destFile = new File(folder.getAbsolutePath() + "/" + soft.getName());
		if(destFile.exists())
		{
			destFile.delete();
		}
	}
	
	private static File getSaveFolder(String saveFolderName)
	{
		String savePath = new StringBuilder().append(getService().getFileServerPath())
											 .append("/")
											 .append(getService().getAppName())
											 .append("/")
											 .append(saveFolderName)
											 .toString();
		File saveDir = new File(savePath);
		if(!saveDir.exists())
		{
			saveDir.mkdir();
		}
		return saveDir;
	}

	
}
