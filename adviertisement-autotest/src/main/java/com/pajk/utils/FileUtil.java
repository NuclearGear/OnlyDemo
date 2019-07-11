package com.pajk.utils;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;

import com.pajk.config.Const;

public class FileUtil {
	
	public static void deleteFile(String fileName) throws Exception {		 
	    File file = new File(fileName);
	    // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
	    if (file.exists() && file.isFile()) {
	        file.delete();
	    } 
	}

	public static void deleteFolder(String folderPath) throws Exception {
		try {
			delAllFile(folderPath); //删除完里面所有内容
			String filePath = folderPath;
			filePath = filePath.toString();
			java.io.File myFilePath = new java.io.File(filePath);
			//myFilePath.delete(); //删除空文件夹
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static boolean delAllFile(String path) {
		boolean flag = false;
		File file = new File(path);
		if (!file.exists()) {
			return flag;
		}
		if (!file.isDirectory()) {
			return flag;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
                    temp.delete();
			}
			if (temp.isDirectory()) {
				    delAllFile(path + "/" + tempList[i]);//先删除文件夹里面的文件
				    flag = true;
			}
		}
		return flag;
	}

	public static boolean isDownloadFileExist(String fileName) throws Exception {	
		String downloadFile = System.getProperty("user.dir") + Const.DOWNLOAD_FOLDER + fileName ;
	    File file = new File(downloadFile);
	    return file.exists()&& file.isFile() && file.length()>0;
	}
	
	public static String getLatestDownloadFile() throws Exception {
		String downloadFilePath = System.getProperty("user.dir") + Const.DOWNLOAD_FOLDER;
		File path = new File(downloadFilePath);
		//列出该目录下所有文件和文件夹
		File[] files = path.listFiles();
		//按照文件最后修改日期倒序排序
		Arrays.sort(files, new Comparator<File>() {
			@Override
			public int compare(File file1, File file2) {
				return (int)(file2.lastModified()-file1.lastModified());
			}
		});
		//取出第一个(即最新修改的)文件
		return files[0].getName();
	} 
	
	public static int countDownloadFolderFileNo() throws Exception {
		String downloadFilePath = System.getProperty("user.dir") + Const.DOWNLOAD_FOLDER;
		File path = new File(downloadFilePath);
		int count=0;
		File[] files = path.listFiles();
		for (int i=0;i<files.length;i++) {
			if (files[i].isFile()) {
				count++;
			}
		}
		return count;
	}

	public static boolean DownloadFileExist() throws Exception {
		try {
			String downloadFile = getLatestDownloadFile();
			return true;
		}catch (Exception e){
			return false;
		}
	}
	public static boolean DownloadFileContain(String fileName) throws Exception {
		try {
			String downloadFile = getLatestDownloadFile();
			if (downloadFile.contains(fileName)){
				return true;}
			else{
				return false;
			}
		}catch (Exception e){
			return false;
		}
	}

	public static void main(String[] args) throws Exception {
		//System.out.println(countDownloadFolderFileNo());
		//FileUtil.delAllFile("./src/test/resources/download/");
        //System.out.println(getLatestDownloadFile());
	}
}
