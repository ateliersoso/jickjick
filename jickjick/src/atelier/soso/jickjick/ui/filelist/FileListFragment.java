package atelier.soso.jickjick.ui.filelist;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import atelier.soso.jickjick.FileInfo;
import atelier.soso.jickjick.PlayList;
import atelier.soso.jickjick.R;
import atelier.soso.jickjick.StateManager;
import atelier.soso.jickjick.file.SoundFileScanner;


public class FileListFragment extends Fragment implements OnClickListener{
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		
	}

	ListView fileListView = null;
	FileListArrayAdapter adapter = null;
	StateManager stateManager = null;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_file_list, container, false);

		stateManager = (StateManager)getActivity().getApplicationContext();
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
						boolean checked = !selectedBox.isChecked();	
						selectedBox.setChecked(checked);
						fileInfo.setChecked(checked);
					}
					//file이면 check추가  ?
					//check 추가하기


				}

			});


		}


        Button b = (Button) rootView.findViewById(R.id.ButtonPlay);
        b.setOnClickListener(this);
        
		return rootView;
	}
	private void refreshFileListView(String newPath) {
		ArrayList<FileInfo> fileList = SoundFileScanner.scanDirectoryNSoundFiles(newPath);
		adapter.clear();
		adapter.addAll(fileList);
		adapter.notifyDataSetChanged();
	}


	/**	선택된 파일을 플레이 리스트에 저장하는 함수.
	 * @param view
	 * @return
	 */
	public int playSelectedItem(View view) {
		int result = 0;

		//1.선택된 파일 뽑
		ArrayList<FileInfo> selectedFile = new ArrayList<FileInfo>();

		for(int i = 0; i < adapter.getCount(); ++i)
		{
			FileInfo fileinfo = adapter.getItem(i);

			if(fileinfo.isChecked())
			{
				selectedFile.add(fileinfo);
			}
		}

		//2. 파일 담기
		//2.1. 현재 재생목록에 담기
		PlayList currentPlayList = stateManager.getCurrentPlayList();
		currentPlayList.getItems().addAll(selectedFile);

		//3. play뷰로 돌아가서 바로 재생.
		//filelist fragment pop
		getActivity().getSupportFragmentManager().popBackStack();
		//todo. 재생위치 결정. 지금은 처음부터 재생.
		playSoundListener.onPlaySoundListner(0);

		return result;
	}

	public interface OnPlaySoundListener {
		public void onPlaySoundListner(int playOffset);
	}

	private OnPlaySoundListener playSoundListener;
	
	@Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
        	playSoundListener = (OnPlaySoundListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnArticleSelectedListener");
        };
    }
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId())
		{
		case R.id.ButtonPlay:
			playSelectedItem(v);
			break;
		}
	}
	

}
