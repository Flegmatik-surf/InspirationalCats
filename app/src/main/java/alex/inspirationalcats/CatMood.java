package alex.inspirationalcats;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.Random;
import java.util.concurrent.ExecutionException;

public class CatMood extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cat_mood);

        Button searchMoodButton = findViewById(R.id.catMoodSearchButton);
        Button launchTopTenButton = findViewById(R.id.topTenFromMoodButton);
        Button launchCatMemeButton = findViewById(R.id.catMemeFromMoodButton);
        ImageView displayMoodCatView = findViewById(R.id.catMoodImage);
        EditText tagOneField = findViewById(R.id.tagOneField);
        EditText tagTwoField = findViewById(R.id.tagTwoField);


        //Handle Search Button
        searchMoodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Request", "SEARCHHHHH");

                String baseDataURL = "https://cataas.com/api/cats";
                String baseImageURL = "https://cataas.com/cat";
                String tag1 = tagOneField.getText().toString();
                String tag2 = tagTwoField.getText().toString();
                AsyncCatsJSONData getImageUrltask = new AsyncCatsJSONData();
                AsyncBitmapDownloader displayImageTask = new AsyncBitmapDownloader(displayMoodCatView);

                String completeDataURL = baseDataURL;
                String completeDisplayURL = baseImageURL;

                //construction de l'URL de requête de données en fonction des tags
                Log.i("Request", "Tag 1 : "+ tag1);
                Log.i("Request", "Tag 2 : "+ tag2);
                if (!tag1.equals("")){
                    completeDataURL += "?tags=" + tag1.toLowerCase();
                    if (!tag2.equals("")){
                        completeDataURL += "," + tag2.toLowerCase();
                    }
                } else if (!tag2.equals("")){
                    completeDataURL += "?tags=" + tag2.toLowerCase();
                }
                Log.i("Request", "URL de requête CAT MOOD : " + completeDataURL);

                //On récupère le JSON du chat
                JSONArray catJSONArray = null;
                try {
                    catJSONArray = getImageUrltask.execute(completeDataURL).get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //On sélectionne aléatoirement un ID d'URL dans le JSON extrait
                if (catJSONArray.length() != 0) {
                    Integer indexId = new Random().nextInt(catJSONArray.length());
                    String urlId = null;
                    try {
                        urlId = catJSONArray.getJSONObject(indexId).getString("id");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    //On change l'affichage de l'image view
                    completeDisplayURL = baseImageURL + "/" + urlId;
                    displayImageTask.execute(completeDisplayURL);
                    Toast.makeText(v.getContext(), "Oh what a mood today", Toast.LENGTH_SHORT).show();
                } else {
                    //si le tableau est vide, un ou plusieurs tags n'existent pas.
                    Drawable errorImage = getDrawable(R.drawable.cat404error);
                    displayMoodCatView.setImageDrawable(errorImage);
                    Toast.makeText(v.getContext(), "Try again with other tags", Toast.LENGTH_SHORT).show();
                }
            }
        });


        //Go to TOP TEN
        launchTopTenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Navigation","Cat Mood --> Top Ten");
                Intent goToTopTenActivity = new Intent(getApplicationContext(), TopTen.class);
                startActivity(goToTopTenActivity);
            }
        });


        //Go to CAT MEME
        launchCatMemeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Navigation", "Cat Mood --> Cat Meme");
                Intent goToCatMemeActivity = new Intent(getApplicationContext(), CatMemem.class);
                startActivity(goToCatMemeActivity);
            }
        });

    }
}