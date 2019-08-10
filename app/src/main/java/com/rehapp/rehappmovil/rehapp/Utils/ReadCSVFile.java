package com.rehapp.rehappmovil.rehapp.Utils;



import com.rehapp.rehappmovil.rehapp.Models.*;
import com.rehapp.rehappmovil.rehapp.Utils.Constants.Constants;
import com.rehapp.rehappmovil.rehapp.Utils.Constants.*;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReadCSVFile {


    private static TherapistViewModel therapist;
    private static InstitutionViewModel institution;
    private static TherapyViewModel therapy;
    private static List<PhysiologicalParameterTherapyViewModel> LPhysiologicalParameterTherapyIn;
    private static PhysiologicalParameterTherapyViewModel physiologicalParameterTherapyOut;
    private static ExerciseViewModel exercise;
    private static List<ExerciseViewModel> LExercises;
    private static Map<String, Object> data = new HashMap<>();

    public static TherapistViewModel getTherapistData() {
        return therapist;
    }

    public static InstitutionViewModel getInstitutionData() {
        return institution;
    }

    public static TherapyViewModel getTherapyData() {
        return therapy;
    }


    public static PhysiologicalParameterTherapyViewModel getPhysiologicalParameterTherapyOutData() {
        return physiologicalParameterTherapyOut;
    }

    public static List<ExerciseViewModel> getExercisesData() {
        return LExercises;
    }

    public static Map<String, Object> loadTherapyInformation() {

        String[] values;


        String fileName = Constants.TEMP_FILE_URL;
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = Row.DATA.getRowDataSeparator();
        String row;


        try {
            br = new BufferedReader(new FileReader(fileName));

            while ((line = br.readLine()) != null) {

                values = line.split(cvsSplitBy);
                row = values[Row.DATA.getDataRow()];

                if (row.equals(String.valueOf(PhysiologicalParameterTherapy.DATA_PHYSIOLOGICAL_PARAMETER_THERAPY_IN.getRowId()))) {

                    LPhysiologicalParameterTherapyIn=getPhysiologicalParameterTherapyViewModel(values);


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
                }else if(row.equals(String.valueOf(Exercise.DATA.getRowId())))
                {
                    LExercises=getExercisesViewModel(values);
                }else if(row.equals(String.valueOf(PhysiologicalParameterTherapy.DATA_PHYSIOLOGICAL_PARAMETER_THERAPY_OUT.getRowId()))) {
                    physiologicalParameterTherapyOut= new PhysiologicalParameterTherapyViewModel(
                            Integer.parseInt(values[PhysiologicalParameterTherapy.DATA_PHYSIOLOGICAL_PARAMETER_THERAPY_OUT.getPhysio_param_thrpy_id()]),
                            Integer.parseInt(values[PhysiologicalParameterTherapy.DATA_PHYSIOLOGICAL_PARAMETER_THERAPY_OUT.getPhysio_param_id()]),
                            Integer.parseInt(values[PhysiologicalParameterTherapy.DATA_PHYSIOLOGICAL_PARAMETER_THERAPY_OUT.getTherapy_id()]),
                            values[PhysiologicalParameterTherapy.DATA_PHYSIOLOGICAL_PARAMETER_THERAPY_OUT.getPhysio_param_thrpy_value()],
                            values[PhysiologicalParameterTherapy.DATA_PHYSIOLOGICAL_PARAMETER_THERAPY_OUT.getPhysio_param_thrpy_in_out()]
                    );
                }

                data.put(Constants.THERAPIST,therapist);
                data.put(Constants.INSTITUTION,institution);
                data.put(Constants.PHYSIOLICAL_PARAMETER_THERAPY_IN,LPhysiologicalParameterTherapyIn);
                data.put(Constants.PHYSIOLICAL_PARAMETER_THERAPY_OUT,physiologicalParameterTherapyOut);
                data.put(Constants.EXERCISES,LExercises);


            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
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

        for (String value : objects) {
            objectValues = value.split(PhysiologicalParameterTherapy.DATA_PHYSIOLOGICAL_PARAMETER_THERAPY_IN.getPhysiologicalParameterSeparator());

            if(objectValues!=null) {
                list.add(new PhysiologicalParameterTherapyViewModel(
                        Integer.parseInt(objectValues[PhysiologicalParameterTherapy.DATA_PHYSIOLOGICAL_PARAMETER_THERAPY_IN.getPhysio_param_thrpy_id()]),
                        Integer.parseInt(objectValues[PhysiologicalParameterTherapy.DATA_PHYSIOLOGICAL_PARAMETER_THERAPY_IN.getPhysio_param_id()]),
                        Integer.parseInt(objectValues[PhysiologicalParameterTherapy.DATA_PHYSIOLOGICAL_PARAMETER_THERAPY_IN.getTherapy_id()]),
                        objectValues[PhysiologicalParameterTherapy.DATA_PHYSIOLOGICAL_PARAMETER_THERAPY_IN.getPhysio_param_thrpy_value()],
                        objectValues[PhysiologicalParameterTherapy.DATA_PHYSIOLOGICAL_PARAMETER_THERAPY_IN.getPhysio_param_thrpy_in_out()]
                ));
            }
        }

        return list;
    }

}