package us.weinberger.natan.simplebudget.fragments;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Calendar;

import us.weinberger.natan.simplebudget.util.Functions;
import us.weinberger.natan.simplebudget.R;
import us.weinberger.natan.simplebudget.Transaction;
import us.weinberger.natan.simplebudget.network.DownloadClient;
import us.weinberger.natan.simplebudget.util.PieGraph;
import us.weinberger.natan.simplebudget.util.PieSlice;


/**
 * Created by Natan on 7/5/2014.
 */
public class AnalysisPieChartFragment extends Fragment {
    static PieGraph pg;
    static ArrayList<Transaction> transactionList = new ArrayList<Transaction>();
    static int numMonths = 1;
    static ArrayList<String> tagsList = new ArrayList<String>();
    static ArrayList<Double> tagTotals = new ArrayList<Double>();
    static ArrayList<PieSlice> slicesList = new ArrayList<PieSlice>();
    Spinner numMonthsSpinner;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_analysis_pie_chart, container, false);

        DownloadClient client = new DownloadClient(getActivity().getApplicationContext(), "analysis");
        client.execute();

        pg = (PieGraph)v.findViewById(R.id.piechart);

        pg.setOnSliceClickedListener(new PieGraph.OnSliceClickedListener() {
            @Override
            public void onClick(int index) {

            }
        });

//            String tagsSerialized = getActivity().getSharedPreferences("SBPref", 0).getString("types", "");
//            tagsList = Functions.deserializeArray(tagsSerialized);
//            tagNames = new String[tagsList.size()];
//            Log.d("size", String.valueOf(tagsList.size()));

        numMonthsSpinner = (Spinner) v.findViewById(R.id.numMonthsPieSpinner);
        String[] numMonthsList = {"1 month", "3 months", "6 months", "9 months", "12 months"};
        ArrayAdapter<String> typesAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, numMonthsList);
        numMonthsSpinner.setAdapter(typesAdapter);

        numMonthsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        numMonths = 1;
                        break;
                    case 1:
                        numMonths = 3;
                        break;
                    case 2:
                        numMonths = 6;
                        break;
                    case 3:
                        numMonths = 9;
                        break;
                    case 4:
                        numMonths = 12;
                        break;
                }
                createChart(transactionList);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        return v;
    }

    public static void createChart(ArrayList<Transaction> newTransactionList) {
        tagsList.clear();
        tagTotals.clear();
        pg.removeSlices();
        int tempNumMonths = numMonths;
        transactionList = newTransactionList;

        Calendar cal = Calendar.getInstance();
        int month = cal.get(cal.MONTH) + 1;
        int year = cal.get(cal.YEAR);

        int currentMonth = month;
        int currentYear = year;

        int[] monthsToCheck = new int[numMonths];



        for (int i = 0; i < numMonths; i++) {

            if (month - i > 0) {
                monthsToCheck[i] = month - i;
            } else {
                monthsToCheck[i] = month - i + 12;
            }
        }


        for (int i = 0; i < transactionList.size(); i++) {
            if (tempNumMonths == 0) break;
            else if (transactionList.get(i).getNumMonth() == currentMonth && transactionList.get(i).getNumYear() == year) {
                int position;
                if (tagsList.contains(transactionList.get(i).getTag())) {
                    position = tagsList.indexOf(transactionList.get(i).getTag());
                }
                else {
                    tagsList.add(transactionList.get(i).getTag());
                    position = tagsList.size()-1;
                    tagTotals.add(position, 0.0);
                }


                if (transactionList.get(i).isOutgoing().equals("true")) {
                    tagTotals.set(position, tagTotals.get(position) + Functions.extractAmount(transactionList.get(i)));
                }
                else tagTotals.set(position, tagTotals.get(position) + (-1 * Functions.extractAmount(transactionList.get(i))));


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

        String[] colorArray = {"#FFBB33", "#42E0F5", "#99CC00", "#FF29F1", "#F51D1D"};
        //String[] monthArray = {"January","February","March","April","May","June","July","August","September","October","November","December"};

        for (int i = 0; i < tagsList.size(); i++) {
            //int monthArrayIndex = month-numMonths+i;
            //if (monthArrayIndex < 0) monthArrayIndex += 12;

            int colorIndex = i%5;
            pg.addSlice(new PieSlice(tagsList.get(i), Color.parseColor(colorArray[colorIndex]), (float) (double) tagTotals.get(i)));

        }





        //pg.draw(new Canvas(null));



    }

}