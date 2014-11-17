package atelier.soso.jickjick.file;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;

import android.util.Log;
import atelier.soso.jickjick.FileInfo;

public class SoundFileScanner {

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
				if(file.isDirectory())
				{
					soundfile.setName(file.getName().substring(0, (file.getName().length())));
				}
				else
				{
					soundfile.setName(file.getName().substring(0, (file.getName().length() - 4)));
				}
				soundfile.setPath(file.getPath());
				soundfile.setFile(file);
//				//todo. 이부분을 밖으로 빼야 안심.
//				if(file.isDirectory())
//				{
//					soundfile.setFileType(FileType.FOLDER);
//				}
//				else
//				{
//					soundfile.setFileType(FileType.SOUND_FILE);
//				}
				
				searchedFileList.add(soundfile);
			}
		}

		//하위폴더 검색하기.
		return searchedFileList;
		
	}

}


