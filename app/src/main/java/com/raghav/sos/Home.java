package com.raghav.sos;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.firebase.auth.FirebaseAuth;

public class Home extends AppCompatActivity {
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;
    ImageButton play,tips,stop,call;
    TextView textView2;
    MediaPlayer alert;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        play = (ImageButton) findViewById(R.id.play);
        textView2 = (TextView) findViewById(R.id.textView2);
        tips = (ImageButton) findViewById(R.id.tips);
        stop = (ImageButton) findViewById(R.id.stop);
        call = (ImageButton) findViewById(R.id.call);
        alert = MediaPlayer.create(Home.this, R.raw.siren);

        tips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this, Tips.class));
            }
        });

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:100"));
                startActivity(intent);
            }
        });

    }
    public void alertplay(View view)
    {
        alert.start();
    }

    public void alertstop(View view)
    {
        alert.stop();
        alert = MediaPlayer.create(Home.this, R.raw.siren);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.drawer_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.account_settings:
                startActivity(new Intent(this, Account_settings.class));
                return true;
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                finish();
                return true;
            case R.id.contacts:
                startActivity(new Intent(this, MainActivity.class));
                return true;
            case R.id.aboutconstraint:
                startActivity(new Intent(this, About.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}