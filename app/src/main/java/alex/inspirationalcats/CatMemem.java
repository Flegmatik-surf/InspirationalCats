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

public class CatMemem extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cat_memem);

        CatMemeDataBase catMemeDataBase = new CatMemeDataBase(this);
        String baseDataURL = "https://cataas.com/api/cats";
        String baseImageURL = "https://cataas.com/cat";

        Button generateMemeButton = findViewById(R.id.catMemeSearchButton);
        Button launchTopTenButton = findViewById(R.id.topTenFromMemeButton);
        Button launchCatMoodButton = findViewById(R.id.catMoodFromMemedButton);
        Button lauchOldMemeButton = findViewById(R.id.goToOldMemes);
        ImageView displayMemeCatView = findViewById(R.id.catMemeImage);
        EditText setenceMeme = findViewById(R.id.sentenceMeme);
        EditText colorTextMeme = findViewById(R.id.colorTextMeme);

        //On load le dernier meme chargé si c'est pas la première fois.
        //PS: on reconstruit l'URL au cas où le nom de domaine de l'API change. La requête restera valide.
        String[] previousMeme = catMemeDataBase.lastMemeRequest();
        if (previousMeme[0]!=null && !previousMeme[0].equals("")){

            AsyncBitmapDownloader displayPreviousMeme = new AsyncBitmapDownloader(displayMemeCatView);
            String previousUrl = baseImageURL + "/" + previousMeme[0]; //on rajoute l'id

            if(!previousMeme[1].equals("")){
                previousUrl += "/says/" + previousMeme[1].replace(" ", "%20");
                setenceMeme.setText(previousMeme[1]);

                if(!previousMeme[2].equals("")){
                    previousUrl += "?color=" + previousMeme[2].toLowerCase();
                    colorTextMeme.setText(previousMeme[2]);
                }
            }

            displayPreviousMeme.execute(previousUrl);
        }


        generateMemeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Request", "GENERATE MEME Nya !");

                String sentence = setenceMeme.getText().toString();
                String color = colorTextMeme.getText().toString();
                AsyncCatsJSONData getImageUrltask = new AsyncCatsJSONData();
                AsyncBitmapDownloader displayImageTask = new AsyncBitmapDownloader(displayMemeCatView);

                String completeDataURL = baseDataURL;
                String completeDisplayURL = baseImageURL;

                //On récupère le JSON de la base de données complète
                JSONArray catJSONArray = null;
                try {
                    catJSONArray = getImageUrltask.execute(completeDataURL).get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //On sélectionne aléatoirement un ID d'URL dans le JSON extrait
                Integer indexId = new Random().nextInt(catJSONArray.length());
                String urlId = null;
                try {
                    urlId = catJSONArray.getJSONObject(indexId).getString("id");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //On construit l'url de requête d'image complète à partir de l'id précédent
                completeDisplayURL += "/" + urlId;
                if(!sentence.equals("")){
                    completeDisplayURL += "/says/" + sentence.replace(" ", "%20");
                    if(!color.equals("")){
                        completeDisplayURL += "?color=" + color.toLowerCase();
                    }
                }
                Log.i("Request", "URL d'affichage CAT MEME : " + completeDisplayURL);

                //On change l'affichage de l'image view
                displayImageTask.execute(completeDisplayURL);
                Toast.makeText(v.getContext(), "Meme generated !", Toast.LENGTH_SHORT).show();

                //Enfin on ajoute à la base de donnée la dernière requête pour l'afficher si on change d'activité
                catMemeDataBase.insertData(urlId, sentence, color);
            }
        });

        launchTopTenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Navigation","Cat Meme --> Top Ten");
                Intent goToTopTenActivity = new Intent(getApplicationContext(), TopTen.class);
                startActivity(goToTopTenActivity);
            }
        });

        launchCatMoodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Navigation", "Cat Meme --> Cat Mood");
                Intent goToCatMoodActivity = new Intent(getApplicationContext(), CatMood.class);
                startActivity(goToCatMoodActivity);
            }
        });

        lauchOldMemeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Navigation", "Cat Meme --> Old Memes");
                Intent goToOldMemesActivity = new Intent(getApplicationContext(), OldMemes.class);
                startActivity(goToOldMemesActivity);
            }
        });
    }
}