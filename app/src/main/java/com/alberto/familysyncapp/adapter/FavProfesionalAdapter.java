package com.alberto.familysyncapp.adapter;

import static com.alberto.familysyncapp.db.Constants.DATABASE_NAME_FAV;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;


import com.alberto.familysyncapp.R;
import com.alberto.familysyncapp.db.GesResFavourites;
import com.alberto.familysyncapp.domain.Favourite;
import com.alberto.familysyncapp.view.profesional.DetailsProfesionalView;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

public class FavProfesionalAdapter extends RecyclerView.Adapter<FavProfesionalAdapter.FavProfesionalHolder>
    {

    private List<Favourite> favProfesionalList;
    //esto sirve para guardar la posición para luego poder hacer cosas con ellos.
    private Context context;

    private int selectedPosition;

    public FavProfesionalAdapter(Context context, List<Favourite> dataList) {
        this.context = context;
        this.favProfesionalList = dataList;


        //esto indica que no hay ninguno seleccionado
        selectedPosition = -1;
    }

    public Context getContext() {
        return context;
    }

    //Patron Holder (ESTO
    // ESTOY OBLIGADO A HACERLO SIEMPRE)
    //metodo que crea cada estructura de layout donde iran los datos de cada cnetro.
    @Override
    public FavProfesionalHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_view_fav, parent, false);
        return new FavProfesionalHolder(view);
    }

    //hace corresponder cada elemento de la lista para decir como pintarlo en cada elemento del layout
    @Override
    public void onBindViewHolder(FavProfesionalHolder holder, int position){
        holder.profesionalNombre.getEditText().setText(favProfesionalList.get(position).getProfesionalNombre());

        Favourite favourite = favProfesionalList.get(position);
    }

    @Override
    public int getItemCount() {
        return favProfesionalList.size();
    }

/*    @Override
    public void showError(String message) {
        Toast.makeText(this.getContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this.getContext(), message, Toast.LENGTH_LONG).show();
    }*/

    public class FavProfesionalHolder extends RecyclerView.ViewHolder{
        public TextInputLayout profesionalNombre;
        public View parentView;

        public Button btDelFav;

        public Button btDetails;

        public FavProfesionalHolder(View view) {
            super(view);
            parentView = view;

            profesionalNombre = view.findViewById(R.id.tilNombre);
            btDelFav = view.findViewById(R.id.btDelFav);
            btDetails = view.findViewById(R.id.btDetails);


            //click on button (remove task from de list).
            btDelFav.setOnClickListener(v -> setFavourite(getAdapterPosition()));
            btDetails.setOnClickListener(v -> details(getAdapterPosition()));


        }

    }

        private void setFavourite(int position) {
            final GesResFavourites db = Room.databaseBuilder(context, GesResFavourites.class, DATABASE_NAME_FAV).allowMainThreadQueries().build();
            try {
                Favourite favourite = favProfesionalList.get(position);
                //Favourite favouriteDel = db.getFavouriteDAO().getFavourite(favourite.getId());
                Log.i("favorito", "favorito seleccionado"+ favourite.getId());
                if (favourite == null) {
                    /*long idProfesional = profesional.getId();
                    Log.i("favorito", " checkedIdProfesional: " + idProfesional);
                    Favourite insert = new Favourite(profesional.getId(), profesional.getNombre());
                    Log.i("favorito", " checkedIdProfesional: " + idProfesional);

                    db.getFavouriteDAO().insert(insert);
                    Log.i("favorito", "favorito añadido "+ profesional.getNombre());*/
                    Log.i("favorito", "favorito seleccionado null");
                } else {
                //long idProfesional = profesional.getId();
                //Log.i("favorito", "elseIdProfesional: " + idProfesional);
                //Favourite delete = db.getFavouriteDAO().getFavourite(favourite.getId());
                //Log.i("favorito", "elseIdProfesional: " + idProfesional);
                    Log.i("favorito", "elsefavorito seleccionado");
                db.getFavouriteDAO().delete(favourite);
                favProfesionalList.remove(position);
                notifyItemRemoved(position);
            }
                notifyDataSetChanged();
            } catch (SQLiteConstraintException sce) {

                sce.printStackTrace();
            } finally {
                db.close();
            }
        }

        private void details(int position) {

        Favourite favourite = favProfesionalList.get(position);
        long idProfesional = favourite.getIdProfesional();
        Intent intent = new Intent(context, DetailsProfesionalView.class);
        intent.putExtra("idProfesional", favourite.getIdProfesional());


        context.startActivity(intent);

        }

    }



