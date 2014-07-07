package us.weinberger.natan.simplebudget.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;

import us.weinberger.natan.simplebudget.R;
import us.weinberger.natan.simplebudget.network.EmailClient;

/**
 * Created by Natan on 7/6/2014.
 */
public class MyDialogPreference extends DialogPreference {
    static String email = "";
    static EditText exportEmail;
    public MyDialogPreference(Context context, AttributeSet attrs) {
        super(context, attrs);

        setPersistent(false);
        setTitle("Export data");
        setDialogLayoutResource(R.layout.dialog_export_data);


    }

    @Override
    protected void onBindDialogView(View view) {
        exportEmail = (EditText) view.findViewById(R.id.export_email);
        EmailClient emailClient = new EmailClient(getContext());
        emailClient.execute();


    }

    public static void setEmail(String newEmail) {
        exportEmail.setText(newEmail);
    }

}