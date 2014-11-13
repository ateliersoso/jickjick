package atelier.soso.jickjick;

import java.util.ArrayList;

import android.app.Application;

public class StateManager extends Application {
	public enum PlayerState {IDLE, WAIT_B};
	private PlayerState playerState;

	private boolean isLoop;
	private boolean isABRepeatMode;


	private ArrayList<ABRepeat> abRepeatList;
	private ABRepeat currentABRepeat;

	private ArrayList<FileInfo> fileList;
//	private PlayFile currentFile;

	private PlayList currentPlayList;

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


}
