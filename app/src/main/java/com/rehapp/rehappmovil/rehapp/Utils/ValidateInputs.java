package com.rehapp.rehappmovil.rehapp.Utils;

import java.util.ArrayList;
import java.util.List;

public class ValidateInputs {

    public static ValidateInputs  validate()
    {
        return new ValidateInputs();
    }

    public boolean ValidateString(List<String> dataInput)
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
