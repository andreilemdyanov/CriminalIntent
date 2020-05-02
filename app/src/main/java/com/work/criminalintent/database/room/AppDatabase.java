package com.work.criminalintent.database.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import com.work.criminalintent.model.Crime;

@Database(entities = {Crime.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract CrimeDao crimeDao();
}
