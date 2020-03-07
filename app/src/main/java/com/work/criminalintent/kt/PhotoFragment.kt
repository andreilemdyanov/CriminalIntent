package com.work.criminalintent.kt

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.DialogFragment
import com.work.criminalintent.R
import com.work.criminalintent.kt.util.PictureUtils
import java.util.*

const val ARG_PHOTO = "argPhoto"

class PhotoFragment : DialogFragment() {

    lateinit var photoFull: ImageView

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val v = LayoutInflater.from(activity)
                .inflate(R.layout.dialog_photo, null)
        photoFull = v.findViewById(R.id.photo_full)

        val path = arguments?.getString(ARG_PHOTO)


        val bitmap = PictureUtils.getScaledBitmap(path!!, requireActivity())
        photoFull.setImageBitmap(bitmap)



        return AlertDialog.Builder(activity)
//                .setTitle(R.string.date_picker_title)
                .setPositiveButton(android.R.string.ok) { dialog: DialogInterface, i: Int ->
//                    sendResult(Activity.RESULT_OK, datePicker)
                }
                .setView(v)
                .create()
    }



    private fun sendResult(resultCode: Int, date: Date) {
        if (targetFragment == null) {
            return
        }
        val intent = Intent()
        targetFragment!!.onActivityResult(targetRequestCode, resultCode, intent)
    }

    companion object {
        fun newInstance(path: String): PhotoFragment {
            val args = Bundle()
            args.putString(ARG_PHOTO, path)
            val fragment = PhotoFragment()
            fragment.arguments = args
            return fragment
        }

    }
}