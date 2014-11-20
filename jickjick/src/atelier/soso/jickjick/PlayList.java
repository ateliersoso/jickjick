package atelier.soso.jickjick;

import java.util.ArrayList;

public class PlayList {
	private ArrayList<FileInfo> items;
	private String name="";	//playlist 의 일
	//		public String createdDate="";
	
	public PlayList()
	{
		items = new ArrayList<FileInfo>();
	}
	public ArrayList<FileInfo> getItems() {
		return items;
	}
	public void setItems(ArrayList<FileInfo> items) {
		this.items = items;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public boolean addItem(FileInfo fileInfo) {
		return items.add(fileInfo);
	}
	
	public void removeAllItems(){
		items.clear();
	}
}
