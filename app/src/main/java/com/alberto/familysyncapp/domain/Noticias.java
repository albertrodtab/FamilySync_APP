package com.alberto.familysyncapp.domain;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.time.LocalDate;

import lombok.AllArgsConstructor;

@Entity
@AllArgsConstructor
public class Noticias implements Serializable {

    @PrimaryKey (autoGenerate = true)
    @NonNull
    private long id;
    @ColumnInfo
    @NonNull
    private String tituloNoticia;
    @ColumnInfo (name = "fecha_noticia")
    private String fechaNoticia;
    @ColumnInfo
    private String titular;
    @ColumnInfo (name = "cuerpo_noticia")
    private String cuerpoNoticia;
    @ColumnInfo
    private String photoUri;

    public Noticias(long id, @NonNull String tituloNoticia, String fechaNoticia, String titular, String cuerpoNoticia) {
        this.id = id;
        this.tituloNoticia = tituloNoticia;
        this.fechaNoticia = fechaNoticia;
        this.titular = titular;
        this.cuerpoNoticia = cuerpoNoticia;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @NonNull
    public String getTituloNoticia() {
        return tituloNoticia;
    }

    public void setTituloNoticia(@NonNull String tituloNoticia) {
        this.tituloNoticia = tituloNoticia;
    }

    public String getFechaNoticia() {
        return fechaNoticia;
    }

    public void setFechaNoticia(String fechaNoticia) {
        this.fechaNoticia = fechaNoticia;
    }

    public String getTitular() {
        return titular;
    }

    public void setTitular(String titular) {
        titular = titular;
    }

    public String getCuerpoNoticia() {
        return cuerpoNoticia;
    }

    public void setCuerpoNoticia(String cuerpoNoticia) {
        cuerpoNoticia = cuerpoNoticia;
    }

    public String getPhotoUri() {
        return photoUri;
    }

    public void setPhotoUri(String photoUri) {
        this.photoUri = photoUri;
    }
}
