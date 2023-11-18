package com.alberto.familysyncapp.view.profesional;

import static com.alberto.familysyncapp.db.Constants.DATABASE_NAME_FAV;

import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.alberto.familysyncapp.R;
import com.alberto.familysyncapp.adapter.FavProfesionalAdapter;
import com.alberto.familysyncapp.db.GesResFavourites;
import com.alberto.familysyncapp.domain.Favourite;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;
import java.util.List;

public class FavProfesionalesListView extends AppCompatActivity {

    public static List<Favourite> favProfesionalesList = new ArrayList<>();
    private FavProfesionalAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav_profesionales);

        MaterialToolbar topAppBar = findViewById(R.id.topAppBar);
        setSupportActionBar(topAppBar);

        topAppBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Acción a realizar al hacer clic en el ícono de navegación
                // Por ejemplo, cerrar la actividad o realizar alguna acción específica
                onBackPressed(); // Ejemplo: retroceder a la actividad anterior
            }
        });
        initializeRecyclerView();
    }

        private void initializeRecyclerView(){
        favProfesionalesList = new ArrayList<>();
        Log.i("favoritos", "lista de favoritos " + favProfesionalesList.size());

        RecyclerView recyclerView = findViewById(R.id.fav_profesionales_list);
        //esto le dice que tenga un tamaño fijo y que ocupe el máximo espacio asignado
        recyclerView.setHasFixedSize(true);
        //Esto le dice que lo va a gestionar un linear layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        //así se ciñe al Layout manager
        recyclerView.setLayoutManager(layoutManager);
        //hago mi adapter propio no utilizo el arrayadapter de android
        adapter = new FavProfesionalAdapter(this, favProfesionalesList);
        recyclerView.setAdapter(adapter);
        Log.i("favoritos", "lista de favoritos " + favProfesionalesList.size());
    }

    @Override
    protected void onResume() {
        super.onResume();


        final GesResFavourites db = Room.databaseBuilder(this, GesResFavourites.class, DATABASE_NAME_FAV)
                .allowMainThreadQueries().build();
        try {
            favProfesionalesList.clear();
            favProfesionalesList.addAll(db.getFavouriteDAO().getAllFavourites());
            Log.i("favoritos", "lista de favoritos " + favProfesionalesList.size());
            adapter.notifyDataSetChanged();
        } catch (SQLiteConstraintException sce) {

        } finally {
            db.close();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_options_clear, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        /*if (item.getItemId() == R.id.registrar) {
            //Con Intent de digo donde estoy y a donde quiero ir
            Intent intent = new Intent(this, RegisterProfesionalView.class);
            startActivity(intent);
            return true;
        }*/
        return false;
    }

    public void cancel(View view) {
        onBackPressed();
    }

    /*@Override
    public void showFavProfesionales(List<Favourite> profesionales) {
        favProfesionalesList.clear();
        favProfesionalesList.addAll(profesionales);
        // con esto la lista siempre estára actualizada cuando vuelva de un segundo plano.
        adapter.notifyDataSetChanged();

    }

    @Override
    public void showMessage(String message) {

    }*/
}


