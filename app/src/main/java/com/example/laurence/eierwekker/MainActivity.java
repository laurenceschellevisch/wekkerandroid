package com.example.laurence.eierwekker;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    SeekBar tijdSelectie;
    TextView tijdView;
    int totaleTijd, minuten, seconden;
    String tijd;
    CountDownTimer timer;
    Button timerButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tijdSelectie = (SeekBar) findViewById(R.id.seekBar);
        tijdView = (TextView) findViewById(R.id.tijdText);
        timerButton = (Button) findViewById(R.id.timerButton);
        totaleTijd = 30;
        minuten = 0;
        seconden = 30;
        trackSeekbar();

    }

    public void trackSeekbar(){
        tijdSelectie.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                totaleTijd = tijdSelectie.getProgress();
                setTimer();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void setTimer(){
        minuten = totaleTijd/60;
        seconden = totaleTijd%60;
        if(seconden < 10 && seconden > 0){
            tijd = "0"+minuten+":0"+seconden;
        }else if(seconden > 9){
            tijd = "0"+minuten+":"+seconden;
        }
        tijdView.setText(tijd);
    }



    public void startStopTimer(View v){

        if(timer != null){
            timerButton.setText("Start");
            timer.cancel();
            timer = null;
        }else {
            timerButton.setText("Stop");
            timer = new CountDownTimer(totaleTijd * 1000 + 100, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    totaleTijd = (int) millisUntilFinished / 1000;
                    setTimer();
                }

                @Override
                public void onFinish() {
                    tijdView.setText("00:00");
                    MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.airhorn);
                    mediaPlayer.start();
                }
            };
            timer.start();
        }
    }






    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}