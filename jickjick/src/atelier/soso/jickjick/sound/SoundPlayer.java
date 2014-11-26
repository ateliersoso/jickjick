package atelier.soso.jickjick.sound;

import java.io.IOException;

import atelier.soso.jickjick.*;	//for R.java
import atelier.soso.jickjick.utils.ShortTask;
import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.util.Log;

public class SoundPlayer {

	MediaPlayer mediaPlayer = null;
	Context curContext;
	StateManager stateManager;
	OnCompletionListener onCompletetoinListener = new OnCompletionListener() {
		
		@Override
		public void onCompletion(MediaPlayer mp) {
			//재생이 끝나면 다음트랙 재
			int position = stateManager.getCurrentPosition();
			playTrack(++position);
		}
	};
	
	public SoundPlayer(Context context) {
		setContext(context);
		mediaPlayer = MediaPlayer.create(curContext, R.raw.testmusic);
		stateManager = (StateManager)context.getApplicationContext();
		mediaPlayer.setOnCompletionListener(onCompletetoinListener);
	}

	
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
			Log.e("SoundPlayer", "MediaPlayer is Null.");
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			Log.e("SoundPlayer", "??");
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			ShortTask.showToast(curContext, "파일이 정상적인 상태가 아닙니다.");
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


	public void playPreviousABRepeat() {
		
		int currentIndex = stateManager.getCurrentABRepeatPosition();
		
		if(currentIndex == 0)
		{
			ShortTask.showToast(curContext, R.string.this_song_is_the_first_abrepeat);
		}
		else
		{
			currentIndex--;
			
			ABRepeat nextABRepeat = stateManager.getAbRepeatList().get( currentIndex );
			stateManager.setCurrentABRepeat(nextABRepeat);
			stateManager.setCurrentABRepeatPosition(currentIndex);
	
			seekTo( nextABRepeat.getStart() );
		}
		
	}


	public void playNextABRepeat() {
		int currentIndex = stateManager.getCurrentABRepeatPosition();
		int maxIndex = stateManager.getAbRepeatList().size();
		
		if(currentIndex + 1 >= maxIndex)
		{
			ShortTask.showToast(curContext, R.string.this_song_is_the_last_abrepeat);
		}
		else
		{
			currentIndex++;
			
			ABRepeat nextABRepeat = stateManager.getAbRepeatList().get( currentIndex );
			stateManager.setCurrentABRepeat(nextABRepeat);
			stateManager.setCurrentABRepeatPosition(currentIndex);		
	
			seekTo( nextABRepeat.getStart() );
		}
		
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
}
