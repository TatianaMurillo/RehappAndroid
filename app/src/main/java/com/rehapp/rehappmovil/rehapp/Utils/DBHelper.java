package com.rehapp.rehappmovil.rehapp.Utils;



import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import android.util.Log;

import com.rehapp.rehappmovil.rehapp.Models.PhysiologicalParameterTherapyViewModel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static  com.rehapp.rehappmovil.rehapp.Utils.TherapyDBHelperEntry.PhysiologicalParameterTherapyEntry;
import static com.rehapp.rehappmovil.rehapp.Models.PhysiologicalParameterTherapyViewModel.toContentValues;

public class DBHelper extends SQLiteOpenHelper {

    private Context context;
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "rehapp.db";
    private static final String TAG = DBHelper.class.getName();

   /* public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }*/
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context=context;
    }

    public static DBHelper connect(Context context)
    {
        return new DBHelper(context);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + PhysiologicalParameterTherapyEntry.TABLE_NAME + " ("
                + PhysiologicalParameterTherapyEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + PhysiologicalParameterTherapyEntry.PHYSIO_PARAM_ID + " TEXT NOT NULL,"
                + PhysiologicalParameterTherapyEntry.PHYSIO_PARAM_THERAPY_ID + " TEXT NOT NULL,"
                + PhysiologicalParameterTherapyEntry.THERAPY_ID + " TEXT NOT NULL,"
                + PhysiologicalParameterTherapyEntry.THERAPY_VALUE + " TEXT NOT NULL,"
                + PhysiologicalParameterTherapyEntry.THERAPY_IN_OUT+ " TEXT NOT NULL,"
                + "UNIQUE (" + PhysiologicalParameterTherapyEntry.PHYSIO_PARAM_ID + "))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.e(TAG, "Updating table from " + oldVersion + " to " + newVersion);
        try {
            for (int i = oldVersion; i < newVersion; ++i) {
                String migrationName = String.format("from_%d_to_%d.sql", i, (i + 1));
                Log.d(TAG, "Looking for migration file: " + migrationName);
                readAndExecuteSQLScript(db,context , migrationName);
            }
        } catch (Exception exception) {
            Log.e(TAG, "Exception running upgrade script:", exception);
        }

    }

    private void readAndExecuteSQLScript(SQLiteDatabase db, Context ctx, String fileName) {
        if (TextUtils.isEmpty(fileName)) {
            Log.d(TAG, "SQL script file name is empty");
            return;
        }

        Log.d(TAG, "Script found. Executing...");
        AssetManager assetManager = ctx.getAssets();
        BufferedReader reader = null;

        try {
            InputStream is = assetManager.open(fileName);
            InputStreamReader isr = new InputStreamReader(is);
            reader = new BufferedReader(isr);
            executeSQLScript(db, reader);
        } catch (IOException e) {
            Log.e(TAG, "IOException:", e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    Log.e(TAG, "IOException:", e);
                }
            }
        }

    }

    private void executeSQLScript(SQLiteDatabase db, BufferedReader reader) throws IOException {
        String line;
        StringBuilder statement = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            statement.append(line);
            statement.append("\n");
            if (line.endsWith(";")) {
                db.execSQL(statement.toString());
                statement = new StringBuilder();
            }
        }
    }



    public boolean savePhysiologicalParametersToTherapy(List<PhysiologicalParameterTherapyViewModel> LphysiologicalParameterTherapyViewModel) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        for (PhysiologicalParameterTherapyViewModel object: LphysiologicalParameterTherapyViewModel) {

            sqLiteDatabase.insert(
                    PhysiologicalParameterTherapyEntry.TABLE_NAME,
                    null,
                    toContentValues(object)
            );

        }


        return true;

    }

    public  List<PhysiologicalParameterTherapyViewModel> readPhysiologicalParametersToTherapy()
    {
        List<PhysiologicalParameterTherapyViewModel> LphysiologicalParameterTherapyViewModel=new ArrayList<>();
        SQLiteDatabase sqLiteDatabase =getReadableDatabase();
        String value;

        Cursor c = sqLiteDatabase.query(
                PhysiologicalParameterTherapyEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );

        while(c.moveToNext()){
            LphysiologicalParameterTherapyViewModel.add(
                    new PhysiologicalParameterTherapyViewModel(
                            Integer.parseInt( c.getString(c.getColumnIndex(PhysiologicalParameterTherapyEntry.PHYSIO_PARAM_ID))),
                            Integer.parseInt(c.getString(c.getColumnIndex(PhysiologicalParameterTherapyEntry.PHYSIO_PARAM_THERAPY_ID))),
                            Integer.parseInt(c.getString(c.getColumnIndex(PhysiologicalParameterTherapyEntry.THERAPY_ID))),
                            c.getString(c.getColumnIndex(PhysiologicalParameterTherapyEntry.THERAPY_VALUE)),
                            c.getString(c.getColumnIndex(PhysiologicalParameterTherapyEntry.THERAPY_IN_OUT))
                            )
            );

        }

        return LphysiologicalParameterTherapyViewModel;
    }


}