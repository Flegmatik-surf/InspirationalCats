package alex.inspirationalcats;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class CatMemeDataBase extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_FILE_NAME = "catmeme_database";
    private static final String DATABASE_TABLE_NAME = "catMemeDatabase";
    private static final String PKEY = "id";
    private static final String COL1 = "urlId";
    private static final String COL2 = "sentence";
    private static final String COL3 = "color";

    CatMemeDataBase(Context context){
        super(context, DATABASE_TABLE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String DATABASE_TABLE_CREATE = "CREATE TABLE " + DATABASE_TABLE_NAME + " (" +
                PKEY + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL1 + " TEXT," +
                COL2 + " TEXT," +
                COL3 + " TEXT);";
        db.execSQL(DATABASE_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Pas idéal mais je remplacerais par autre chose si je trouve mieux
        if (oldVersion != newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_NAME);
            onCreate(db);
        }
    }

    public void insertData(String urlId, String sentence, String color)
    {
        SQLiteDatabase dataBase = getWritableDatabase();
        dataBase.beginTransaction();
        ContentValues values = new ContentValues();
        values.put(COL1, urlId);
        values.put(COL2, sentence);
        values.put(COL3, color);
        dataBase.insertOrThrow(DATABASE_TABLE_NAME,null, values);
        dataBase.setTransactionSuccessful();
        dataBase.endTransaction();
        Log.i("Database", "Something has been added to " + DATABASE_TABLE_NAME);
    }

    @SuppressLint("Range")
    public void readData()
    {

        String select = new String("SELECT * from " + DATABASE_TABLE_NAME);
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(select, null);
        Log.i("Database", "Nb tuples in " + DATABASE_TABLE_NAME + ": " + cursor.getCount());
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                Log.i("Database", "tuple: " + cursor.getString(cursor.getColumnIndex(COL1))
                + " " + cursor.getString(cursor.getColumnIndex(COL2))
                + " " + cursor.getString(cursor.getColumnIndex(COL3)));
            } while (cursor.moveToNext());
        }
    }

    @SuppressLint("Range")
    public String[] lastMemeRequest(){
        String select = new String("SELECT * from " + DATABASE_TABLE_NAME + " ORDER BY " + PKEY + " DESC");
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(select, null);

        String[] result = new String[3];
        if (cursor.getCount() >0) {
            cursor.moveToFirst();
            result[0] = cursor.getString(cursor.getColumnIndex(COL1));
            result[1] = cursor.getString(cursor.getColumnIndex(COL2));
            result[2] = cursor.getString(cursor.getColumnIndex(COL3));
        }
        Log.i("Database", "Last request :" + result[0] + " " + result[1] + " " + result[2] + " from " + DATABASE_TABLE_NAME);
        return result;

    }


}
