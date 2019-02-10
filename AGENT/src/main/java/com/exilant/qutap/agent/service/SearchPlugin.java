package com.exilant.qutap.agent.service;

import java.io.File;
import java.io.FilenameFilter;

public class SearchPlugin {

	private static boolean foundFolder = false;
	
	
	
	public static String[] getAllDirectories(String path)
	{
		File file = new File(path);
		String[] directories = file.list(new FilenameFilter() {
		  @Override
		  public boolean accept(File current, String name) {
			  System.out.println("searchpath:::::"+current);
			  System.out.println("searchpath:Name::::"+name);
		    return new File(current, name).isDirectory();
		  }
		});
	  return directories;

	}


//	public static boolean findDirectory(File parentDirectory,String dir) {
//
//		try
//		{
//		File[] files = parentDirectory.listFiles();
//		for (File file : files) {
//			if (file.isFile()) {
//				continue;
//			}
//			if (file.getName().equals(dir)) {
//				foundFolder = true;
//				break;
//			}
//			if (file.isDirectory()) {
//				findDirectory(file,dir);
//			}
//		}
//		if (foundFolder) {
//			return true;
//		}
//		}catch(Exception e)
//		{
//			e.printStackTrace();
//		}
//		return foundFolder;
//	}
//	
	

}
