package com.topic.tool;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class ImgOperation {
	static BASE64Encoder encoder = new sun.misc.BASE64Encoder();
	static BASE64Decoder decoder = new sun.misc.BASE64Decoder();

	public static String getImageBinary(String Imgpath) {
		File f = new File(Imgpath);
		String imgType = Imgpath.split("\\.")[1];
		BufferedImage bi;
		try {
			bi = ImageIO.read(f);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(bi, imgType, baos);
			byte[] bytes = baos.toByteArray();
			return encoder.encodeBuffer(bytes).trim();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void base64StringToImage(String savePath, String base64String) {
		String imgType = savePath.split("\\.")[1];
		try {
			byte[] bytes1 = decoder.decodeBuffer(base64String);

			ByteArrayInputStream bais = new ByteArrayInputStream(bytes1);
			BufferedImage bi1 = ImageIO.read(bais);

			File w2 = new File(savePath);// 可以是jpg,png,gif格式

			ImageIO.write(bi1, imgType, w2);// 不管输出什么格式图片，此处不需改动
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
//		String url = "C:\\Users\\SWL\\Desktop\\showView\\成绩.png";
//		String savePath = "C:\\Users\\SWL\\Desktop\\showView\\test.png";
//		base64StringToImage(savePath,getImageBinary(url));
//		String data = "data:image/" + imgType + ";base64,"
//		+ encoder.encodeBuffer(bytes).trim();
	}
}
