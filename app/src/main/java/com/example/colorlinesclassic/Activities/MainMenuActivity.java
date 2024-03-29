package com.example.colorlinesclassic.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.example.colorlinesclassic.R;

import org.apache.logging.log4j.Level;

import java.util.logging.Logger;

public class MainMenuActivity extends AppCompatActivity {
    private long backPressedTime;
    private Toast backToast;
    private org.apache.logging.log4j.Logger logger = (org.apache.logging.log4j.Logger)Logger.getLogger(MainMenuActivity.class.getName());
    private final int timeForDoubleBackPressed = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        configureWindow();

    }

    private void configureWindow(){
        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    public void onNewGamePressed(View w){
        try{
            Intent intent = new Intent(MainMenuActivity.this, GameActivity.class);
            startActivity(intent);//open new activity
            finish();//close this window
        }catch(Exception e){
            logger.log(Level.ERROR,e.getMessage());
        }
    }

    public void onQuitPressed(View w){
        if(backPressedTime + timeForDoubleBackPressed > System.currentTimeMillis()) {
            backToast.cancel();
            super.onBackPressed();
            return;//exit from application
        }else{
            backToast = Toast.makeText(getBaseContext(),"Press one more time for exit",Toast.LENGTH_SHORT);
            backToast.show();
        }
        backPressedTime = System.currentTimeMillis();
    }

}
