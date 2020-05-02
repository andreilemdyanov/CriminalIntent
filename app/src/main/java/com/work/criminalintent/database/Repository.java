package com.work.criminalintent.database;

import android.content.Context;
import com.work.criminalintent.App;
import com.work.criminalintent.database.room.CrimeDao;
import com.work.criminalintent.model.Crime;
import java.io.File;
import java.util.List;

public class Repository {
    private static Repository sRepo;
    private Context mContext;
    private CrimeDao mCrimeDao;

    private Repository(Context context) {
        mContext = context.getApplicationContext();
        mCrimeDao = App.getInstance().getDatabase().crimeDao();
    }

    public static Repository get(Context context) {
        if (sRepo == null) {
            sRepo = new Repository(context);
        }
        return sRepo;
    }

    public List<Crime> getCrimes() {
        List<Crime> crimes = mCrimeDao.getCrimes();
        return crimes;
    }

    public Crime getCrime(long id) {
        Crime crime = mCrimeDao.getCrime(id);
        return crime;
    }

    public File getPhotoFile(Crime crime) {
        File filesDir = mContext.getFilesDir();
        return new File(filesDir, crime.getPhotoFileName());

    }

    public void updateCrime(Crime crime) {
        mCrimeDao.updateCrime(crime);
    }

    public long addCrime(Crime crime) {
       return mCrimeDao.addCrime(crime);
    }

    public void deleteCrime(Crime crime) {
        mCrimeDao.deleteCrime(crime);
    }
}
