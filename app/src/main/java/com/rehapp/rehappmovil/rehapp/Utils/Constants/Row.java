package com.rehapp.rehappmovil.rehapp.Utils.Constants;

public enum Row {

    DATA(0,"-");

    int  dataRow;
    String rowDataSeparator;



    Row(int dataRow,String rowDataSeparator) { this.dataRow=dataRow;this.rowDataSeparator=rowDataSeparator; }

    public int getDataRow() {
        return dataRow;
    }

    public String getRowDataSeparator() {
        return rowDataSeparator;
    }
}
