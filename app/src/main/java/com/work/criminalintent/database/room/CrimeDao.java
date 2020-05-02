package com.work.criminalintent.database.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.work.criminalintent.model.Crime;
import java.util.List;

@Dao
public interface CrimeDao {
    @Query("SELECT * FROM crime")
    List<Crime> getCrimes();
    @Query("SELECT * FROM crime WHERE id = :id")
    Crime getCrime(long id);
    @Update
    void updateCrime(Crime c);
    @Insert
    long addCrime(Crime c);
    @Delete
    void deleteCrime(Crime c);
}
