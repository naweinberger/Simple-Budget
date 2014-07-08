package us.weinberger.natan.simplebudget.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import us.weinberger.natan.simplebudget.R;

/**
 * Created by Natan on 6/8/2014.
 */

public class HomeActivity extends Activity {
    Context context = HomeActivity.this;
    String username, password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (getSharedPreferences("SBPref", 0).getString("types", " ").length() < 1) {
            SharedPreferences prefs = getSharedPreferences("SBPref", 0);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("types", ";");
            editor.commit();

        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_home);

        getActionBar().setIcon(R.drawable.ic_launcher);
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
        username = getSharedPreferences("SBPref", 0).getString("logged_in_username", "");
        password = getSharedPreferences("SBPref", 0).getString("logged_in_password", "");




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_activity_home, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_logout:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Are you sure you want to log out?")
                        .setCancelable(true)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                logout();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();

                return true;
            case R.id.action_settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.right_slide_in, R.anim.right_slide_out);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void addTransaction(View view) {
        Intent intent = new Intent(this, TransactionActivity.class);
        intent.putExtra("username", username);
        intent.putExtra("password", password);
        startActivity(intent);
        overridePendingTransition(R.anim.right_slide_in, R.anim.right_slide_out);

    }

    public void viewHistory(View view) {
        Intent intent = new Intent(this, HistoryActivity.class);
        intent.putExtra("username", username);
        intent.putExtra("password", password);
        startActivity(intent);
        overridePendingTransition(R.anim.right_slide_in, R.anim.right_slide_out);

    }

    public void analysisFeatures(View view) {
        Intent intent = new Intent(this, AnalysisActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.right_slide_in, R.anim.right_slide_out);
    }

    private void logout() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("SBPref", 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.remove("logged_in_username");
        editor.remove("logged_in_password");
        editor.commit();
        Intent intent = new Intent(this, StartupActivity.class);
        startActivity(intent);
        finish();

    }
}
