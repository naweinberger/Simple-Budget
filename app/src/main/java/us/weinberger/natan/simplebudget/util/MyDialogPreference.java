package us.weinberger.natan.simplebudget.util;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Environment;
import android.preference.DialogPreference;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import us.weinberger.natan.simplebudget.R;
import us.weinberger.natan.simplebudget.Transaction;
import us.weinberger.natan.simplebudget.network.DownloadClient;
import us.weinberger.natan.simplebudget.network.EmailClient;

/**
 * Created by Natan on 7/6/2014.
 */
public class MyDialogPreference extends DialogPreference implements DialogInterface.OnClickListener{
    static String email = "";
    static EditText exportEmail;
    static ArrayList<Transaction> transactionList = new ArrayList<Transaction>();
    Context c;

    public MyDialogPreference(Context context, AttributeSet attrs) {
        super(context, attrs);

        c = context;
        setPersistent(false);
        setTitle("Export data");
        setDialogLayoutResource(R.layout.dialog_export_data);
        setPositiveButtonText("Send");





    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if (which == DialogInterface.BUTTON_POSITIVE) {

            DownloadClient downloadClient = new DownloadClient(c, "export");
            downloadClient.execute();
        }
    }

    @Override
    protected void onBindDialogView(View view) {
        exportEmail = (EditText) view.findViewById(R.id.export_email);
        EmailClient emailClient = new EmailClient(getContext());
        emailClient.execute();

        exportEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                email = exportEmail.getText().toString();
            }
        });




    }

    public static void setEmail(String newEmail) {
        exportEmail.setText(newEmail);

    }

    public static void setTransactionList(ArrayList<Transaction> newList, Context c) {
        transactionList = newList;
        String separator = ",";

        String output = "Month,Day,Year,Amount,Location,Tag,Outgoing\n";

        for (Transaction transaction : transactionList) {
            output += transaction.getMonth() + separator + transaction.getDay() + separator +
                    transaction.getYear() + separator + transaction.getAmount() + separator +
                    transaction.getLocation() + separator + transaction.getTag() + separator +
                    transaction.isOutgoing() + "\n";
        }

        File outputFile = null;
        File root = Environment.getExternalStorageDirectory();
        if (root.canWrite()) {
            File dir = new File(root.getAbsolutePath() + "/SimpleBudgetExportData");
            dir.mkdirs();
            outputFile = new File(dir, "SimpleBudgetExportData.csv");
            FileOutputStream outputStream = null;

            try {
                outputStream = new FileOutputStream(outputFile);
            }
            catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            try {
                outputStream.write(output.getBytes());
            }
            catch (IOException e) {
                e.printStackTrace();
            }

            try {
                outputStream.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }

        Uri u1 = Uri.fromFile(outputFile);

        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
        sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Your Simple Budget data");
        sendIntent.putExtra(Intent.EXTRA_STREAM, u1);
        sendIntent.setType("text/html");
        c.startActivity(sendIntent);
    }
}