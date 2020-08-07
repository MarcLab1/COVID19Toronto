package com.covid19toronto.room;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.covid19toronto.Record;

import java.util.List;

@Dao
public interface RecordDao {
    @Insert
    void insert(Record... record);


    @Query("SELECT * FROM Record WHERE FSA LIKE :FSA ORDER BY EpisodeDate DESC")
    List<Record> getRecordsByFSA(String FSA);

    @Query("SELECT COUNT(*) FROM Record")
    int getNumberOfRecords();

    @Query("SELECT COUNT(*) FROM Record WHERE FSA LIKE :FSA")
    int getNumberOfRecordsByFSA(String FSA);

    @Query("SELECT * FROM Record WHERE FSA LIKE :FSA ORDER BY EpisodeDate ASC LIMIT 1")
    Record getDateFirstRecordByFSA(String FSA);

    @Query("SELECT * FROM Record WHERE FSA LIKE :FSA ORDER BY EpisodeDate DESC LIMIT 1")
    Record getDateLastRecordByFSA(String FSA);

    @Query("SELECT COUNT(_id) FROM Record WHERE FSA LIKE :FSA AND EverHospitalized='Yes'")
    int getNumberOfHospitalizationsbyFSA(String FSA);

    @Query("SELECT COUNT(_id) FROM Record WHERE FSA LIKE :FSA AND Outcome='FATAL'")
    int getNumberOfDeathsByFSA(String FSA);

    @Query("SELECT COUNT(_id) FROM Record WHERE FSA LIKE :FSA AND ClientGender='MALE'")
    int getNumberOfMaleCasesByFSA(String FSA);

    @Query("SELECT COUNT(_id) FROM Record WHERE FSA LIKE :FSA AND ClientGender='FEMALE'")
    int getNumberOfFemaleCasesByFSA(String FSA);

    @Query("SELECT COUNT(_id) FROM Record WHERE FSA LIKE :FSA AND SourceOfInfection='Close contact'")
    int getNumberOfCloseContactCasesbyFSA(String FSA);

    @Query("SELECT COUNT(_id) FROM Record WHERE FSA LIKE :FSA AND SourceOfInfection='Community'")
    int getNumberOfCommunityCasesbyFSA(String FSA);

    @Query("SELECT COUNT(_id) FROM Record WHERE FSA LIKE :FSA AND SourceOfInfection='Institutional'")
    int getNumberOfInstitutionalCasesbyFSA(String FSA);

    @Query("SELECT COUNT(_id) FROM Record WHERE FSA LIKE :FSA AND SourceOfInfection='N/A - Outbreak associated'")
    int getNumberOfOutbreakAssociatedCasesbyFSA(String FSA);

    @Query("SELECT COUNT(_id) FROM Record WHERE FSA LIKE :FSA AND SourceOfInfection='Healthcare'")
    int getNumberOfHealthcareCasesbyFSA(String FSA);

    @Query("SELECT COUNT(_id) FROM Record WHERE FSA LIKE :FSA AND SourceOfInfection='Travel'")
    int getNumberOfTravelCasesbyFSA(String FSA);

    @Query("SELECT COUNT(_id) FROM Record WHERE FSA LIKE :FSA AND AgeGroup='19 and younger'")
    int getNumberOfCases19YoungerbyFSA(String FSA);

    @Query("SELECT COUNT(_id) FROM Record WHERE FSA LIKE :FSA AND AgeGroup='20-29'")
    int getNumberOfCases20to29byFSA(String FSA);

    @Query("SELECT COUNT(_id) FROM Record WHERE FSA LIKE :FSA AND AgeGroup='30-39'")
    int getNumberOfCases30to39byFSA(String FSA);

    @Query("SELECT COUNT(_id) FROM Record WHERE FSA LIKE :FSA AND AgeGroup='40-49'")
    int getNumberOfCases40to49byFSA(String FSA);

    @Query("SELECT COUNT(_id) FROM Record WHERE FSA LIKE :FSA AND AgeGroup='50-59'")
    int getNumberOfCases50to59byFSA(String FSA);

    @Query("SELECT COUNT(_id) FROM Record WHERE FSA LIKE :FSA AND AgeGroup='60-69'")
    int getNumberOfCases60to69byFSA(String FSA);

    @Query("SELECT COUNT(_id) FROM Record WHERE FSA LIKE :FSA AND AgeGroup='70-79'")
    int getNumberOfCases70to79byFSA(String FSA);

    @Query("SELECT COUNT(_id) FROM Record WHERE FSA LIKE :FSA AND AgeGroup='80-89'")
    int getNumberOfCases80to89byFSA(String FSA);

    @Query("SELECT COUNT(_id) FROM Record WHERE FSA LIKE :FSA AND AgeGroup='90+'")
    int getNumberOfCases90PlusbyFSA(String FSA);
}

