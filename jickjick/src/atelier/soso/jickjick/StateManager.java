package atelier.soso.jickjick;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

import android.app.Application;

public class StateManager extends Application {
	public enum PlayerState {IDLE, WAIT_B, REPEAT_AB};
	private PlayerState playerState;

	private boolean isLoop;
	private boolean isABRepeatMode;

	private ArrayList<ABRepeat> abRepeatList;
	private ABRepeat currentABRepeat;

	private File currentPath;			//마지막으로 이용자가 접근한 path

	private ArrayList<FileInfo> fileList;
	private PlayList currentPlayList;	//

	private int currentPosition;

	private int currentABRepeatPosition;
	
	public StateManager() {
		abRepeatList = new ArrayList<ABRepeat>();
		currentABRepeat = new ABRepeat(0, 0);
		currentPath = null;
		fileList = new ArrayList<FileInfo>();
		currentPlayList = new PlayList();
		currentPosition = 0;
	} 
	
	public boolean isLoop() {
		return isLoop;
	}
	public void setLoop(boolean isLoop) {
		this.isLoop = isLoop;
	}
	public boolean isABRepeatMode() {
		return isABRepeatMode;
	}
	public void setABRepeatMode(boolean isABRepeatMode) {
		this.isABRepeatMode = isABRepeatMode;
	}
	public PlayerState getPlayerState() {
		return playerState;
	}
	public void setPlayerState(PlayerState playerState) {
		this.playerState = playerState;
	}
	public ArrayList<ABRepeat> getAbRepeatList() {
		return abRepeatList;
	}
	public void setAbRepeatList(ArrayList<ABRepeat> abRepeatList) {
		this.abRepeatList = abRepeatList;
	}
	public ABRepeat getCurrentABRepeat() {
		return currentABRepeat;
	}
	public void setCurrentABRepeat(ABRepeat currentABRepeat) {
		this.currentABRepeat = currentABRepeat;
	}
	public ArrayList<FileInfo> getFileList() {
		return fileList;
	}
	public void setFileList(ArrayList<FileInfo> fileList) {
		this.fileList = fileList;
	}
	public PlayList getCurrentPlayList() {
		return currentPlayList;
	}
	public void setCurrentPlayList(PlayList currentPlayList) {
		this.currentPlayList = currentPlayList;
	}
	public File getCurrentPath() {
		return currentPath;
	}
	public void setCurrentPath(File externalStoragePath) {
		this.currentPath = externalStoragePath;
	}
	public int getCurrentPosition() {
		// TODO Auto-generated method stub
		return currentPosition;
	}

	public void setCurrentPosition(int position)
	{
		currentPosition = position;
	}

	public boolean addABRepeat(ABRepeat abRepeat) {
		boolean result =abRepeatList.add(abRepeat);
		//추가에 따라 abrepeat 추가 구현.
		if(result)
		{
			Collections.sort(abRepeatList, new ABRepeatCompre());
		}
		//current index 갱신.
		setCurrentABRepeatPosition(abRepeatList.indexOf(abRepeat));
		return result; 
	}

	public void setCurrentABRepeatPosition(int position)
	{
		currentABRepeatPosition = position;
	}
	
	public int getCurrentABRepeatPosition() {
		return currentABRepeatPosition;
	}

	public void setCurrentA(int currentA) {
		currentABRepeat.setStart( currentA );
		
	}

	public void setCurrentB(int currentB) {
		currentABRepeat.setEnd( currentB );
	}

}
