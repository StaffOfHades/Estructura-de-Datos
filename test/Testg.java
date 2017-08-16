package test;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

/**
 * Created by mauriciog on 8/20/16.
 */
public class Testg {

    private static final int SIZE = 10;
    private static int count;
    private static int[] array = new int[SIZE];

    public static void main(String[] args) {

        JSONParser parser = new JSONParser();

        try {
            Object obj = parser.parse(new FileReader("/Users/mauriciog/Documents/Pokemon Daily/JSON/images.json"));

            JSONObject jsonObject = (JSONObject) obj;

            System.out.println(jsonObject.toString());


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

}
