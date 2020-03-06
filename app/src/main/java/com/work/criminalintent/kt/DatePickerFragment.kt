package com.work.criminalintent.kt

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import com.work.criminalintent.R
import java.util.*

const val ARG_DATE = "date"

class DatePickerFragment : DialogFragment() {

    val EXTRA_DATE = "com.work.criminalintent.date";

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val date = arguments?.getSerializable(ARG_DATE) as Date
        val calendar = Calendar.getInstance()
        calendar.time = date
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val v = LayoutInflater.from(activity)
                .inflate(R.layout.dialog_date, null)

        val mDatePicker = v.findViewById<DatePicker>(R.id.dialog_date_picker)
        mDatePicker.init(year, month, day, null)
        return AlertDialog.Builder(activity)
                .setTitle(R.string.date_picker_title)
                .setPositiveButton(android.R.string.ok) { dialog: DialogInterface, i: Int ->
                    val yearPicker = mDatePicker.year
                    val monthPicker = mDatePicker.month
                    val dayPicker = mDatePicker.dayOfMonth
                    val datePicker = GregorianCalendar(yearPicker, monthPicker, dayPicker).time
                    sendResult(Activity.RESULT_OK, datePicker)
                }
                .setView(v)
                .create()
    }

    private fun sendResult(resultCode: Int, date: Date) {
        if (targetFragment == null) {
            return
        }
        val intent = Intent()
        intent.putExtra(EXTRA_DATE, date)
        targetFragment!!.onActivityResult(targetRequestCode, resultCode, intent)
    }

    companion object {
        fun newInstance(date: Date): DatePickerFragment {
            val args = Bundle()
            args.putSerializable(ARG_DATE, date)
            val fragment = DatePickerFragment()
            fragment.arguments = args
            return fragment
        }

    }
}
