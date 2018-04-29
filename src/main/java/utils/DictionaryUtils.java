package utils;

import enums.Dictionary;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.Collectors;

public class DictionaryUtils {

    public static String getWord(ArrayList<String> dictionary) {
        if (dictionary.size() > 0) {
            int index = new Random().nextInt(dictionary.size());
            return dictionary.remove(index);
        }

        return null;
    }

    public static ArrayList<String> buildDictionary() {
        return (ArrayList<String>) Arrays.stream(Dictionary.values())
                .map(Enum::name)
                .collect(Collectors.toList());
    }

}
