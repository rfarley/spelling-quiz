package utils;

import java.util.ArrayList;

public class DictionaryUtils {

    public static String getWord(ArrayList<String> dictionary) {
        if (dictionary.size() > 0) {
            return dictionary.remove(0);
        }

        return null;
    }

}
