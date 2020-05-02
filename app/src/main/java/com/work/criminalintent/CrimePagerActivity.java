package com.work.criminalintent;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.work.criminalintent.database.Repository;
import com.work.criminalintent.model.Crime;
import java.util.List;

public class CrimePagerActivity extends AppCompatActivity implements CrimeFragment.Callbacks {

    private static final  String EXTRA_CRIME_ID = "android.criminalintent.crime_id";

    private ViewPager mViewPager;
    private List<Crime> mCrimes;
    private Repository mRepository;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actvity_crime_pager);
        mRepository = Repository.get(this);

        long crimeId =  getIntent().getLongExtra(EXTRA_CRIME_ID, 0);

        mViewPager = findViewById(R.id.crime_view_pager);

        mCrimes = mRepository.getCrimes();
        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager,
                FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                Crime crime = mCrimes.get(position);
                Log.d("CrimePagerActivity", crime + "");
                return CrimeFragment.newInstance(crime.getId());
            }

            @Override
            public int getCount() {
                return mCrimes.size();
            }
        });
        setItem(crimeId);

    }

    public static Intent newIntent(Context context, long crimeId) {
        Intent intent = new Intent(context, CrimePagerActivity.class);
        intent.putExtra(EXTRA_CRIME_ID, crimeId);
        return intent;
    }

    private void setItem(long crimeId) {
        for (int i = 0; i < mCrimes.size(); i++) {
            if (mCrimes.get(i).getId() == crimeId) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }

    @Override
    public void onCrimeUpdated(Crime crime) {
    }

    @Override
    public void onCrimeChanged(Crime crime) {
        setItem(crime.getId());
    }
}
