package common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.kin207.zn.AppConst;

/**
 * 文件工具
 * @author wujun
 */
public class FileUtil {

    /**
     * 图片格式后缀
     */
    public static final String[] IMAGE_TYPES = new String[] { ".JPG", ".PNG",
            ".GIF", ".BMP" };

    /**
     * OFFICE软件后缀
     */
    public static final String[] OFFICE_TYPES = new String[] { ".DOC", ".DOCX",
            ".XLS", ".TXT", ".RTF", ".PDF", ".PPT", "PPTX", "XLSX" };

    /**
     * 常用格式（包含图片，Office，压缩格式）
     */
    public static final String[] NORMAL_TYPES = new String[] { ".JPG", ".PNG",
            ".GIF", ".BMP", ".DOC", ".DOCX", ".XLS", ".TXT", ".RTF", ".PDF",
            ".PPT", "PPTX", "XLSX", ".ZIP", ".ZIPX", ".RAR", "7Z" };
    
    /**
     * 数组图片格式后缀
     */
    public static final List<String> arrPicType = Arrays.asList(new String[] { ".jpeg", ".jpg", ".png", ".gif", ".JPEG", ".JPG", ".PNG", ".GIF" });
    
    public static void main(String[] args) {
		System.out.println(File.pathSeparator);
		System.out.println(File.pathSeparatorChar);
		System.out.println(File.separatorChar);
		System.out.println(File.separator);
	}
    
    /**
     * 上传文件
     * @param request 请求
     * @param fileName 文件名
     * @return 数据库中的路径(null 上传失败 ，1没有上传文件，2上传不是图片类型)
     */
    public static String upload(HttpServletRequest request/*, String fileName*/) {
        try {
            MultipartFile file = null;
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            file = multipartRequest.getFile("file");
            if (file == null || file.getSize() == 0) {
                return "1";
            }
            // 取得根目录
            String root = AppConst.EMPLOYEE_PHOTO_PATH;//request.getSession().getServletContext().getRealPath(AppConst.EMPLOYEE_PHOTO_PATH);
            System.out.println(root);
            if(!createFolder(root)){
                return null;
            }
            
            CommonsMultipartFile mf = (CommonsMultipartFile) file;
            String name = mf.getOriginalFilename();
            // 取得后缀
            String postfix = name.substring(name.indexOf("."));
            // 如果不是图片格式
            if (!arrPicType.contains(postfix)) {
                return "2";
            }
            // 保存图片名称
            String str = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + postfix;
            String filename = root + File.separator + str;
            File files = new File(filename);
            mf.getFileItem().write(files);
            String imgurl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()  
                    + (/*request.getContextPath() + */AppConst.EMPLOYEE_PHOTO_PATH + str).replace("//", "/");

            ImageCompressUtil.saveMinPhoto(files.getAbsolutePath(), files.getAbsolutePath(), 8000, 0.5d);//文件缩小
            return imgurl;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 上传文件
     * 
     * @param request
     *            上传文件请求
     * @param path
     *            保存路径（父级文件夹可以不存在）以'/'结尾
     * @param types
     *            可上传类型
     * @param map
     *            KEY：上传文件的name,VALUE:保存文件名，不包括后缀
     * @throws java.io.IOException
     *             文件写入异常
     */
    public static void upload(HttpServletRequest request, String path,
            String[] types, Map<String, Object> map) throws IOException {

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;

        MultipartFile file = null;

        if (!createFolder(path)){
            return;// 创建目录失败
        }
        
        for (Iterator<String> iterator = map.keySet().iterator(); iterator
                .hasNext();) {

            String key = iterator.next();

            file = multipartRequest.getFile(key);

            int point = 0;
            String postfix = "";
            if ((point = file.getOriginalFilename().lastIndexOf(".")) != -1){
                postfix = file.getOriginalFilename().substring(point);
            }
            if (isAllowed(postfix, types)) {

                if (map.get(key) == null){
                    map.put(key, System.currentTimeMillis());
                }
                File f = new File(path + map.get(key) + postfix);

                map.put(key, f);

                file.transferTo(f);

            }

        }

    }

    /**
     * 上传单个文件
     * 
     * @param request
     *            上传文件请求
     * @param path
     *            保存路径（父级文件夹可以不存在）以'/'结尾
     * @param types
     *            可上传类型
     * @param key
     *            上传文件的name
     * @param name
     *            保存文件名，不包括后缀
     * @return 文件
     * @throws java.io.IOException
     *             文件写入异常
     */
    public static File upload(HttpServletRequest request, String path,
            String[] types, String key, String name) throws IOException {

        Map<String, Object> map = new HashMap<String, Object>();
        map.put(key, name);

        upload(request, path, types, map);
        if(map.get(key) instanceof String){
            return null;
        }
        return (File) map.get(key);
    }

    /**
     * 是否允许
     * @param postfix   后缀
     * @param types     类型
     * @return          boolean
     */
    private static boolean isAllowed(String postfix, String... types) {

        if (types == null || types.length == 0){
            return true;
        }
        for (String type : types) {

            if (postfix.equalsIgnoreCase(type)){
                return true;
            }
        }

        return false;

    }

    /**
     * 创建文件夹
     * @param folderPath    路径
     * @return  boolean
     */
    private static boolean createFolder(String folderPath) {
        boolean result = false;
        
        File f = new File(folderPath);
        result = f.exists();
        if (!result) {
            result = f.mkdirs();
        }
        
        return result;
    }


    public static String singletonFileUpLoad(){
        return "";
    }

    /**
     * 上传文件：多个图片
     * @param request   请求
     * @param fileName
     *            文件名
     * @param folder
     *            文件夹
     * @return 数据库中的路径（null 上传失败 ，1没有上传文件，2上传不是图片类型）
     */
    public static List<String> uploads(HttpServletRequest request, String fileName,
                                String folder) {
        try {
            List<MultipartFile> filelist = null;
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            filelist = multipartRequest.getFiles(fileName);
            if (filelist == null || filelist.size() == 0) {
                return null;
            }
            // 取得根目录
            String root = request.getSession().getServletContext().getRealPath("/upload/" + folder)  + "/";
            System.out.println(root);
            if(!createFolder(root)){
                return null;
            }

            List<String> relist = new ArrayList<String>();

            int i = 0;//时间命名文件重复问题：运行太快
            for(MultipartFile file : filelist){
                CommonsMultipartFile mf = (CommonsMultipartFile) file;
                String name = mf.getOriginalFilename();
                // 取得后缀
                String postfix = null;
                postfix = name.substring(name.indexOf(".")).toUpperCase();
                // 如果不是图片格式
                if (!postfix.equals(".JPG") && !postfix.equals(".GIF")
                        && !postfix.equals(".PNG") &&!postfix.equals(".JPEG")) {
                    relist.add("2");
                    return relist;
                }
                // 保存图片名称
                String filename = null;
                String str = new SimpleDateFormat("yyyyMMddHHmmss")
                        .format(new Date())+ (++i) + postfix;
                filename = root + str;
                File files = new File(filename);
                mf.getFileItem().write(files);
                String imgurl = request.getScheme()
                        + "://"
                        + request.getServerName()
                        + ":"
                        + request.getServerPort()
                        + (request.getContextPath() + "/upload/" + folder + "/" + str)
                        .replace("//", "/");

                ImageCompressUtil.saveMinPhoto(files.getAbsolutePath(), files.getAbsolutePath(), 8000, 0.5d);//文件缩小

                relist.add(imgurl);
            }

            return relist;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 上传文件
     * @param request   请求
     * @param fileName
     *            文件名
     * @param folder
     *            文件夹
     * @return 数据库中的路径（null 上传失败 ，1没有上传文件，2上传不是图片类型）
     */
    public static String upload(HttpServletRequest request, String fileName,
            String folder) {
        try {
            MultipartFile file = null;
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            file = multipartRequest.getFile(fileName);
            if (file == null || file.getSize() == 0) {
                return "1";
            }
            // 取得根目录
            String root = request.getSession().getServletContext().getRealPath("/upload/" + folder)  + "/";
            System.out.println(root);
            if(!createFolder(root)){
                return null;
            }
            
            CommonsMultipartFile mf = (CommonsMultipartFile) file;
            String name = mf.getOriginalFilename();
            // 取得后缀
            String postfix = null;
            postfix = name.substring(name.indexOf(".")).toUpperCase();
            // 如果不是图片格式
            if (!postfix.equals(".JPG") && !postfix.equals(".GIF")
                    && !postfix.equals(".PNG") &&!postfix.equals(".JPEG")) {
                return "2";
            }
            // 保存图片名称
            String filename = null;
            String str = new SimpleDateFormat("yyyyMMddHHmmss")
                    .format(new Date()) + postfix;
            filename = root + str;
            File files = new File(filename);
            mf.getFileItem().write(files);
            String imgurl = request.getScheme()
                    + "://"
                    + request.getServerName()
                    + ":"
                    + request.getServerPort()
                    + (request.getContextPath() + "/upload/" + folder + "/" + str)
                            .replace("//", "/");

            ImageCompressUtil.saveMinPhoto(files.getAbsolutePath(), files.getAbsolutePath(), 8000, 0.5d);//文件缩小

            return imgurl;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 不压缩上传文件
     * @param request   请求
     * @param fileName
     *            文件名
     * @param folder
     *            文件夹
     * @return 数据库中的路径（null 上传失败 ，1没有上传文件，2上传不是图片类型）
     */
    public static String notrarupload(HttpServletRequest request, String fileName,
                                String folder) {
        try {
            MultipartFile file = null;
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            file = multipartRequest.getFile(fileName);
            if (file == null || file.getSize() == 0) {
                return "1";
            }
            // 取得根目录
            String root = request.getSession().getServletContext().getRealPath("/upload/" + folder)  + "/";
            System.out.println(root);
            if(!createFolder(root)){
                return null;
            }

            CommonsMultipartFile mf = (CommonsMultipartFile) file;
            String name = mf.getOriginalFilename();
            // 取得后缀
            String postfix = null;
            postfix = name.substring(name.indexOf(".")).toUpperCase();
            // 如果不是图片格式
            if (!postfix.equals(".JPG") && !postfix.equals(".GIF")
                    && !postfix.equals(".PNG") &&!postfix.equals(".JPEG")) {
                return "2";
            }
            // 保存图片名称
            String filename = null;
            String str = new SimpleDateFormat("yyyyMMddHHmmss")
                    .format(new Date()) + postfix;
            filename = root + str;
            File files = new File(filename);
            mf.getFileItem().write(files);
            String imgurl = request.getScheme()
                    + "://"
                    + request.getServerName()
                    + ":"
                    + request.getServerPort()
                    + (request.getContextPath() + "/upload/" + folder + "/" + str)
                    .replace("//", "/");

//            ImageCompressUtil.saveMinPhoto(files.getAbsolutePath(), files.getAbsolutePath(), 8000, 0.5d);//文件缩小

            return imgurl;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 删除文件
     * 
     * @param filename
     *            文件名称
     * @param folder
     *            文件夹
     * @param request   请求
     * @return true 删除成功 false删除失败
     */
    public static boolean deleteFile(String filename, String folder,
            HttpServletRequest request) {
        try {
            String root = request.getSession().getServletContext()
                    .getRealPath("/upload/" + folder)
                    + "/";
            String name = filename.substring(filename.lastIndexOf("/"),
                    filename.length());
            File file = new File(root + name);
            if (file.isFile() && file.exists()) {
                file.delete();
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 下载文件
     * 
     * @param filePath
     *            文件绝对路径
     * @param fileName
     *            下载文件名称
     * @param response  响应
     * @throws java.io.IOException  异常
     */
    public static void downFile(String filePath, String fileName,
            HttpServletResponse response) throws IOException {

        OutputStream os = null;
        InputStream in = null;
        try {
            os = response.getOutputStream();
            response.reset();
            response.setHeader("Content-Disposition", "attachment; filename="
                    + new String(fileName.getBytes("UTF-8"), "ISO-8859-1"));
            response.setContentType("application/octet-stream; charset=UTF-8");

            in = new FileInputStream(filePath);
            byte[] b = new byte[in.available()];

            in.read(b);
            os.write(b);
            os.flush();
        } finally {
            if (os != null)
                os.close();
            if (in != null)
                in.close();
        }
    }
    
    
    /**
     * @param request
     * @param fileName
     * @param folder
     * @return
     */
    public static String uploadPdf(HttpServletRequest request, String fileName,
            String folder) {
        try {
            MultipartFile file = null;
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            file = multipartRequest.getFile(fileName);
            if (file == null || file.getSize() == 0) {
                return "1";
            }
            // 取得根目录
            String root = request.getSession().getServletContext()
                    .getRealPath("/upload/" + folder)
                    + "/";
            
            if(!createFolder(root)){
                return null;
            }
            
            CommonsMultipartFile mf = (CommonsMultipartFile) file;
            String name = mf.getOriginalFilename();
            // 取得后缀
            String postfix = null;
            postfix = name.substring(name.indexOf(".")).toUpperCase();
            // 如果不是图片格式
            if (!postfix.equals(".PDF") ){
                return "2";
            }
            // 保存图片名称
            String filename = null;
            String str = new SimpleDateFormat("yyyyMMddHHmmss")
                    .format(new Date()) + postfix;
            filename = root + str;
            File files = new File(filename);
            mf.getFileItem().write(files);
            String imgurl = request.getScheme()
                    + "://"
                    + request.getServerName()
                    + ":"
                    + request.getServerPort()
                    + (request.getContextPath() + "/upload/" + folder + "/" + str)
                            .replace("//", "/");
            return imgurl;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 页面上传文件
     * @param request
     * @param file
     */
    public static String transferFile(HttpServletRequest request,MultipartFile file){
        String folder = "vehicle";
        String path = request.getSession().getServletContext().getRealPath("/upload" + File.separator + folder) + File.separator;
        String fileName = file.getOriginalFilename();
        System.out.println(path);
        File targetFile = new File(path, fileName);
        if(!targetFile.exists()){
            targetFile.mkdirs();
        }
        //保存
        try {
            file.transferTo(targetFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String returnStr = folder + File.separator + fileName;
        return returnStr;
    }

    /**
     * 上传文件
     * @param request   请求
     * @param fileName
     *            文件名
     * @param folder  文件夹
     */
    public static String uploadXls(HttpServletRequest request, String fileName, String folder) {
        try {
            MultipartFile file = null;
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            file = multipartRequest.getFile(fileName);
            if (file == null || file.getSize() == 0) {
                return "1";
            }
            // 取得根目录
            String root = request.getSession().getServletContext().getRealPath("upload" + File.separator + folder)  + File.separator;
            System.out.println(root);
            if(!createFolder(root)){
                return null;
            }

            CommonsMultipartFile mf = (CommonsMultipartFile) file;
            String name = mf.getOriginalFilename();
            // 取得后缀
            String postfix = null;
            postfix = name.substring(name.indexOf(".")).toLowerCase();
            // 如果不是图片格式
            if (!postfix.equals(".xls")) {
                return "2";
            }
            // 保存图片名称
            String filename = null;
            String str = new SimpleDateFormat("yyyyMMddHHmmss") .format(new Date()) + postfix;
            filename = root + str;
            File files = new File(filename);
            mf.getFileItem().write(files);
            String imgurl = "upload" + File.separator + folder + File.separator + str;
            return imgurl;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}


