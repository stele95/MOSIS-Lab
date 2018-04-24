package rs.elfak.mosis.stele.myplaces.Classes;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

public class MyPlacesDatabaseHelper extends SQLiteOpenHelper {


    Context context;

    private static final String DATABASE_CREATE = "create table " + MyPlacesDBAdapter.DATABASE_TABLE + " ("
            +MyPlacesDBAdapter.PLACE_ID + " INTEGER primary key autoincrement, "
            +MyPlacesDBAdapter.DATABASE_NAME + " TEXT unique not null, "
            +MyPlacesDBAdapter.PLACE_DESCRIPTION + " TEXT, "
            +MyPlacesDBAdapter.PLACE_LONG + " TEXT, "
            +MyPlacesDBAdapter.PLACE_LAT + " TEXT)";



    public MyPlacesDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context, name, factory, version);
        this.context=context;
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(DATABASE_CREATE);
            Log.e("MYPLACESDBHELPER", "DB CREATED??????");
        }
        catch (SQLiteException ex)
        {
            Log.e("MyPlacesDatabaseHelper", ex.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MyPlacesDBAdapter.DATABASE_TABLE);
        onCreate(db);
    }
}
