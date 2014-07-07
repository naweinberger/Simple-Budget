package us.weinberger.natan.simplebudget.network;

import android.content.Context;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import us.weinberger.natan.simplebudget.fragments.AnalysisBarGraphFragment;
import us.weinberger.natan.simplebudget.fragments.AnalysisPieChartFragment;
import us.weinberger.natan.simplebudget.fragments.HistoryListFragment;
import us.weinberger.natan.simplebudget.util.MyBaseAdapter;
import us.weinberger.natan.simplebudget.util.MyDialogPreference;

/**
 * Created by Natan on 7/6/2014.
 */
public class EmailClient extends AsyncTask<Void, Void, Void> {
    String username;
    String password;
    Context context;
    String jsonResult;
    String email = "";
    public EmailClient(Context c) {
        context = c;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected Void doInBackground(Void... params) {
        username = context.getSharedPreferences("SBPref", 0).getString("logged_in_username", "");
        password = context.getSharedPreferences("SBPref", 0).getString("logged_in_password", "");

        DefaultHttpClient client = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost("http://natan.weinberger.us/simplebudget/index.php");
        List<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();
        parameters.add(new BasicNameValuePair("username", username));
        parameters.add(new BasicNameValuePair("password", password));
        parameters.add(new BasicNameValuePair("type", "getEmail"));

        try {
            httpPost.setEntity(new UrlEncodedFormEntity(parameters));
        } catch (Exception e) {
            System.out.println(e);
        }

        try {
            HttpResponse response = client.execute(httpPost);
            jsonResult = inputStreamToString(response.getEntity().getContent()).toString();
            email = jsonResult;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private StringBuilder inputStreamToString(InputStream is) {
        String rLine = "";
        StringBuilder answer = new StringBuilder();
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));

        try {
            while ((rLine = rd.readLine()) != null) {
                answer.append(rLine);
            }
        } catch (IOException e) {
            // e.printStackTrace();

        }
        return answer;
    }

    @Override
    protected void onPostExecute(Void v) {
        MyDialogPreference.setEmail(email);
    }
}
