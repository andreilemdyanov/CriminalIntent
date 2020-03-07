package com.work.criminalintent.kt

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.ContactsContract
import android.text.Editable
import android.text.TextWatcher
import android.text.format.DateFormat
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import com.work.criminalintent.R
import com.work.criminalintent.databinding.FragmentCrimeBinding
import java.util.*

const val ARG_CRIME_ID = "crime_id"

class CrimeFragment : Fragment() {
    val DIALOG_DATE = "DialogDate"
    val REQUEST_DATE = 0
    val REQUEST_CONTACT = 1

    private var _binding: FragmentCrimeBinding? = null
    private lateinit var mCrime: Crime


    private val binding get() = _binding!!

    companion object {
        fun newInstance(crimeId: UUID): CrimeFragment {
            val args = Bundle()
            args.putSerializable(ARG_CRIME_ID, crimeId)
            val fragment = CrimeFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentCrimeBinding.inflate(inflater, container, false)
        val v = binding.root
        binding.crimeTitle.setText(mCrime.title)
        binding.crimeTitle.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                mCrime.title = s.toString()
            }
        })
        updateDate()
        binding.crimeDate.setOnClickListener {
            val dialog = DatePickerFragment.newInstance(mCrime.date)
            dialog.setTargetFragment(this, REQUEST_DATE)
            dialog.show(fragmentManager!!, DIALOG_DATE)
        }
        binding.crimeSolved.isChecked = mCrime.isSolved
        binding.crimeSolved.setOnCheckedChangeListener { buttonView, isChecked ->
            mCrime.isSolved = isChecked
        }

        val pickContact = Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI)
        binding.chooseSuspect.setOnClickListener {
            startActivityForResult(pickContact, REQUEST_CONTACT)
        }

        binding.crimeReport.setOnClickListener {
            Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, getCrimeReport())
                putExtra(Intent.EXTRA_SUBJECT, getString(R.string.crime_report_subject))
                val i = Intent.createChooser(this, getString(R.string.send_report))
                startActivity(i)
            }
        }

        val crimes = CrimeLab.get(activity as Context).getCrimes()
        binding.firstCrime.isEnabled = mCrime.id != crimes.first().id

        binding.lastCrime.isEnabled = mCrime.id != crimes.last().id

        binding.firstCrime.setOnClickListener {
            val intent = CrimePagerActivity.newIntent(activity as Context, crimes.first().id)
            startActivity(intent)
        }

        binding.lastCrime.setOnClickListener {
            val intent = CrimePagerActivity.newIntent(activity as Context, crimes.last().id)
            startActivity(intent)
        }

        if (mCrime.suspect != null) binding.chooseSuspect.text = mCrime.suspect

        val packageManager = activity?.packageManager
        if (packageManager?.resolveActivity(pickContact, PackageManager.MATCH_DEFAULT_ONLY) == null) {
            binding.chooseSuspect.isEnabled = false
        }
        return v
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        val crimeId = arguments?.getSerializable(ARG_CRIME_ID) as UUID
        mCrime = CrimeLab.get(activity as Context).getCrime(crimeId)!!
        super.onCreate(savedInstanceState)
    }

    override fun onPause() {
        super.onPause()
        CrimeLab.get(activity as Context).updateCrime(mCrime)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_crime, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.delete_crime -> {
                CrimeLab.get(activity as Context).deleteCrime(mCrime)
                activity?.finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode != Activity.RESULT_OK) return

        if (requestCode == REQUEST_DATE) {
            val date = data?.getSerializableExtra(DatePickerFragment().EXTRA_DATE) as Date
            mCrime.date = date
            updateDate()
        } else if (requestCode == REQUEST_CONTACT && data != null) {
            val contactUri = data.data
            val queryFields = arrayOf(ContactsContract.Contacts.DISPLAY_NAME)
            activity?.contentResolver?.query(contactUri!!, queryFields, null, null, null).use {
                if (it?.count == 0) return
                it?.moveToFirst()
                val suspect = it?.getString(0)
                mCrime.suspect = suspect
                binding.chooseSuspect.text = suspect
            }
        }

    }


    private fun updateDate() {
        binding.crimeDate.text = mCrime.date.toString()
    }

    private fun getCrimeReport(): String {
        val solvedString =
                if (mCrime.isSolved) {
                    getString(R.string.crime_report_solved)
                } else {
                    getString(R.string.crime_report_unsolved)
                }

        val dateFormat = "EEE, MMM dd"
        val dateString = DateFormat.format(dateFormat, mCrime.date).toString()

        val suspect = if (mCrime.suspect == null) {
            getString(R.string.crime_report_no_suspect)
        } else {
            getString(R.string.crime_report_suspect)
        }

        return getString(R.string.crime_report, mCrime.title, dateString, solvedString, suspect)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}