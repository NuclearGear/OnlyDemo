package com.winmax.utils;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;

import com.winmax.config.Const;

public class FileUtil {
	
	public static void deleteFile(String fileName) throws Exception {		 
	    File file = new File(fileName);
	    // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
	    if (file.exists() && file.isFile()) {
	        file.delete();
	    } 
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
	
	public static void main(String[] args) throws Exception {
		System.out.println(countDownloadFolderFileNo());
	}
}
