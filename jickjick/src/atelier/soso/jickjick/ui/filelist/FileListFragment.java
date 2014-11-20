package atelier.soso.jickjick.ui.filelist;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.CheckedTextView;
import android.widget.ListView;
import atelier.soso.jickjick.FileInfo;
import atelier.soso.jickjick.R;
import atelier.soso.jickjick.StateManager;
import atelier.soso.jickjick.file.SoundFileScanner;


public class FileListFragment extends Fragment {
	ListView fileListView = null;
	FileListArrayAdapter adapter = null;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_file_list, container, false);

		StateManager stateManager = (StateManager)getActivity().getApplicationContext();
		String currentPath = stateManager.getCurrentPath().toString();

		ArrayList<FileInfo> fileList = SoundFileScanner.scanDirectoryNSoundFiles(currentPath);

		Log.v("FileInfo", "File List Size = " + String.valueOf( fileList.size()));
		adapter = new FileListArrayAdapter(getActivity(), 0, fileList);

		if(fileListView == null)
		{
			fileListView = (ListView)rootView.findViewById(R.id.filelist);
			fileListView.setAdapter(adapter);

//			fileListView.setItemsCanFocus(false);
//			fileListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
			
			fileListView.setOnItemClickListener(new OnItemClickListener() {
			
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					FileInfo fileInfo = (FileInfo)parent.getItemAtPosition(position);
					//directory면 하위폴더로 보여주기
					if(fileInfo.getFile().isDirectory())
					{
						//해당 경로로 들어가서 폴더 갱신
						String newPath = fileInfo.getFile().getPath();
						refreshFileListView(newPath);
					}
					else
					{
						//파일이면 체크 반전시키기.
						//todo. 디렉토리는.. 롱버튼으로 체크 가능하도록 하자.
						CheckBox selectedBox = (CheckBox)view.findViewById(R.id.fileName);
						selectedBox.setChecked(!selectedBox.isChecked());
					}
					//file이면 check추가  ?
					//check 추가하기


				}

			});
			
			
		}




		return rootView;
	}
	private void refreshFileListView(String newPath) {
		ArrayList<FileInfo> fileList = SoundFileScanner.scanDirectoryNSoundFiles(newPath);
		adapter.clear();
		adapter.addAll(fileList);
		adapter.notifyDataSetChanged();
	}



}
