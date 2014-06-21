package us.weinberger.natan.simplebudget;

import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class TransactionClient  {
	private static final String uploadUrl = "http://natan.weinberger.us/simplebudget/index.php";
	private static final String downloadUrl = "http://natan.weinberger.us/simplebudget/download.php";
	private static final String deleteUrl = "http://natan.weinberger.us/simplebudget/delete.php";

	
	public static void upload(Transaction newEntry, String username, String password) {

		List<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();
		parameters.add(new BasicNameValuePair("username", username));
        parameters.add(new BasicNameValuePair("password", password));
		parameters.add(new BasicNameValuePair("amount", newEntry.getAmount()));
		parameters.add(new BasicNameValuePair("location", newEntry.getLocation()));
		parameters.add(new BasicNameValuePair("date", newEntry.getDate()));
		//parameters.add(new BasicNameValuePair("outgoing", String.valueOf(newEntry.isOutgoing())));
        parameters.add(new BasicNameValuePair("tag", "upload"));

		DefaultHttpClient client = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(uploadUrl);
		try{
			httpPost.setEntity(new UrlEncodedFormEntity(parameters));
		}
		catch(Exception e){
			System.out.println(e);
		}
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		try {
			String response = client.execute(httpPost, responseHandler);
            Log.d("transaction stuff", response);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void delete(Transaction newEntry, String user) {
		List<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();
		parameters.add(new BasicNameValuePair("user", user));
		parameters.add(new BasicNameValuePair("id", newEntry.getId()));
		DefaultHttpClient client = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(deleteUrl);
		try{
			httpPost.setEntity(new UrlEncodedFormEntity(parameters));
		}
		catch(Exception e){
			System.out.println(e);
		}
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		try {
			String response = client.execute(httpPost, responseHandler);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

    public static Transaction createRecord(String amount, String location, String date, String outgoing, String id) {
        Transaction newTransaction = new Transaction();
        newTransaction.setAmount(amount);
        newTransaction.setLocation(location);
        newTransaction.setDate(date);
        newTransaction.setOutgoing(outgoing);
        newTransaction.setId(id);
        if (newTransaction.isOutgoing().equals("true")) {
            newTransaction.setIcon(R.drawable.button_minus_red);
        }

        else {
            newTransaction.setIcon(R.drawable.button_plus_green);
        }
        return newTransaction;
    }



}
