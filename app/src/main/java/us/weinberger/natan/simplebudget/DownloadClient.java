package us.weinberger.natan.simplebudget;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;
import android.content.Context;

import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Natan on 6/20/2014.
 */
public class DownloadClient extends AsyncTask<Void, Void, Void> {
    private static final String downloadUrl = "http://natan.weinberger.us/simplebudget/index.php";
    String jsonResult, username, password, tag;
    public static ArrayList<Transaction> transactionList = new ArrayList<Transaction>();
    private Context context;

    public DownloadClient(Context c) {
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
        tag = "download";
        DefaultHttpClient client = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(downloadUrl);
        List<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();
        parameters.add(new BasicNameValuePair("username", username));
        parameters.add(new BasicNameValuePair("password", password));
        parameters.add(new BasicNameValuePair("tag", tag));

        try {
            httpPost.setEntity(new UrlEncodedFormEntity(parameters));
        } catch (Exception e) {
            System.out.println(e);
        }

        try {
            HttpResponse response = client.execute(httpPost);
            jsonResult = inputStreamToString(response.getEntity().getContent()).toString();

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
        transactionList = makeList();
        HistoryListFragment.list.setAdapter(new MyBaseAdapter(context, transactionList));
    }

    public ArrayList<Transaction> makeList() {

        try {
            transactionList = new ArrayList<Transaction>();
            JSONObject jsonResponse = new JSONObject(jsonResult);
            JSONArray jsonMainNode = jsonResponse.optJSONArray(username);
            for (int i = 0; i < jsonMainNode.length(); i++) {
                JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
                String amount = jsonChildNode.optString("amount");
                String location = jsonChildNode.optString("location");
                String date = jsonChildNode.optString("date");
                String outgoing = jsonChildNode.optString("outgoing");
                String id = jsonChildNode.optString("id");
                transactionList.add(TransactionClient.createRecord(amount, location, date, outgoing, id));
            }
        } catch (JSONException e) {
//    	   Toast.makeText(getApplicationContext(), "Error" + e.toString(),
//    	     Toast.LENGTH_LONG).show();
            Log.d("MAKELIST", e.toString());
        }

        return transactionList;

        //transactions = transactionList;
        //list.setAdapter(new MyBaseAdapter(context, transactions));

    }



}