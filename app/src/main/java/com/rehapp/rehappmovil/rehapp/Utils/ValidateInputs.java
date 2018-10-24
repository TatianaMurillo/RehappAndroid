package com.rehapp.rehappmovil.rehapp.Utils;

import java.util.ArrayList;

public class ValidateInputs {

    public  ValidateInputs()
    {

    }

    public static boolean ValidateString(ArrayList<String> dataInput)
    {
            for(String data:dataInput)
            {
                if(data.isEmpty())
                {
                    return false;
                }

            }

            return true;
    }


}
