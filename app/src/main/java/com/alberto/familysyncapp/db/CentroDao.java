package com.alberto.familysyncapp.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.alberto.familysyncapp.domain.Centro;

import java.util.List;

@Dao
public interface CentroDao {

    @Query("SELECT * FROM Centro")
    List<Centro> getAll();

    @Query("SELECT * FROM Centro WHERE nombre = :name")
    Centro getByName(String name);

    @Query("DELETE FROM Centro WHERE nombre = :name")
    void deleteByName(String name);

    @Query("UPDATE Centro SET nombre = :nombre, direccion = :direccion, numRegistro = :numRegistro, telefono = :telefono, email = :email, tieneWifi = :tieneWifi WHERE nombre = :nombre ")
    void update(String nombre, String direccion, String numRegistro, String telefono, String email, boolean tieneWifi);


    @Query("SELECT * FROM centro WHERE id = :id")
    Centro getById(long id);
    @Insert
    void insert(Centro centro);

    @Delete
    void delete(Centro centro);

    @Update
    void update(Centro centro);

}
