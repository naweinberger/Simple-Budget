package us.weinberger.natan.simplebudget;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends Activity {
	String jsonResult, username, password, email;
	Context context = RegisterActivity.this;
	EditText usernameET, emailET, passwordET;
    SharedPreferences pref;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_register);
        
	}
	
	@Override
	public void onBackPressed() 
	{
	    this.finish();
	    overridePendingTransition (R.anim.left_slide_in, R.anim.left_slide_out);
	}
	
	public void register(View view) {
		usernameET = (EditText) findViewById(R.id.usernameET);
		passwordET = (EditText) findViewById(R.id.passwordET);
		emailET = (EditText) findViewById(R.id.emailET);
		
		username = usernameET.getText().toString();
		password = passwordET.getText().toString();
		email = emailET.getText().toString();
		String tag = "register";

        if (username.contains(";") || password.contains(";")) {
            Toast.makeText(context, "Invalid username or password.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.length() < 6) {
            Toast.makeText(context, "Password must be at least six characters long.", Toast.LENGTH_SHORT).show();
            return;
        }

        else if(!email.contains("@") || !email.contains(".") || email.contains(";")) {
            Toast.makeText(context, "Invalid email!", Toast.LENGTH_SHORT).show();
            return;
        }

		
		RegisterClient client = new RegisterClient();
		client.execute(username, password, email, tag);
	}
	
	private class RegisterClient extends AsyncTask<String, Void, Integer> {
		private final String indexUrl = "http://natan.weinberger.us/simplebudget/index.php";
		@Override
        //This method returns Integers that refer to outcomes:
        //return 0: process registration
        //return 1: username exists
        //return 2: invalid password
        //return 3: invalid email
		protected Integer doInBackground(String... params) {
			String mUser = params[0];
			String mPass = params[1];
			String mEmail = params[2];
			String mTag = params[3];
			
			List<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();
			parameters.add(new BasicNameValuePair("username", mUser));
			parameters.add(new BasicNameValuePair("password", mPass));
			parameters.add(new BasicNameValuePair("email", mEmail));
			parameters.add(new BasicNameValuePair("tag", mTag));
			DefaultHttpClient client = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(indexUrl);
			try{
				httpPost.setEntity(new UrlEncodedFormEntity(parameters));
			}
			catch(Exception e){
				System.out.println(e);
			}
			
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			try {
				HttpResponse response = client.execute(httpPost);
				jsonResult = JsonReader.inputStreamToString(response.getEntity().getContent()).toString();
				
				if (checkSuccess()) {
                    return Integer.valueOf(0);

				}
				
				else {
                    return Integer.valueOf(1);
                    /*
					Intent intent = new Intent(context, LoginActivity.class);
					startActivity(intent);
					finish();
					*/
				}
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

            return Integer.valueOf(1);
		}

        @Override
        protected void onPostExecute(Integer success) {
            if (success.intValue() == 0) {
                pref = getApplicationContext().getSharedPreferences("SBPref", 0);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("logged_in_username", ""+username);
                editor.putString("logged_in_password", ""+password);
                editor.commit();
                Intent intent = new Intent(context, HomeActivity.class);
                intent.putExtra("username", username);
                startActivity(intent);
                finish();
            }

            else {
                Toast.makeText(context, "That username already exists!", Toast.LENGTH_SHORT).show();
            }
        }
		
		
		public boolean checkSuccess() {
		 try {
	    	   JSONObject jsonResponse = new JSONObject(jsonResult);
	    	   Log.d("NATAN REPLY", jsonResponse.toString());
	    	   JSONObject jsonMainNode = jsonResponse.optJSONObject("users");
	    	   String result = jsonMainNode.optString("reply");
	    	   Log.d("NATAN REPLY", result);
	    	     
	    	   if (result.equals("success")) {
	    	    	return true;
	    	    }
	    	   else {
	    	    	return false;
	    	    }
	    	   
	    	  } catch (Exception e) {
	    	   Log.d("checkUsernameFailure", String.valueOf(e));
	    	  }
		 return false;
		 
		}
		
	}
}
