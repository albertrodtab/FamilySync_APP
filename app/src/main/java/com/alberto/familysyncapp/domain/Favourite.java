package com.alberto.familysyncapp.domain;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Favourite {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private long id;
    @ColumnInfo
    @NonNull
    private long idProfesional;
    @ColumnInfo
    private String profesionalNombre;

    public Favourite(long idProfesional, String profesionalNombre) {
        this.idProfesional = idProfesional;
        this.profesionalNombre = profesionalNombre;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdProfesional() {
        return idProfesional;
    }

    public void setIdProfesional(long idProfesional) {
        this.idProfesional = idProfesional;
    }

    public String getProfesionalNombre() {
        return profesionalNombre;
    }

    public void setProfesionalName(String profesionalNombre) {
        this.profesionalNombre = profesionalNombre;
    }
}
