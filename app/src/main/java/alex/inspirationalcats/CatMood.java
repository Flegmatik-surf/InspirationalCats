package alex.inspirationalcats;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class CatMood extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cat_mood);

        Button searchMoodButton = findViewById(R.id.catMoodSearchButton);
        Button launchTopTenButton = findViewById(R.id.topTenFromMoodButton);
        Button launchCatMemeButton = findViewById(R.id.catMemeFromMoodButton);

        searchMoodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Cat Mood", "SEARCHHHHH");
                //TODO
            }
        });

        launchTopTenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Cat Mood","Cat Mood --> Top Ten");
                Intent goToTopTenActivity = new Intent(getApplicationContext(), TopTen.class);
                startActivity(goToTopTenActivity);
            }
        });

        launchCatMemeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Cat Mood", "Cat Mood --> Cat Meme");
                Intent goToCatMemeActivity = new Intent(getApplicationContext(), CatMemem.class);
                startActivity(goToCatMemeActivity);
            }
        });
    }
}