package us.weinberger.natan.simplebudget;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.AdapterView;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by Natan on 6/21/2014.
 */
public class HistoryListFragment extends Fragment {
    public static ListView list;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_history_list, container, false);

        
        final DownloadClient client = new DownloadClient(getActivity().getApplicationContext());
        client.execute();

        list = (ListView) v.findViewById(R.id.historyListView);
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           final int position, long id) {


                AlertDialog dialog = new AlertDialog.Builder(getActivity()).create();
                dialog.setTitle("Delete");
                dialog.setMessage("Would you like to delete this entry?");
                dialog.setCancelable(true);
                dialog.setButton(DialogInterface.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int buttonId) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    SharedPreferences prefs = getActivity().getSharedPreferences("SBPref", 0);
                                    String username = prefs.getString("logged_in_username", "");
                                    String password = prefs.getString("logged_in_password", "");
                                    TransactionClient transactionClient = new TransactionClient(getActivity());
                                    transactionClient.delete(client.transactionList.get(position), username, password);
                                } catch (Exception e) {
                                }
                            }
                        }).start();

                        //download();
                    }
                });

                dialog.setIcon(android.R.drawable.ic_dialog_alert);
                dialog.show();


                return false;
            }

        });


        getActivity().getActionBar().setTitle("History");


        return v;
    }



}
