package com.alberto.familysyncapp.domain;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.AllArgsConstructor;

@Entity
@AllArgsConstructor
public class Usuario {

    @PrimaryKey(autoGenerate = true)
    private Long id ;
    @ColumnInfo
    private String nombre ;
    @ColumnInfo
    private String email ;
    @ColumnInfo
    private String password;

    public Usuario(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
