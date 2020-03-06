package com.work.criminalintent.kt

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import com.work.criminalintent.R
import kotlinx.android.synthetic.main.actvity_crime_pager.*
import java.util.*
import kotlin.collections.ArrayList

const val EXTRA_CRIME_ID = "android.criminalintent.crime_id"

class CrimePagerActivity : AppCompatActivity() {

    private var mCrimes: List<Crime> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.actvity_crime_pager)

        val crimeId = intent.getSerializableExtra(EXTRA_CRIME_ID)
        mCrimes = CrimeLab.get(this).getCrimes()
        val fragmentManager = supportFragmentManager
        crime_view_pager.adapter = object : FragmentStatePagerAdapter(fragmentManager,
                BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
            override fun getItem(position: Int): Fragment {
                val crime = mCrimes.get(position)
                return CrimeFragment.newInstance(crime.id)
            }

            override fun getCount(): Int = mCrimes.size

        }

        for (i in 0..mCrimes.size) {
            if (mCrimes[i].id == crimeId) {
                crime_view_pager.currentItem = i
                break
            }
        }

    }

    companion object {
        fun newIntent(context: Context, crimeId: UUID): Intent {
            val intent = Intent(context, CrimePagerActivity::class.java)
            intent.putExtra(EXTRA_CRIME_ID, crimeId)
            return intent
        }
    }
}