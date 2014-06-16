package com.example.simplebudget;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Natan on 6/8/2014.
 */

public class HomeActivity extends Activity {
    Context context = HomeActivity.this;
    String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_home);

        TextView logo = (TextView) findViewById(R.id.logoHome);
        Typeface roboFont = Typeface.createFromAsset(context.getAssets(), "Roboto-Thin.ttf");
        logo.setTypeface(roboFont);

        Button addTransaction = (Button) findViewById(R.id.addTransaction);
        Button viewHistory = (Button) findViewById(R.id.viewHistory);
        Button analysisFeatures = (Button) findViewById(R.id.analysisFeatures);

        addTransaction.setTypeface(roboFont);
        viewHistory.setTypeface(roboFont);
        analysisFeatures.setTypeface(roboFont);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        username = extras.getString("username");

    }

    public void addTransaction(View view) {
        Intent intent = new Intent(this, TransactionActivity.class);
        intent.putExtra("username", username);
        startActivity(intent);
        overridePendingTransition(R.anim.right_slide_in, R.anim.right_slide_out);



    }
}
