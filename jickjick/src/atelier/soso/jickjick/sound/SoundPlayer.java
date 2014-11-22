package atelier.soso.jickjick.sound;

import java.io.File;
import java.io.IOException;

import atelier.soso.jickjick.*;	//for R.java
import atelier.soso.jickjick.utils.ShortTask;
import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

public class SoundPlayer {


	MediaPlayer mediaPlayer = null;
	Context curContext;
	StateManager stateManager;
	
	public SoundPlayer(Context context) {
		setContext(context);
		mediaPlayer = MediaPlayer.create(curContext, R.raw.testmusic);
		stateManager = (StateManager)context.getApplicationContext();
	}

//	public boolean loadMedia(String filePath){
//		boolean result = true;
//		//reload
//		if(mediaPlayer != null){
//			mediaPlayer.stop();
//			mediaPlayer.release();
//			mediaPlayer = null;
//			mediaPlayer = MediaPlayer.create(curContext, R.raw.testmusic);
//		}
//		else
//		{
//			mediaPlayer = MediaPlayer.create(curContext, R.raw.testmusic);
//		}
//		return result;
//	}
	
	public int setContext(Context context)
	{
		curContext = context;
		return 1;
	}

	public int play(String filePath) {
		int result = 0;

		try{
			mediaPlayer.setDataSource(filePath);
	        mediaPlayer.prepare();
			mediaPlayer.start(); // no need to call prepare(); create() does that for you
		}
		catch(NullPointerException e)
		{
			Log.e("NullPoint", "MediaPlayer is Null.");
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	public int stop(){
		int result = 1;

		if (mediaPlayer != null){
			mediaPlayer.stop();
		}

		return result;
	}

	public int getDuration() {
		return mediaPlayer.getDuration();
	}

	public int getCurrentPosition() {
		// TODO Auto-generated method stub
		return mediaPlayer.getCurrentPosition();
	}

	public boolean isNowPlaying() {
		boolean nowPlaying = true;

		if (mediaPlayer == null)
		{
			nowPlaying = false;
		}
		else {
			nowPlaying = mediaPlayer.isPlaying();
		}
		
		return nowPlaying;
	}

	public void seekTo(int afterPosition) {
		// TODO Auto-generated method stub
		mediaPlayer.seekTo(afterPosition);
	}

	public void playTrack(int position) {
		//해당 위치의 파일 재생
		FileInfo currentFilePath = stateManager.getCurrentPlayList().getItems().get(position);
		mediaPlayer.reset();
		play(currentFilePath.getFile().getPath());
		//플레이 후 상태 갱신.
		stateManager.setCurrentPosition(position);
	}

	public void playBeforeTrack(){
		int currentIndex = stateManager.getCurrentPosition();
		int nSoundFileCount = stateManager.getCurrentPlayList().getItems().size();
		boolean isLoop = stateManager.isLoop();
		
		int originIndex = currentIndex;
		//재생할 트랙 계산.
		if(currentIndex == 0)
		{
			if(isLoop)	//마지막 트랙으로 보냄
			{
				currentIndex = nSoundFileCount-1;
			}
			else
			{
				ShortTask.showToast(curContext, R.string.this_song_is_the_first_song);
			}
		}
		else
		{
			currentIndex--;
		}
		
		showTrackInfo(currentIndex, nSoundFileCount, isLoop, originIndex);

		
		//다음곡 플레이
		playTrack(currentIndex);
	}
	
	public void playNextTrack() {
		int currentIndex = stateManager.getCurrentPosition();
		int nSoundFileCount = stateManager.getCurrentPlayList().getItems().size();
		boolean isLoop = stateManager.isLoop();
		
		int originIndex = currentIndex; 
		
		//재생할 트랙 계산.
		if(currentIndex + 1 >= nSoundFileCount )
		{
			if(isLoop)
			{
				currentIndex = 0;
			}
			else
			{
				ShortTask.showToast(curContext, R.string.this_song_is_the_last_song);
			}
		}
		else
		{
			currentIndex ++;
		}
		
		showTrackInfo(currentIndex, nSoundFileCount, isLoop, originIndex);
		//다음곡 플레
		playTrack(currentIndex);
	}

	private void showTrackInfo(int currentIndex, int nSoundFileCount,
			boolean isLoop, int originIndex) {
		Log.v("SoundPlayer", String.format("track[%d->%d]. TrackSize[%d]. isLoop[%b]", originIndex, currentIndex, nSoundFileCount, isLoop));
	}
	
}
