package com.alberto.familysyncapp.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.alberto.familysyncapp.R;
import com.alberto.familysyncapp.db.AppDatabase;
import com.alberto.familysyncapp.domain.Residente;
import com.alberto.familysyncapp.view.residente.RegisterResidenteActivity;
import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

public class ResidenteAdapter extends RecyclerView.Adapter<ResidenteAdapter.ResidenteHolder>{

    private List<Residente> residenteList;
    //esto sirve para guardar la posición para luego poder hacer cosas con ellos.
    private Context context;

    private int selectedPosition;

    public ResidenteAdapter(Context context, List<Residente> dataList) {
        this.context = context;
        this.residenteList = dataList;

        //esto indica que no hay ninguno seleccionado
        selectedPosition = -1;
    }

    //Patron Holder (ESTO
    // ESTOY OBLIGADO A HACERLO SIEMPRE)
    //metodo que crea cada estructura de layout donde iran los datos de cada cnetro.
    @Override
    public ResidenteHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_view_residente, parent, false);
        return new ResidenteHolder(view);
    }

    //hace corresponder cada elemento de la lista para decir como pintarlo en cada elemento del layout
    @Override
    public void onBindViewHolder(ResidenteHolder holder, int position){
        holder.residenteNombre.getEditText().setText(residenteList.get(position).getNombre());
        holder.residenteApellidos.getEditText().setText(residenteList.get(position).getApellidos());
        holder.residenteDni.getEditText().setText(residenteList.get(position).getDni());
        //Date fechaNacimiento = profesionalList.get(position).getFechaNacimiento();
        //String fechaNacimientoString = "";
        //if (fechaNacimiento != null) {
        //    fechaNacimientoString = fechaNacimiento.toString(); // Convertir Date a String
        //}
        //holder.profesionalFechaNac.setText(fechaNacimientoString);
        holder.residenteSexo.getEditText().setText(residenteList.get(position).getSexo());

        Residente residente = residenteList.get(position);

        // Cargar y mostrar la foto en el ImageView
        String photoUriString = residente.getPhotoUri();
        if (photoUriString != null) {
            Uri photoUri = Uri.parse(photoUriString);
            Glide.with(context)
                    .load(photoUri)
                    .into(holder.residenteImagen);
        } else {
            // Mostrar una imagen de placeholder si no hay foto disponible
            Glide.with(context)
                    .load(R.drawable.icons8_city_buildings_100)
                    .into(holder.residenteImagen);
        }
    }

    @Override
    public int getItemCount() {
        return residenteList.size();
    }

    public class ResidenteHolder extends RecyclerView.ViewHolder{
        public TextInputLayout residenteNombre;
        public TextInputLayout residenteApellidos;
        public TextInputLayout residenteDni;
        public TextInputLayout residenteSexo;
        //public TextView profesionalFechaNac;
        public ImageView residenteImagen;

        //public CheckBox taskDone;
        //public Button doTaskButton;
        //public Button seeDetailsButton;
        public Button btDelete;
        public View parentView;
        public Button btMod;

        public ResidenteHolder(View view) {
            super(view);
            parentView = view;

            residenteNombre = view.findViewById(R.id.tilNombre);
            residenteApellidos = view.findViewById(R.id.tilApellidos);
            residenteDni = view.findViewById(R.id.tilDni);
            //profesionalFechaNac = view.findViewById(R.id.tvProfesionalFechaNac);
            residenteSexo = view.findViewById(R.id.tilSexo);
            residenteImagen = view.findViewById(R.id.ivResidente);


            btDelete = view.findViewById(R.id.btDelete);

            btMod = view.findViewById(R.id.btMod);

//            doTaskButton = view.findViewById(R.id.do_task_button);




            //Programar boton ver detalles de la tarea
            //doTaskButton.setOnClickListener(v -> doTask(getAdapterPosition()));

            //Programar boton marcar tarea como hecha.
            //seeDetailsButton.setOnClickListener(v -> seeDetails(getAdapterPosition()));

            //click on button (remove task from de list).
            btDelete.setOnClickListener(v -> deleteResidente(getAdapterPosition()));

            btMod.setOnClickListener(v-> modifyResidente(getAdapterPosition()));
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

        private void deleteResidente(int position){
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
        }

        private void modifyResidente(int position) {
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
        }

    }
}
