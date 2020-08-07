package com.covid19toronto.ui;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;

import com.covid19toronto.DelayedProgressDialogFragment;
import com.covid19toronto.FSA;
import com.covid19toronto.helpers.HelperFormatting;
import com.covid19toronto.R;
import com.covid19toronto.Record;
import com.covid19toronto.room.RecordDB;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class HomeFragment extends DelayedProgressDialogFragment {

    private List<Record> recordsList;
    private static RecordDB recordDB;
    private LinearLayout fsaSelectorLayout, statsLayout;
    private View fsaSelectorView, statsView;
    private Spinner spinnerFSA;
    private List<String> FSAList;
    private TextView textViewNumberOfCases, textViewNumberOfCasesText, textViewNumberOfCasesToronto, textViewDateOfFirstCase,
            textViewDateOfLastCase, textViewDateMostCases, textViewPopulationText, textViewPopulation, textViewDeaths, textViewHospitalizations,
            textViewGender, textViewSourceOfInfection, textViewAgeGroup;
    private int numberOfCasesAllGTA, numberOfCasesInFSA;
    private MyViewModel myViewModel;
    private HelperFormatting  myHelperFormatting;
    private String dateFirstCase, dateLastCase;
    private int population;
    private int hospitalizations, deaths;
    private String FSA;
    private int maleCases, femaleCases;
    private int closeContactCases, healthcareCases, travelCases, communityCases, institutionalCases, outbreakCases;
    private int ageGroup19Younger, ageGroup20to29, ageGroup30to39, ageGroup40to49, ageGroup50to59,
            ageGroup60to69, ageGroup70to79, ageGroup80to89, ageGroup90plus;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_stats, container, false);

        fsaSelectorLayout = rootView.findViewById(R.id.layout_fsa_selector);
        fsaSelectorView = inflater.inflate(R.layout.view_fsa_selector, container, false);
        fsaSelectorLayout.addView((fsaSelectorView));

        statsLayout = rootView.findViewById(R.id.layout_stats);
        statsView = inflater.inflate(R.layout.view_stats, container, false);
        statsLayout.addView((statsView));

        textViewNumberOfCasesText = statsView.findViewById(R.id.numberofcases_text);
        textViewNumberOfCases = statsView.findViewById(R.id.numberofcases_value);
        textViewNumberOfCasesToronto = statsView.findViewById(R.id.numberofcasesallgta);
        textViewPopulationText = statsView.findViewById(R.id.population_text);
        textViewPopulation = statsView.findViewById(R.id.population_value);
        textViewDateOfFirstCase = statsView.findViewById(R.id.dateoffirstcase);
        textViewDateOfLastCase = statsView.findViewById(R.id.dateoflastcase);
        textViewDateMostCases = statsView.findViewById(R.id.datemostcases);
        textViewHospitalizations = statsView.findViewById(R.id.hospitalizations);
        textViewDeaths = statsView.findViewById(R.id.deaths);
        textViewGender = statsView.findViewById(R.id.gender);
        textViewSourceOfInfection = statsView.findViewById(R.id.sourceofinfection);
        textViewAgeGroup = statsView.findViewById(R.id.agegroup);
        spinnerFSA = fsaSelectorView.findViewById(R.id.spinner_FSA);

        myHelperFormatting = new HelperFormatting(getContext());

        myViewModel = new ViewModelProvider(getActivity()).get(MyViewModel.class);
        FSA = myViewModel.getFSA().getValue();
        FSAList = myViewModel.getFSAList().getValue();

        //populate DB if first run
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        if (!preferences.getBoolean("firstrun", false)) {
            startDialog();
            new DBInsert().execute();
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("firstrun", true).apply();
        }

        spinnerFSA.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                new DBQuery(FSAList.get(position).toString()).execute();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        if (FSAList == null) {
            //startDialog();
            new PopulateFSAList().execute();
        } else {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_layout, FSAList.toArray(new String[FSAList.size()]));
            spinnerFSA.setAdapter(adapter);
            spinnerFSA.setSelection(FSAList.indexOf(FSA), false);
        }

        return rootView;
    }

    private void populateStats(String FSA) {
        textViewNumberOfCasesText.setText(myHelperFormatting.getNumberOfCasesTextFormatted(FSA));
        textViewNumberOfCases.setText(myHelperFormatting.getNumberFormatted(numberOfCasesInFSA));
        textViewNumberOfCasesToronto.setText(myHelperFormatting.getNumberFormatted(numberOfCasesAllGTA));
        textViewPopulationText.setText(myHelperFormatting.getPopulationInFSATextFormatted(FSA));
        textViewPopulation.setText(myHelperFormatting.getNumberFormatted(population));

        if (recordsList == null | recordsList.size() == 0) {
            textViewDateOfFirstCase.setText(getResources().getString(R.string.empty_placeholder));
            textViewDateOfLastCase.setText(getResources().getString(R.string.empty_placeholder));
            textViewDateMostCases.setText(getResources().getString(R.string.empty_placeholder));
            textViewHospitalizations.setText(getResources().getString(R.string.zero));
            textViewDeaths.setText(getResources().getString(R.string.zero));
            textViewGender.setText(getResources().getString(R.string.empty_placeholder));
            textViewSourceOfInfection.setText(getResources().getString(R.string.empty_placeholder));
            textViewAgeGroup.setText(getResources().getString(R.string.empty_placeholder));

        } else {
            textViewDateOfFirstCase.setText(dateFirstCase);
            textViewDateOfLastCase.setText(dateLastCase);
            textViewDateMostCases.setText(myHelperFormatting.getMostCasesFormatted(recordsList));
            textViewHospitalizations.setText(myHelperFormatting.getHospitalizationsFormatted(recordsList.size(), hospitalizations));
            textViewDeaths.setText(myHelperFormatting.getDeathsFormatted(recordsList.size(), deaths));
            textViewGender.setText(myHelperFormatting.getGenderFormatted(maleCases, femaleCases));
            textViewSourceOfInfection.setText(myHelperFormatting.getSourcesOfInfectionFormatted(closeContactCases, outbreakCases, communityCases,
                    travelCases, institutionalCases, healthcareCases));
            textViewAgeGroup.setText(myHelperFormatting.getAgeGroupsFormatted(ageGroup19Younger, ageGroup20to29, ageGroup30to39, ageGroup40to49,
                    ageGroup50to59, ageGroup60to69, ageGroup70to79, ageGroup80to89, ageGroup90plus));
        }
    }

    public String loadJSONFromAsset(String fileName) {
        String json = null;
        try {
            InputStream is = getActivity().getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    private class DBInsert extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {

            try {
                JSONArray jsonArray = new JSONArray(loadJSONFromAsset("covid19cases.json"));

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObj = jsonArray.getJSONObject(i);

                    recordDB.getRecordDao().insert(new Record(
                            jsonObj.getString("Age Group"),
                            jsonObj.getString("Neighbourhood Name"),
                            jsonObj.getString("Outcome"),
                            jsonObj.getString("Client Gender"),
                            jsonObj.getString("Classification"),
                            jsonObj.getString("FSA"),
                            jsonObj.getString("Currently Hospitalized"),
                            jsonObj.getString("Episode Date"),
                            jsonObj.getString("Ever in ICU"),
                            jsonObj.getString("Outbreak Associated"),
                            jsonObj.getString("Reported Date"),
                            jsonObj.getString("Currently in ICU"),
                            jsonObj.getString("Source of Infection"),
                            jsonObj.getString("_id"),
                            jsonObj.getString("Ever Intubated"),
                            jsonObj.getString("Ever Hospitalized"),
                            jsonObj.getString("Currently Intubated")));
                }

            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(getContext(), R.string.data_loading_exception_message, Toast.LENGTH_SHORT).show();
            }

            try {
                JSONArray jsonArray2 = new JSONArray(loadJSONFromAsset("fsacensusdata2016.json"));

                for (int i = 0; i < jsonArray2.length(); i++) {
                    JSONObject jsonObj = jsonArray2.getJSONObject(i);

                    recordDB.getFSADao().insert(new FSA(
                            jsonObj.getString("FSA"),
                            jsonObj.getInt("population"),
                            jsonObj.getInt("privateDwellings")
                    ));
                }

            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(getContext(), R.string.data_loading_exception_message, Toast.LENGTH_SHORT).show();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            stopDialog();
        }
    }

    private class DBQuery extends AsyncTask<Void, Void, Void> {
        private String fsa;

        public DBQuery(String fsa) {
            this.fsa = fsa;
        }

        @Override
        protected Void doInBackground(Void... voids) {

            recordsList = recordDB.getRecordDao().getRecordsByFSA(fsa);
            population = recordDB.getFSADao().getFSAPopulationByFSA(fsa);
            if (recordsList == null || recordsList.size() == 0) {
                numberOfCasesAllGTA = recordDB.getRecordDao().getNumberOfRecords();
                numberOfCasesInFSA = 0;
                dateFirstCase = getResources().getString(R.string.empty_placeholder);
                dateLastCase = getResources().getString(R.string.empty_placeholder);
                deaths = 0;
                hospitalizations = 0;
            } else {
                numberOfCasesAllGTA = recordDB.getRecordDao().getNumberOfRecords();
                numberOfCasesInFSA = recordDB.getRecordDao().getNumberOfRecordsByFSA(fsa);
                dateFirstCase = recordDB.getRecordDao().getDateFirstRecordByFSA(fsa).getEpisodeDate();
                dateLastCase = recordDB.getRecordDao().getDateLastRecordByFSA(fsa).getEpisodeDate();
                deaths = recordDB.getRecordDao().getNumberOfDeathsByFSA(fsa);
                hospitalizations = recordDB.getRecordDao().getNumberOfHospitalizationsbyFSA(fsa);

                closeContactCases = recordDB.getRecordDao().getNumberOfCloseContactCasesbyFSA(fsa);
                healthcareCases = recordDB.getRecordDao().getNumberOfHealthcareCasesbyFSA(fsa);
                institutionalCases = recordDB.getRecordDao().getNumberOfInstitutionalCasesbyFSA(fsa);
                outbreakCases = recordDB.getRecordDao().getNumberOfOutbreakAssociatedCasesbyFSA(fsa);
                communityCases = recordDB.getRecordDao().getNumberOfCommunityCasesbyFSA(fsa);
                travelCases = recordDB.getRecordDao().getNumberOfTravelCasesbyFSA(fsa);
                maleCases = recordDB.getRecordDao().getNumberOfMaleCasesByFSA(fsa);
                femaleCases = recordDB.getRecordDao().getNumberOfFemaleCasesByFSA(fsa);

                ageGroup19Younger = recordDB.getRecordDao().getNumberOfCases19YoungerbyFSA(fsa);
                ageGroup20to29 = recordDB.getRecordDao().getNumberOfCases20to29byFSA(fsa);
                ageGroup30to39 = recordDB.getRecordDao().getNumberOfCases30to39byFSA(fsa);
                ageGroup40to49 = recordDB.getRecordDao().getNumberOfCases40to49byFSA(fsa);
                ageGroup50to59 = recordDB.getRecordDao().getNumberOfCases50to59byFSA(fsa);
                ageGroup60to69 = recordDB.getRecordDao().getNumberOfCases60to69byFSA(fsa);
                ageGroup70to79 = recordDB.getRecordDao().getNumberOfCases70to79byFSA(fsa);
                ageGroup80to89 = recordDB.getRecordDao().getNumberOfCases80to89byFSA(fsa);
                ageGroup90plus = recordDB.getRecordDao().getNumberOfCases90PlusbyFSA(fsa);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            myViewModel.getFSA().setValue(fsa);
            populateStats(fsa);
        }
    }

    private class PopulateFSAList extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            recordDB = Room.databaseBuilder(getContext(), RecordDB.class, "dbone").build();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            FSAList = recordDB.getFSADao().getFSAList();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            myViewModel.getFSAList().setValue(FSAList);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_layout, FSAList.toArray(new String[FSAList.size()]));
            spinnerFSA.setAdapter(adapter);
            myViewModel.setDefaultFSA();
            FSA = myViewModel.getFSA().getValue();
            spinnerFSA.setSelection(FSAList.indexOf(FSA), false);
        }
    }
}