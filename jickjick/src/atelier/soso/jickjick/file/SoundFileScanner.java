package atelier.soso.jickjick.file;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;

import android.util.Log;
import atelier.soso.jickjick.FileInfo;

public class SoundFileScanner {

	public static ArrayList<FileInfo> scanDirectoryNSoundFiles(String currentPath) {
		ArrayList<FileInfo> fileList;
		fileList = scanDirectory(currentPath, false);
		fileList.addAll(  scanSoundFile(currentPath, false) );
		return fileList;
	}
	
	public static ArrayList<FileInfo> scanSoundFile(String targetPath, boolean searchSubDirectory)
	{
		return scanFile(targetPath, searchSubDirectory, new SoundFileExtensionFilter());
	}
	
	public static ArrayList<FileInfo> scanDirectory(String targetPath, boolean searchSubDirectory)
	{
		return scanFile(targetPath, searchSubDirectory, new DirectoryFilter());
	}
	
	public static ArrayList<FileInfo> scanFile(String targetPath, boolean searchSubDirectory, FilenameFilter fileFilter)
	{
		ArrayList<FileInfo> searchedFileList = new ArrayList<FileInfo>();

		File home = new File(targetPath);
		if(!home.isDirectory())
		{
			home = home.getParentFile();
			Log.v("fileinfo", "parent directory  = " + home.toString() );
		}
		File[] filteredFiles = home.listFiles(fileFilter);
		Log.v("fileinfo", "scanned file list = " + String.valueOf(1));
//		File[] filteredFiles = home.listFiles();
		Log.v("fileinfo", "scanned file list = " + String.valueOf(filteredFiles.length));
		
		if (filteredFiles.length > 0) {
			for (File file : filteredFiles) {

				FileInfo soundfile = new FileInfo();

				soundfile.setFile(file);
				
				searchedFileList.add(soundfile);
			}
		}

		//하위폴더 검색하기.
		return searchedFileList;
		
	}

}


