package us.weinberger.natan.simplebudget.network;

import android.content.Context;
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

import us.weinberger.natan.simplebudget.Transaction;


public class TransactionClient  {
	private static final String uploadUrl = "http://natan.weinberger.us/simplebudget/index.php";
	private static final String downloadUrl = "http://natan.weinberger.us/simplebudget/download.php";
	private static final String deleteUrl = "http://natan.weinberger.us/simplebudget/index.php";
    static String response;
    private static Context context;

    public TransactionClient(Context c) {
        context = c;
    }
	
	public static boolean upload(Transaction newEntry, String username, String password) {

		List<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();
		parameters.add(new BasicNameValuePair("username", username));
        parameters.add(new BasicNameValuePair("password", password));
		parameters.add(new BasicNameValuePair("amount", newEntry.getAmount()));
		parameters.add(new BasicNameValuePair("location", newEntry.getLocation()));
        parameters.add(new BasicNameValuePair("tag", newEntry.getTag()));
		parameters.add(new BasicNameValuePair("day", newEntry.getDay()));
        parameters.add(new BasicNameValuePair("month", newEntry.getMonth()));
        parameters.add(new BasicNameValuePair("year", newEntry.getYear()));
		parameters.add(new BasicNameValuePair("outgoing", newEntry.isOutgoing()));
        parameters.add(new BasicNameValuePair("type", "upload"));

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
			response = client.execute(httpPost, responseHandler);


		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

        if (response.equals("success")) {
            return true;
        }
        else {
            return false;
        }

	}
	
	public static void delete(Transaction newEntry, String username, String password) {
        Log.d("TEST", "1");
		List<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();
		parameters.add(new BasicNameValuePair("username", username));
        parameters.add(new BasicNameValuePair("password", password));
		parameters.add(new BasicNameValuePair("id", newEntry.getId()));
        parameters.add(new BasicNameValuePair("type", "delete"));
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
            //if response = success... else error
            final DownloadClient downloadClient = new DownloadClient(context, "history");
            downloadClient.execute();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

    public static Transaction createRecord(String amount, String location, String outgoing, String tag, String id, String day, String month, String year) {
        Transaction newTransaction = new Transaction();
        newTransaction.setAmount(amount);
        newTransaction.setLocation(location);
        newTransaction.setDay(day);
        newTransaction.setMonth(month);
        newTransaction.setYear(year);
        newTransaction.setOutgoing(outgoing);
        newTransaction.setTag(tag);
        newTransaction.setId(id);
        if (newTransaction.isOutgoing().equals("true")) {
            newTransaction.setIcon("#ad0202");
        }

        else {
            newTransaction.setIcon("#289404");
        }
        return newTransaction;
    }



}
