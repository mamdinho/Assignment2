package com.example.assignment2;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CarDao {
    @Insert
    public void insertNewCar(Car newCar);

    @Query("SELECT * FROM Car WHERE id = :idcar")
    public List<Car> getCarId(int idcar);

    @Delete
    public void deleteCar(Car existingCar);

    @Query("Select * FROM Car WHERE carModel1 LIKE :carModel")
    public List<Car> getCar(String carModel);
}
