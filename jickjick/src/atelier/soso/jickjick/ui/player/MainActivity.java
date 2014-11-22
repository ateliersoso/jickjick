package atelier.soso.jickjick.ui.player;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;
import atelier.soso.jickjick.R;
import atelier.soso.jickjick.StateManager;
import atelier.soso.jickjick.StateManager.PlayerState;
import atelier.soso.jickjick.db.SoundDBOpenHelper;
import atelier.soso.jickjick.db.DBTables.SoundFile;
import atelier.soso.jickjick.sound.SoundPlayer;
import atelier.soso.jickjick.ui.filelist.FileListFragment.OnPlaySoundListener;


//전체 컨테이너 액티비티.
public class MainActivity extends ActionBarActivity
implements NavigationDrawerFragment.NavigationDrawerCallbacks, OnPlaySoundListener {

	public static final String EXTRA_MESSAGE = "atelier.soso.jickjick.MESSAGE";

	public static final int REFRESH_SCREEN 	= 2;
	public static final int MONITOR_STATE 	= 4;

	/**
	 * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
	 */
	private NavigationDrawerFragment mNavigationDrawerFragment;

	/**
	 * Used to store the last screen title. For use in {@link #restoreActionBar()}.
	 */
	private CharSequence mTitle;

	//members
	private SoundPlayer soundPlayer = null;
	private RefreshStateHandler refreshStateHandler = null; 
	private SoundDBOpenHelper soundDBOpenHelper = null;
	private Timer refreshTimer = null;

	//app states
	private StateManager stateManager = null;

	//UIs
	private SeekBar playingSeekBar = null;
	private EditText topText;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mNavigationDrawerFragment = (NavigationDrawerFragment)
				getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
		mTitle = getTitle();

		// Set up the drawer.
		mNavigationDrawerFragment.setUp(
				R.id.navigation_drawer,
				(DrawerLayout) findViewById(R.id.drawer_layout));

		initialize();

		//Animation 
		TimerTask screenRefreshTask = new TimerTask() {

			@Override
			public void run() {
				Message msg = new Message();
				msg.what = REFRESH_SCREEN;

				refreshStateHandler.sendMessage(msg);
			}
		};

		TimerTask stateRefreshTask = new TimerTask() {

			@Override
			public void run() {
				Message msg = new Message();
				msg.what = MONITOR_STATE;

			}
		};

		//refreshing screen every 500ms
		//refreshing state every 200ms
		if(refreshTimer == null)
		{
			refreshTimer = new Timer();
			refreshTimer.schedule(screenRefreshTask, 500, 500);
			refreshTimer.schedule(stateRefreshTask, 200, 200);
		}
		
	}

	@Override
	protected void onStart(){
		super.onStart();
		playingSeekBar = (SeekBar)findViewById(R.id.playingSeekBar);
		if (playingSeekBar != null) {
			//임시코드. onseekbarchangelistener를 두번 안넣게 하기 위함
			if(playingSeekBar.hasOnClickListeners() == false){
				playingSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

					//손가락을 때는 시점에서 재생 위치 갱신.
					@Override
					public void onStopTrackingTouch(SeekBar seekBar) {
						int position = seekBar.getProgress();
						soundPlayer.seekTo(position);
					}

					@Override
					public void onStartTrackingTouch(SeekBar seekBar) {

					}

					@Override
					public void onProgressChanged(SeekBar seekBar, int progress,
							boolean fromUser) {
					}
				});
				playingSeekBar.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub

					}
				});
			}
		}
	}

	/**
	 * initialize members 
	 */
	private void initialize() {
		if(soundPlayer == null)
		{
			soundPlayer = new SoundPlayer(this);
		}		

		if(refreshStateHandler == null)
		{
			refreshStateHandler = new RefreshStateHandler();
		}

		if(soundDBOpenHelper == null)
		{
			soundDBOpenHelper = new SoundDBOpenHelper(this);
			soundDBOpenHelper.open();

			//for test data
			soundDBOpenHelper.insertSoundFile("/sound/file", "keyFirstFile");
			soundDBOpenHelper.insertSoundFile("/sound/file", "keySecoundFile");
			//soundDBOpenHelper.insertColumn();

			Cursor cursor = soundDBOpenHelper.selectSoundFile(null, null);//select all
			cursor.moveToFirst();
			int count = cursor.getColumnCount();
			if(count > 0)
			{
				Log.v("DB", "cursor count : " + String.valueOf(count) );
				for(int i = 0; i < count; ++i){
					String id 	= cursor.getString( cursor.getColumnIndex(SoundFile.ID) );
					String path = cursor.getString( cursor.getColumnIndex(SoundFile.PATH) );
					String key 	= cursor.getString( cursor.getColumnIndex(SoundFile.KEY) );

					String result = String.format("id = %s, path = %s, key = %s", id, path, key);

					Log.v("DB", result);
					cursor.moveToNext();
				}
			}
		}

		stateManager = (StateManager)getApplicationContext();
		stateManager.setPlayerState(PlayerState.IDLE);
//		File externalStoragePath = getFilesDir();
		File externalStoragePath = Environment.getExternalStorageDirectory();
		stateManager.setCurrentPath(externalStoragePath);
		stateManager.setCurrentPosition(0);
		currentA=0;
		currentB=0;
		fileID=0;

	}

	@Override
	public void onNavigationDrawerItemSelected(int position) {
		// update the main content by replacing fragments
		FragmentManager fragmentManager = getSupportFragmentManager();
		fragmentManager.beginTransaction()
		.replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
		.commit();
	}

	public void onSectionAttached(int number) {
		switch (number) {
		case 1:
			mTitle = getString(R.string.title_section1);
			break;
		case 2:
			mTitle = getString(R.string.title_section2);
			break;
		case 3:
			mTitle = getString(R.string.title_section3);
			break;
		}
	}

	public void restoreActionBar() {
		ActionBar actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle(mTitle);
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (!mNavigationDrawerFragment.isDrawerOpen()) {
			// Only show items in the action bar relevant to this screen
			// if the drawer is not showing. Otherwise, let the drawer
			// decide what to show in the action bar.
			getMenuInflater().inflate(R.menu.main, menu);
			restoreActionBar();
			return true;
		}
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		private static final String ARG_SECTION_NUMBER = "section_number";


		/**
		 * Returns a new instance of this fragment for the given section
		 * number.
		 */
		public static PlaceholderFragment newInstance(int sectionNumber) {
			PlaceholderFragment fragment = new PlaceholderFragment();
			Bundle args = new Bundle();
			args.putInt(ARG_SECTION_NUMBER, sectionNumber);
			fragment.setArguments(args);
			return fragment;
		}

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container, false);

			return rootView;
		}

		@Override
		public void onAttach(Activity activity) {
			super.onAttach(activity);
			((MainActivity) activity).onSectionAttached(
					getArguments().getInt(ARG_SECTION_NUMBER));
		}
	}

	/**	for test method
	 * @param view
	 * @return
	 */
	public int sendMessage(View view)	{

		return 1;
	}

	/**
	 * 실제 사운드 파일에 대한 재생을 컨트롤 하는 함수.
	 * 버튼을 누르면 이 함수를 탐.
	 * @param view
	 * @return
	 */
	public int controlSound(View view) {
		int id = view.getId();
		switch(id)
		{
		case R.id.bottomButton:
			stopSound();
			break;
		case R.id.topLeftButton:
			break;
		case R.id.centerButton:
			playSound();
			break;
		case R.id.leftButton:
			soundPlayer.playBeforeTrack();
			break;
		case R.id.rightButton:
			soundPlayer.playNextTrack();
			break;
		case R.id.topButton:
			ABRepeatSound(view);
			break;

		}

		Context context = getApplicationContext();
		CharSequence text = ((Button)(view)).getText();
		int duration = Toast.LENGTH_SHORT;

		Toast toast = Toast.makeText(context, text, duration);
		toast.show();


		return 1;
	}

	private int currentA;
	private int currentB;
	private int fileID;	//파일 오픈 지점에서 db에 저장된 파일 아이디를 담음.

	private void ABRepeatSound(View view) {
		switch( stateManager.getPlayerState() )
		{

		case IDLE:		//1. A얻기
		{
			currentA = playingSeekBar.getProgress();
			stateManager.setPlayerState( PlayerState.WAIT_B );
		}
		break;

		case WAIT_B:	//2. B얻기
		{	
			currentB = playingSeekBar.getProgress();
			//db에 insert
			soundDBOpenHelper.insertABRepeat(currentA, currentB, fileID);

			stateManager.setPlayerState( PlayerState.IDLE );
			stateManager.setABRepeatMode(true);

		}
		break;
		}
	}

	private void stopSound() {
		soundPlayer.stop();
	}

	private void playSound() {
		//1. play sound file
		if(soundPlayer == null)
		{
			soundPlayer = new SoundPlayer(this);
		}
		else
		{
			soundPlayer.playTrack(stateManager.getCurrentPosition());
//			soundPlayer.loadMedia("TestPath");
//			soundPlayer.play("");
		}
		//2. refresh view
		refreshTimeLabel();
	}

	public void refreshTimeLabel() {
		int duration = soundPlayer.getDuration();
		int minute = duration / 1000 / 60;
		int second = (duration - (1000 * 60 * minute) ) / 1000;

		int currentTime = soundPlayer.getCurrentPosition();
		int curMinute = currentTime / 1000 / 60;                       
		int curSecond = (currentTime - (1000 * 60 * curMinute) ) / 1000;  

		topText = (EditText)findViewById(R.id.edit_message);
		String message = String.format("%3d:%2d / %3d:%2d", curMinute, curSecond, minute, second);
		topText.setText(message);
	}

	public void refreshProgress() {
		// TODO Auto-generated method stub
		boolean isNowPlaying = soundPlayer.isNowPlaying();
		if(isNowPlaying){

			int duration = soundPlayer.getDuration();
			int currentPosition = soundPlayer.getCurrentPosition();

			playingSeekBar = (SeekBar)findViewById(R.id.playingSeekBar);

			playingSeekBar.setMax(duration);
			playingSeekBar.setProgress(currentPosition);
		}

	}


	class RefreshStateHandler extends Handler {

		@Override
		public void handleMessage(Message msg) {
			switch(msg.what)
			{
			case REFRESH_SCREEN:
				//현재시간:재생시간
				refreshScreen();

				//프로그레스바 갱


				break;
			case MONITOR_STATE:
			{
				//구간 반복 모드에서 다시 돌아가기.
				boolean isABRepeatMode = stateManager.isABRepeatMode();
				if(isABRepeatMode == true)
				{
					int currentposition = soundPlayer.getCurrentPosition();
					//repeat모드에서 
					if(currentposition > currentB)
					{
						soundPlayer.seekTo(currentA);
						refreshScreen();
					}
				}
				else
				{
					;
				}
			}
			break;
			default:
				break;
			}
		}

		private void refreshScreen() {
			refreshProgress();
			refreshTimeLabel();

		}
	}

	@Override
	public void onPlaySoundListner(int playOffset) {
		// TODO Auto-generated method stub
		soundPlayer.playTrack(0);
	}


}