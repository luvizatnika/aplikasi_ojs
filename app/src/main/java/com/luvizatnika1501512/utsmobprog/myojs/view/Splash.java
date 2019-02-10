package com.luvizatnika1501512.utsmobprog.myojs.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.luvizatnika1501512.utsmobprog.myojs.R;

public class Splash extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Thread thread = new Thread(){
            public void run(){
                try{
                    sleep(2000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }finally {
                    startActivity(new Intent(Splash.this, MainActivity.class));
                    finish();
                }
            }
        };
        thread.start();
    }
}
