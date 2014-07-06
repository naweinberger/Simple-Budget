package us.weinberger.natan.simplebudget;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Calendar;


/**
 * Created by Natan on 7/5/2014.
 */
public class AnalysisPieChartFragment extends Fragment {
        static PieGraph pg;
        static ArrayList<Transaction> transactionList = new ArrayList<Transaction>();
        static int numMonths = 1;
        static ArrayList<String> tagsList = new ArrayList<String>();
        static ArrayList<PieSlice> slicesList = new ArrayList<PieSlice>();
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            final View v = inflater.inflate(R.layout.fragment_analysis_pie_chart, container, false);

            DownloadClient client = new DownloadClient(getActivity().getApplicationContext(), "analysis");
            client.execute();

            pg = (PieGraph)v.findViewById(R.id.piechart);

            String tagsSerialized = getActivity().getSharedPreferences("SBPref", 0).getString("tags", "");
            tagsList = Functions.deserializeArray(tagsSerialized);

            return v;
        }

    public static void createChart(ArrayList<Transaction> newTransactionList) {


        pg.removeSlices();
        int tempNumMonths = numMonths;
        transactionList = newTransactionList;

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

        double[] tagTotals = new double[tagsList.size()];
        for (int i = 0 ; i < tagTotals.length; i++) {
            tagTotals[i] = 0;
        }

        for (int i = 0; i < transactionList.size(); i++) {
            if (tempNumMonths == 0) break;
            else if (transactionList.get(i).getNumMonth() == currentMonth && transactionList.get(i).getNumYear() == year) {
                if (tagsList.contains(transactionList.get(i).getTag())) {
                    int position = tagsList.indexOf(transactionList.get(i).getTag());
                    if (transactionList.get(i).isOutgoing().equals("true")) tagTotals[i] += Double.valueOf(transactionList.get(i).getAmount());
                    else tagTotals[i] -= Double.valueOf(transactionList.get(i).getAmount());
                }


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
        String[] monthArray = {"January","February","March","April","May","June","July","August","September","October","November","December"};

        for (int i = 0; i < numMonths; i++) {
            int monthArrayIndex = month-numMonths+i;
            if (monthArrayIndex < 0) monthArrayIndex += 12;

            int colorIndex = i%3;

            slicesList.add(i, new PieSlice("title", Color.parseColor(colorArray[colorIndex]), 40.00f));

        }

        pg.setSlices(slicesList);



    }

}
