package example.dinotest.repository;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import example.dinotest.entity.Dino;
import example.dinotest.entity.DinoResp;

public class DinoRepository {

    static public List<DinoResp> getDinos() {
        String url = "/rest/dinos";

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
        List<DinoResp> resultDinos = null;
        if (result != null) {
            Log.d("Dlog", result);
            try {
                JSONObject object = new JSONObject(result);
                resultDinos = new ArrayList<>();
                JSONArray array = object.getJSONArray("dinos");
                for (int i = 0; i < array.length(); i++) {
                    DinoResp dino = new DinoResp();
                    Log.d("Dlog", "array " + i + " " + array.getJSONObject(i).toString());
                    dino.setName(array.getJSONObject(i).getJSONObject("dino").getString("dino_title"));
                    dino.setColor(array.getJSONObject(i).getJSONObject("dino").getString("dino_color"));
                    dino.setBirthdate(array.getJSONObject(i).getJSONObject("dino").getString("dino_birthdate"));
                    dino.setAbout(array.getJSONObject(i).getJSONObject("dino").getString("dino_about"));
                    dino.setImgURL(array.getJSONObject(i).getJSONObject("dino").getJSONObject("dino_image").getString("src"));
                    resultDinos.add(dino);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return resultDinos;
    }

    public static String createDino(Dino dino, String cookie, String token) {

        String url = "/rest/node";

        JSONObject jsonParam = new JSONObject();
        try {
            jsonParam.put("title", dino.getName());
            jsonParam.put("status", "1");
            jsonParam.put("name", dino.getAuthorName());
            jsonParam.put("type", "dino");
            jsonParam.put("field_dino_color", new JSONObject().put("und", new JSONObject().put("tid", dino.getColorTID())));
            jsonParam.put("field_dino_about", new JSONObject().put("und", new JSONArray().put(new JSONObject().put("value", dino.getAbout()))));
            jsonParam.put("field_dino_birth_date", new JSONObject().put("und", new JSONArray().put(new JSONObject().put("value", new JSONObject().put("day", dino.getDay()).put("month", dino.getMonth()).put("year", dino.getYear()).put("hour", "00").put("minute", "00").put("second", "00")))));
            jsonParam.put("field_dito_image", new JSONObject().put("und", new JSONArray().put(new JSONObject().put("fid", dino.getFileId()))));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("Dlog", jsonParam.toString());

        Connection connection = new Connection();
        connection.execute(url, "POST", jsonParam.toString(), cookie, token);

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
            try {
                JSONObject object = new JSONObject(result);
                result = object.getString("nid");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

}
