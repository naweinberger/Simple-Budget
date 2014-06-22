package us.weinberger.natan.simplebudget;

import android.app.Activity;

import java.util.ArrayList;

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
}
