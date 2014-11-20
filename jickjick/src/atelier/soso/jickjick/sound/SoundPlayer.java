package atelier.soso.jickjick.sound;

import java.io.File;
import java.io.IOException;

import atelier.soso.jickjick.*;	//for R.java
import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;

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
		// TODO Auto-generated method stub
		FileInfo currentFilePath = stateManager.getCurrentPlayList().getItems().get(position);
		
		mediaPlayer.reset();
	 
		play(currentFilePath.getFile().getPath());
		
	}
}
