package alex.inspirationalcats;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class CatMemem extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cat_memem);

        Button generateMemeButton = findViewById(R.id.catMemeSearchButton);
        Button launchTopTenButton = findViewById(R.id.topTenFromMemeButton);
        Button launchCatMoodButton = findViewById(R.id.catMoodFromMemedButton);

        generateMemeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Cat Meme", "GENERATE MEME Nya !");
                //TODO
            }
        });

        launchTopTenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Cat Meme","Cat Meme --> Top Ten");
                Intent goToTopTenActivity = new Intent(getApplicationContext(), TopTen.class);
                startActivity(goToTopTenActivity);
            }
        });

        launchCatMoodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Cat Memem", "Cat Meme --> Cat Mood");
                Intent goToCatMoodActivity = new Intent(getApplicationContext(), CatMood.class);
                startActivity(goToCatMoodActivity);
            }
        });
    }
}