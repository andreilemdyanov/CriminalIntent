package com.work.criminalintent.kt

import androidx.fragment.app.Fragment

class CrimeListActivity : SingleFragmentActivity() {

    override fun createFragment() : Fragment {
        return CrimeListFragment()
    }
}
