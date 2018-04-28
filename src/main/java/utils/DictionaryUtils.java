package utils;

import enums.Dictionary;

import java.util.ArrayList;

public class DictionaryUtils {

    public static String getWord(ArrayList<Dictionary> dictionary) {
        if (dictionary.size() > 0) {
            return dictionary.remove(0).name();
        }

        return null;
    }

}
