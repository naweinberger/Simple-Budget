package us.weinberger.natan.simplebudget;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {
	EditText usernameET, passwordET;
	String jsonResult, username, password;
	boolean validUser = false;
	Context context = LoginActivity.this;
	boolean success = false;
    CheckBox rememberMe;
    boolean rememberMeChecked = false;
    SharedPreferences pref;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_login);


	}

    @Override
    public void onBackPressed()
    {
        this.finish();
        overridePendingTransition (R.anim.left_slide_in, R.anim.left_slide_out);
    }

	public void login(View view) {
		usernameET = (EditText) findViewById(R.id.usernameET);
		passwordET = (EditText) findViewById(R.id.passwordET);
		
		username = usernameET.getText().toString();
		password = passwordET.getText().toString();

		String tag = "login";

        if (username.contains(";") || password.contains(";")) {
            Toast.makeText(context, "Invalid username or password.", Toast.LENGTH_SHORT).show();
            return;
        }
		
		LoginClient client = new LoginClient();
		client.execute(username, password, tag);

		
	}
	
	public void pushRegister(View view) {
		Intent intent = new Intent(this, RegisterActivity.class);
		startActivity(intent);
	}
	public class LoginClient extends AsyncTask<String, Void, Integer> {
		private final String indexUrl = "http://natan.weinberger.us/simplebudget/index.php";

		@Override
		protected Integer doInBackground(String... params) {
			String mUser = params[0];
			String mPass = params[1];
			String mTag = params[2];
			
			List<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();
			parameters.add(new BasicNameValuePair("username", mUser));
			parameters.add(new BasicNameValuePair("password", mPass));
			parameters.add(new BasicNameValuePair("tag", mTag));
			DefaultHttpClient client = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(indexUrl);
			try{
				httpPost.setEntity(new UrlEncodedFormEntity(parameters));
			}
			catch(Exception e){
				System.out.println(e);
			}
			
			
			try {
				HttpResponse response = client.execute(httpPost);
				jsonResult = JsonReader.inputStreamToString(response.getEntity().getContent()).toString();
				
				if (checkUsername()) {
                    return Integer.valueOf(0);

					
				}
				
				else {
                    return Integer.valueOf(1);
                }
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}

        @Override
        protected void onPostExecute(Integer code) {
            if (code.intValue() == 0) { //value 0 means success
                Intent intent = new Intent(context, HomeActivity.class);

                pref = getApplicationContext().getSharedPreferences("SBPref", 0);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("logged_in_username", ""+username);
                editor.putString("logged_in_password", ""+password);
                editor.commit();
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.right_slide_in, R.anim.right_slide_out);
            }

            else {
                Toast.makeText(context, "Incorrect username or password!", Toast.LENGTH_SHORT).show();
            }
        }
		
		
		public boolean checkUsername() {
		 try {
	    	   JSONObject jsonResponse = new JSONObject(jsonResult);
//	    	   Log.d("checkUsernameFailure", String.valueOf(jsonResponse));
	    	   JSONObject jsonMainNode = jsonResponse.optJSONObject("users");
//	    	   Log.d("checkUsernameFailure", String.valueOf(jsonMainNode));
	    	   String result = jsonMainNode.optString("reply");
//	    	   Log.d("checkUsernameFailure", result);
	    	     
	    	   if (result.equals("true")) {
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
