package com.rehapp.rehappmovil.rehapp.Utils;

import android.content.Context;
import android.widget.Toast;

import com.rehapp.rehappmovil.rehapp.Models.PreferencesData;

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

    public boolean ValidateStringsAndIntegers(List<String> dataInput,List<Integer> dataIntegerInput)
    {
        for(String data:dataInput)
        {
            if("".equals(data)){
                return false;
            }

        }

        for(int data:dataIntegerInput)
        {
            if(data==-1)
            {
                return false;
            }
        }

        return true;
    }


    public boolean ValidateDataObject(List<DataValidation> objectValidationList, Context context)
    {
        String msg="";
        for(DataValidation data:objectValidationList)
        {
            if(data.getMaxText()!=-1 && data.getValue().length()>data.getMaxTextValue()){
                msg="El máximo valor para " + data.getName() + " es " + data.getMaxTextValue();
                Toast.makeText(context, msg,Toast.LENGTH_LONG).show();
                return false;
            }

            if(data.getMinText()!=-1 && data.getValue().length()<data.getMinTextValue()){
                msg="El mínimo valor para " + data.getName() + " es " + data.getMinTextValue();
                Toast.makeText(context, msg,Toast.LENGTH_LONG).show();
                return false;
            }

            if(data.getEqualsTo()!=-1 && data.getValue().length()!=data.getEqualsToValue()){
                msg="El tamaño del campo " + data.getName() + " debe ser " + data.getEqualsToValue();
                Toast.makeText(context, msg,Toast.LENGTH_LONG).show();
                return false;
            }

            if(data.getEmptyValue()!=-1 && !data.getValue().equals("")){
                msg="El campo " + data.getName() + " no esta vacío ";
                Toast.makeText(context, msg,Toast.LENGTH_LONG).show();
                return false;
            }

            if(data.getNotEmptyValue()!=-1 && data.getValue().equals("")){
                msg="El campo " + data.getName() + " no tiene valor";
                Toast.makeText(context, msg,Toast.LENGTH_LONG).show();
                return false;
            }

            if(data.getNotZeroValue()!=-1 && !data.getValue().equals("0")&& !data.getValue().equals("-1")){
                msg="El valor del campo " + data.getName() + " no es válido.";
                Toast.makeText(context, msg,Toast.LENGTH_LONG).show();
                return false;
            }
            if(data.getNotSelectedValue()!=-1 && data.getValue().equals("-1")){
                msg="No se seleccionó el valor del campo " + data.getName() + ".";
                Toast.makeText(context, msg,Toast.LENGTH_LONG).show();
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

    public boolean validateNonAcceptableValueInInteger(List<Integer> dataInput)
    {
        for(int data:dataInput)
        {
            if(data==0 || data==-1)
            {
                return false;
            }
        }

        return true;
    }


}
