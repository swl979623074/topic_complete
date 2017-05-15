package com.topic.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.topic.db.OperateJDBC;
import com.topic.tool.ImgOperation;
import com.topic.tool.MVC;

@Controller
@RequestMapping("/imgUploadController")
public class ImgUploadController {
	@RequestMapping("/fileUpload")
	public ModelAndView fileUpload(@RequestParam("file") MultipartFile file,
			HttpServletRequest req) {
//		String eclipseRootPath = System.getProperty("user.dir");
//		String rootPath = eclipseRootPath.substring(0,
//				eclipseRootPath.lastIndexOf("\\"));
//		URL realPath = this.getClass().getResource("/");
//		String projectName = realPath.getPath().substring(
//				realPath.getPath().indexOf("webapps") + 8,
//				realPath.getPath().indexOf("WEB-INF") - 1);
//		String saveBasePath = rootPath + "\\" + projectName + "Config\\users";

		String userAccount = req.getQueryString();

		String saveBasePath = "C:\\Users\\Administrator\\Desktop\\TopicUsersimg";
		Map<String, Object> map = new HashMap<String, Object>();
		// 判断文件是否为空
		if (!file.isEmpty()) {
			try {
				String originName = file.getOriginalFilename();
				String[] strs = originName.split("\\.");
				String newName = userAccount + "." + strs[strs.length - 1];
				// 文件保存路径
				String filePath = saveBasePath + "\\" + newName;
				File targetFile = new File(filePath);
				if (!targetFile.exists()) {
					targetFile.mkdirs();
				}
				// 转存文件
				file.transferTo(new File(filePath));

				String type = file.getContentType();
				String header = "data:" + type + ";base64,";
				map.put("binaryImg",
						header + ImgOperation.getImageBinary(filePath));
				map.put("status", "SUCCESS");

				updateUserImg(filePath.replaceAll("\\\\", "/"), userAccount);
			} catch (Exception e) {
				e.printStackTrace();
				map.put("status", "FALSE");
				map.put("msg", "Exception");
			}
		} else {
			map.put("status", "FALSE");
			map.put("msg", "file is not exist");
		}
		return MVC.toString(map);
	}

	private void updateUserImg(String savePath, String userAccount) {
		String sql_update_user = "update user set user_photo='" + savePath
				+ "' where user_account='" + userAccount + "'";
		OperateJDBC OJDBC = new OperateJDBC();
		OJDBC.executeUpdate(sql_update_user);
		return;
	}
}
