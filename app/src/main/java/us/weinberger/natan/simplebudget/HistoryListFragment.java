package us.weinberger.natan.simplebudget;

import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;

/**
 * Created by Natan on 6/21/2014.
 */
public class HistoryListFragment extends Fragment {
    public static ListView list;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_history_list, container, false);



        DownloadClient client = new DownloadClient(getActivity().getApplicationContext());
        client.execute();
        list = (ListView) v.findViewById(R.id.historyListView);
//        MyBaseAdapter adapter = new MyBaseAdapter(getActivity(), client.transactionList);
        //list.setAdapter(adapter);


        getActivity().getActionBar().setTitle("History");


        return v;
    }

}
