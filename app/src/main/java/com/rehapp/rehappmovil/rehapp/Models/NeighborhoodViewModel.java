package com.rehapp.rehappmovil.rehapp.Models;

public class NeighborhoodViewModel {

    private int neighborhood_id;
    private String neighborhood_description;
    private String neighborhood_name;
    private int city_id;

    public NeighborhoodViewModel(int neighborhood_id, String neighborhood_description, String neighborhood_name, int city_id) {
        this.neighborhood_id = neighborhood_id;
        this.neighborhood_description = neighborhood_description;
        this.neighborhood_name = neighborhood_name;
        this.city_id = city_id;
    }

    public int getNeighborhood_id() {
        return neighborhood_id;
    }

    public void setNeighborhood_id(int neighborhood_id) {
        this.neighborhood_id = neighborhood_id;
    }

    public String getNeighborhood_description() {
        return neighborhood_description;
    }

    public void setNeighborhood_description(String neighborhood_description) {
        this.neighborhood_description = neighborhood_description;
    }

    public String getNeighborhood_name() {
        return neighborhood_name;
    }

    public void setNeighborhood_name(String neighborhood_name) {
        this.neighborhood_name = neighborhood_name;
    }

    public int getCity_id() {
        return city_id;
    }

    public void setCity_id(int city_id) {
        this.city_id = city_id;
    }
}

