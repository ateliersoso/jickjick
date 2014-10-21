package atelier.soso.jickjick;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;
import atelier.soso.jickjick.sound.SoundPlayer;


public class MainActivity extends ActionBarActivity
implements NavigationDrawerFragment.NavigationDrawerCallbacks {

	public static final String EXTRA_MESSAGE = "atelier.soso.jickjick.MESSAGE";

	public static final int REFRESH_SCREEN = 3;

	/**
	 * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
	 */
	private NavigationDrawerFragment mNavigationDrawerFragment;

	/**
	 * Used to store the last screen title. For use in {@link #restoreActionBar()}.
	 */
	private CharSequence mTitle;

	private SoundPlayer soundPlayer = null;
	private RefreshScreenHandler refreshScreenHandler = null; 
	//UIs

	//Animation
	Timer animator = null;

	//UI Items
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

		// Set up UI Items
		playingSeekBar  = (SeekBar)findViewById(R.id.playingSeekBar);
		PlayingSeekBarEventListener seekEvent = new PlayingSeekBarEventListener();
		playingSeekBar.setOnSeekBarChangeListener(seekEvent);
		
		initialize();

		//Animation 
		TimerTask screenRefreshTask = new TimerTask() {

			@Override
			public void run() {
				Message msg = new Message();
				msg.what = REFRESH_SCREEN;
				
				refreshScreenHandler.sendMessage(msg);
			}
		};

		//refreshing screen every 500ms
		if(animator == null)
		{
			animator = new Timer();
			animator.schedule(screenRefreshTask, 500, 500);
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
		
		if(refreshScreenHandler == null)
		{
			refreshScreenHandler = new RefreshScreenHandler();
		}
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

	public int sumActivity(int i, int j) {
		// TODO Auto-generated method stub
		return i+j;
	}

	public int sendMessage(View view)	{
		Intent intent = new Intent(this, DisplayMessageActivity.class);
		EditText editText = (EditText)findViewById(R.id.edit_message);
		String message = editText.getText().toString();;
		intent.putExtra(EXTRA_MESSAGE, message);
		startActivity(intent);

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

		}

		Context context = getApplicationContext();
		CharSequence text = ((Button)(view)).getText();
		int duration = Toast.LENGTH_SHORT;

		Toast toast = Toast.makeText(context, text, duration);
		toast.show();


		return 1;
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
			soundPlayer.loadMedia("TestPath");
			soundPlayer.play("");
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


	class RefreshScreenHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			switch(msg.what)
			{
			case REFRESH_SCREEN:
				//현재시간:재생시간
				refreshProgress();
				refreshTimeLabel();

				//프로그레스바 갱

				break;
			default:
				break;
			}
		}
	}
	
	class PlayingSeekBarEventListener implements SeekBar.OnSeekBarChangeListener{
		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub
			int afterPosition = seekBar.getProgress();
			soundPlayer.seekTo(afterPosition);
			
//			int seekMax = seekBar.getMax();
			
			
		}
		
		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
			// TODO Auto-generated method stub
			
		}
	};
	
}