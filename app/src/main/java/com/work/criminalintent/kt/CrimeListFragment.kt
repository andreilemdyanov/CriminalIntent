package com.work.criminalintent.kt

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.work.criminalintent.R
import com.work.criminalintent.databinding.FragmentCrimeListBinding
import java.text.SimpleDateFormat

class CrimeListFragment : Fragment() {

    private val SAVED_SUBTITLE_VISIBLE = "subtitle";

    private var mAdapter: CrimeAdapter? = null
    private var mSubtitleVisible: Boolean = false
    private var _binding: FragmentCrimeListBinding? = null

    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentCrimeListBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.addCrimeButton.setOnClickListener {
            Crime().apply {
                CrimeLab.get(activity as Context).addCrime(this)
                val intent = CrimePagerActivity.newIntent(activity as Context, this.id)
                startActivity(intent)
            }
        }
        binding.crimeRecyclerView.layoutManager = LinearLayoutManager(activity)
        if (savedInstanceState != null) {
            mSubtitleVisible = savedInstanceState.getBoolean(SAVED_SUBTITLE_VISIBLE)
        }
        updateUI()
        return view
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(SAVED_SUBTITLE_VISIBLE, mSubtitleVisible)
    }

    override fun onResume() {
        super.onResume()
        updateUI()
    }

    private fun updateUI() {
        val crimes = CrimeLab.get(activity as Context).getCrimes()
        if (mAdapter == null) {
            mAdapter = CrimeAdapter(crimes)
            binding.crimeRecyclerView.adapter = mAdapter
        } else {
            mAdapter!!.crimes = crimes
            mAdapter!!.notifyDataSetChanged()
        }
        updateSubtitle()
        updateEmptyTitle()
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_crime_list, menu)

        val subtitleItem = menu.findItem(R.id.show_subtitle)
        if (mSubtitleVisible) {
            subtitleItem.setTitle(R.string.hide_subtitle)
        } else {
            subtitleItem.setTitle(R.string.show_subtitle)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.new_crime -> {
                Crime().apply {
                    CrimeLab.get(activity as Context).addCrime(this)
                    val intent = CrimePagerActivity.newIntent(activity as Context, this.id)
                    startActivity(intent)
                }
                return true
            }
            R.id.show_subtitle -> {
                mSubtitleVisible = !mSubtitleVisible
                activity?.invalidateOptionsMenu()
                updateSubtitle()
                return true
            }
            else ->
                return super.onOptionsItemSelected(item)
        }

    }

    private fun updateSubtitle() {
        val crimeCount = CrimeLab.get(activity as Context).getCrimes().size
        var subtitle: String? = resources.getQuantityString(R.plurals.subtitle_plural, crimeCount, crimeCount)
        if (!mSubtitleVisible) {
            subtitle = null
        }
        val appCompatActivity = activity as AppCompatActivity
        appCompatActivity.supportActionBar?.subtitle = subtitle
    }

    private fun updateEmptyTitle() {
        if (CrimeLab.get(activity as Context).getCrimes().isEmpty()) {
            binding.emptyTitle.visibility = View.VISIBLE
            binding.addCrimeButton.visibility = View.VISIBLE
        } else {
            binding.emptyTitle.visibility = View.GONE
            binding.addCrimeButton.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private inner class CrimeHolder(inflater: LayoutInflater, parent: ViewGroup)
        : RecyclerView.ViewHolder(inflater.inflate(R.layout.list_item_crime, parent, false)), View.OnClickListener {

        private val mTitleTextView = itemView.findViewById<TextView>(R.id.crime_title)
        private val mDateTextView = itemView.findViewById<TextView>(R.id.crime_date)
        private val mSolvedImageView = itemView.findViewById<ImageView>(R.id.crime_solved)
        private var mCrime: Crime = Crime()

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(crime: Crime) {
            this.mCrime = crime
            mTitleTextView.text = crime.title
            val formatter = SimpleDateFormat("EEEE, MMM dd, yyyy");
            val date = formatter.format(crime.date)
            mDateTextView.text = date
            mSolvedImageView.visibility = if (crime.isSolved) View.VISIBLE else View.GONE
        }

        override fun onClick(view: View) {
            val intent = CrimePagerActivity.newIntent(activity as Context, mCrime.id)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
            startActivity(intent)
        }

    }

    private inner class CrimeAdapter(var crimes: List<Crime>) : RecyclerView.Adapter<CrimeHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CrimeHolder {
            val layoutInflater = LayoutInflater.from(activity)
            return CrimeHolder(layoutInflater, parent)
        }

        override fun onBindViewHolder(holder: CrimeHolder, position: Int) {
            val crime = crimes[position]
            holder.bind(crime)
        }

        override fun getItemCount(): Int = crimes.size

    }
}