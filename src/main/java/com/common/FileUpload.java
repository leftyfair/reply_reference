package com.common;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;

public class FileUpload {
	
	private static final String REPO_PATH = "c:/file_repo/";
	private String servicePathName;
	
	public FileUpload(String servicePathName) {
		this.servicePathName = servicePathName;
	}

	public Map<String, String> getMultipartRequest(HttpServletRequest request) {
		Map<String, String> paramMap = new HashMap<>();
		File currentDirPath = new File(REPO_PATH + servicePathName + "temp");
		
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload repository = new ServletFileUpload(factory);
		repository.setFileSizeMax(1024 * 1024 * 10);
		
		try {
			List<FileItem> items = repository.parseRequest(request);
			for(FileItem item : items) {
				if(item.isFormField()) {
					paramMap.put(item.getFieldName(), item.getString("utf-8"));
				} else {
					if(item.getSize() > 0) {
						String fileName = item.getName();
						paramMap.put(item.getFieldName(), fileName);
						File uploadFile = new File(currentDirPath, fileName);
						item.write(uploadFile);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return paramMap;
	}
	
	// 새로운 이미지 업로드
	public void uploadImage(int no, String imageFileName) throws IOException {
		File srcFile = new File(REPO_PATH + servicePathName + "temp", imageFileName);
		File destFile = new File(REPO_PATH + servicePathName + no);
		destFile.mkdirs();
		FileUtils.moveFileToDirectory(srcFile, destFile, false);
	}
	
	// 기존 이미지 삭제
	public void deleteOriginImage(String no, String originFileName) {
		File oldFile = new File(REPO_PATH + servicePathName + no + "/" + originFileName);
		oldFile.delete();
	}
	
	// 모든 이미지 삭제
	public void deleteAllImage(int no) throws IOException {
		File targetDir = new File(REPO_PATH + servicePathName + no);
		if(targetDir.exists()) {
			FileUtils.deleteDirectory(targetDir);
		}
	}
}
