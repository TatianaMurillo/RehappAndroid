package com.rehapp.rehappmovil.rehapp.Utils.Constants;

public enum Neighborhood {

    DATA(5,"*",0,1,2,3);

    int rowId;
    String neighborhoodObjectSeparator;
    int neighborhood_id;
    int neighborhood_description;
    int neighborhood_name;
    int city_id;

    Neighborhood(int rowId, String neighborhoodObjectSeparator, int neighborhood_id, int neighborhood_description, int neighborhood_name, int city_id) {
        this.rowId = rowId;
        this.neighborhoodObjectSeparator = neighborhoodObjectSeparator;
        this.neighborhood_id = neighborhood_id;
        this.neighborhood_description = neighborhood_description;
        this.neighborhood_name = neighborhood_name;
        this.city_id = city_id;
    }

    public String getNeighborhoodObjectSeparator() {
        return neighborhoodObjectSeparator;
    }

    public int getNeighborhood_id() {
        return neighborhood_id;
    }

    public int getNeighborhood_description() {
        return neighborhood_description;
    }

    public int getNeighborhood_name() {
        return neighborhood_name;
    }

    public int getCity_id() {
        return city_id;
    }

    public int getRowId() {
        return rowId;
    }


}
