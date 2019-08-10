package com.rehapp.rehappmovil.rehapp.Utils.Constants;

public enum DocumentType {

    DATA(1,"*",0,1,2);

    int rowId;
    String documentTypeObjectSeparator;
    int document_type_id;
    int document_type_name;
    int document_type_description;

    DocumentType(int rowId, String documentTypeObjectSeparator, int document_type_id, int document_type_name, int document_type_description) {
        this.rowId = rowId;
        this.documentTypeObjectSeparator = documentTypeObjectSeparator;
        this.document_type_id = document_type_id;
        this.document_type_name = document_type_name;
        this.document_type_description = document_type_description;
    }

    public int getRowId() {
        return rowId;
    }

    public String getDocumentTypeObjectSeparator() {
        return documentTypeObjectSeparator;
    }

    public int getDocument_type_id() {
        return document_type_id;
    }

    public int getDocument_type_name() {
        return document_type_name;
    }

    public int getDocument_type_description() {
        return document_type_description;
    }
}
