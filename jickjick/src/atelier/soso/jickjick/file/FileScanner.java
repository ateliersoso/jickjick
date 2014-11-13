package atelier.soso.jickjick.file;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;

import atelier.soso.jickjick.FileInfo;

public class FileScanner {

	public ArrayList<FileInfo> scanFile(String targetPath, boolean searchSubDirectory)
	{
		return scanFile(targetPath, searchSubDirectory, new SoundFileExtensionFilter());
	}
	
	public ArrayList<FileInfo> scanDirectory(String targetPath, boolean searchSubDirectory)
	{
		return scanFile(targetPath, searchSubDirectory, new DirectoryFilter());
	}
	
	public ArrayList<FileInfo> scanFile(String targetPath, boolean searchSubDirectory, FilenameFilter soundFileExtensionFilter)
	{
		ArrayList<FileInfo> searchedFileList = new ArrayList<FileInfo>();

		File home = new File(targetPath);

		File[] filteredFiles = home.listFiles(soundFileExtensionFilter);

		if (filteredFiles.length > 0) {
			for (File file : filteredFiles) {

				FileInfo soundfile = new FileInfo();
				soundfile.setName(file.getName().substring(0, (file.getName().length() - 4)));
				soundfile.setPath(file.getPath());file.getPath();

				searchedFileList.add(soundfile);
			}
		}

		//하위폴더 검색하기.
		return searchedFileList;
		
	}

}


