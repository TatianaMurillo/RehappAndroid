package com.rehapp.rehappmovil.rehapp.Utils;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.rehapp.rehappmovil.rehapp.Models.PhysiologicalParameterTherapyViewModel;
import com.rehapp.rehappmovil.rehapp.Models.PreferencesData;

import java.util.ArrayList;
import java.util.List;
import static com.rehapp.rehappmovil.rehapp.Utils.TherapyDBHelperEntry.PhysiologicalParameterTherapyEntry;

public class DBHelper2 extends SQLiteOpenHelper {

    private Context context;
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "rehapp.db";

    String CREATE_TABLE_SCRIPT="CREATE TABLE " + PhysiologicalParameterTherapyEntry.TABLE_NAME + " ("
            + PhysiologicalParameterTherapyEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + PhysiologicalParameterTherapyEntry.PHYSIO_PARAM_ID + " INTEGER NOT NULL,"
            + PhysiologicalParameterTherapyEntry.PHYSIO_PARAM_THERAPY_ID + " INTEGER NOT NULL,"
            + PhysiologicalParameterTherapyEntry.THERAPY_ID + " INTEGER NOT NULL,"
            + PhysiologicalParameterTherapyEntry.THERAPY_VALUE + " TEXT NOT NULL,"
            + PhysiologicalParameterTherapyEntry.THERAPY_IN_OUT+ " TEXT NOT NULL, "
            + PhysiologicalParameterTherapyEntry.ORDER+ " TEXT NOT NULL,"
            + "UNIQUE (" + PhysiologicalParameterTherapyEntry.ORDER + "))";

    String DELETE_TABLE_SCRIPT="DROP TABLE IF EXISTS " + PhysiologicalParameterTherapyEntry.TABLE_NAME;



    public DBHelper2(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context=context;
    }

    public static DBHelper2 connect(Context context)
    {
        return new DBHelper2(context);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_TABLE_SCRIPT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(DELETE_TABLE_SCRIPT);
        db.execSQL(CREATE_TABLE_SCRIPT);
    }


    public void addPhysiologicalParametersInRegister( List<PhysiologicalParameterTherapyViewModel> objects)
    {

        for (PhysiologicalParameterTherapyViewModel object : objects)
        {
            String INSERT_TABLE_SCRIPT="INSERT INTO " + PhysiologicalParameterTherapyEntry.TABLE_NAME
                    +" VALUES( "
                    +object.getPhysio_param_id() +","
                    +object.getPhysio_param_thrpy_id() + ","
                    +object.getTherapy_id() + ",'"
                    +object.getPhysio_param_thrpy_value() + "','"
                    +object.getPhysio_param_thrpy_in_out()+"',"
                    +objects.indexOf(object)+")";

            SQLiteDatabase  db = getWritableDatabase();
            if(db!=null)
            {
                db.execSQL(INSERT_TABLE_SCRIPT);
                db.close();
                Toast.makeText(context, PreferencesData.PhysiologicalParameterTherapySuccessMgs,Toast.LENGTH_LONG).show();
            }
        }


    }

    public List<PhysiologicalParameterTherapyViewModel> listPhysiologicalParametersInRegister()
    {

        List<PhysiologicalParameterTherapyViewModel> listPhysiologicalParametersInRegister = new ArrayList<>();

            String SELECT_TABLE_SCRIPT="SELECT "
                    +PhysiologicalParameterTherapyEntry.PHYSIO_PARAM_THERAPY_ID +","
                    +PhysiologicalParameterTherapyEntry.PHYSIO_PARAM_ID +","
                    +PhysiologicalParameterTherapyEntry.THERAPY_ID +","
                    +PhysiologicalParameterTherapyEntry.THERAPY_VALUE+","
                    +PhysiologicalParameterTherapyEntry.THERAPY_IN_OUT
                    +" FROM " + PhysiologicalParameterTherapyEntry.TABLE_NAME
                    +" ORDER BY '" + PhysiologicalParameterTherapyEntry.ORDER +  " ASC'";

            SQLiteDatabase  db = getReadableDatabase();


            Cursor cursor = db.rawQuery(SELECT_TABLE_SCRIPT,null);

            if(cursor.moveToFirst())
            {
                do {
                    listPhysiologicalParametersInRegister.add(
                            new PhysiologicalParameterTherapyViewModel(
                                    cursor.getInt(0),
                                    cursor.getInt(1),
                                    cursor.getInt(2),
                                    cursor.getString(3),
                                    cursor.getString(4)
                            )
                    );
                }while(cursor.moveToNext());
            }

        return listPhysiologicalParametersInRegister;

    }


}