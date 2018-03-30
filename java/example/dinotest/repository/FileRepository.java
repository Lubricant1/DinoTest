package example.dinotest.repository;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

import example.dinotest.entity.File;

public class FileRepository {

    public static String createFile(File file, String cookie, String token) {

        String url = "/rest/file";

        JSONObject jsonParam = new JSONObject();
        try {
            jsonParam.put("filename", file.getName());
            jsonParam.put("target_uri", file.getTargetUri());
            jsonParam.put("filemime", file.getFileMime());
            jsonParam.put("file", file.getBase());
            jsonParam.put("filesize", file.getFileSize());
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
                result = object.getString("fid");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
