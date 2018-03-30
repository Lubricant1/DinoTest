package example.dinotest.repository;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class TaxonomyRepository {

    public static Map<String, String> getTaxonomy() {
        String url = "/rest/taxonomy/13";
        Connection connection = new Connection();
        connection.execute(url, "GET", "", "", "");
        String result = null;
        try {
            result = connection.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        if (result != null) {
            Log.d("Dlog", result);
        }

        Map<String, String> resultTax = null;
        try {
            JSONObject object = new JSONObject(result);
            resultTax = new HashMap<>();
            JSONArray array = object.getJSONArray("terms");
            for (int i = 0; i < array.length(); i++) {
                resultTax.put(array.getJSONObject(i).getJSONObject("term").getString("name"),array.getJSONObject(i).getJSONObject("term").getString("TID"));
            }
            Log.d("Dlog", resultTax.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return resultTax;
    }
}
