package com.emad.restaurantproject.database.entities;

import android.graphics.Bitmap;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.emad.restaurantproject.utils.Converters;

@Entity(tableName = "user_table")
@TypeConverters(Converters.class)
public class User {
    @PrimaryKey(autoGenerate = true)
    private int userId;
    private String name;
    private String address;
    private String email;
    private String password;
    private String userType;
    private Bitmap photo;

    public User(String name, String address, String email, String password, String userType, Bitmap photo) {
        this.name = name;
        this.address = address;
        this.email = email;
        this.password = password;
        this.userType = userType;
        this.photo = photo;
    }

    @Ignore
    public User(String name, String address, String email, String password, String userType) {
        this.name = name;
        this.address = address;
        this.email = email;
        this.password = password;
        this.userType = userType;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public Bitmap getPhoto() {
        return photo;
    }

    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }
}
