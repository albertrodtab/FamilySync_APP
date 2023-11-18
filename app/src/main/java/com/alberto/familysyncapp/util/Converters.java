package com.alberto.familysyncapp.util;

import androidx.room.TypeConverter;



import java.util.Date;


public class Converters {
    @TypeConverter
    public static Date fromTimestamp(Long value){
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTomestamp(Date date){
        return date == null ? null : date.getTime();
    }

}