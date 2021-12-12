package alex.inspirationalcats;

//Cette classe existe car l'API 23 ne supporte pas ThreadLocalRandom qui
// permettait de générer des entiers distincts dans une tranche donnée.


import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DistinctRandom {

    //fonction statique qui renvoie une liste d'entiers distincts dans une tranche donnée
    //Borne supérieure exclue.
    public static Integer[] distinctRandom(int min, int max, int nb_a_tirer){
        assert max - min >= nb_a_tirer : "Pas assez de nombre à tirer dans la tranche donnée";

        List<Integer> indexList = new ArrayList<>();
        for(int i = min; i<max; i++){
            indexList.add(i);
        }
        Collections.shuffle(indexList);
        Integer[] resultatTemp = indexList.toArray(new Integer[indexList.size()]);
        Integer[] resultat = Arrays.copyOfRange(resultatTemp, 0, nb_a_tirer);


        Log.i("Shuffle", "Random unique index array :" + Arrays.asList(resultat));
        return resultat;

    }
}
