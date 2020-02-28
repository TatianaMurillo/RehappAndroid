package com.rehapp.rehappmovil.rehapp.Utils;

public class DataValidation {


    private String value;
    private String name;


    private int maxTextValue;
    private int minTextValue;
    private int equalsToValue;

    /**
     * Indicadores de ejecución
     */
    private int minText = -1;
    private int maxText = -1;
    private int equalsTo = -1;
    private int noEqualsTo = -1;
    private int notEmptyValue = -1;
    private int emptyValue = -1;
    private int notSelectedValue = -1;
    private int notZeroValue = -1;

    public DataValidation(String value, String name) {
        this.value = value;
        this.name = name;
    }


    public DataValidation textMinLength(int minText) {
        this.minTextValue=minText;
        this.minText = 1;
        return this;
    }

    public DataValidation textMaxLength(int maxText) {
        this.maxTextValue=maxText;
        this.maxText = 1;
        return this;
    }

    public DataValidation textLengthEqualsTo(int equalsTo) {
        this.equalsToValue=equalsTo;
        this.equalsTo = 1;
        return this;
    }

    public DataValidation noEmptyValue() {
        this.notEmptyValue = 1;
        return this;
    }

    public DataValidation emptyValue() {
        this.emptyValue = 1;
        return this;
    }

    public DataValidation selectedValue() {
        this.notSelectedValue = 1;
        return this;
    }

    public DataValidation notZeroValue() {
        this.notZeroValue = 1;
        return this;
    }


    public DataValidation notEqualsTo() {
        this.noEqualsTo = 1;
        return this;
    }

    public static DataValidation dataToValidate(String value, String name) {
        return new DataValidation(value, name);
    }

    /**
     *
     * Getters indicadores
     */

    public String getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public int getMinText() {
        return minText;
    }

    public int getMaxText() {
        return maxText;
    }

    public int getEqualsTo() {
        return equalsTo;
    }

    public int getNotEmptyValue() {
        return notEmptyValue;
    }

    public int getEmptyValue() {
        return emptyValue;
    }

    public int getNotSelectedValue() {
        return notSelectedValue;
    }

    public int getNotZeroValue() {
        return notZeroValue;
    }


    /**
     * Getters valores a comparar
     */
    public int getMinTextValue() {
        return minTextValue;
    }

    public int getMaxTextValue() {
        return maxTextValue;
    }

    public int getEqualsToValue() {
        return equalsToValue;
    }
}
