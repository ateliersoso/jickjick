package atelier.soso.jickjick;

import java.io.File;

public class FileInfo {
	private int id;		//soundfile DB에 담길 테이블 아이디?? key?
	private String key;
	private File file;
	public enum FileType {FOLDER, SOUND_FILE};
	private FileType fileType;
	private boolean checked;
	
	private int playCount;	//재생횟수.
	
	public FileInfo(){
		initiate();
	}
	
	public FileInfo(File fileInfo){
		file = fileInfo;
		if(fileInfo.isDirectory()) {
			fileType = FileType.FOLDER;
		}
		else {
			fileType = FileType.SOUND_FILE;
		}
		
	}



	private void initiate() {
		id=0;
		key = "";
		
		fileType = FileType.SOUND_FILE;
		checked = false;
		playCount=0;
	}
	
	

	public int getPlayCount() {
		return playCount;
	}
	public void setPlayCount(int playCount) {
		this.playCount = playCount;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setFileType(FileType type) {
		this.fileType = type;
	}
	public FileType getFileType()
	{
		return fileType;
	}
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}

//	private String path="";
//	private String name="";	//소리 파일 제목. 
							//Todo 없으면 파일명으로 대체하자.?
//	public String getPath() {
//		return path;
//	}
//	public void setPath(String path) {
//		this.path = path;
//	}
//	public String getName() {
//		return name;
//	}
//	public void setName(String name) {
//		this.name = name;
//	}
//	public FileInfo(int id, String path){
//		this.setId(id);
//		this.path = path;
//	}
	
}
