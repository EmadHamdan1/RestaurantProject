package com.emad.restaurantproject.utils;

import androidx.room.TypeConverter;

import java.util.Date;

public class Converters {

    @TypeConverter
    public Long toLong(Date date) {
        return date.getTime();
    }

    @TypeConverter
    public Date toDate(Long date) {
        return new Date(date);
    }

}
