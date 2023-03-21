package com.example.musicplayer;

import android.media.MediaPlayer;

import android.os.Bundle;

import android.util.Log;

import android.view.View;

import android.widget.ImageView;

import android.widget.SeekBar;

import android.widget.TextView;

import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;

import java.util.TimerTask;



public class MainActivity extends AppCompatActivity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {



    private ImageView iv_play, iv_stop, iv_reset;

    private SeekBar seekBar;

    private MediaPlayer mediaPlayer;

    private long duration;

    private TextView tv_start, tv_total;

    private long minutes, seconds;



    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);



        // method for find ids..

        findViews();



        // method for click Listener

        initListner();

    }



    private void findViews() {



        iv_play = findViewById(R.id.iv_play);

        iv_stop = findViewById(R.id.iv_stop);

        iv_reset = findViewById(R.id.iv_reset);



        tv_start = findViewById(R.id.tv_start);

        tv_total = findViewById(R.id.tv_total);



        seekBar = findViewById(R.id.seekbar);

    }



    private void initListner() {



        iv_play.setOnClickListener(this);

        iv_stop.setOnClickListener(this);

        iv_reset.setOnClickListener(this);



        seekBar.setOnSeekBarChangeListener(this);

    }




    @Override

    public void onClick(View view) {



        switch (view.getId()) {

            case R.id.iv_play:

                // for check the mediaplayer is null

                if (mediaPlayer == null) {

                    /*

                     * if media player is null then create a new mediaplayer*/

                    mediaPlayer = MediaPlayer.create(this, R.raw.renaissance);



                    /*

                     * for getting the duration of mediaplayer*/

                    duration = mediaPlayer.getDuration();



                    minutes = (duration / 1000) / 60;//converting into minutes

                    seconds = ((duration / 1000) % 60);//converting into seconds

                    tv_total.setText(minutes + ":" + seconds);



                    /* start mediaplayer*/

                    mediaPlayer.start();



                    /*

                     * setting the max-length of seekbar according to mediaplayre duration

                     * */

                    seekBar.setMax((int) duration);



                    /*

                     * use Timer task for updating the seekbar progress according to mediaplayer Current position

                     * */

                    new Timer().scheduleAtFixedRate(new TimerTask() {

                        @Override

                        public void run() {

                            try {

                                seekBar.setProgress(mediaPlayer.getCurrentPosition());

                            } catch (Exception e) {

                            }

                        }

                    }, 0, 1000);




                    /*

                     * for reset the mediaplayer when mediaplayer is complete

                     * */

                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                        @Override

                        public void onCompletion(MediaPlayer mediaPlayer) {

                            mediaPlayer.release();

                            mediaPlayer = null;

                        }

                    });



                    Toast.makeText(this, "start mediaplayer", Toast.LENGTH_SHORT).show();

                } else {

                    /*

                     * if media player is already created then only play

                     * */

                    Log.e("play else", "onClick: ");

                    mediaPlayer.start();

                }

                break;



            case R.id.iv_stop:



                /* stop media player if is playing */

                Log.e("stop", "onClick: " + mediaPlayer.isPlaying());



                if (mediaPlayer.isPlaying()) {

                    mediaPlayer.pause();

                    Toast.makeText(this, "Pause mediaplayre", Toast.LENGTH_SHORT).show();

                }



                break;



            case R.id.iv_reset:




                /**  Reset Media player..*/



                if (mediaPlayer != null) {

                    mediaPlayer.release();

                    mediaPlayer = null;

                    seekBar.setProgress(0);



                    /*

                     * set both text to 00:00 when mediaplayer is reset...

                     * */

                    tv_start.setText("00:00");

                    tv_total.setText("00:00");



                    Toast.makeText(this, "Reset mediaplayer", Toast.LENGTH_SHORT).show();

                } else {

                    Toast.makeText(this, "There is no sound playing play sound first", Toast.LENGTH_SHORT).show();

                }

                break;

        }

    }



    /* seek bar progress change Listener*/

    @Override

    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromuser) {



        if (mediaPlayer != null) {

            if (fromuser) {

                /*

                 * media player seek to progress of the seekbar

                 * */

                mediaPlayer.seekTo(progress);

            }



            minutes = (progress / 1000) / 60; //converting the progress into minutes

            seconds = ((progress / 1000) % 60); //converting the progress into second

            tv_start.setText(minutes + ":" + seconds); // set text according to progress change of seekbar

        }

    }



    @Override

    public void onStartTrackingTouch(SeekBar seekBar) {



    }



    @Override

    public void onStopTrackingTouch(SeekBar seekBar) {



    }

}