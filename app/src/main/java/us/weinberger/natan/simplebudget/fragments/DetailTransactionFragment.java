package us.weinberger.natan.simplebudget.fragments;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.Calendar;

import us.weinberger.natan.simplebudget.util.Functions;
import us.weinberger.natan.simplebudget.activities.HomeActivity;
import us.weinberger.natan.simplebudget.R;
import us.weinberger.natan.simplebudget.Transaction;
import us.weinberger.natan.simplebudget.network.TransactionClient;

/**
 * Created by Natan on 6/12/2014.
 */
public class DetailTransactionFragment extends Fragment {
    static TextView amountDetailTV, locationTV, dateTV, typeTV;
    static String amountString;
    private static int mYear, mMonth, mDay;
    static AutoCompleteTextView locationET;
    static String date = "test";
    static Button datePickerButton;
    static Spinner typeSpinner;
    ImageButton completeTransactionButton;
    static ToggleButton outgoingToggle;
    static boolean toggleChecked = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        amountString = getArguments().getString("AMT");
        View v = inflater.inflate(R.layout.detail_transaction, container, false);

        TextView addPurchaseTV = (TextView) v.findViewById(R.id.addPurchaseTV);
        amountDetailTV = (TextView) v.findViewById(R.id.amountDetailTV);
        locationTV = (TextView) v.findViewById(R.id.DetailTransactionLocationTextView);
        dateTV = (TextView) v.findViewById(R.id.DetailTransactionDateTextView);
        typeTV = (TextView) v.findViewById(R.id.DetailTransactionTypeTextView);
        //outgoingTV = (TextView) v.findViewById(R.id.DetailTransactionOutgoingTextView);
        typeSpinner = (Spinner) v.findViewById(R.id.typeSpinner);
        locationET = (AutoCompleteTextView) v.findViewById(R.id.AutoCompleteTextViewLocation);




        SharedPreferences prefs = getActivity().getSharedPreferences("SBPref", 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.commit();

        ArrayList<String> placesList = new ArrayList<String>();
        String serializedPlacesArray = getActivity().getApplicationContext().getSharedPreferences("SBPref", 0).getString("places", "");
        placesList = Functions.deserializeArray(serializedPlacesArray);
        ArrayAdapter<String> placesAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, placesList);

        locationET.setAdapter(placesAdapter);

        ArrayList<String> typesList = new ArrayList<String>();
        String serializedTypesArray = getActivity().getApplicationContext().getSharedPreferences("SBPref", 0).getString("types", "");
        typesList = Functions.deserializeArray(serializedTypesArray);
        ArrayAdapter<String> typesAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, typesList);
        typeSpinner.setAdapter(typesAdapter);

        TextView[] textViews = {addPurchaseTV, amountDetailTV, locationTV, dateTV, typeTV};

        Typeface roboFont = Typeface.createFromAsset(getActivity().getAssets(), "Roboto-Thin.ttf");
        for (TextView textView : textViews) textView.setTypeface(roboFont);

        amountDetailTV.setText(amountString);

        completeTransactionButton = (ImageButton) v.findViewById(R.id.completeTransactionButton);
        completeTransactionButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                completeTransaction(v);
            }
        });

        outgoingToggle = (ToggleButton) v.findViewById(R.id.outgoingToggleButton);
        outgoingToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    toggleChecked = true;
                }

                else {
                    toggleChecked = false;
                }
            }
        });


        datePickerButton = (Button) v.findViewById(R.id.datePickerButton);
        datePickerButton.setTypeface(roboFont);

        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        datePickerButton.setText(Functions.formDate(mDay, mMonth+1, mYear));
        datePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        datePickerButton.setText(Functions.formDate(dayOfMonth, monthOfYear+1, year));
                        mYear = year;
                        mMonth = monthOfYear;
                        mDay = dayOfMonth;
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        return v;
    }

    /*
    public static void setAmountDetailText(String text) {
        amountDetailTV.setText(text);
    }
    */



    public static Transaction createTransaction() {
        Transaction transaction = new Transaction();
        transaction.setAmount(amountString);
        transaction.setLocation(locationET.getText().toString());
        transaction.setDay(Integer.toString(mDay));
        transaction.setMonth(Integer.toString(mMonth+1));
        transaction.setYear(Integer.toString(mYear));
        transaction.setTag(typeSpinner.getSelectedItem().toString());
        if (toggleChecked) {
            transaction.setOutgoing("true");
        }
        else {
            transaction.setOutgoing("false");
        }
        return transaction;
    }

    public void completeTransaction(View view) {
        final Transaction transaction = createTransaction();
        final String username = getActivity().getApplicationContext().getSharedPreferences("SBPref", 0).getString("logged_in_username", "");
        final String password = getActivity().getApplicationContext().getSharedPreferences("SBPref", 0).getString("logged_in_password", "");

        new Thread(new Runnable() {

            @Override
            public void run() {
                try{
                    boolean success = TransactionClient.upload(transaction, username, password);

                    if (success) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getActivity().getApplicationContext(), "Transaction added.", Toast.LENGTH_SHORT).show();
                            }
                        });

                        Intent intent = new Intent(getActivity(), HomeActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        getActivity().overridePendingTransition(R.anim.right_slide_in, R.anim.right_slide_out);
                        getActivity().finish();

                    }
                    else {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getActivity().getApplicationContext(), "Network error. Please try again.", Toast.LENGTH_LONG).show();
                            }
                        });

                    }
                }
                catch(Exception e) {
                }
            }

        }).start();

    }


}