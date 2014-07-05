package us.weinberger.natan.simplebudget;

import android.graphics.Color;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by Natan on 7/4/2014.
 */
public class AnalysisBarGraphFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_analysis_bar_graph, container, false);
        assert v != null;

        ArrayList<Bar> points = new ArrayList<Bar>();
        Bar d = new Bar();
        d.setColor(Color.parseColor("#99CC00"));
        d.setName("Test1");
        d.setValue(10);
        Bar d2 = new Bar();
        d2.setColor(Color.parseColor("#FFBB33"));
        d2.setName("Test2");
        d2.setValue(20);
        points.add(d);
        points.add(d2);

        BarGraph g = (BarGraph)v.findViewById(R.id.bargraph);
        assert g != null;
        g.setUnit("€");
        g.appendUnit(true);
        g.setBars(points);

        g.setOnBarClickedListener(new BarGraph.OnBarClickedListener(){

            @Override
            public void onClick(int index) {

            }

        });

        return v;
    }
}
