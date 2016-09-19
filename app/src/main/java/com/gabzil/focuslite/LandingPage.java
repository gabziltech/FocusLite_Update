package com.gabzil.focuslite;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

public class LandingPage extends Activity implements Animation.AnimationListener {
    VideoView video;
    TextView companyname;
    ImageView companylogo;
    Animation animFadein;
    Animation animBlink;
    Typeface face;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landing_page);

        video = (VideoView) findViewById(R.id.videoView);
        companyname = (TextView) findViewById(R.id.companyname);
        companylogo = (ImageView) findViewById(R.id.companylogo);
        animFadein = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        face = Typeface.createFromAsset(getAssets(), "brush-script-mt.ttf");
        companyname.setTypeface(face);
        animFadein.setAnimationListener(this);
        animBlink = AnimationUtils.loadAnimation(this, R.anim.blink);

        CheckSession();
        SetVideo();
        video.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                video.setVisibility(View.GONE);
                companylogo.setVisibility(View.VISIBLE);
                companylogo.startAnimation(animFadein);
                companyname.setVisibility(View.VISIBLE);
                companyname.startAnimation(animFadein);
            }
        });

        companylogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LandingPage.this, StartPage.class);
                startActivity(i);
            }
        });
    }

    public void SetVideo() {
        MediaController mc = new MediaController(this);
        video.setMediaController(mc);
        video.setVideoURI(Uri.parse("android.resource://com.gabzil.focuslite/" + R.raw.myvideo));
        video.start();
    }

    public void CheckSession() {
        MyOpenHelper db = new MyOpenHelper(this);
        String Session = db.getSessionTake();

        if (Session.equals("Yes")) {
            Intent i = new Intent(LandingPage.this, ConnectionPage.class);
            startActivity(i);
        }
    }

    @Override
    public void onAnimationStart(Animation animation) {
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        companylogo.startAnimation(animBlink);
    }

    @Override
    public void onAnimationRepeat(Animation animation) {
    }

    boolean second;
    @Override
    public void onBackPressed() {
        if (!second) {
            Toast.makeText(getApplication(), "Press once again to exit!", Toast.LENGTH_SHORT).show();
            second = true;
        } else {
            second = false;
            finish();
            System.exit(0);
        }
    }
}