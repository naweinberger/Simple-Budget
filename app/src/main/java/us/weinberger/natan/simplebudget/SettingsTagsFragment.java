package us.weinberger.natan.simplebudget;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Natan on 6/22/2014.
 */
public class SettingsTagsFragment extends Fragment {
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_settings_tags, container, false);

        ListView tagsList = (ListView) v.findViewById(R.id.tags_list);

        ArrayList<Transaction> transactionList = new ArrayList<Transaction>();
        Transaction transaction = new Transaction();
        transaction.setDate("1/1/2014");
        transaction.setAmount("$0.00");
        transactionList.add(transaction);
        MyBaseAdapter adapter = new MyBaseAdapter(getActivity(), transactionList);
        tagsList.setAdapter(adapter);

        return v;
    }
}
