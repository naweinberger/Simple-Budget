package com.example.simplebudget;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.view.Display;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

//public class StartupFragment extends Fragment {
//
//	    @Override
//	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//	        Bundle savedInstanceState) {
//	        // Inflate the layout for this fragment
//	        return inflater.inflate(R.layout.fragment_startup, container, false);
//	    }
//	
//}

public class StartupActivity extends Activity {

	private float xCurrentPos, yCurrentPos;
	Button loginBtn, registerBtn;
	TextView loginText, registerText, logo;
    Context context = StartupActivity.this;
    int pixels, height;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!getApplicationContext().getSharedPreferences("SBPref", 0).getString("logged_in", "").equals("")) {
            Intent intent = new Intent(context, HomeActivity.class);
            intent.putExtra("username", getApplicationContext().getSharedPreferences("SBPref", 0).getString("logged_in", ""));
            startActivity(intent);
            finish();
        }
        setContentView(R.layout.activity_startup);

        logo = (TextView) findViewById(R.id.logoTV);
    	xCurrentPos = logo.getLeft();
        yCurrentPos = logo.getTop(); 
        
        loginBtn = (Button) findViewById(R.id.loginBtn);
        registerBtn = (Button) findViewById(R.id.registerBtn);
        
        loginText = (TextView) findViewById(R.id.loginTV);
        registerText = (TextView) findViewById(R.id.registerTV);

        Typeface roboFont = Typeface.createFromAsset(context.getAssets(), "Roboto-Thin.ttf");
        logo.setTypeface(roboFont);


        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        height = size.y;






        pixels = height/4;
        
        new Handler().postDelayed(new Runnable(){
       	 
            @Override

            public void run() {
            	
                Animation anim= new TranslateAnimation(xCurrentPos, xCurrentPos, yCurrentPos, yCurrentPos-pixels);
                anim.setDuration(1000); 
                anim.setFillAfter(true); 
                anim.setFillEnabled(true); 
                anim.setAnimationListener(new AnimationListener() {

                    @Override
                    public void onAnimationStart(Animation arg0) {}

                    @Override
                    public void onAnimationRepeat(Animation arg0) {}

                    @Override
                    public void onAnimationEnd(Animation arg0) {
                        yCurrentPos -= pixels;
                    }
                });
                
                
                AlphaAnimation fadeIn = new AlphaAnimation(0, 0.1f);
                fadeIn.setDuration(1000);
                
                
                AlphaAnimation newFadeIn = new AlphaAnimation(0.0f, 1.0f);
                newFadeIn.setDuration(1000);
                
                logo.startAnimation(anim);
                
                loginBtn.setVisibility(View.VISIBLE);
                registerBtn.setVisibility(View.VISIBLE);
                loginBtn.startAnimation(fadeIn);
                registerBtn.startAnimation(fadeIn);
                
                loginText.setVisibility(View.VISIBLE);
                registerText.setVisibility(View.VISIBLE);
                loginText.startAnimation(newFadeIn);
                registerText.startAnimation(newFadeIn);
            }

    }, 3000);


        //Animation to translate logo up, then animation to fade in buttons. Buttons to alpha 0.1, text to alpha 1.0
        
        
	}
	
	public void pushLogin(View view) {
		Intent intent = new Intent(this, LoginActivity.class);
		startActivity(intent);
		overridePendingTransition(R.anim.right_slide_in, R.anim.right_slide_out);
		
	}
    
	public void pushRegister(View view) {
		Intent intent = new Intent(this, RegisterActivity.class);
		startActivity(intent);
		overridePendingTransition(R.anim.right_slide_in, R.anim.right_slide_out);
		
	}
}
