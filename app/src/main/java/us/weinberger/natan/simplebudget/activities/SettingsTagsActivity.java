package us.weinberger.natan.simplebudget.activities;

import android.app.ListActivity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import com.mobeta.android.dslv.DragSortController;
import com.mobeta.android.dslv.DragSortListView;

import java.util.ArrayList;

import us.weinberger.natan.simplebudget.R;
import us.weinberger.natan.simplebudget.util.Functions;

/**
 * Created by Natan on 6/22/2014.
 */
public class SettingsTagsActivity extends ListActivity {
    DragSortListView listView;
    ArrayAdapter<String> adapter;
    ArrayList<String> list = new ArrayList<String>();


    private DragSortListView.DropListener onDrop = new DragSortListView.DropListener()
    {
        @Override
        public void drop(int from, int to)
        {
            if (from != to)
            {
                String item = adapter.getItem(from);
                adapter.remove(item);
                adapter.insert(item, to);
                ArrayList<String> newTypesListOnClick = new ArrayList<String>();
                for (int i = 0; i < adapter.getCount(); i++) {
                    newTypesListOnClick.add(adapter.getItem(i));
                }
                String newTypesListOnDrop = Functions.serializeArray(newTypesListOnClick);
                SharedPreferences prefs = getSharedPreferences("SBPref", 0);
                SharedPreferences.Editor editor = prefs.edit();
                editor.remove("types");
                editor.putString("types", newTypesListOnDrop);
                editor.commit();
            }
        }
    };


    private DragSortListView.RemoveListener onRemove = new DragSortListView.RemoveListener()
    {
        @Override
        public void remove(int which)
        {
            adapter.remove(adapter.getItem(which));
            adapter.notifyDataSetChanged();
            SharedPreferences prefs = getSharedPreferences("SBPref", 0);
            SharedPreferences.Editor editor = prefs.edit();
            ArrayList<String> newTypesList = new ArrayList<String>();
            for (int i = 0; i < adapter.getCount(); i++) {
                newTypesList.add(adapter.getItem(i));
            }
            editor.remove("types");
            editor.putString("types", Functions.serializeArray(newTypesList));
            editor.commit();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_tags);

        listView = (DragSortListView) findViewById(android.R.id.list);

        SharedPreferences prefs = getSharedPreferences("SBPref", 0);
        String tags = prefs.getString("types", "");
        list = Functions.deserializeArray(tags);
        adapter = new ArrayAdapter<String>(this,
                R.layout.tag_row_layout, R.id.tagName, list);
        listView.setAdapter(adapter);
        listView.setDropListener(onDrop);
        listView.setRemoveListener(onRemove);

        DragSortController controller = new DragSortController(listView);
        controller.setDragHandleId(R.id.dragImageView);
        controller.setClickRemoveId(R.id.removeTagImageView);
        controller.setRemoveEnabled(true);
        controller.setSortEnabled(true);
        controller.setDragInitMode(1);
        //controller.setRemoveMode(removeMode);

        listView.setFloatViewManager(controller);
        listView.setOnTouchListener(controller);
        listView.setDragEnabled(true);

        Button addTagBtn = (Button) findViewById(R.id.add_tag_submit);
        final EditText addTagEditText = (EditText) findViewById(R.id.add_tag_edittext);

        addTagBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newTag = addTagEditText.getText().toString();
                adapter.insert(newTag, 0);
                adapter.notifyDataSetChanged();



                SharedPreferences prefs = getSharedPreferences("SBPref", 0);
                SharedPreferences.Editor editor = prefs.edit();
                ArrayList<String> newTypesListOnClick = new ArrayList<String>();
                for (int i = 0; i < adapter.getCount(); i++) {
                    newTypesListOnClick.add(adapter.getItem(i));
                }
                editor.remove("types");
                editor.putString("types", Functions.serializeArray(newTypesListOnClick));
                editor.commit();
                addTagEditText.setText("");

            }
        });


//
//        Button submitButton = (Button) findViewById(R.id.submitButtonTagsActivity);
//        submitButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String newTypesList = Functions.serializeArray(list);
//                SharedPreferences prefs = getSharedPreferences("SBPref", 0);
//                SharedPreferences.Editor editor = prefs.edit();
//                editor.remove("types");
//                editor.putString("types", newTypesList);
//                editor.commit();
//                onBackPressed();
//            }
//        });




    }



    @Override
    public DragSortListView getListView() {
        return (DragSortListView) super.getListView();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}