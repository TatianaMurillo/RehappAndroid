package com.rehapp.rehappmovil.rehapp.Utils;


import com.rehapp.rehappmovil.rehapp.Models.ExerciseViewModel;
import com.rehapp.rehappmovil.rehapp.Models.InstitutionViewModel;
import com.rehapp.rehappmovil.rehapp.Models.PhysiologicalParameterTherapyViewModel;
import com.rehapp.rehappmovil.rehapp.Models.TherapistViewModel;
import com.rehapp.rehappmovil.rehapp.Models.TherapyViewModel;
import com.rehapp.rehappmovil.rehapp.Utils.Constants.Constants;
import com.rehapp.rehappmovil.rehapp.Utils.Constants.Institution;
import com.rehapp.rehappmovil.rehapp.Utils.Constants.PhysiologicalParameterTherapy;
import com.rehapp.rehappmovil.rehapp.Utils.Constants.Row;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ReadCSVFile {


    private static TherapistViewModel therapist;
    private static InstitutionViewModel institution;
    private static TherapyViewModel therapy;
    private static PhysiologicalParameterTherapyViewModel physiologicalParameterTherapyIn;
    private static PhysiologicalParameterTherapyViewModel physiologicalParameterTherapyOut;
    private static ExerciseViewModel exercise;
    private static List<ExerciseViewModel> LExercises;
    private static List<Object> list = new ArrayList<>();

    public static TherapistViewModel getTherapistData()
    {
        return therapist;
    }
    public static InstitutionViewModel getInstitutionData()
    {
        return institution;
    }

    public static TherapyViewModel getTherapyData()
    {
        return therapy;
    }
    public static PhysiologicalParameterTherapyViewModel getPhysiologicalParameterTherapyInData()
    {
        return physiologicalParameterTherapyIn;
    }
    public static PhysiologicalParameterTherapyViewModel getPhysiologicalParameterTherapyOutData()
    {
        return physiologicalParameterTherapyOut;
    }
    public static List<ExerciseViewModel> getExercisesData()
    {
        return LExercises;
    }

    public static List<Object> loadTherapyInformation() {

        String[] values;


        String fileName = Constants.TEMP_FILE_URL;
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = "-";
        String row;


        try {
            br = new BufferedReader(new FileReader(fileName));

            while ((line = br.readLine())!= null) {

                values = line.split(cvsSplitBy);
                row=values[Row.DATA.getDataRow()];

                if(row.equals(String.valueOf(PhysiologicalParameterTherapy.DATA.getRow())))
                {
                    physiologicalParameterTherapyIn = new PhysiologicalParameterTherapyViewModel(
                            Integer.parseInt(values[PhysiologicalParameterTherapy.DATA.getPhysio_param_thrpy_id()]),
                            Integer.parseInt(values[PhysiologicalParameterTherapy.DATA.getPhysio_param_id()]),
                            Integer.parseInt(values[PhysiologicalParameterTherapy.DATA.getTherapy_id()]),
                            values[PhysiologicalParameterTherapy.DATA.getPhysio_param_thrpy_value()],
                            values[PhysiologicalParameterTherapy.DATA.getPhysio_param_thrpy_in_out()]
                    );

                }else if(row.equals(String.valueOf(Institution.DATA.getRow())))
                {

                }

                list.add(therapist);
                list.add(institution);
                list.add(therapy);
                list.add(physiologicalParameterTherapyIn);
                list.add(physiologicalParameterTherapyOut);
                list.add(LExercises);



            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
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

        return list;
    }


}


