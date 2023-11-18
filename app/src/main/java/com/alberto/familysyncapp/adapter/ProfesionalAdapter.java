package com.alberto.familysyncapp.adapter;

import static com.alberto.familysyncapp.db.Constants.DATABASE_NAME_FAV;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.alberto.familysyncapp.R;
import com.alberto.familysyncapp.contract.profesional.DeleteProfesionalContract;
import com.alberto.familysyncapp.db.GesResFavourites;
import com.alberto.familysyncapp.domain.Favourite;
import com.alberto.familysyncapp.domain.Profesional;
import com.alberto.familysyncapp.presenter.profesional.DeleteProfesionalPresenter;
import com.alberto.familysyncapp.view.profesional.RegisterProfesionalView;
import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

public class ProfesionalAdapter extends RecyclerView.Adapter<ProfesionalAdapter.ProfesionalHolder>
    implements DeleteProfesionalContract.view{

    private List<Profesional> profesionalList;
    //esto sirve para guardar la posición para luego poder hacer cosas con ellos.
    private Context context;

    private int selectedPosition;
    private DeleteProfesionalPresenter deleteProfesionalPresenter;

    //private CheckBox iconFav;

    public ProfesionalAdapter(Context context, List<Profesional> dataList) {
        this.context = context;
        this.profesionalList = dataList;
        this.deleteProfesionalPresenter = new DeleteProfesionalPresenter(this);

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
    public ProfesionalHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_view_profesional, parent, false);
        return new ProfesionalHolder(view);
    }

    //hace corresponder cada elemento de la lista para decir como pintarlo en cada elemento del layout
    @Override
    public void onBindViewHolder(ProfesionalHolder holder, int position){
        holder.profesionalNombre.getEditText().setText(profesionalList.get(position).getNombre());
        holder.profesionalApellidos.getEditText().setText(profesionalList.get(position).getApellidos());
        holder.profesionalDni.getEditText().setText(profesionalList.get(position).getDni());
        //Date fechaNacimiento = profesionalList.get(position).getFechaNacimiento();
        //String fechaNacimientoString = "";
        //if (fechaNacimiento != null) {
        //    fechaNacimientoString = fechaNacimiento.toString(); // Convertir Date a String
        //}
        //holder.profesionalFechaNac.setText(fechaNacimientoString);
        holder.profesionalCategoria.getEditText().setText(profesionalList.get(position).getCategoria());


        Profesional profesional = profesionalList.get(position);

        // Cargar y mostrar la foto en el ImageView
        String photoUriString = profesional.getPhotoUri();
        if (photoUriString != null) {
            Uri photoUri = Uri.parse(photoUriString);
            Glide.with(context)
                    .load(photoUri)
                    .into(holder.profesionalImagen);
        } else {
            // Mostrar una imagen de placeholder si no hay foto disponible
            Glide.with(context)
                    .load(R.drawable.profesional)
                    .into(holder.profesionalImagen);
        }
    }

    @Override
    public int getItemCount() {
        return profesionalList.size();
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this.getContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this.getContext(), message, Toast.LENGTH_LONG).show();
    }

    public class ProfesionalHolder extends RecyclerView.ViewHolder{
        public TextInputLayout profesionalNombre;
        public TextInputLayout profesionalApellidos;
        public TextInputLayout profesionalDni;
        public TextInputLayout profesionalCategoria;
        //public TextView profesionalFechaNac;
        public ImageView profesionalImagen;

        //public CheckBox taskDone;
        //public Button doTaskButton;
        //public Button seeDetailsButton;
        public Button btDelete;
        public View parentView;
        public Button btMod;

        public Button btFav;

        public ProfesionalHolder(View view) {
            super(view);
            parentView = view;

            profesionalNombre = view.findViewById(R.id.tilNombre);
            profesionalApellidos = view.findViewById(R.id.tilApellidos);
            profesionalDni = view.findViewById(R.id.tilDni);
            //profesionalFechaNac = view.findViewById(R.id.tvProfesionalFechaNac);
            profesionalCategoria = view.findViewById(R.id.tilCategoria);
            profesionalImagen = view.findViewById(R.id.ivProfesional);


            btDelete = view.findViewById(R.id.btDelete);

            btMod = view.findViewById(R.id.btMod);
            btFav= view.findViewById(R.id.btDelFav);
            //iconFav = view.findViewById(R.id.iconFav);

//            doTaskButton = view.findViewById(R.id.do_task_button);




            //Programar boton ver detalles de la tarea
            //doTaskButton.setOnClickListener(v -> doTask(getAdapterPosition()));

            //Programar boton marcar tarea como hecha.
            //seeDetailsButton.setOnClickListener(v -> seeDetails(getAdapterPosition()));

            //click on button (remove task from de list).
            btDelete.setOnClickListener(v -> deleteProfesional(getAdapterPosition()));

            btMod.setOnClickListener(v-> modifyProfesional(getAdapterPosition()));
            btFav.setOnClickListener(v -> setFavourite(getAdapterPosition()));
            /*iconFav.setOnCheckedChangeListener((buttonView, isChecked) -> {

                int position = getAdapterPosition();
                setCheckboxed(position);
                setFavourite(position);


            });*/

        }

/*        private void doTask(int position){
            Car car = carList.get(position);
            car.setDone(true);

            final AppDatabase db = Room.databaseBuilder(context, AppDatabase.class, "tasks")
                    .allowMainThreadQueries().build();
            db.taskDao().update(car);

            notifyItemChanged(position);

        }*/

   /*     private void seeDetails(int position){
            Centro centro = centroList.get(position);

            Intent intent = new Intent(context, ViewCentroActivity.class);
            intent.putExtra("name", centro.getNombre());
            context.startActivity(intent);


        }*/

        private void deleteProfesional(int position){
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage(R.string.estasSeguroDeBorrarElProfesional)
                    .setTitle(R.string.ConfirmarBorrado)
                    .setPositiveButton(R.string.si, (dialog, id) -> {
                        Profesional profesional = profesionalList.get(position);
                        deleteProfesionalPresenter.deleteProfesional(profesional.getId());

                        profesionalList.remove(position);
                        notifyItemRemoved(position);
                    })
                    .setNegativeButton(R.string.no, (dialog, id) -> dialog.dismiss());
            AlertDialog dialog = builder.create();
            dialog.show();
        }

        private void modifyProfesional(int position) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage(R.string.deseasModificarElProfesional)
                    .setTitle(R.string.confirmarModificación)
                    .setPositiveButton(R.string.si, (dialog, id) -> {
                        Profesional profesional = profesionalList.get(position);

                        Intent intent = new Intent(context, RegisterProfesionalView.class);
                        intent.putExtra("modify_profesional", profesional);
                       /* intent.putExtra("id", profesional.getId());
                        intent.putExtra("nombre", profesional.getNombre());
                        intent.putExtra("apellidos", profesional.getApellidos());
                        intent.putExtra("dni", profesional.getDni());
                        //intent.putExtra("fechaNac", profesional.getFechaNacimiento());
                        intent.putExtra("categoria", profesional.getCategoria());*/

                        context.startActivity(intent);
                    })
                    .setNegativeButton(R.string.no, (dialog, id) -> dialog.dismiss());

            AlertDialog dialog = builder.create();
            dialog.show();
        }

    }

    private void setFavourite(int position) {
        final GesResFavourites db = Room.databaseBuilder(context, GesResFavourites.class, DATABASE_NAME_FAV).allowMainThreadQueries().build();
        try {
            Profesional profesional = profesionalList.get(position);
            Favourite favourite = db.getFavouriteDAO().getFavourite(profesional.getId());

            if (favourite == null) {
                long idProfesional = profesional.getId();
                Log.i("favorito", " checkedIdProfesional: " + idProfesional);
                Favourite insert = new Favourite(profesional.getId(), profesional.getNombre());
                Log.i("favorito", " checkedIdProfesional: " + idProfesional);

                db.getFavouriteDAO().insert(insert);
                Log.i("favorito", "favorito añadido "+ profesional.getNombre());
            } /*else {
                long idProfesional = profesional.getId();
                Log.i("favorito", "elseIdProfesional: " + idProfesional);
                Favourite delete = db.getFavouriteDAO().getFavourite(profesional.getId());
                Log.i("favorito", "elseIdProfesional: " + idProfesional);
                db.getFavouriteDAO().delete(favourite);
            }*/
            notifyDataSetChanged();
        } catch (SQLiteConstraintException sce) {

            sce.printStackTrace();
        } finally {
            db.close();
        }
    }

    //Esto es para intentar trabajar con icono de favoritos, pero me da algún error, pte de revisar.
    /*private void setFavouriteCheckBox(int position) {
        final GesResFavourites db = Room.databaseBuilder(context, GesResFavourites.class, DATABASE_NAME_FAV).allowMainThreadQueries().build();
        try {
            Profesional profesional = profesionalList.get(position);
            if (iconFav.isChecked()) {
                long idProfesional = profesional.getId();
                Log.i("favorito", " checkedIdProfesional: " + idProfesional);
                Favourite insert = new Favourite(profesional.getId(), profesional.getNombre());
                Log.i("favorito", " checkedIdProfesional: " + idProfesional);

                db.getFavouriteDAO().insert(insert);
                Log.i("favorito", "favorito añadido "+ profesional.getNombre());
            } else {
                long idProfesional = profesional.getId();
                Log.i("favorito", "elseIdProfesional: " + idProfesional);
                Favourite delete = db.getFavouriteDAO().getFavourite(profesional.getId());
                Log.i("favorito", "elseIdProfesional: " + idProfesional);
                db.getFavouriteDAO().delete(delete);
            }
            notifyDataSetChanged();
        } catch (SQLiteConstraintException sce) {

            sce.printStackTrace();
        } finally {
            db.close();
        }
    }

    private void setCheckboxed(int position) {
        final GesResFavourites db = Room.databaseBuilder(context, GesResFavourites.class, DATABASE_NAME_FAV).allowMainThreadQueries().build();
        Profesional profesional = profesionalList.get(position);
        try {
            Favourite favourite = db.getFavouriteDAO().getFavourite(profesional.getId());
            if(favourite != null){
                iconFav.setChecked(true);

            } else {
                iconFav.setChecked(false);


            }
        } catch (SQLiteConstraintException sce) {

            sce.printStackTrace();
        } finally {
            db.close();
        }
    }*/
}



