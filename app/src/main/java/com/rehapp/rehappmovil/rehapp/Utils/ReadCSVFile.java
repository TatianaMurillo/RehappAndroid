package com.rehapp.rehappmovil.rehapp.Utils;


import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import com.rehapp.rehappmovil.rehapp.Models.*;
import com.rehapp.rehappmovil.rehapp.R;
import com.rehapp.rehappmovil.rehapp.Utils.Constants.*;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import static com.rehapp.rehappmovil.rehapp.Utils.Constants.Constants.TEMP_FILE_PATH;
import static com.rehapp.rehappmovil.rehapp.Utils.Constants.Constants.ANDROID_RESOURCES_PATH;

public class ReadCSVFile extends SQLiteOpenHelper {

    private static TherapistViewModel therapist;
    private static InstitutionViewModel institution;
    private static TherapyViewModel therapy;
    private static List<PhysiologicalParameterTherapyViewModel> LPhysiologicalParameterTherapyIn;
    private static PhysiologicalParameterTherapyViewModel physiologicalParameterTherapyOut;
    private static ExerciseViewModel exercise;
    private static List<ExerciseViewModel> LExercises;
    private static Map<String, Object> data = new HashMap<>();
    private int tempdata;

    public ReadCSVFile(Context context,String name,SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public ReadCSVFile(Context context,String name,SQLiteDatabase.CursorFactory factory, int version,DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    public static String writeTempTherapyInformation(Context context,int rowId,Object object) {


        try {
            loadTempTherapyInformation(context);
            if (rowId==PhysiologicalParameterTherapy.DATA_PHYSIOLOGICAL_PARAMETER_THERAPY_IN.getRowId()) {
                data.put(Constants.PHYSIOLICAL_PARAMETER_THERAPY_IN,object);
            }else if(rowId==PhysiologicalParameterTherapy.DATA_PHYSIOLOGICAL_PARAMETER_THERAPY_OUT.getRowId()) {
                data.put(Constants.PHYSIOLICAL_PARAMETER_THERAPY_OUT,object);
            }else if(rowId==Therapist.DATA.getRowId()){
                data.put(Constants.THERAPIST,object);
            }

            storeDataInTempFile(context);


        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    private static String storeDataInTempFile(Context context)
    {
        //BufferedWriter bw = null;
        loadTempTherapyInformation(context);
        FileOutputStream fileOutputStream=null;
        //String path = Uri.parse(ANDROID_RESOURCES_PATH+ context.getPackageName() +TEMP_FILE_PATH).getPath();

        try {

            fileOutputStream=  context.openFileOutput(TEMP_FILE_PATH,context.MODE_PRIVATE);

            //bw = new BufferedWriter(new FileWriter(path));



            String key;
            Object object;
            StringBuilder sb;
            for (Map.Entry<String, Object> entry : data.entrySet()) {
                key = entry.getKey();
                object = entry.getValue();
                sb= new StringBuilder();

                if (key.equals(Constants.PHYSIOLICAL_PARAMETER_THERAPY_IN)) {
                    List<PhysiologicalParameterTherapyViewModel> objects=(List<PhysiologicalParameterTherapyViewModel>)object;

                    sb.append(PhysiologicalParameterTherapy.DATA_PHYSIOLOGICAL_PARAMETER_THERAPY_IN.getRowId());

                    for (PhysiologicalParameterTherapyViewModel objectData : objects) {
                        sb.append(Row.DATA.getRowDataSeparator());
                        sb.append(objectData.getPhysio_param_thrpy_id());
                        sb.append(PhysiologicalParameterTherapy.DATA_PHYSIOLOGICAL_PARAMETER_THERAPY_IN.getPhysiologicalParameterSeparator());
                        sb.append(objectData.getPhysio_param_id());
                        sb.append(PhysiologicalParameterTherapy.DATA_PHYSIOLOGICAL_PARAMETER_THERAPY_IN.getPhysiologicalParameterSeparator());
                        sb.append(objectData.getTherapy_id());
                        sb.append(PhysiologicalParameterTherapy.DATA_PHYSIOLOGICAL_PARAMETER_THERAPY_IN.getPhysiologicalParameterSeparator());
                        sb.append(objectData.getPhysio_param_thrpy_value());
                        sb.append(PhysiologicalParameterTherapy.DATA_PHYSIOLOGICAL_PARAMETER_THERAPY_IN.getPhysiologicalParameterSeparator());
                        sb.append(objectData.getPhysio_param_thrpy_in_out());
                    }

                }if (key.equals(Constants.THERAPIST)) {
                    /*
                        TherapistViewModel therapist = (TherapistViewModel) object;
                        sb.append(Therapist.DATA.getRowId());
                        sb.append(Row.DATA.getRowDataSeparator());

                        sb.append(therapist.getTherapist_id());
                        sb.append(Therapist.DATA.getTherapistSeparator());

                        sb.append(therapist.getTherapist_first_name());
                        sb.append(Therapist.DATA.getTherapistSeparator());

                        sb.append(therapist.getTherapist_second_name());
                        sb.append(Therapist.DATA.getTherapistSeparator());

                        sb.append(therapist.getTherapist_first_lastname());
                        sb.append(Therapist.DATA.getTherapistSeparator());

                        sb.append(therapist.getTherapist_second_lastname());
                        sb.append(Therapist.DATA.getTherapistSeparator());
                    */


                }


                String data =sb.toString();
                byte[] bytes = data.getBytes("UTF-8");
                fileOutputStream.write(bytes);
               // bw.write(sb.toString());

            }


        }catch (FileNotFoundException e) {
            e.printStackTrace();
            return "";
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            /*if (bw != null) {
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }*/

            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return "";
    }

    public static  Map<String, Object> loadTempTherapyInformation(Context context) {

        String[] values;

        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = Row.DATA.getRowDataSeparator();
        String row;

        InputStream inputStream = context.getResources().openRawResource(R.raw.tempdata);

        InputStreamReader inputreader = new InputStreamReader(inputStream);


        String path = Uri.parse(ANDROID_RESOURCES_PATH+ context.getPackageName() +TEMP_FILE_PATH).getPath();//.getPath();

        try {
            br = new BufferedReader(new InputStreamReader(context.openFileInput(TEMP_FILE_PATH)));

            //br = new BufferedReader(new InputStreamReader(inputStream));

            while ((line = br.readLine()) != null) {

                values = line.split(cvsSplitBy);
                row = values[Row.DATA.getDataRow()];

                if (row.equals(String.valueOf(PhysiologicalParameterTherapy.DATA_PHYSIOLOGICAL_PARAMETER_THERAPY_IN.getRowId()))) {

                    LPhysiologicalParameterTherapyIn = getPhysiologicalParameterTherapyViewModel(values);


                } else if (row.equals(String.valueOf(Institution.DATA.getRowId()))) {
                    institution = new InstitutionViewModel(
                            Integer.parseInt(values[Institution.DATA.getInstitution_id()]),
                            values[Institution.DATA.getInstitution_name()],
                            values[Institution.DATA.getInstitution_additional_info()]
                    );
                } else if (row.equals(String.valueOf(Therapist.DATA.getRowId()))) {
                    therapist = new TherapistViewModel(
                            Integer.parseInt(values[Therapist.DATA.getTherapist_id()]),
                            values[Therapist.DATA.getTherapist_first_name()],
                            values[Therapist.DATA.getTherapist_second_name()],
                            values[Therapist.DATA.getTherapist_first_lastname()],
                            values[Therapist.DATA.getTherapist_second_lastname()],
                            Integer.parseInt(values[Therapist.DATA.getTherapist_age()]),
                            getGenderViewModel(values[Therapist.DATA.getGender()]),
                            getDocumentTypeViewModel(values[Therapist.DATA.getDocumentType()]),
                            getNeighborhoodViewModel(values[Therapist.DATA.getNeighborhood()])
                    );
                } else if (row.equals(String.valueOf(Exercise.DATA.getRowId()))) {
                    LExercises = getExercisesViewModel(values);
                } else if (row.equals(String.valueOf(PhysiologicalParameterTherapy.DATA_PHYSIOLOGICAL_PARAMETER_THERAPY_OUT.getRowId()))) {
                    physiologicalParameterTherapyOut = new PhysiologicalParameterTherapyViewModel(
                            Integer.parseInt(values[PhysiologicalParameterTherapy.DATA_PHYSIOLOGICAL_PARAMETER_THERAPY_OUT.getPhysio_param_thrpy_id()]),
                            Integer.parseInt(values[PhysiologicalParameterTherapy.DATA_PHYSIOLOGICAL_PARAMETER_THERAPY_OUT.getPhysio_param_id()]),
                            Integer.parseInt(values[PhysiologicalParameterTherapy.DATA_PHYSIOLOGICAL_PARAMETER_THERAPY_OUT.getTherapy_id()]),
                            values[PhysiologicalParameterTherapy.DATA_PHYSIOLOGICAL_PARAMETER_THERAPY_OUT.getPhysio_param_thrpy_value()],
                            values[PhysiologicalParameterTherapy.DATA_PHYSIOLOGICAL_PARAMETER_THERAPY_OUT.getPhysio_param_thrpy_in_out()]
                    );
                }

                data.put(Constants.THERAPIST, therapist);
                data.put(Constants.INSTITUTION, institution);
                data.put(Constants.PHYSIOLICAL_PARAMETER_THERAPY_IN, LPhysiologicalParameterTherapyIn);
                data.put(Constants.PHYSIOLICAL_PARAMETER_THERAPY_OUT, physiologicalParameterTherapyOut);
                data.put(Constants.EXERCISES, LExercises);


            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
        e.printStackTrace();
        }finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return data;
    }

    private static GenderViewModel getGenderViewModel(String object) {
        String[] values = object.split(Gender.DATA.getGenderObjectSeparator());
        return new GenderViewModel(
                Integer.parseInt(values[Gender.DATA.getGender_id()]),
                values[Gender.DATA.getGender_description()],
                values[Gender.DATA.getGender_name()]
        );

    }

    private static DocumentTypeViewModel getDocumentTypeViewModel(String object) {
        String[] values = object.split(DocumentType.DATA.getDocumentTypeObjectSeparator());
        return new DocumentTypeViewModel(
                Integer.parseInt(values[DocumentType.DATA.getDocument_type_id()]),
                values[DocumentType.DATA.getDocument_type_description()],
                values[DocumentType.DATA.getDocument_type_name()]
        );

    }

    private static NeighborhoodViewModel getNeighborhoodViewModel(String object) {
        String[] values = object.split(Neighborhood.DATA.getNeighborhoodObjectSeparator());

        return new NeighborhoodViewModel(
                Integer.parseInt(values[Neighborhood.DATA.getNeighborhood_id()]),
                values[Neighborhood.DATA.getNeighborhood_description()],
                values[Neighborhood.DATA.getNeighborhood_name()],
                Integer.parseInt(values[Neighborhood.DATA.getCity_id()])
        );

    }

    private static List<ExerciseViewModel> getExercisesViewModel(String[] objects) {

        String[] objectValues;
        List<ExerciseViewModel> list = new ArrayList<>();

        for (String value : objects) {
            objectValues = value.split(Exercise.DATA.getExerciseObjectSeparator());

            list.add(new ExerciseViewModel(
                    objectValues[Exercise.DATA.getExercise_routine_id()],
                    objectValues[Exercise.DATA.getExercise_routine_url()],
                    objectValues[Exercise.DATA.getExercise_routine_name()],
                    objectValues[Exercise.DATA.getExercise_routine_description()]
            ));
        }

        return list;
    }

    private static List<PhysiologicalParameterTherapyViewModel> getPhysiologicalParameterTherapyViewModel(String[] objects) {

        String[] objectValues;
        List<PhysiologicalParameterTherapyViewModel> list = new ArrayList<>();
        try {
            for (String value : objects) {
                if (value.contains(PhysiologicalParameterTherapy.DATA_PHYSIOLOGICAL_PARAMETER_THERAPY_IN.getPhysiologicalParameterSeparator())) {

                    objectValues = value.split(PhysiologicalParameterTherapy.DATA_PHYSIOLOGICAL_PARAMETER_THERAPY_IN.getPhysiologicalParameterSeparator());

                    if (objectValues != null) {
                        list.add(new PhysiologicalParameterTherapyViewModel(
                                Integer.parseInt(objectValues[PhysiologicalParameterTherapy.DATA_PHYSIOLOGICAL_PARAMETER_THERAPY_IN.getPhysio_param_thrpy_id()]),
                                Integer.parseInt(objectValues[PhysiologicalParameterTherapy.DATA_PHYSIOLOGICAL_PARAMETER_THERAPY_IN.getPhysio_param_id()]),
                                Integer.parseInt(objectValues[PhysiologicalParameterTherapy.DATA_PHYSIOLOGICAL_PARAMETER_THERAPY_IN.getTherapy_id()]),
                                objectValues[PhysiologicalParameterTherapy.DATA_PHYSIOLOGICAL_PARAMETER_THERAPY_IN.getPhysio_param_thrpy_value()],
                                objectValues[PhysiologicalParameterTherapy.DATA_PHYSIOLOGICAL_PARAMETER_THERAPY_IN.getPhysio_param_thrpy_in_out()]
                        ));
                    }
                }
            }


            return list;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

    }


    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}