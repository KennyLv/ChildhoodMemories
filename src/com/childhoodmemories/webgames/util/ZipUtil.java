package com.childhoodmemories.webgames.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ZipUtil {
	public static void unzip(File target, File dest){
		try {
			if(!dest.exists()){
				dest.mkdir();
			}
			ZipInputStream zis = new ZipInputStream(new FileInputStream(target));
			ZipEntry nextEntry = null;
			while((nextEntry = zis.getNextEntry())!=null){
				String name = nextEntry.getName();
				File file = new File(dest,name);
				if(nextEntry.isDirectory()){
					file.mkdirs();
				}else{
					File parentFolder = file.getParentFile();
					if(!parentFolder.exists()){
						parentFolder.mkdirs();
					}
					file.createNewFile();
					FileOutputStream fos = new FileOutputStream(file);
					byte[] b = new byte[1024];
					int count = 0;
					while((count = zis.read(b))!=-1){
						fos.write(b,0,count);
					}
					fos.flush();
					fos.close();
				}
			}
			zis.closeEntry();
			zis.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
