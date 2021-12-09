package alex.inspirationalcats;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class TopTen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_ten);

        Button randomizeButton = findViewById(R.id.topTenSearchButton);
        Button launchCatMoodButton = findViewById(R.id.catMoodFromTopTenButton);
        Button launchCatMemeButton = findViewById(R.id.catmemeFromTopTenButton);

        randomizeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Top Ten", "RANDOOOOOOM");
                //TODO
            }
        });

        launchCatMoodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Top Ten","Top Ten --> Cat Mood");
                Intent goToCatMoodActivity = new Intent(getApplicationContext(), CatMood.class);
                startActivity(goToCatMoodActivity);
            }
        });

        launchCatMemeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Top Ten", "Top Ten --> Cat Meme");
                Intent goToCatMemeActivity = new Intent(getApplicationContext(), CatMemem.class);
                startActivity(goToCatMemeActivity);
            }
        });
    }
}