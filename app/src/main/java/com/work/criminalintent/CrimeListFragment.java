package com.work.criminalintent;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CrimeListFragment extends Fragment {

    private RecyclerView crimeRecyclerView;
    private CrimeAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crime_list, container, false);
        crimeRecyclerView = view.findViewById(R.id.crime_recycler_view);
        crimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();
        return view;
    }

    private void updateUI() {
        CrimeLab crimeLab = CrimeLab.get(getActivity());
        List<Crime> crimes = crimeLab.getCrimes();
        adapter = new CrimeAdapter(crimes);
        crimeRecyclerView.setAdapter(adapter);
    }

    private class CrimeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView titleTextView;
        private TextView dateTextView;
        private Crime crime;

        public CrimeHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_crime, parent, false));
            itemView.setOnClickListener(this);
            titleTextView = itemView.findViewById(R.id.crime_title);
            dateTextView = itemView.findViewById(R.id.crime_date);
        }

        public void bind(Crime crime) {
            this.crime = crime;
            titleTextView.setText(crime.getTitle());
            dateTextView.setText(crime.getDate().toString());
        }

        @Override
        public void onClick(View view) {
            Toast.makeText(getActivity(), crime.getTitle() + " clicked!", Toast.LENGTH_SHORT).show();
        }
    }

    private class CrimeHolderNeedPolice extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView titleTextView;
        private TextView dateTextView;
        private Crime crime;

        public CrimeHolderNeedPolice(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_police_need_crime, parent, false));
            itemView.setOnClickListener(this);
            titleTextView = itemView.findViewById(R.id.crime_title);
            dateTextView = itemView.findViewById(R.id.crime_date);
        }

        public void bind(Crime crime) {
            this.crime = crime;
            titleTextView.setText(crime.getTitle());
            dateTextView.setText(crime.getDate().toString());
        }

        @Override
        public void onClick(View view) {
            Toast.makeText(getActivity(), crime.getTitle() + " clicked!", Toast.LENGTH_SHORT).show();
        }
    }

    private class CrimeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private List<Crime> crimes;

        public CrimeAdapter(List<Crime> crimes) {
            this.crimes = crimes;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            RecyclerView.ViewHolder holder;
            switch (viewType) {
                case R.layout.list_item_police_need_crime:
                    holder = new CrimeHolderNeedPolice(layoutInflater, parent);
                    break;
                case R.layout.list_item_crime:
                    holder = new CrimeHolder(layoutInflater, parent);
                    break;
                default:
                    holder = new CrimeHolder(layoutInflater, parent);
                    break;
            }
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            Crime crime = crimes.get(position);
            switch (holder.getItemViewType()) {
                case R.layout.list_item_police_need_crime:
                    ((CrimeHolderNeedPolice) holder).bind(crime);
                    break;
                case R.layout.list_item_crime:
                    ((CrimeHolder) holder).bind(crime);
                    break;
            }
        }

        @Override
        public int getItemViewType(int position) {
            Crime crime = crimes.get(position);
            if (crime.isRequiresPolice()) {
                return R.layout.list_item_police_need_crime;
            }
            return R.layout.list_item_crime;
        }

        @Override
        public int getItemCount() {
            return crimes.size();
        }
    }
}
