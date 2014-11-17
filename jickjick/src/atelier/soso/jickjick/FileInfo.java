package atelier.soso.jickjick;

import java.io.File;

public class FileInfo {
	private int id=0;		//soundfile DB에 담길 테이블 아이디?? key?
	private String path="";
	private String name="";	//소리 파일 제목. 
							//Todo 없으면 파일명으로 대체하자.?
	private String key="";
	private File file;
	public enum FileType {FOLDER, SOUND_FILE};

	private FileType fileType;

	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public FileInfo(){
		
	}
	public FileInfo(int id, String path){
		this.setId(id);
		this.path = path;
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
}
