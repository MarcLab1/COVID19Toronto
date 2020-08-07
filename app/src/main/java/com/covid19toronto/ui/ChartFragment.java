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

import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;


import com.covid19toronto.DelayedProgressDialogFragment;
import com.covid19toronto.helpers.HelperDate;
import com.covid19toronto.R;
import com.covid19toronto.Record;
import com.covid19toronto.room.RecordDB;

import java.util.List;
import java.util.Map;

public class ChartFragment extends DelayedProgressDialogFragment {

    private List<Record> recordsList;
    private static RecordDB recordDB;
    private Spinner spinnerFSA;
    private LinearLayout fsaSelectorLayout, layoutChart;
    private View fsaSelectorView;
    private LayoutChart chart;
    private List<String> FSAList;
    private String FSA;
    private MyViewModel myViewModel;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_chart, container, false);
        fsaSelectorLayout = rootView.findViewById(R.id.layout_fsa_selector);
        fsaSelectorView = inflater.inflate(R.layout.view_fsa_selector, container, false);
        fsaSelectorLayout.addView((fsaSelectorView));

        layoutChart = rootView.findViewById(R.id.layout_chart);
        recordDB = Room.databaseBuilder(getContext(), RecordDB.class, "dbone").build();

        spinnerFSA = fsaSelectorView.findViewById(R.id.spinner_FSA);

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

        chart = new LayoutChart(getContext());
        layoutChart.addView(chart);

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

            Map<Long, Integer> recordHashmap = HelperDate.getCasesByDateLong(recordsList);
            chart.drawChart(recordHashmap, FSA);
            myViewModel.getFSA().postValue(FSA);
        }

    }

}