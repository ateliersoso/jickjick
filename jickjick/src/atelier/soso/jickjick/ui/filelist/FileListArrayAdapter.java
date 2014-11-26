package atelier.soso.jickjick.ui.filelist;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import atelier.soso.jickjick.FileInfo;
import atelier.soso.jickjick.R;


public class FileListArrayAdapter extends ArrayAdapter<FileInfo>{

	private final Context context;
	private final ArrayList<FileInfo> values;

	private static class ViewHolder {
		public final CheckBox fileItemView;

		public ViewHolder(CheckBox fileItem){
			this.fileItemView = fileItem;
		}
	}

	public FileListArrayAdapter(Context context, int resource,
			ArrayList<FileInfo> objects) {
		super(context, resource, objects);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.values = objects;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		CheckBox checkBoxView;

		if(convertView == null)
		{	
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.file_list_item, parent, false);

			checkBoxView = (CheckBox) convertView.findViewById(R.id.fileName);
			checkBoxView.setClickable(false);
			checkBoxView.setFocusable(false);
			holder = new ViewHolder(checkBoxView);
			
			convertView.setTag(holder);
		}
		else
		{
			holder = (ViewHolder) convertView.getTag();
			checkBoxView = holder.fileItemView;
		}

		FileInfo item = values.get(position);

		checkBoxView.setText(item.getFile().getName());

		return convertView;
	}
}

