package com.alberto.familysyncapp.view.residente;

import static com.alberto.familysyncapp.db.Constants.DATABASE_NAME;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.room.Room;

import com.alberto.familysyncapp.R;
import com.alberto.familysyncapp.db.AppDatabase;
import com.alberto.familysyncapp.domain.Residente;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class RegisterResidenteActivity extends AppCompatActivity {

    private static final int REQUEST_SELECT_PHOTO = 1;

    private boolean isModifyResidente;
    private AppDatabase db;

    private TextInputLayout tilNombre;
    private TextInputEditText etNombre;
    private TextInputLayout tilApellidos;
    private TextInputEditText etApellidos;
    private TextInputLayout tilDni;
    private TextInputEditText etDni;
    //private EditText etFechaNac;
    private TextInputLayout tilSexo;
    private TextInputEditText etSexo;
    private ImageView imageView;

    private Residente residente;

    private ActivityResultLauncher<Intent> photoPickerLauncher;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register_residente);

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


        photoPickerLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            Uri selectedPhotoUri = data.getData();
                            String photoUriString = selectedPhotoUri.toString();

                            // Guardar la URI de la foto en el centro
                            residente.setPhotoUri(photoUriString);

                            // Cargar y mostrar la foto en el ImageView
                            loadImage(photoUriString);

                            Snackbar.make(imageView, R.string.fotoSeleccionada, BaseTransientBottomBar.LENGTH_LONG).show();
                        } else {
                            // Foto capturada con la cámara
                            Uri photoUri = Uri.fromFile(createTempImageFile());
                            String photoUriString = photoUri.toString();
                            residente.setPhotoUri(photoUriString);
                            loadImage(photoUriString);
                            Snackbar.make(imageView, R.string.fotoCapturada, BaseTransientBottomBar.LENGTH_LONG).show();
                        }
                    }
                }
        );

        tilNombre = findViewById(R.id.tilNombre);
        tilApellidos = findViewById(R.id.tilApellidos);
        tilDni = findViewById(R.id.tilDni);
        tilSexo = findViewById(R.id.tilSexo);
        etNombre = findViewById(R.id.etNombre);
        etApellidos = findViewById(R.id.etApellidos);
        etDni = findViewById(R.id.etDni);
        //etFechaNac = findViewById(R.id.etFechaNac);
        etSexo = findViewById(R.id.etSexo);
        imageView = findViewById(R.id.ivResidenteReg);

        db = Room.databaseBuilder(this, AppDatabase.class, DATABASE_NAME)
                .allowMainThreadQueries().build();

        Intent intent = getIntent();
        long residenteId = intent.getLongExtra("id", -1);
        isModifyResidente = intent.getBooleanExtra("modify_residente", false);

        if (isModifyResidente) {
            if (residenteId != -1) {
                residente = db.residenteDao().getById(residenteId);
                if (residente != null) {
                    fillData(residente);
                    loadImage(residente.getPhotoUri());
                }
            }
        } else {
            residente = new Residente();

        }
    }


    private void fillData(Residente residente) {
        tilNombre.getEditText().setText(residente.getNombre());
        tilApellidos.getEditText().setText(residente.getApellidos());
        tilSexo.getEditText().setText(residente.getSexo());
        tilDni.getEditText().setText(residente.getDni());
        //if (residente.getFechaNacimiento() != null) {
        //    etFechaNac.setText(residente.getFechaNacimiento().toString());
        //} else {
        //    etFechaNac.setText(""); // or provide a default value or handle the case when fechaNacimiento is null
        //}
    }

    /*//usando la libreria Glide
    private void loadImage(String photoUriString) {
        if (photoUriString != null) {
            Uri photoUri = Uri.parse(photoUriString);
            Glide.with(this)
                    .load(photoUri)
                    .into(imageView);
        } else {
            Glide.with(this)
                    .load(R.drawable.vector_sector_sanidad)
                    .into(imageView);
        }
    }*/
    //Usando la libreria picasso
    private void loadImage(String photoUriString) {
        if (photoUriString != null) {
            Uri photoUri = Uri.parse(photoUriString);
            Picasso.get()
                    .load(photoUri)
                    .into(imageView);
        } else {
            Picasso.get()
                    .load(R.drawable.icons8_city_buildings_100)
                    .into(imageView);
        }
    }

    public void selectPhoto(View view) {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // Crea un archivo temporal para guardar la foto capturada por la cámara
        File photoFile = createTempImageFile();

        if (photoFile != null) {
            Uri photoUri = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".fileprovider", photoFile);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
        }

        Intent chooserIntent = Intent.createChooser(galleryIntent, "Seleccionar foto");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{cameraIntent});

        //startActivityForResult(chooserIntent, REQUEST_SELECT_PHOTO);
        photoPickerLauncher.launch(chooserIntent);
    }

    private File createTempImageFile() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";

        try {
            File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            File imageFile = File.createTempFile(imageFileName, ".jpg", storageDir);
            return imageFile;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void registerResidente(View view) {
        String nombre = tilNombre.getEditText().getText().toString();
        String apellidos = tilApellidos.getEditText().getText().toString();
        String dni = tilDni.getEditText().getText().toString();
        //Editable editableFechaNac = etFechaNac.getText();
        //String fechaNacString = editableFechaNac.toString();

        //SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        //Date fechaNac = null;
        //try {
        //    fechaNac = dateFormat.parse(fechaNacString);
        //} catch (ParseException e) {
        //    e.printStackTrace();
        //}
        String sexo = tilSexo.getEditText().getText().toString();



        if (isModifyResidente) {
            residente.setNombre(nombre);
            residente.setApellidos(apellidos);
            residente.setDni(dni);
            //residente.setFechaNacimiento(fechaNac);
            residente.setSexo(sexo);
            db.residenteDao().update(residente);
            Toast.makeText(this, R.string.residenteModificado, Toast.LENGTH_LONG).show();
        } else {
            residente.setNombre(nombre);
            residente.setApellidos(apellidos);
            residente.setDni(dni);
            //residente.setFechaNacimiento(fechaNac);
            residente.setSexo(sexo);
            db.residenteDao().insert(residente);
            Toast.makeText(this, R.string.residenteRegistado, Toast.LENGTH_LONG).show();
        }

        etNombre.setText("");
        etApellidos.setText("");
        etSexo.setText("");
        etDni.setText("");
        //etFechaNac.setText("");
        etNombre.requestFocus();
    }


//Con atctivityResultLauncher ya no hace falta.
    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_SELECT_PHOTO && resultCode == RESULT_OK) {
            if (data != null) {
                // Foto seleccionada desde la galería
                Uri selectedPhotoUri = data.getData();
                String photoUriString = selectedPhotoUri.toString();
                centro.setPhotoUri(photoUriString);
                loadImage(photoUriString);
                Snackbar.make(imageView, "Foto seleccionada", BaseTransientBottomBar.LENGTH_LONG).show();
            } else {
                // Foto capturada con la cámara
                Uri photoUri = Uri.fromFile(createTempImageFile());
                String photoUriString = photoUri.toString();
                centro.setPhotoUri(photoUriString);
                loadImage(photoUriString);
                Snackbar.make(imageView, "Foto capturada", BaseTransientBottomBar.LENGTH_LONG).show();
            }
        }
    }*/


   /* @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_SELECT_PHOTO && resultCode == RESULT_OK && data != null) {
            Uri selectedPhotoUri = data.getData();
            String photoUriString = selectedPhotoUri.toString();

            // Guardar la URI de la foto en el centro
            centro.setPhotoUri(photoUriString);

            // Cargar y mostrar la foto en el ImageView
            loadImage(photoUriString);

            Snackbar.make(imageView, "Foto seleccionada", BaseTransientBottomBar.LENGTH_LONG).show();
        }
    }*/


    public void cancel(View view) {
        onBackPressed();
    }
}

