package com.work.criminalintent;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import com.bumptech.glide.Glide;
import java.io.File;

public class PhotoDialogFragment extends DialogFragment {
    private static final String ARG_FILE = "file";
    private ImageView mPhotoImage;

    public static PhotoDialogFragment newInstance(File file) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_FILE, file);
        Log.d("PhotoDialogFragment", "newInstance = " + file);
        PhotoDialogFragment fragment = new PhotoDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        File file = (File) getArguments().getSerializable(ARG_FILE);
        Log.d("PhotoDialogFragment", "onCreateDialog = " + file);
        View v = inflater.inflate(R.layout.fragment_dialog_photo, null);
        mPhotoImage = v.findViewById(R.id.crime_photo_full);
        Glide.with(getActivity())
                .load(file)
                .centerCrop()
                .into(mPhotoImage);
        return v;
    }
}
