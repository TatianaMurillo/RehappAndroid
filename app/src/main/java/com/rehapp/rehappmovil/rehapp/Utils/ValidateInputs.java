package com.rehapp.rehappmovil.rehapp.Utils;

import java.util.ArrayList;

public class ValidateInputs {

    public static ValidateInputs  validate()
    {
        return new ValidateInputs();
    }

    public boolean ValidateString(ArrayList<String> dataInput)
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
