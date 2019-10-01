package com.work.criminalintent;

import android.content.Context;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CrimeLab {
    private static CrimeLab crimeLab;
    private List<Crime> crimes;

    private CrimeLab(Context context) {
        crimes = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Crime crime = new Crime();
            crime.setTitle("Crime #" + i);
            crime.setSolved(i % 2 == 0);
            crimes.add(crime);
        }
    }

    public static CrimeLab get(Context context) {
        if (crimeLab == null) {
            crimeLab = new CrimeLab(context);
        }
        return crimeLab;
    }
    public List<Crime> getCrimes() {
        return crimes;
    }

    public Crime getCrime(UUID uuid) {
        Crime resCrime = null;
        for (Crime crime : crimes) {
            if (crime.getId().equals(uuid)) {
                resCrime = crime;
            }
        }
        return resCrime;
    }
}
