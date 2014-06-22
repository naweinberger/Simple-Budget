package us.weinberger.natan.simplebudget;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

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
                logout();
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


//        DownloadClient client = new DownloadClient(HomeActivity.this);
//        client.execute();
//        ArrayList<Transaction> transactionList = DownloadClient.transactionList;
//        for (Transaction transaction : transactionList) {
//            Log.d("TEST", transaction.getLocation());
//        }
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
