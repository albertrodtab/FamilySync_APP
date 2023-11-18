package com.alberto.familysyncapp.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.alberto.familysyncapp.domain.Favourite;

@Database(entities = {Favourite.class}, version = 1)
public abstract class GesResFavourites extends RoomDatabase {
    public abstract FavouriteDAO getFavouriteDAO();
}
