package atelier.soso.jickjick.ui.filelist;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import atelier.soso.jickjick.FileInfo;
import atelier.soso.jickjick.R;
import atelier.soso.jickjick.StateManager;
import atelier.soso.jickjick.file.SoundFileScanner;


public class FileListFragment extends Fragment {
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_file_list, container, false);

		ArrayList<FileInfo> fileList = new ArrayList<FileInfo>();
		
		StateManager stateManager = (StateManager)getActivity().getApplicationContext();
		
		String currentPath = stateManager.getCurrentPath().toString();
		Log.v("FileInfo", "CurrentPath = " + currentPath);
		fileList = SoundFileScanner.scanDirectory(currentPath, false);
		Log.v("FileInfo", "File List Size = " + String.valueOf( fileList.size()));
		fileList.addAll(  SoundFileScanner.scanSoundFile(currentPath, false) );
		
		Log.v("FileInfo", "File List Size = " + String.valueOf( fileList.size()));
		FileListArrayAdapter adapter = new FileListArrayAdapter(getActivity(), 0, fileList);
		
		ListView fileListView = (ListView)rootView.findViewById(R.id.filelist);
		fileListView.setAdapter(adapter);
		
//		fileListView.setOnItemClickListener(new OnItemClickListener(){
//			
//		});
		return rootView;
	}
	

}
