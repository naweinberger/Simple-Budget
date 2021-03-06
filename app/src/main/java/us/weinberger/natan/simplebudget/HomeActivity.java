package us.weinberger.natan.simplebudget;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
        Bundle extras = intent.getExtras();
        username = extras.getString("username");

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
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void addTransaction(View view) {
        Intent intent = new Intent(this, TransactionActivity.class);
        intent.putExtra("username", username);
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
