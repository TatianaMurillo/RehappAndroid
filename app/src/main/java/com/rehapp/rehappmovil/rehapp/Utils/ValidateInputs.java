package com.rehapp.rehappmovil.rehapp.Utils;

import android.content.Context;
import android.widget.Toast;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
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


    public List<Object> ValidateDataObject(List<DataValidation> objectValidationList)
    {
        String msg="";
        List<Object> list= new ArrayList<>();
        for(DataValidation data:objectValidationList)
        {
            if(data.getMaxText()!=-1 && data.getValue().length()>data.getMaxTextValue()){
                msg="El máximo valor para " + data.getName() + " es " + data.getMaxTextValue();
                list.add(false);
                list.add(msg);
                return list;
            }

            if(data.getMinText()!=-1 && data.getValue().length()<data.getMinTextValue()){
                msg="El mínimo valor para " + data.getName() + " es " + data.getMinTextValue();
                list.add(false);
                list.add(msg);
                return list;
            }

            if(data.getEqualsTo()!=-1 && data.getValue().length()!=data.getEqualsToValue()){
                msg="El tamaño del campo " + data.getName() + " debe ser " + data.getEqualsToValue();
                list.add(false);
                list.add(msg);
                return list;
            }

            if(data.getEmptyValue()!=-1 && data.getValue().equals("")){
                msg="El campo " + data.getName() + " no esta vacío ";
                list.add(false);
                list.add(msg);
                return list;
            }

            if(data.getNotEmptyValue()!=-1 && data.getValue().equals("")){
                msg="El campo " + data.getName() + " no tiene valor";
                list.add(false);
                list.add(msg);
                return list;
            }

            if(data.getNotZeroValue()!=-1 && (data.getValue().equals("0") || data.getValue().equals("0.0"))){
                msg="El valor del campo " + data.getName() + " no es válido.";
                list.add(false);
                list.add(msg);
                return list;
            }
            if(data.getNotSelectedValue()!=-1 && data.getValue().equals("-1")){
                msg="No se seleccionó el valor del campo " + data.getName() + ".";
                list.add(false);
                list.add(msg);
                return list;
            }

        }
        list.add(true);
        list.add("");
        return list;
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
