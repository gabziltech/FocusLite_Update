package com.gabzil.focuslite;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ConnectionPage extends ActionBarActivity {

    ImageView image;
    Button getstart;
    DataHelp dh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection_page);

        image=(ImageView)findViewById(R.id.animationView);
        getstart=(Button)findViewById(R.id.getstart);

        dh = new DataHelp(this);
        dh.UpdateSession("Yes");

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("");
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#000000")));

        RotateAnimation anim = new RotateAnimation(0f, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        anim.setFillAfter(true);
        anim.setInterpolator(new LinearInterpolator());
        anim.setRepeatCount(Animation.INFINITE);
        anim.setDuration(5000);

        image.startAnimation(anim);
        getstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ConnectionPage.this, MainSwipeActivity.class);
                startActivity(i);
            }
        });

        TextView myText = (TextView) findViewById(R.id.dots);

        Animation anim1 = new AlphaAnimation(0.0f, 1.0f);
        anim1.setDuration(500); //You can manage the time of the blink with this parameter
        anim1.setStartOffset(20);
        anim1.setRepeatMode(Animation.REVERSE);
        anim1.setRepeatCount(Animation.INFINITE);
        myText.startAnimation(anim1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (!second) {
                    Toast.makeText(getApplication(), "Press once again to exit!", Toast.LENGTH_SHORT).show();
                    second = true;
                } else {
                    second = false;
                    finish();
                    System.exit(0);
                }
                return true;
            case R.id.action_settings:
                Toast.makeText(getApplicationContext(),"Settings",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_log_out:
                dh.UpdateSession("No");
                Intent i = new Intent(ConnectionPage.this, SignIn.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    boolean second;
    @Override
    public void onBackPressed(){
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
