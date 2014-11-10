package ctec.soundandvideo.controller;


import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

public class SoundActivity extends Activity implements Runnable
{
	private Button startButton;
	private Button stopButton;
	private Button pauseButton;
	private Button videoButton;
	private MediaPlayer soundPlayer;
	private SeekBar soundSeekBar;
	private Thread soundThread;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sound);
		
		startButton = (Button) findViewById(R.id.playButton);
		pauseButton = (Button) findViewById(R.id.pauseButton);
		stopButton = (Button) findViewById(R.id.stopButton);
		soundSeekBar = (SeekBar) findViewById(R.id.soundSeekBar);
		videoButton = (Button) findViewById(R.id.videoButton);
		soundPlayer = MediaPlayer.create(this.getBaseContext(), R.raw.GetLove);
		
		setupListeners();
		
		soundThread = new Thread(this);
		soundThread.start();
	}
	
	private void setupListeners()
	{
		startButton.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				soundPlayer.start();
				
			}
			
		});
		
		pauseButton.setOnClickListener(new View.OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				soundPlayer.pause();
				
			}
		});
		
		stopButton.setOnClickListener(new View.OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				soundPlayer.stop();
				soundPlayer = MediaPlayer.create(getBaseContext(), R.raw.getlove);
			}
		});
		
		videoButton.setOnClickListener(new View.OnClickListener()
		{
			
			@Override
			public void onClick(View currentView)
			{
				Intent myIntent = new Intent(currentView.getContext(), VideoActivity.class);
				startActivityForResult(myIntent, 0);
			}
		});
		
		soundSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
		{
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar)
			{}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar)
			{}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
			{
				if(fromUser)
				{
					soundPlayer.seekTo(progress);
				}
			}
		});
	}
	
	/**
	 * Required since we are implementing Runnable. Allows the seekbar to update.
	 */
	@Override
	public void run()
	{
		int currentPosition = 0;
		int soundTotal = soundPlayer.getDuration();
		soundSeekBar.setMax(soundTotal);
		
		while (soundPlayer != null && currentPosition < soundTotal)
		{
			try
			{
				Thread.sleep(50);
				currentPosition = soundPlayer.getCurrentPosition();
			}
			catch(InterruptedException soundException)
			{
				return;
			}
			catch(Exception otherException)
			{
				return;
			}
			soundSeekBar.setProgress(currentPosition);
		}
	}
}
