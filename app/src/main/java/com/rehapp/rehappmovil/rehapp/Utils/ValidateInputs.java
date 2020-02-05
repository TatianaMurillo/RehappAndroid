package com.rehapp.rehappmovil.rehapp.Utils;

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
                if("".equals(data)){
                    return false;
                }

            }

            return true;
    }


    public boolean ValidateIntegers(List<Integer> dataInput)
    {
        for(int data:dataInput)
        {
            if(data==-1)
            {
                return false;
            }
        }

        return true;
    }

    public boolean ValidateValueZeroInIntegers(List<Integer> dataInput)
    {
        for(int data:dataInput)
        {
            if(data==0)
            {
                return false;
            }
        }

        return true;
    }


}
