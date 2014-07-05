package us.weinberger.natan.simplebudget;

import android.app.Activity;
import android.util.Log;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Natan on 6/21/2014.
 */
public class Functions {

    public static String serializeArray(ArrayList<String> list) {
        String serialized = "";
        for (int i = 0; i < list.size(); i++) {
            serialized += list.get(i) + ";";
        }
        return serialized;
    }

    public static ArrayList<String> deserializeArray(String serialized) {
        ArrayList<String> list = new ArrayList<String>();
        int place = 0;
        int beginningOfWord = 0;
        while (place < serialized.length()) {
            if (serialized.charAt(place) == ';') {
                list.add(serialized.substring(beginningOfWord, place));
                beginningOfWord = place+1;
                place++;
            }
            else {
                place++;
            }
        }
        return list;
    }

    public static String addToSerializedArray (String array, String newStr) {
        return array+newStr+";";
    }

    public static String removeFromSerializedArray(String array, String removeStr) {
        if (array.length() > removeStr.length()) {
            for (int i = 0; i < array.length()-removeStr.length(); i++) {
                if (array.substring(i, i+removeStr.length()+2).equals(";"+removeStr+";")) {
                    String returnString = array.substring(0, i) + array.substring(i+removeStr.length()+1);
                    return returnString;
                }
            }
        }

        return array;
    }

    public static String formDate(int day, int month, int year) {
        String date = String.valueOf(month) + "/" + String.valueOf(day) + "/" + String.valueOf(year);
        return date;
    }

    public static ArrayList<Transaction> sortDates(ArrayList<Transaction> transactionList) {
        for (int i = 0; i < transactionList.size(); i++) {
            int itemMonth = Calendar.JANUARY;
            switch (transactionList.get(i).getNumMonth()) {
                case 1:
                    itemMonth = Calendar.JANUARY;
                    break;
                case 2:
                    itemMonth = Calendar.FEBRUARY;
                    break;
                case 3:
                    itemMonth = Calendar.MARCH;
                    break;
                case 4:
                    itemMonth = Calendar.APRIL;
                    break;
                case 5:
                    itemMonth = Calendar.MAY;
                    break;
                case 6:
                    itemMonth = Calendar.JUNE;
                    break;
                case 7:
                    itemMonth = Calendar.JULY;
                    break;
                case 8:
                    itemMonth = Calendar.AUGUST;
                    break;
                case 9:
                    itemMonth = Calendar.SEPTEMBER;
                    break;
                case 10:
                    itemMonth = Calendar.OCTOBER;
                    break;
                case 11:
                    itemMonth = Calendar.NOVEMBER;
                    break;
                case 12:
                    itemMonth = Calendar.DECEMBER;
                    break;
            }
            Calendar cal = Calendar.getInstance();
            cal.set(transactionList.get(i).getNumYear(), itemMonth, transactionList.get(i).getNumDay());
            transactionList.get(i).setDate(cal);
        }

        for (int i = 0; i < transactionList.size(); i++) {
            for (int j = 0; j < transactionList.size() - 1; j++) {
                if (transactionList.get(j).getDate().compareTo(transactionList.get(j+1).getDate()) < 0) {
                    Transaction temp = transactionList.get(j);
                    transactionList.set(j, transactionList.get(j+1));
                    transactionList.set(j+1, temp);
                }
            }
        }


        return transactionList;
    }

    public static double extractAmount(Transaction transaction) {
        String amount = transaction.getAmount();
        amount = amount.replace("$", "");
        amount = amount.replace(",", "");
        return Double.valueOf(amount);

    }
}
