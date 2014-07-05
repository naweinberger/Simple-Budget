package us.weinberger.natan.simplebudget;

import android.graphics.Color;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by Natan on 7/4/2014.
 */
public class AnalysisBarGraphFragment extends Fragment {
    //public static ArrayList<Transaction> transactionList = new ArrayList<Transaction>();
    private static int numMonths = 3;
    static BarGraph g;
    static ArrayList<Bar> points = new ArrayList<Bar>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_analysis_bar_graph, container, false);
        assert v != null;

        DownloadClient client = new DownloadClient(getActivity().getApplicationContext(), "analysis");
        client.execute();


        g = (BarGraph)v.findViewById(R.id.bargraph);
        assert g != null;

        g.setBars(points);

        g.setOnBarClickedListener(new BarGraph.OnBarClickedListener(){

            @Override
            public void onClick(int index) {

            }

        });



        return v;
    }

    public static void createChart(ArrayList<Transaction> transactionList) {
        points.clear();
        int tempNumMonths = numMonths;

        Calendar cal = Calendar.getInstance();
        int month = cal.get(cal.MONTH) + 1;
        int year = cal.get(cal.YEAR);

        int currentMonth = month;

        int[] monthsToCheck = new int[numMonths];



        for (int i = 0; i < numMonths; i++) {

            if (month - i > 0) {
                monthsToCheck[i] = month - i;
            } else {
                monthsToCheck[i] = month - i + 12;
            }
        }

        double[] monthlyTotals = new double[numMonths];
        for (int i = 0 ; i < monthlyTotals.length; i++) {
            monthlyTotals[i] = 0;
        }

        for (int i = 0; i < transactionList.size(); i++) {
            Log.d("numMonths ", String.valueOf(numMonths));
            Log.d("currentMonth ", String.valueOf(currentMonth));
            Log.d("getNumMonth ", String.valueOf(transactionList.get(i).getNumMonth()));
            Log.d("year ", String.valueOf(year));
            Log.d("getNumYear ", String.valueOf(transactionList.get(i).getNumYear()));
            if (tempNumMonths == 0) break;
            else if (transactionList.get(i).getNumMonth() == currentMonth && transactionList.get(i).getNumYear() == year) {
                monthlyTotals[tempNumMonths-1] += Functions.extractAmount(transactionList.get(i));
            }
            else {
                if (i > 0) i--;
                else i = 0;
                tempNumMonths--;
                if (currentMonth == 1) {
                    currentMonth = 12;
                    year--;
                }
                else currentMonth--;
            }
        }

        String[] colorArray = {"#FFBB33", "#42E0F5", "#99CC00"};
        String [] monthArray = {"January","February","March","April","May","June","July","August","September","October","November","December"};

        for (int i = 0; i < numMonths; i++) {
            points.add(new Bar(Color.parseColor(colorArray[i]), monthArray[month-numMonths+i], (float)monthlyTotals[i]));
        }

        g.setBars(points);
        g.setUnit("$");
        g.appendUnit(false);


    }

//    public static void setList(ArrayList<Transaction> newList) {
//        transactionList = newList;
//    }

    public ArrayList<ArrayList<Integer>> extractValues(ArrayList<Transaction> list) {
        ArrayList<ArrayList<Integer>> values = new ArrayList<ArrayList<Integer>>();

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        int month = cal.get(Calendar.MONTH);

        for (int i = 0; i < list.size(); i++) {
            if (Integer.valueOf(list.get(i).getMonth()) < (month - (numMonths - 1))) {
                break;
            }



        }

        return values;
    }
}
