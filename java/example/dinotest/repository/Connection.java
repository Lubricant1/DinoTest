package example.dinotest.repository;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class Connection extends AsyncTask<String, String, String> {

    final String BASE_URL = "http://dinotest.art-coral.com";


    @Override
    protected String doInBackground(String... strings) {
        String result = null;
        HttpURLConnection conn = null;

        try {
            URL url = new URL(BASE_URL + strings[0]);
            Log.d("Dlog", url.toString());
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestMethod(strings[1]);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            if (strings[4].length() > 1)
                conn.setRequestProperty("X-CSRF-Token", strings[4]);
            if (strings[3].length() > 1)
                conn.setRequestProperty("Cookie", strings[3]);
            conn.connect();
            OutputStreamWriter os = new OutputStreamWriter(conn.getOutputStream());
            os.write(strings[2]);
            os.flush();
            os.close();
            int resp = conn.getResponseCode();
            StringBuilder sb = new StringBuilder();
            if (resp == HttpURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
                String line = null;
                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }
                br.close();
                result = sb.toString();
            } else result = conn.getResponseMessage();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (conn != null)
                conn.disconnect();
            return result;
        }
    }
}
