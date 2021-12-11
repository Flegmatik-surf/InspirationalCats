package alex.inspirationalcats;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
        ImageView displayCatView = findViewById(R.id.topTenDefaultImage);

        randomizeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Request", "RANDOOOOOOM");
                String baseURL = "https://cataas.com/api/cats";
                AsyncCatsJSONData getImageUrltask = new AsyncCatsJSONData();
                AsyncBitmapDownloader displayImageTask = new AsyncBitmapDownloader(displayCatView);

                //On récupère le JSON de la base de données complète
                JSONArray catJSONArray = null;
                try {
                    catJSONArray = getImageUrltask.execute(baseURL).get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //On sélectionne aléatoirement 10 index distincts des éléments du JSON
                //Integer[] selectedIndexs;

                //On récupère les Id des images choisies pour faire la bonne requête
                String[] urlIds = new String[10];
                try {
                    for(int i=0; i<10; i++){
                        urlIds[i] = catJSONArray.getJSONObject(i).getString("id");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //On change l'affichage de la la liste view
                //TODO changer l'image view en liste view, et add les 10 images à partir de leurs URLs
                displayImageTask.execute("https://cataas.com/cat/" + urlIds[0]);
                Toast.makeText(v.getContext(), "Randomized", Toast.LENGTH_SHORT).show();

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