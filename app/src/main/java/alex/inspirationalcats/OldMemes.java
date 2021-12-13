package alex.inspirationalcats;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.Arrays;

public class OldMemes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_old_memes);

        Button back = findViewById(R.id.oldMemesBack);
        ListView displayTopTenCatView = findViewById(R.id.displayOldMemes);
        BitmapListAdapter adapter = new BitmapListAdapter();

        CatMemeDataBase catMemeDataBase = new CatMemeDataBase(this);
        String baseDataURL = "https://cataas.com/api/cats";
        String baseImageURL = "https://cataas.com/cat";
        int displayOldNumber = 10;
        int notNullRequestNumber = 0;
        String[] previousUrls = new String[displayOldNumber];

        String[][] previousMemes = catMemeDataBase.lastMemeRequests(displayOldNumber);
        for (int i = 0; i < displayOldNumber; i++) {
            if (previousMemes[i][0] != null && !previousMemes[i][0].equals("")) {
                notNullRequestNumber +=1;
                previousUrls[i] = baseImageURL + "/" + previousMemes[i][0]; //on rajoute l'id

                if (!previousMemes[i][1].equals("")) {
                    previousUrls[i] += "/says/" + previousMemes[i][1].replace(" ", "%20");

                    if (!previousMemes[i][2].equals("")) {
                        previousUrls[i] += "?color=" + previousMemes[i][2].toLowerCase();
                    }
                }
            }
        }

        String[] previouCompletedUrls = Arrays.copyOfRange(previousUrls, 0, notNullRequestNumber);
        AsyncBitmapDownloaderList displayMemesListTask = new AsyncBitmapDownloaderList(adapter);
        displayMemesListTask.execute(previouCompletedUrls);
        displayTopTenCatView.setAdapter(adapter);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Navigation", "Old Memes --> Cat Meme");
                Intent goToCatMemeActivity = new Intent(getApplicationContext(), CatMemem.class);
                startActivity(goToCatMemeActivity);
            }
        });

    }
}