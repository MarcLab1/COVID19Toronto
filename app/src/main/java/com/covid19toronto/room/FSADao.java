package com.covid19toronto.room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.covid19toronto.FSA;

import java.util.List;

@Dao
public interface FSADao {
    @Insert
    void insert(FSA... FSA);

    @Query("SELECT FSA FROM FSA")
    List<String> getFSAList();

    @Query("SELECT population FROM FSA WHERE FSA LIKE :FSA")
    int getFSAPopulationByFSA(String FSA);
}
