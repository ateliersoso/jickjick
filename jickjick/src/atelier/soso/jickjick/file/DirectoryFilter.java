package atelier.soso.jickjick.file;

import java.io.File;
import java.io.FilenameFilter;

public class DirectoryFilter implements FilenameFilter{

	@Override
	public boolean accept(File dir, String filename) {
		return dir.isDirectory();
	}

}
