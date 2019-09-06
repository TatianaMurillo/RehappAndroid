package com.rehapp.rehappmovil.rehapp.Utils;

import android.provider.BaseColumns;

public class TherapyDBHelperEntry {


    public static abstract class PhysiologicalParameterTherapyEntry implements BaseColumns {
        public static final String TABLE_NAME ="physioParamTherapy";

        public static final String PHYSIO_PARAM_THERAPY_ID = "physioParamTherapyId";
        public static final String PHYSIO_PARAM_ID = "physioParam";
        public static final String THERAPY_ID = "therapyId";
        public static final String THERAPY_VALUE = "therapyValue";
        public static final String THERAPY_IN_OUT = "therapyInOut";
        public static final String ORDER = "rowSequence";
    }
}
