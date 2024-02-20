package com.alberto.familysyncapp.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.alberto.familysyncapp.R;
import com.alberto.familysyncapp.domain.Noticias;
import com.alberto.familysyncapp.domain.Residente;
import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

public class NoticiasAdapter extends RecyclerView.Adapter<NoticiasAdapter.NoticiasHolder>{

    private List<Noticias> noticiasList;
    //esto sirve para guardar la posición para luego poder hacer cosas con ellos.
    private Context context;

    private int selectedPosition;

    public NoticiasAdapter(Context context, List<Noticias> dataList) {
        this.context = context;
        this.noticiasList = dataList;

        //esto indica que no hay ninguno seleccionado
        selectedPosition = -1;
    }

    //Patron Holder (ESTO
    // ESTOY OBLIGADO A HACERLO SIEMPRE)
    //metodo que crea cada estructura de layout donde iran los datos de cada noticia.
    @Override
    public NoticiasHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_view_noticia, parent, false);
        return new NoticiasHolder(view);
    }

    //hace corresponder cada elemento de la lista para decir como pintarlo en cada elemento del layout
    @Override
    public void onBindViewHolder(NoticiasHolder holder, int position){
        holder.tituloNoticia.getEditText().setText(noticiasList.get(position).getTituloNoticia());
        holder.titular.getEditText().setText(noticiasList.get(position).getTitular());
        holder.cuerpo.getEditText().setText(noticiasList.get(position).getCuerpoNoticia());
        //Date fechaNacimiento = profesionalList.get(position).getFechaNacimiento();
        //String fechaNacimientoString = "";
        //if (fechaNacimiento != null) {
        //    fechaNacimientoString = fechaNacimiento.toString(); // Convertir Date a String
        //}
        //holder.profesionalFechaNac.setText(fechaNacimientoString);
        //holder.residenteSexo.getEditText().setText(noticiasList.get(position).getSexo());

        Noticias noticias = noticiasList.get(position);

        // Cargar y mostrar la foto en el ImageView
        String photoUriString = noticias.getPhotoUri();
        if (photoUriString != null) {
            Uri photoUri = Uri.parse(photoUriString);
            Glide.with(context)
                    .load(photoUri)
                    .into(holder.noticiaImagen);
        } else {
            // Mostrar una imagen de placeholder si no hay foto disponible
            Glide.with(context)
                    .load(R.drawable.icons8_city_buildings_100)
                    .into(holder.noticiaImagen);
        }
    }

    @Override
    public int getItemCount() {
        return noticiasList.size();
    }


    public class NoticiasHolder extends RecyclerView.ViewHolder{
        public TextInputLayout tituloNoticia;
        public TextInputLayout titular;
        public TextInputLayout cuerpo;

        //public TextInputLayout residenteSexo;
        //public TextView profesionalFechaNac;
        public ImageView noticiaImagen;

        //public CheckBox taskDone;
        //public Button doTaskButton;
        //public Button seeDetailsButton;
        //public Button btDelete;
        public View parentView;
        //public Button btMod;

        public NoticiasHolder(View view) {
            super(view);
            parentView = view;

            tituloNoticia = view.findViewById(R.id.tilTitulo);
            titular = view.findViewById(R.id.tilTitular);
            cuerpo = view.findViewById(R.id.tilCuerpo);
            //profesionalFechaNac = view.findViewById(R.id.tvProfesionalFechaNac);
            //residenteSexo = view.findViewById(R.id.tilSexo);
            noticiaImagen = view.findViewById(R.id.ivNoticia);


            //btDelete = view.findViewById(R.id.btDelete);

            //btMod = view.findViewById(R.id.btMod);

//            doTaskButton = view.findViewById(R.id.do_task_button);




            //Programar boton ver detalles de la tarea
            //doTaskButton.setOnClickListener(v -> doTask(getAdapterPosition()));

            //Programar boton marcar tarea como hecha.
            //seeDetailsButton.setOnClickListener(v -> seeDetails(getAdapterPosition()));

            //click on button (remove task from de list).
            //btDelete.setOnClickListener(v -> deleteResidente(getAdapterPosition()));

            //btMod.setOnClickListener(v-> modifyResidente(getAdapterPosition()));
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

    /*    private void deleteResidente(int position){
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage(R.string.estasSeguroDeBorrarElResidente)
                    .setTitle(R.string.ConfirmarBorrado)
                    .setPositiveButton(R.string.si, (dialog, id) -> {
                        final AppDatabase db = Room.databaseBuilder(context, AppDatabase.class, "Gesresfamily")
                                .allowMainThreadQueries().build();
                        Residente residente = residenteList.get(position);
                        db.residenteDao().delete(residente);

                        residenteList.remove(position);
                        notifyItemRemoved(position);
                    })
                    .setNegativeButton(R.string.no, (dialog, id) -> dialog.dismiss());
            AlertDialog dialog = builder.create();
            dialog.show();
        }*/

       /* private void modifyResidente(int position) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage(R.string.deseasModificarElResidente)
                    .setTitle(R.string.confirmarModificación)
                    .setPositiveButton(R.string.si, (dialog, id) -> {
                        Residente residente = residenteList.get(position);

                        Intent intent = new Intent(context, RegisterResidenteActivity.class);
                        intent.putExtra("modify_residente", true);
                        intent.putExtra("id", residente.getId());
                        intent.putExtra("nombre", residente.getNombre());
                        intent.putExtra("apellidos", residente.getApellidos());
                        intent.putExtra("dni", residente.getDni());
                        //intent.putExtra("fechaNac", profesional.getFechaNacimiento());
                        intent.putExtra("sexo", residente.getSexo());

                        context.startActivity(intent);
                    })
                    .setNegativeButton(R.string.no, (dialog, id) -> dialog.dismiss());

            AlertDialog dialog = builder.create();
            dialog.show();
        }*/

    }
}
