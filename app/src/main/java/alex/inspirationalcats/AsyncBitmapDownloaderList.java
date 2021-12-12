package alex.inspirationalcats;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class AsyncBitmapDownloaderList extends AsyncTask<String, Void, Bitmap[]> {
    private BitmapListAdapter adapter;

    public AsyncBitmapDownloaderList(BitmapListAdapter adapter){
        this.adapter = adapter;
    }

    @Override
    protected Bitmap[] doInBackground(String... strings) {
        int n = strings.length;
        Bitmap[] resultBitmaps = new Bitmap[n];

        for(int i=0; i<n; i++) {

            URL url = null;
            try {
                HttpsURLConnection urlConnection = null;
                Bitmap resultBitmap = null;
                url = new URL(strings[i]);
                urlConnection = (HttpsURLConnection) url.openConnection();
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                resultBitmaps[i] = BitmapFactory.decodeStream(in);
                in.close();
                urlConnection.disconnect();


            } catch (MalformedURLException e) {
                e.printStackTrace();
                return null;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        return resultBitmaps;
    }

    @Override
    protected void onPostExecute(Bitmap[] bitmaps) {

        for(int i=0; i<bitmaps.length; i++) {
            Log.i("Alex", "Bitmap Object : " + i + " " + bitmaps[i]);
            adapter.add(bitmaps[i]);
        }
        adapter.notifyDataSetChanged();

    }
}
