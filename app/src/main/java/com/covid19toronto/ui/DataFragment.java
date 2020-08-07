package com.covid19toronto.ui;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.covid19toronto.DelayedProgressDialogFragment;
import com.covid19toronto.R;
import com.covid19toronto.Record;
import com.covid19toronto.room.RecordDB;

import java.util.ArrayList;
import java.util.List;


public class DataFragment extends DelayedProgressDialogFragment {

    private List<Record> recordsList;
    private static RecordDB recordDB;
    private LinearLayout fsaSelectorLayout, columnHeaderLayout;
    private View fsaSelectorView,  columnHeaderView;
    private RecyclerView recordsView;
    private Spinner spinnerFSA;
    private List<String> FSAList;
    private MyViewModel myViewModel;
    private RecyclerView.LayoutManager layoutManager;
    private RecordsAdapter adapter;
    private String FSA;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_data, container, false);

        fsaSelectorLayout = rootView.findViewById(R.id.layout_fsa_selector);
        fsaSelectorView = inflater.inflate(R.layout.view_fsa_selector, container, false);
        fsaSelectorLayout.addView((fsaSelectorView));

        columnHeaderLayout = rootView.findViewById(R.id.layout_column_header);
        columnHeaderView = inflater.inflate(R.layout.view_column_headers, container, false);
        columnHeaderLayout.addView((columnHeaderView));

        recordsView = rootView.findViewById(R.id.records_view);
        layoutManager = new LinearLayoutManager(getContext());
        recordsView.setLayoutManager(layoutManager);
        recordsList = new ArrayList<>();

        myViewModel = new ViewModelProvider(getActivity()).get(MyViewModel.class);
        FSAList = myViewModel.getFSAList().getValue();
        FSA = myViewModel.getFSA().getValue();

        spinnerFSA = fsaSelectorView.findViewById(R.id.spinner_FSA);
        recordDB = Room.databaseBuilder(getContext(), RecordDB.class, "dbone").build();

        spinnerFSA.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                new DBQuery(FSAList.get(position).toString()).execute();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_layout, FSAList.toArray(new String[FSAList.size()]));
        spinnerFSA.setAdapter(adapter);
        spinnerFSA.setSelection(FSAList.indexOf(FSA), false);

        return rootView;
    }

    private class DBQuery extends AsyncTask<Void, Void, Void> {
        private String FSA;

        public DBQuery(String FSA) {
            this.FSA = FSA;
        }

        @Override
        protected Void doInBackground(Void... voids) {

            recordsList = recordDB.getRecordDao().getRecordsByFSA(FSA);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            myViewModel.getFSA().postValue(FSA);
            adapter = new RecordsAdapter(recordsList);
            recordsView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }

    private class RecordsAdapter extends RecyclerView.Adapter<RecordsAdapter.ViewHolder> {
        private List<Record> records;

        RecordsAdapter(List<Record> records) {
            this.records = new ArrayList<>();
            this.records = records;
        }

        @NonNull
        @Override
        public RecordsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_record, parent, false);
            return new RecordsAdapter.ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull final RecordsAdapter.ViewHolder holder, final int position) {
            final Record record = records.get(position);
            //holder._id.setText(record.get_id());
            holder.AgeGroup.setText(record.getAgeGroup());
            holder.NeighbourhoodName.setText(record.getNeighbourhoodName());
            holder.FSA.setText(record.getFSA());
            holder.EpisodeDate.setText(record.getEpisodeDate());
            holder.ReportedDate.setText(record.getReportedDate());
            holder.SourceOfInfection.setText(record.getSourceOfInfection());
        }

        @Override
        public int getItemCount() {
            return records.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            private TextView _id, OutbreakAssociated, AgeGroup, NeighbourhoodName, FSA, SourceOfInfection, Classification, EpisodeDate, ReportedDate, ClientGender, Outcome,
                    CurrentlyHospitalized, CurrentlyInICU, CurrentlyIncubated, EverHospitalized, EverInICU, EverIntubated;

            ViewHolder(View itemView) {
                super(itemView);
                // _id = itemView.findViewById(R.id.record_id);
                AgeGroup = itemView.findViewById(R.id.record_AgeGroup);
                NeighbourhoodName = itemView.findViewById(R.id.record_NeighbourhoodName);
                EpisodeDate = itemView.findViewById(R.id.record_EpisodeDate);
                ReportedDate = itemView.findViewById(R.id.record_ReportedDate);
                SourceOfInfection = itemView.findViewById(R.id.record_SourceOfInfection);
                FSA = itemView.findViewById(R.id.record_FSA);
            }
        }
    }
}