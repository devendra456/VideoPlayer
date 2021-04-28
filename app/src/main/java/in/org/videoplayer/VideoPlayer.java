package in.org.videoplayer;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.lang.reflect.GenericArrayType;
import java.util.Objects;

public class VideoPlayer extends AppCompatActivity {
    private VideoView videoView;
    private int pos;
    private LinearLayout headerLayout, footerLayout;
    private ImageView centerPlayPause,playPause;
    private boolean isTouch = true;
    private boolean isPlaying = true;
    private TextView totalTime,currentTime;
    private double total_duration,current_duration;
    private SeekBar seekBar;
    private LinearLayout doubleTap_back,doubleTap_next,doubleTap_playPause;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_video_player);
        initViews();
        pos = getIntent().getIntExtra("pos", 0);
        videoView.setVideoURI(MainActivity.videoArrayList.get(pos).getData());
        videoView.start();
        total_duration = videoView.getDuration();
        current_duration = videoView.getCurrentPosition();
        totalTime.setText(String.valueOf(total_duration));
        currentTime.setText(String.valueOf(current_duration));
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                setVideoProgress();
            }
        });
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                centerPlayPause.setImageResource(R.drawable.ic_baseline_play_circle_outline_24);
                playPause.setImageResource(R.drawable.ic_baseline_play_arrow_24);
                isPlaying = false;
                if (pos < MainActivity.videoArrayList.size() - 1) {
                    pos++;
                    videoView.setVideoURI(MainActivity.videoArrayList.get(pos).getData());
                    videoView.start();
                    centerPlayPause.setImageResource(R.drawable.ic_baseline_pause_circle_outline_24);
                    playPause.setImageResource(R.drawable.ic_baseline_pause_24);
                    isPlaying = true;
                }
            }
        });
        doubleTap_playPause.setOnTouchListener(new View.OnTouchListener() {
            GestureDetector gestureDetector=new GestureDetector(VideoPlayer.this, new GestureDetector.SimpleOnGestureListener(){
                @Override
                public boolean onDoubleTap(MotionEvent e) {
                    if(isPlaying){
                        videoView.pause();
                        centerPlayPause.setImageResource(R.drawable.ic_baseline_play_circle_outline_24);
                        playPause.setImageResource(R.drawable.ic_baseline_play_arrow_24);
                        isPlaying=false;
                    }else{
                        videoView.start();
                        centerPlayPause.setImageResource(R.drawable.ic_baseline_pause_circle_outline_24);
                        playPause.setImageResource(R.drawable.ic_baseline_pause_24);
                        isPlaying=true;
                    }
                    return true;
                }
                @Override
                public boolean onSingleTapConfirmed(MotionEvent e) {
                    if (isTouch) {
                        headerLayout.setVisibility(View.VISIBLE);
                        footerLayout.setVisibility(View.VISIBLE);
                        centerPlayPause.setVisibility(View.VISIBLE);
                        isTouch = false;
                    } else {
                        headerLayout.setVisibility(View.INVISIBLE);
                        footerLayout.setVisibility(View.INVISIBLE);
                        centerPlayPause.setVisibility(View.INVISIBLE);
                        isTouch = true;
                    }
                    return true;
                }
            });
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gestureDetector.onTouchEvent(event);
                return true;
            }
        });
        doubleTap_next.setOnTouchListener(new View.OnTouchListener() {
            GestureDetector gestureDetector=new GestureDetector(VideoPlayer.this, new GestureDetector.SimpleOnGestureListener(){
                @Override
                public boolean onDoubleTap(MotionEvent e) {
                    current_duration=videoView.getCurrentPosition();
                        current_duration = current_duration + 10000;
                        videoView.seekTo((int) current_duration);
                        seekBar.setProgress((int) current_duration);
                        totalTime.setText(timeConversion((long) total_duration));
                        currentTime.setText(timeConversion((long) current_duration));
                    Log.i("TAG", "onDoubleTap: "+current_duration);
                    return true;
                }
                @Override
                public boolean onSingleTapConfirmed(MotionEvent e) {
                    if (isTouch) {
                        headerLayout.setVisibility(View.VISIBLE);
                        footerLayout.setVisibility(View.VISIBLE);
                        centerPlayPause.setVisibility(View.VISIBLE);
                        isTouch = false;
                    } else {
                        headerLayout.setVisibility(View.INVISIBLE);
                        footerLayout.setVisibility(View.INVISIBLE);
                        centerPlayPause.setVisibility(View.INVISIBLE);
                        isTouch = true;
                    }
                    return true;
                }
            });
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gestureDetector.onTouchEvent(event);
                return true;
            }
        });
        doubleTap_back.setOnTouchListener(new View.OnTouchListener() {
            GestureDetector gestureDetector=new GestureDetector(VideoPlayer.this, new GestureDetector.SimpleOnGestureListener(){
                @Override
                public boolean onDoubleTap(MotionEvent e) {
                    current_duration=videoView.getCurrentPosition();
                        current_duration = current_duration - 10000;
                        videoView.seekTo((int) current_duration);
                        seekBar.setProgress((int) current_duration);
                        totalTime.setText(timeConversion((long) total_duration));
                        currentTime.setText(timeConversion((long) current_duration));
                    Log.i("TAG", "onDoubleTap: "+current_duration);
                    return true;
                }
                @Override
                public boolean onSingleTapConfirmed(MotionEvent e) {
                    if (isTouch) {
                        headerLayout.setVisibility(View.VISIBLE);
                        footerLayout.setVisibility(View.VISIBLE);
                        centerPlayPause.setVisibility(View.VISIBLE);
                        isTouch = false;
                    } else {
                        headerLayout.setVisibility(View.INVISIBLE);
                        footerLayout.setVisibility(View.INVISIBLE);
                        centerPlayPause.setVisibility(View.INVISIBLE);
                        isTouch = true;
                    }
                    return true;
                }
            });
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gestureDetector.onTouchEvent(event);
                return true;
            }
        });
    }

    // display video progress
    public void setVideoProgress() {
        //get the video duration
        current_duration = videoView.getCurrentPosition();
        total_duration = videoView.getDuration();

        //display video duration
        totalTime.setText(timeConversion((long) total_duration));
        currentTime.setText(timeConversion((long) current_duration));
        seekBar.setMax((int) total_duration);
        final Handler handler = new Handler();

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    current_duration = videoView.getCurrentPosition();
                    currentTime.setText(timeConversion((long) current_duration));
                    seekBar.setProgress((int) current_duration);
                    handler.postDelayed(this, 100);
                } catch (IllegalStateException ed){
                    ed.printStackTrace();
                }
            }
        };
        handler.postDelayed(runnable, 100);

        //seekbar change listner
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                current_duration = seekBar.getProgress();
                videoView.seekTo((int) current_duration);
            }
        });
    }

    //time conversion
    public String timeConversion(long value) {
        String songTime;
        int dur = (int) value;
        int hrs = (dur / 3600000);
        int mns = (dur / 60000) % 60000;
        int scs = dur % 60000 / 1000;

        if (hrs > 0) {
            songTime = String.format("%02d:%02d:%02d", hrs, mns, scs);
        } else {
            songTime = String.format("%02d:%02d", mns, scs);
        }
        return songTime;
    }

    private void initViews() {
        videoView = findViewById(R.id.video_view);
        TextView textView = findViewById(R.id.video_name);
        textView.setSelected(true);
        textView.setText(MainActivity.videoArrayList.get(pos).getName());
        headerLayout = findViewById(R.id.header);
        footerLayout = findViewById(R.id.footer);
        centerPlayPause = findViewById(R.id.center_play_pause);
        currentTime=findViewById(R.id.current_time);
        totalTime=findViewById(R.id.total_time);
        playPause=findViewById(R.id.play_pause);
        seekBar=findViewById(R.id.seekbar);
        doubleTap_back=findViewById(R.id.double_tap_backward);
        doubleTap_next=findViewById(R.id.double_tap_forward);
        doubleTap_playPause=findViewById(R.id.double_tap);
    }

    public void backButton(View view) {
        finish();
    }

    public void centerPlayPause(View view) {
        if(isPlaying){
            videoView.pause();
            centerPlayPause.setImageResource(R.drawable.ic_baseline_play_circle_outline_24);
            playPause.setImageResource(R.drawable.ic_baseline_play_arrow_24);
            isPlaying=false;
        }else{
            videoView.start();
            centerPlayPause.setImageResource(R.drawable.ic_baseline_pause_circle_outline_24);
            playPause.setImageResource(R.drawable.ic_baseline_pause_24);
            isPlaying=true;
        }

    }

    public void previous(View view) {
        if(pos>0) {
            pos--;
            videoView.setVideoURI(MainActivity.videoArrayList.get(pos).getData());
            videoView.start();
            centerPlayPause.setImageResource(R.drawable.ic_baseline_pause_circle_outline_24);
            playPause.setImageResource(R.drawable.ic_baseline_pause_24);
            isPlaying=true;
        }
    }

    public void next(View view) {
        if(pos<MainActivity.videoArrayList.size()-1) {
            pos++;
            videoView.setVideoURI(MainActivity.videoArrayList.get(pos).getData());
            videoView.start();
            centerPlayPause.setImageResource(R.drawable.ic_baseline_pause_circle_outline_24);
            playPause.setImageResource(R.drawable.ic_baseline_pause_24);
            isPlaying=true;
        }
    }


}