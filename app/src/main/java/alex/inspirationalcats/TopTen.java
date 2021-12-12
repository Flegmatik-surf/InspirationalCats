package alex.inspirationalcats;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.Random;
import java.util.concurrent.ExecutionException;

public class TopTen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_ten);

        Button randomizeButton = findViewById(R.id.topTenSearchButton);
        Button launchCatMoodButton = findViewById(R.id.catMoodFromTopTenButton);
        Button launchCatMemeButton = findViewById(R.id.catmemeFromTopTenButton);
        //ImageView displayCatView = findViewById(R.id.topTenDefaultImage);


        ListView displayTopTenCatView = findViewById(R.id.displayTopTen);
        BitmapListAdapter adapter = new BitmapListAdapter();

        randomizeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Request", "RANDOOOOOOM");
                String baseDataURL = "https://cataas.com/api/cats";
                String baseImageURL = "https://cataas.com/cat/";
                AsyncCatsJSONData getImageUrltask = new AsyncCatsJSONData();
                AsyncBitmapDownloaderList displayImageListTask = new AsyncBitmapDownloaderList(adapter);
                //AsyncBitmapDownloader displayImageTask = new AsyncBitmapDownloader(displayCatView);

                //On récupère le JSON de la base de données complète
                JSONArray catJSONArray = null;
                try {
                    catJSONArray = getImageUrltask.execute(baseDataURL).get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //On sélectionne aléatoirement 10 index distincts des éléments du JSON
                //Integer[] selectedIndexs;
                int nb_element = 10;
                Integer[] indexs = DistinctRandom.distinctRandom(0, catJSONArray.length(), nb_element);

                //On récupère les Id des images choisies pour faire compléter les bonnes requête
                String[] completeDisplaysURLs = new String[nb_element];
                try {
                    for(int i=0; i<nb_element; i++){
                        completeDisplaysURLs[i] = baseImageURL + catJSONArray.getJSONObject(indexs[i]).getString("id");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                //On change l'affichage de la la liste view
                //TODO changer l'image view en liste view, et add les 10 images à partir de leurs URLs
                //displayImageTask.execute(completeDisplaysURLs[0]);
                adapter.clear();
                displayImageListTask.execute(completeDisplaysURLs);
                displayTopTenCatView.setAdapter(adapter);
                Toast.makeText(v.getContext(), "Randomizing...", Toast.LENGTH_SHORT).show();

            }
        });

        launchCatMoodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Navigation","Top Ten --> Cat Mood");
                Intent goToCatMoodActivity = new Intent(getApplicationContext(), CatMood.class);
                startActivity(goToCatMoodActivity);
            }
        });

        launchCatMemeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Navigation", "Top Ten --> Cat Meme");
                Intent goToCatMemeActivity = new Intent(getApplicationContext(), CatMemem.class);
                startActivity(goToCatMemeActivity);
            }
        });
    }
}