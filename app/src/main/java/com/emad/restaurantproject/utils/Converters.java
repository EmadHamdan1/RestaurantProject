package com.emad.restaurantproject.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.room.TypeConverter;

import java.io.ByteArrayOutputStream;
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

    @TypeConverter
    public static byte[] bitmapToBytes(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        return outputStream.toByteArray();
    }

    @TypeConverter
    public static Bitmap bytesToBitmap(byte[] byteArray) {
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
    }

}
