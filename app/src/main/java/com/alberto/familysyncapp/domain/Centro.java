package com.alberto.familysyncapp.domain;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

import lombok.AllArgsConstructor;

@Entity
@AllArgsConstructor
public class Centro implements Serializable {

    @PrimaryKey (autoGenerate = true)
    @NonNull
    private long id;
    @ColumnInfo
    @NonNull
    private String nombre;
    @ColumnInfo
    private String direccion;
    @ColumnInfo
    private String numRegistro;
    @ColumnInfo
    private String email;
    @ColumnInfo
    private String telefono;

    @ColumnInfo
    private boolean tieneWifi;

    @ColumnInfo
    private double latitude; //para poder ubicar en el mapa
    @ColumnInfo
    private double longitude; //para poder ubicar en el mapa

    @ColumnInfo
    private String photoUri;

    public Centro() {
        this.nombre = nombre;
        this.direccion = direccion;
        this.numRegistro = numRegistro;
        this.telefono = telefono;
        this.email = email;
        this.tieneWifi = tieneWifi;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public Centro(String nombre, String direccion, String numRegistro, String telefono, String mail, boolean tieneWifi) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.numRegistro = numRegistro;
        this.telefono = telefono;
        this.email = mail;
        this.tieneWifi = tieneWifi;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @NonNull
    public String getNombre() {
        return nombre;
    }

    public void setNombre(@NonNull String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getNumRegistro() {
        return numRegistro;
    }

    public void setNumRegistro(String numRegistro) {
        this.numRegistro = numRegistro;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getPhotoUri() {
        return photoUri;
    }

    public void setPhotoUri(String photoUri) {
        this.photoUri = photoUri;
    }

    public boolean getTieneWifi() {
        return tieneWifi;
    }

    public void setTieneWifi(boolean tieneWifi) {
        this.tieneWifi = tieneWifi;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
