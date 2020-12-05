package com.example.assignment2;

import android.content.Context;

import androidx.room.Room;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DatabaseClient {
    private static AppDatabase sDatabase;

    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(4);

    public static AppDatabase getInstance(Context context){

        if (sDatabase == null) {
            sDatabase = Room.databaseBuilder(context, AppDatabase.class, "FavouriteCars_db").build();        }

        return sDatabase;
    }

    public static AppDatabase getsDatabase(){
        return sDatabase;
    }

    /* Inserts favourite car into database*/
    public static void insertFavouriteCar(Car newcar){
        databaseWriteExecutor.execute(()->{
            getsDatabase().carDao().insertNewCar(newcar);
        });
    }

    /* Removes favourite car from database*/
    public static void deleteFavouriteCar(Car existingCar){
        databaseWriteExecutor.execute(()->{
            getsDatabase().carDao().deleteCar(existingCar);
        });
    }
}
