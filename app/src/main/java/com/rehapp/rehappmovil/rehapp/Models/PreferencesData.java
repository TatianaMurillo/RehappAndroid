package com.rehapp.rehappmovil.rehapp.Models;

public class PreferencesData {

    public static final String PreferenceFileName = "infoToRestore";
    public static final String loginToken = "token";
    public static final String userActive ="userActive";
    public static final String userActiveId ="userActiveId";

    public static final String TherapistEmailLogin = "therapistEmailLogin";
    public static final String TherapistPasswordlLogin = "therapistPasswordlLogin";


    public static final String PatientId ="patientId";
    public static final String PatientDocument ="document";
    public static final String PatientTpoDocument ="tpoDocument";
    public static final String TherapySelectedId="TherapySelectedId";
    public static final String MedicalHistorySelectedId="MedicalHistorySelectedId";
    public static final String ExerciseRoutineUrl="exerciseRoutineUrl";
    public static final String TherapyExerciseRoutineId="therapyExerciseRoutineId";
    public static final String ExerciseRoutineId="exerciseRoutineId";

    public static final String TherapyId="TherapyId";
    public static final String TherapistId="TherapistId";
    public static final String TherapistName="TherapistName";
    public static final String TherapistEmail="TherapistEmail";
    public static final String ADD="ADD";
    public static final String DETAIL="DETAIL";
    public static final String PatientAction="PatientAction";
    public static final String TherapyAction="TherapyAction";
    public static final String MedicaHistoryAction="MedicaHistoryAction";
    public static final String PhysiologicalParameterAction="PhysiologicalParameterAction";
    public static final String PhysiologicalParameterTherapyDataListFailed ="Hubo un error al consultar los parametros fisiológicos.";
    public static final String PhysiologicalParameterTherapyDataMgsError ="Error al diligenciar  los parametros fisiológicos. Contacte al administrador.";
    public static final String MedicalHistoryVitalSignsDataMgsError ="Error al diligenciar los signos vitales. Contacte al administrador.";

    public static final String PhysiologicalParameterTherapySuccessMgs ="Parametros fisiológicos guardados correctamente.";
    public static final String MedicalHistoryVitalSignsSuccessMgs ="Signos vitales guardados correctamente.";
    public static final String PhysiologicalParameterTherapySesionIN ="IN";
    public static final String PhysiologicalParameterTherapySesionOUT ="OUT";

    public static final String loginIncorrectDataMgs ="Por favor ingrese los datos para iniciar sesión.";
    public static final String loginFailureMsg="Error en la aplicacion.";
    public static final String searchPatientListDocumentTypesMgs ="Diligenciar todos los datos por favor.";
    public static final String registerUserDataMgs ="Diligenciar todos los datos por favor.";
    public static final String registerUserFailedMgs ="Error al registrar usuario.";
    public static final String registerExistedUserMgs ="El terapeuta ya se encuentra registrado o presenta conflictos.";
    public static final String registerUserEmailDataMgs ="Los correos no coinciden.";
    public static final String registerUserPasswordDataMgs ="Las contraseñas no coinciden.";

    public static final String searchTherapistPatient ="Hubo un error al buscar terapeuta.";
    public static final String TherapistNotFoundPatient ="El terapeuta no existe.";
    public static final String searchPatientPatient ="Hubo un error al buscar paciente.";
    public static final String searchPatientPatientNonExist ="Paciente no existe.";
    public static final String therapyDetailTherapyNonExist ="La terapia seleccionada no existe.";
    public static final String therapyDetailOptionNotFound ="No se encontró opción a ejecutar";
    public static final String documentTypeList ="Hubo un error al listar tipos de documentos.";
    public static final String therapyListError ="Hubo un error al listar terapias.";
    public static final String patientMedicalHistoryListError ="Hubo un error al listar historias médicas.";
    public static final String searchCreatePatientDataMsg="Diligenciar todos los datos por favor.";



    public static final String vitalSignsLoadFailed ="Fallo al cargar signos vitales.";
    public static final String questionairesLoadFailed ="Fallo al cargar questionarios para diligenciar.";
    public static final String questionairesOptionSelectedLoadFailed ="Fallo al cargar registros.";

    public static final String questionairesOptionCreateSuccessMsg ="Respuestas guardadas correctamente.";
    public static final String questionairesOptionCreateFailedMsg ="Hubo un error al guardar respuestas.";
    public static final String questionairesOptionResponseMsg ="Algunas respuestas no se diligenciaron o cargaron.";

    public static final String therapyAddAdditionalInformationFailedMsg ="Error al guardar información adicional de la terapia.";
    public static final String therapyAddAdditionalInformationSuccessMsg ="Datos guardados correctamente.";
    public static final String therapyUpdateSuccessMsg ="Se actualizó la terapia con éxito.";
    public static final String therapyUpdateFailedMsg ="Hubo un error al crear terapia.";
    public static final String therapyUpdateDataFailedMsg ="Ingresar los datos por favor.";
    public static final String therapyNotFoundMsg ="No se encontró la terapia.";

    public static final String therapyCreationIdSuccessMsg ="Se creó la terapia con éxito.";
    public static final String therapyCreationIdFailedMsg ="Hubo un error al generar terapia.";
    public static final String therapyDetailTherapistListMsg ="Hubo un error al listar terapeutas.";
    public static final String therapyDetailExerciseRoutineListMsg ="Hubo un error al listar ejercicios de rutina.";
    public static final String medicalHistoryDiseasesListMsg ="Hubo un error al listar las enfermedades.";

    public static final String therapyDetailSaveExerciseRoutineFailedMsg ="Hubo un error al guardar ejercicios de terapia.";
    public static final String medicalHistoryDiseaseSaveFailedMsg ="Hubo un error al guardar las enfermedades.";
    public static final String therapyDetailSaveExerciseRoutineSuccessMsg ="Ejercicios de terapia guardados correctamente.";
    public static final String medicalHistoryDiseaseSaveSuccessMsg ="enfermedades guardadas correctamente.";

    public static final String medicalHistoryDetailDataMessage ="Los datos para actualizar la terapia no estan completos.";
    public static final String medicalHistoryDetailUpdateSuccesfullMsg ="Se actualizó la historia medica exitosamente.";
    public static final String medicalHistoryDetailDefaultName ="Nombre";
    public static final String medicalHistoryDetailDefaultDescription ="Descripción";
    public static final String medicalHistoryVitalSignEmptyListMsg ="No se encontraron toma de vitales para el paciente.";
    public static final String therapyDetailPhysiologicalParameterTherapyEmptyListMsg ="No se encontraron parametros fisiologicos para la terapia.";
    public static final String therapyDetailTherapyExerciseRoutinesEmptyListMsg ="No se encontraron rutinas para la terapia.";
    public static final String therapyDetailTherapyExerciseRoutinesListMsg ="Hubo un error al listar los ejercicios previamente seleccionados.";
    public static final String therapyDetailInstitutionListMsg ="Hubo un error al listar instituciones.";
    public static final String therapyDetailInstitutionNonExist="No se encontró la institución seleccionada.";
    public static final String therapyCreationDescriptionFieldValue ="Nueva terapia.";

    public static final String therapyDetailTherapistNonExist="No se encontró el terapeuta seleccionado.";
    public static final String therapyRoutineDetailDataMessage="Ingrese los valores en los parametros indicados.";

    public static final String therapyRoutineSuccessCreationMessage="Valores de rutina actualizados correctamente.";
    public static final String therapyRoutineUpdateWithoutRowsMsg="Rutina no registrada para la terapia, por favor crear.";
    public static final String therapyRoutineFailedCreationMessage="Hubo un error al actualizar rutina.";

    public static final String listDocumentTypesFailed="No se pudieron cargar los tipos de documento correctamente.";
    public static final String listNeighborhoodFailed="No se pudieron cargar los barrios correctamente.";
    public static final String listGenderFailed="No se pudieron cargar los géneros correctamente.";

    public static final String updateTherapistSuccess="Terapeuta actualizado exitosamente.";
    public static final String storePatientSuccess="Paciente creado exitosamente.";
    public static final String updatePatientSuccess="Paciente actualizado exitosamente.";
    public static final String storePatientFailed="Error al crear paciente.";
    public static final String udpatePatientFailed="Error al actualizar paciente.";
    public static final String udpateTherapistFailed="Error al actualizar terapeuta.";

    public static final String storePatientEmptyDataMsg="Diligenciar todos los datos por favor.";
    public static final String storeTherapistEmptyDataMsg="Diligenciar todos los datos por favor.";

    public static final int PhysiologicalParameterTherapyValueSize=5;
    public static final int VitalSignsTherapyValueSize=5;
    public static final String therapyDetailWatchVideo="Ver.";


    /**
     * Mensajes para redirigir a los fragments si no se tienen los datos a utiliza
     */
    public static final String MedicalHistoriesPatientFragment ="Seleccionar o crear una historia médica";
    public static final String HistoryTherapiesPatientFragment ="Seleccionar o crear una terapia";
    public static final String SearchCreatePatientFragment ="Buscar o registrar un paciente.";
    public static final String TherapistFragment ="Error al sincronizar datos terapeuta. Contacte admin.";
}
