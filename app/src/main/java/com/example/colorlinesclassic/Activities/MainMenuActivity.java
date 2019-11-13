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

public class MainMenuActivity extends AppCompatActivity {

    private Button newGameBtm;
    private Button continueGameBtn;
    private Button quitGameBtn;
    private long backPressedTime;
    private Toast backToast;
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

    private void initializeButtons(){
        newGameBtm = findViewById(R.id.newGameBtn);
        continueGameBtn = findViewById(R.id.continueGameBtn);
        quitGameBtn = findViewById(R.id.quitGameBtn);

    }
    public void onNewGamePressed(View w){
        try{
            Intent intent = new Intent(MainMenuActivity.this, GameActivity.class);
            startActivity(intent);//open new activity
            finish();//close this window
        }catch(Exception e){
            //catch
        }
    }

    public void onContinuePressed(View w){
        try{
            Intent intent = new Intent(MainMenuActivity.this,GameActivity.class);
            startActivity(intent);//open new activity
            finish();//close this window
        }catch(Exception e){
            //catch
        }
    }

    public void onQuitPressed(View w){
        if(backPressedTime + 2000 > System.currentTimeMillis()) {
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
