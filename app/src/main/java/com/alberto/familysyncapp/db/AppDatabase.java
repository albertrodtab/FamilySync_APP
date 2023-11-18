package com.alberto.familysyncapp.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.alberto.familysyncapp.domain.Centro;
import com.alberto.familysyncapp.domain.Residente;
import com.alberto.familysyncapp.util.Converters;

@Database(entities = {Centro.class, Residente.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {

    public abstract CentroDao centroDao();



    public abstract ResidenteDao residenteDao();
}

