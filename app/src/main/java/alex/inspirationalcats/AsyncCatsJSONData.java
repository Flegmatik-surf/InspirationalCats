package alex.inspirationalcats;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class AsyncCatsJSONData extends AsyncTask<String, Void, JSONArray>{

    @Override
    protected JSONArray doInBackground(String... strings) {
        URL url = null;
        try {
            HttpsURLConnection urlConnection = null;
            String result = null;
            url = new URL(strings[0]);
            urlConnection = (HttpsURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            result = readStream(in);
            in.close();
            urlConnection.disconnect();
            JSONArray json = new JSONArray(result);
            return json;
        } catch (MalformedURLException e){
            e.printStackTrace();
            return null;
        } catch (IOException e){
            e.printStackTrace();
            return null;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(JSONArray jsonArray) {
        Log.i("Alex", "Json Object : " + jsonArray);
    }

    private String readStream(InputStream is) {
        try {
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            int i = is.read();
            while(i != -1) {
                bo.write(i);
                i = is.read();
            }
            return bo.toString();
        } catch (IOException e) {
            return "";
        }
    }

}
