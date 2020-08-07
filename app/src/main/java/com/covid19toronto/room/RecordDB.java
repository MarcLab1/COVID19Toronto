package com.covid19toronto.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.covid19toronto.FSA;
import com.covid19toronto.Record;

@Database(entities = {Record.class, FSA.class}, version = RecordDB.VERSION)
public abstract class RecordDB extends RoomDatabase {
    static final int VERSION = 1;
    public abstract RecordDao getRecordDao();
    public abstract FSADao getFSADao();
}
