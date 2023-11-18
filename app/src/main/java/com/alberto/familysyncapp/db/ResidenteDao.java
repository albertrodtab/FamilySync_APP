package com.alberto.familysyncapp.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.alberto.familysyncapp.domain.Residente;

import java.util.List;

@Dao
public interface ResidenteDao {

    @Query("SELECT * FROM Residente")
    List<Residente> getAll();

    @Query("SELECT * FROM Residente WHERE nombre = :name")
    Residente getByName(String name);

    @Query("DELETE FROM Residente WHERE nombre = :name")
    void deleteByName(String name);

    @Query("UPDATE Residente SET nombre = :nombre, apellidos = :apellidos, dni = :dni, sexo = :sexo WHERE nombre = :nombre ")
    void update(String nombre, String apellidos, String dni, /*Date fechaNac,*/ String sexo);


    @Query("SELECT * FROM Residente WHERE id = :id")
    Residente getById(long id);
    @Insert
    void insert(Residente residente);

    @Delete
    void delete(Residente residente);

    @Update
    void update(Residente residente);

}
