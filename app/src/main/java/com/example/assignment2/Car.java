package com.example.assignment2;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Car {
    @PrimaryKey
    int id;

    String carModel1;
    String carModel2;
    Double year;

    @Ignore
    Boolean isFavourite;

    public Car(){}

    public Car(int p_id, String carmod1, String carmod2, Double caryr, Boolean isFav){
        id= p_id;
        carModel1= carmod1;
        carModel2= carmod2;
        year= caryr;
        isFavourite= isFav;
    }

    public void setIsFavourite(Boolean flag){
        this.isFavourite = flag;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCarModel1() {
        return carModel1;
    }

    public void setCarModel1(String carModel1) {
        this.carModel1 = carModel1;
    }

    public String getCarModel2() {
        return carModel2;
    }

    public void setCarModel2(String carModel2) {
        this.carModel2 = carModel2;
    }

    public Double getYear() {
        return year;
    }

    public void setYear(Double year) {
        this.year = year;
    }

    public Boolean getFavourite() {
        return isFavourite;
    }

    public void setFavourite(Boolean favourite) {
        isFavourite = favourite;
    }
}


