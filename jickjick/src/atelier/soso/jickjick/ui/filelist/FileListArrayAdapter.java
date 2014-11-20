package atelier.soso.jickjick.ui.filelist;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import atelier.soso.jickjick.FileInfo;
import atelier.soso.jickjick.R;

public class FileListArrayAdapter extends ArrayAdapter<FileInfo>{

	private final Context context;
	private final ArrayList<FileInfo> values;

	public FileListArrayAdapter(Context context, int resource,
			ArrayList<FileInfo> objects) {
		super(context, resource, objects);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.values = objects;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		View rowView = inflater.inflate(R.layout.file_list_item, parent, false);
		
		FileInfo item = values.get(position);
		
		//directory 그리기 
//		if(item.getFile().isDirectory())
//		{
			CheckBox checkBoxView = (CheckBox) rowView.findViewById(R.id.fileName);
			//checkbox view가 클릭과 포커스 이벤트를 먹으면 listview의 onitemclicklistner를 타지않음.
			//이벤트 처리를 listview로 넘기자.
			checkBoxView.setClickable(false);
			checkBoxView.setFocusable(false);
 
			checkBoxView.setText(item.getFile().getName());
			
			//		}
		
		return rowView;
	}
}

