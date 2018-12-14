package common.utils;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.imageio.ImageIO;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class ImageCompressUtil {
	/**
	 * 直接指定压缩后的宽高：
	 * (先保存原文件，再压缩、上传)
	 * 壹拍项目中用于二维码压缩
	 * @param oldFile 要进行压缩的文件全路径
	 * @param width 压缩后的宽度
	 * @param height 压缩后的高度
	 * @param quality 压缩质量
	 * @param smallIcon 文件名的小小后缀(注意，非文件后缀名称),入压缩文件名是yasuo.jpg,则压缩后文件名是yasuo(+smallIcon).jpg
	 * @return 返回压缩后的文件的全路径
	 */
	public static String zipImageFile(String oldFile, int width, int height,
			float quality, String smallIcon) {
		if (oldFile == null) {
			return null;
		}
		String newImage = null;
		try {
			/**对服务器上的临时文件进行处理 */
			Image srcFile = ImageIO.read(new File(oldFile));
			/** 宽,高设定 */
			BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			tag.getGraphics().drawImage(srcFile, 0, 0, width, height, null);
			String filePrex = oldFile.substring(0, oldFile.indexOf('.'));
			/** 压缩后的文件名 */
			newImage = filePrex + smallIcon	+ oldFile.substring(filePrex.length());
			/** 压缩之后临时存放位置 */
			FileOutputStream out = new FileOutputStream(newImage);
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
			JPEGEncodeParam jep = JPEGCodec.getDefaultJPEGEncodeParam(tag);
			/** 压缩质量 */
			jep.setQuality(quality, true);
			encoder.encode(tag, jep);
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return newImage;
	}
	
	/**
	 * 缩略图
	 * @param srcURL 原图地址
	 * @param deskURL 缩略图地址
	 * @param width 图片宽度
	 * @param height 图片高度
	 */
	public static void saveMinPhoto(String srcURL, String deskURL, int width, int height) throws Exception {
		/**对服务器上的临时文件进行处理 */
		Image srcFile = ImageIO.read(new File(srcURL));
		/** 宽,高设定 */
		BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		tag.getGraphics().drawImage(srcFile, 0, 0, width, height, null); //绘制缩小后的图
		FileOutputStream deskImage = new FileOutputStream(deskURL); //输出到文件流
		JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(deskImage);
		encoder.encode(tag); //近JPEG编码
		deskImage.close();
	}


	/**
	 * 等比例压缩算法： 
	 * 算法思想：根据压缩基数和压缩比来压缩原图，生产一张图片效果最接近原图的缩略图
	 * @param srcURL 原图地址
	 * @param deskURL 缩略图地址
	 * @param comBase 压缩基数
	 * @param scale 压缩限制(宽/高)比例  一般用1：
	 * 当scale>=1,缩略图height=comBase,width按原图宽高比例;若scale<1,缩略图width=comBase,height按原图宽高比例
	 */
	public static void saveMinPhoto(String srcURL, String deskURL, double comBase,
			double scale) throws Exception {
		File srcFile = new java.io.File(srcURL);
		Image src = ImageIO.read(srcFile);
		int srcHeight = src.getHeight(null);
		int srcWidth = src.getWidth(null);
		int deskHeight = 0;// 缩略图高
		int deskWidth = 0;// 缩略图宽
		double srcScale = (double) srcHeight / srcWidth;
		/**缩略图宽高算法*/
		if ((double) srcHeight > comBase || (double) srcWidth > comBase) {
			if (srcScale >= scale || 1 / srcScale > scale) {
				if (srcScale >= scale) {
					deskHeight = (int) comBase;
					deskWidth = srcWidth * deskHeight / srcHeight;
				} else {
					deskWidth = (int) comBase;
					deskHeight = srcHeight * deskWidth / srcWidth;
				}
			} else {
				if ((double) srcHeight > comBase) {
					deskHeight = (int) comBase;
					deskWidth = srcWidth * deskHeight / srcHeight;
				} else {
					deskWidth = (int) comBase;
					deskHeight = srcHeight * deskWidth / srcWidth;
				}
			}
		} else {
			deskHeight = srcHeight;
			deskWidth = srcWidth;
		}
		BufferedImage tag = new BufferedImage(deskWidth, deskHeight, BufferedImage.TYPE_3BYTE_BGR);
		tag.getGraphics().drawImage(src, 0, 0, deskWidth, deskHeight, null); //绘制缩小后的图
		FileOutputStream deskImage = new FileOutputStream(deskURL); //输出到文件流
		JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(deskImage);
		encoder.encode(tag); //近JPEG编码
		deskImage.close();
	}
	
	/**
	 * 通过给定网络状态,图片最大值及服务器访问图片URL压缩图片后新成新的服务器URL路径后返回
	 * @param network 网络状态(2G/3G/4G/wifi)
	 * @param picSize 图片最大宽度
	 * @param srcURL 服务器访问图片URL地址
	 * @param destPath 服务器目标地址
	 * @return 图片压缩后的新URL地址
	 * @throws Exception
	 */
	public static synchronized String compressPicPath(String network,String picSize,String srcURL,String destPath) throws Exception{
		//String homeUrl = "http://www.yoyo-group.com";
		//srcURL = homeUrl + srcURL;
		picSize = picSize.replaceAll("X", "x");
		destPath = destPath + picSize + "/";
		File fileDir = new File(destPath);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        String fileName = srcURL.substring(srcURL.lastIndexOf("/")+1);
        destPath = destPath + fileName;
        File destDir = new File(destPath);
        if(destDir.exists()){
        	//return destPath;
        	//return homeUrl + destPath;
        	return destPath;
        }
        //ImageCompressUtil.saveToFile(srcURL, destPath);
        String[] widthHeight = picSize.split("x");
        if(widthHeight.length == 2){
        	//ImageCompressUtil.zipImageFile(destPath, Integer.parseInt(widthHeight[0]), Integer.parseInt(widthHeight[1]), 1f, "");	
        	/* 根据要求,图片放大1.5倍,便于图片放大后的清晰度效果 */
        	//ImageCompressUtil.zipImageFile(srcURL, (int)(Integer.parseInt(widthHeight[0])*1.5), (int)(Integer.parseInt(widthHeight[1])*1.5), 1f, "");	
        	ImageCompressUtil.saveMinPhoto(srcURL, destPath, (int)(Integer.parseInt(widthHeight[0])*1.5), (int)(Integer.parseInt(widthHeight[1])*1.5));
        }else if(widthHeight.length == 1){
        	//ImageCompressUtil.saveMinPhoto(destPath, destPath, Integer.parseInt(widthHeight[0]), 0.9d);
        	/* 根据要求,图片放大1.5倍,便于图片放大后的清晰度效果 */
        	ImageCompressUtil.saveMinPhoto(srcURL, destPath, (int)(Integer.parseInt(widthHeight[0])*1.5), 0.9d);
        }
			//return destPath;
	        //return homeUrl + destPath;
	    	return destPath;
	}

	public static void main(String[] args) throws Exception {
		///**
		Thread t1 = new Thread(){
			public void run(){
				try {
					//String destPath = ImageCompressUtil.compressPicPath("", "480X320", "/data/yotrip/pictures/productpackage/1435204556587.jpg", "E:/aaa/");
					String destPath = ImageCompressUtil.compressPicPath("", "480X320", "E:/img27.jpg", "E:/aaa/");
					System.out.println(destPath);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		Thread t2 = new Thread(){
			public void run(){
				try {
					//String destPath = ImageCompressUtil.compressPicPath("", "320x240", "/data/yotrip/pictures/productpackage/1435204556587.jpg", "E:/aaa/");
					String destPath = ImageCompressUtil.compressPicPath("", "320x240", "E:/img27.jpg", "E:/aaa/");
					System.out.println(destPath);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		t1.start();
		t2.start();
		//*/
	}
	
	public static void saveToFile(String destUrl,String destPath) throws Exception {  
		FileOutputStream fos = null;
		BufferedInputStream bis = null;
		HttpURLConnection httpUrl = null;
		URL url = null;
		int BUFFER_SIZE = 1024;
		byte[] buf = new byte[BUFFER_SIZE];
		int size = 0;
		try {
			url = new URL(destUrl);
			httpUrl = (HttpURLConnection) url.openConnection();
			httpUrl.connect();
			bis = new BufferedInputStream(httpUrl.getInputStream());
			fos = new FileOutputStream(destPath);
			while ((size = bis.read(buf)) != -1) {
				fos.write(buf, 0, size);
			}
			fos.flush();
		} catch (IOException e) {
		} catch (ClassCastException e) {
		} finally {
			try {
				fos.close();
				bis.close();
				httpUrl.disconnect();
			} catch (IOException e) {
			} catch (NullPointerException e) {
			}
		}
	}
	
	/**
	 * 保存文件到服务器临时路径(用于文件上传)
	 * @param fileName
	 * @param is
	 * @return 文件全路径
	 */
	public static String writeFile(String fileName, InputStream is) {
		if (fileName == null || fileName.trim().length() == 0) {
			return null;
		}
		try {
			/** 首先保存到临时文件 */
			FileOutputStream fos = new FileOutputStream(fileName);
			byte[] readBytes = new byte[512];// 缓冲大小
			int readed = 0;
			while ((readed = is.read(readBytes)) > 0) {
				fos.write(readBytes, 0, readed);
			}
			fos.close();
			is.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fileName;
	}
	
	/**
     * 缩放图像（按高度和宽度缩放）
     * @param srcImageFile 源图像文件地址
     * @param result 缩放后的图像地址
     * @param height 缩放后的高度
     * @param width 缩放后的宽度
     * @param bb 比例不对时是否需要补白：true为补白; false为不补白;
     */
    @SuppressWarnings("static-access")
	public static void scale2(String srcImageFile, String result, int height, int width, boolean bb) {
        try {
            double ratio = 0.0; // 缩放比例
            File f = new File(srcImageFile);
            BufferedImage bi = ImageIO.read(f);
			Image itemp = bi.getScaledInstance(width, height, bi.SCALE_SMOOTH);
            // 计算比例
            if ((bi.getHeight() > height) || (bi.getWidth() > width)) {
                if (bi.getHeight() > bi.getWidth()) {
                    ratio = (new Integer(height)).doubleValue()
                            / bi.getHeight();
                } else {
                    ratio = (new Integer(width)).doubleValue() / bi.getWidth();
                }
                AffineTransformOp op = new AffineTransformOp(AffineTransform
                        .getScaleInstance(ratio, ratio), null);
                itemp = op.filter(bi, null);
            }else{
            	return;
            }
            if (bb) {//补白
                BufferedImage image = new BufferedImage(width, height,
                        BufferedImage.TYPE_INT_RGB);
                Graphics2D g = image.createGraphics();
                g.setColor(Color.white);
                g.fillRect(0, 0, width, height);
                if (width == itemp.getWidth(null))
                    g.drawImage(itemp, 0, (height - itemp.getHeight(null)) / 2,
                            itemp.getWidth(null), itemp.getHeight(null),
                            Color.white, null);
                else
                    g.drawImage(itemp, (width - itemp.getWidth(null)) / 2, 0,
                            itemp.getWidth(null), itemp.getHeight(null),
                            Color.white, null);
                g.dispose();
                itemp = image;
            }
            ImageIO.write((BufferedImage) itemp, "JPEG", new File(result));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
