package alex.inspirationalcats;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class AsyncBitmapDownloader extends AsyncTask<String, Void, Bitmap> {
    private final WeakReference<ImageView> imageViewRef;

    public AsyncBitmapDownloader(ImageView imageViewRef){
        this.imageViewRef = new WeakReference<ImageView>(imageViewRef);
    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        URL url = null;
        try {
            HttpsURLConnection urlConnection = null;
            Bitmap resultBitmap = null;
            url = new URL(strings[0]);
            urlConnection = (HttpsURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            resultBitmap = BitmapFactory.decodeStream(in);
            in.close();
            urlConnection.disconnect();
            return resultBitmap;

        } catch (MalformedURLException e){
            e.printStackTrace();
            return null;
        } catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        Log.i("Alex", "Json Object : " + bitmap);
        if (imageViewRef != null) {
            ImageView imageView = imageViewRef.get();
            if (imageView != null) {
                imageView.setImageBitmap(bitmap);
            }
        }

    }


}
