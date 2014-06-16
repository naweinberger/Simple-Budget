package com.example.simplebudget;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.http.HttpResponse;
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

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity implements ActionBar.TabListener  {
	private ProgressDialog pDialog;
	EditText amountET, locationET;
	String amount, location, date, outgoing, id, jsonResult;
	Double tempAmount;
	ArrayList<Transaction> transactions = new ArrayList<Transaction>();
	ListView list;
	Context context = MainActivity.this;
	Transaction sample;
	DatePicker datepicker;
	Switch mySwitch;
	int icon;
	boolean visitedHistory = false;
	String user;
	
	
	public void clearEntries(View view) {
		amountET = (EditText) findViewById(R.id.amount);
		locationET = (EditText) findViewById(R.id.locationText);

		amountET.setText("");
		locationET.setText("");
		
		amountET.requestFocus();
		
	}
	//Called when submit button pressed in Transaction view
	public void newTransaction(View view) {
		Toast.makeText(this, "Transaction added.", Toast.LENGTH_SHORT).show();
		
		amountET = (EditText) findViewById(R.id.amount);
		locationET = (EditText) findViewById(R.id.locationText);
		datepicker = (DatePicker) findViewById(R.id.datePicker1);
		mySwitch = (Switch) findViewById(R.id.switch1);
		
		  //check the current state before we display the screen
		  if(mySwitch.isChecked()){
			  outgoing = "false";
			  icon = R.drawable.button_plus_green;
		  }
		  else {
			  outgoing = "true";
			  icon = R.drawable.button_minus_red;
		  }

		try {
		tempAmount = Double.parseDouble(amountET.getText().toString());
		}
		catch (Exception e) {
			Toast.makeText(this, "Please enter a valid amount.", Toast.LENGTH_SHORT).show();
		}
		amount = String.format("$%.02f", tempAmount);

		location = locationET.getText().toString();
		
		int month = datepicker.getMonth() + 1;
		int day = datepicker.getDayOfMonth();
		int year = datepicker.getYear();
		
		date = (String.valueOf(month) + "/" + String.valueOf(day) + "/" + String.valueOf(year));
		
		
		final Transaction newEntry = new Transaction();
		newEntry.amount = amount;
		newEntry.date = date;
		newEntry.location=location;
		newEntry.outgoing = outgoing;
		newEntry.icon = icon;
		//transactions.add(newEntry);
		
		new Thread(new Runnable() {

			@Override
			public void run() {
				try{
					TransactionClient.upload(newEntry, user);
					
				}
				catch(Exception e) {
					
				}
	
			}
			
		}).start();
		
		
		clearEntries(view);
		
	}
	
	
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        user = extras.getString("username");
        
        // Set up the action bar.
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        //actionBar.setDisplayShowTitleEnabled(false);
        //actionBar.setDisplayShowHomeEnabled(false);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // When swiping between different sections, select the corresponding
        // tab. We can also use ActionBar.Tab#select() to do this if we have
        // a reference to the Tab.
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by
            // the adapter. Also specify this Activity object, which implements
            // the TabListener interface, as the callback (listener) for when
            // this tab is selected.
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }
        

        
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in
        // the ViewPager.
    	
    	
    	
    	if (tab.getPosition() == 1) {
    		download();
    		
    		list = (ListView) findViewById(R.id.listView1);
            
            list.setOnItemLongClickListener(new OnItemLongClickListener() {

				@Override
				public boolean onItemLongClick(AdapterView<?> parent, View view,
						final int position, long id) {
			
					
				    AlertDialog dialog = new AlertDialog.Builder(context).create();
				    dialog.setTitle("Delete");
				    dialog.setMessage("Would you like to delete this entry?");
				    dialog.setCancelable(true);
				    dialog.setButton(DialogInterface.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {
				        public void onClick(DialogInterface dialog, int buttonId) {
				        	new Thread(new Runnable() {
				    			@Override
				    			public void run() {
				    				try{
				    					TransactionClient.delete(transactions.get(position), user);	
				    				}
				    				catch(Exception e) {		
				    				}
				    			}	
				    		}).start();
				            
				            download();
				        }
				    });
				    
				    dialog.setIcon(android.R.drawable.ic_dialog_alert);
				    dialog.show();
				    
					
					return false;
				}
            	
            }); 
            
            if (!visitedHistory) {
        		Toast.makeText(this, "To delete an entry, simply press and hold.", Toast.LENGTH_LONG).show();
        		visitedHistory = true;
        	}

    	}
    	
    	
        mViewPager.setCurrentItem(tab.getPosition());
        
    	
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    	if (tab.getPosition() == 0) {
    		amountET = (EditText) findViewById(R.id.amount);
    		amountET.requestFocus();
    	}
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            //return PlaceholderFragment.newInstance(position + 1);
        	
        	switch (position) {
        		case 0:
        			return new FragmentTransactionActivity();
        		case 1:
        			return new FragmentHistoryActivity();
        	}
        	
        	return null;
        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.title_section1).toUpperCase(l);
                case 1:
                    return getString(R.string.title_section2).toUpperCase(l);
            }
            return null;
        }
    }
    
    public class DownloadClient extends AsyncTask<Void, Void, Void>{
    	private static final String downloadUrl = "http://natan.weinberger.us/simplebudget/download.php";
    	
    	@Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(true);
            pDialog.show();

        }
        
    	@Override
    	protected Void doInBackground(Void... params) {
    		DefaultHttpClient client = new DefaultHttpClient();
    		HttpPost httpPost = new HttpPost(downloadUrl);
    		List<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();
    		parameters.add(new BasicNameValuePair("user", user));
    		
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
    		
    		try {
    			HttpResponse response = client.execute(httpPost);
    			jsonResult = inputStreamToString(response.getEntity().getContent()).toString();
    		}
    		catch (Exception e) {
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
    		   }
    		 
    		   catch (IOException e) {
    		    // e.printStackTrace();
    		    Toast.makeText(getApplicationContext(),
    		      "Error..." + e.toString(), Toast.LENGTH_LONG).show();
    		   }
    		   return answer;
    		  }
    		 
    		  @Override
    		  protected void onPostExecute(Void v) {
    		   makeList();
    		   if (pDialog.isShowing())
                   pDialog.dismiss();
    		  }
    		 }
    
    public void makeList() {
    	ArrayList<Transaction> transactionList = new ArrayList<Transaction>();
    	 
    	  try {
    		  Log.d("MAKELIST", user);
    	   JSONObject jsonResponse = new JSONObject(jsonResult);
    	   Log.d("MAKELIST", jsonResponse.toString());
    	   JSONArray jsonMainNode = jsonResponse.optJSONArray(user);
    	   Log.d("MAKELIST", jsonMainNode.toString());
    	   for (int i = 0; i < jsonMainNode.length(); i++) {
    	    JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
    	    amount = jsonChildNode.optString("amount");
    	    location = jsonChildNode.optString("location");
    	    date = jsonChildNode.optString("date");
    	    outgoing = jsonChildNode.optString("outgoing");
    	    id = jsonChildNode.optString("id");
    	    transactionList.add(createRecord(amount, location, date, outgoing, id));
    	   }
    	  } catch (JSONException e) {
//    	   Toast.makeText(getApplicationContext(), "Error" + e.toString(),
//    	     Toast.LENGTH_LONG).show();
    	   Log.d("MAKELIST", e.toString());
    	  }
    	  
    	  transactions = transactionList;
    	  list.setAdapter(new MyBaseAdapter(context, transactions));
    	  
    }
    
    
    private Transaction createRecord(String amount, String location, String date, String outgoing, String id) {
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

 
    private void download() {
    	DownloadClient mClient = new DownloadClient();
    	mClient.execute();
    }

}
