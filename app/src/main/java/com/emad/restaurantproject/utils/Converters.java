package com.emad.restaurantproject.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.room.TypeConverter;

import com.emad.restaurantproject.database.data.MyViewModel;
import com.emad.restaurantproject.database.entities.Category;

import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.List;

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
        if (bitmap == null) return null;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        return outputStream.toByteArray();
    }

    @TypeConverter
    public static Bitmap bytesToBitmap(byte[] byteArray) {
        if (byteArray == null) return null;
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
    }

}
