package example.dinotest.repository;

import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.concurrent.ExecutionException;

import example.dinotest.entity.Session;
import example.dinotest.entity.User;

public class UserRepository {

    static public String signUp(User user) {

        String url = "/rest/user";

        JSONObject jsonParam = new JSONObject();
        try {
            jsonParam.put("name", user.getName());
            jsonParam.put("mail", user.getMail());
            jsonParam.put("pass", user.getPassword());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("Dlog", jsonParam.toString());

        Connection connection = new Connection();
        connection.execute(url, "POST", jsonParam.toString(), "", "");
        String result = null;
        try {
            result = connection.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        if (result != null)
            Log.d("Dlog", result);
        return result;
    }

    static public Session login(User user) {

        String url = "/rest/user/login";

        JSONObject jsonParam = new JSONObject();
        try {
            jsonParam.put("username", user.getName());
            jsonParam.put("password", user.getPassword());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("Dlog", jsonParam.toString());
        Connection connection = new Connection();
        connection.execute(url, "POST", jsonParam.toString(), "", "");
        String result = null;
        try {
            result = connection.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        Session session = null;
        if (result != null) {
            Log.d("Dlog", result);
            try {
                JSONObject object = new JSONObject(result);
                session = new Session();
                session.setId(object.getString("sessid"));
                session.setName(object.getString("session_name"));
                session.setUser(object.getJSONObject("user").getString("name"));
                session.setToken(object.getString("token"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (session != null)
            Log.d("Dlog", session.getId());
        return session;
    }

    static public boolean logout(Session session) {

        String url = "/rest/user/logout";

        Connection connection = new Connection();
        connection.execute(url, "POST", "", (session.getName() + "=" + session.getId()), session.getToken());

        boolean result = false;
        String resultString = null;
        try {
            resultString = connection.get();
            resultString = resultString.replaceAll("[^A-Za-z]", "");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        result = Boolean.parseBoolean(resultString);
        Log.d("Dlog", Boolean.toString(result));
        return result;
    }

}
