package com.alberto.familysyncapp.view.profesional;

import static com.alberto.familysyncapp.db.Constants.DATABASE_NAME;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.room.Room;

import com.alberto.familysyncapp.R;
import com.alberto.familysyncapp.contract.profesional.DetailsProfesionalContract;
import com.alberto.familysyncapp.db.AppDatabase;
import com.alberto.familysyncapp.domain.Profesional;
import com.alberto.familysyncapp.presenter.profesional.DetailsProfesionalPresenter;
import com.alberto.familysyncapp.presenter.profesional.RegisterProfesionalPresenter;
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

public class DetailsProfesionalView extends AppCompatActivity implements DetailsProfesionalContract.view {

    private static final int REQUEST_SELECT_PHOTO = 1;

    Profesional isModifyProfesional;
    private AppDatabase db;
    private TextInputLayout tilNombre;
    private TextInputEditText etNombre;
    private TextInputLayout tilApellidos;
    private TextInputEditText etApellidos;
    private TextInputLayout tilDni;
    private TextInputEditText etDni;
    //private EditText etFechaNac;
    private TextInputLayout tilCategoria;
    private TextInputEditText etCategoria;
    private ImageView imageView;

    private Profesional profesional;

    private ActivityResultLauncher<Intent> photoPickerLauncher;

    private RegisterProfesionalPresenter registerProfesionalPresenter;

    private DetailsProfesionalPresenter detailsProfesionalPresenter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_details_profesional);
        detailsProfesionalPresenter = new DetailsProfesionalPresenter(this);

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
                            profesional.setPhotoUri(photoUriString);

                            // Cargar y mostrar la foto en el ImageView
                            loadImage(photoUriString);

                            Snackbar.make(imageView, R.string.fotoSeleccionada, BaseTransientBottomBar.LENGTH_LONG).show();
                        } else {
                            // Foto capturada con la cámara
                            Uri photoUri = Uri.fromFile(createTempImageFile());
                            String photoUriString = photoUri.toString();
                            profesional.setPhotoUri(photoUriString);
                            loadImage(photoUriString);
                            Snackbar.make(imageView, R.string.fotoCapturada, BaseTransientBottomBar.LENGTH_LONG).show();
                        }
                    }
                }
        );

        tilNombre = findViewById(R.id.tilNombre);
        tilApellidos = findViewById(R.id.tilApellidos);
        tilDni = findViewById(R.id.tilDni);
        tilCategoria = findViewById(R.id.tilCategoria);
        etNombre = findViewById(R.id.etTitulo);
        etApellidos = findViewById(R.id.etTitular);
        etDni = findViewById(R.id.etCuerpo);
        //etFechaNac = findViewById(R.id.etFechaNac);
        etCategoria = findViewById(R.id.etCategoria);
        imageView = findViewById(R.id.ivProfesionalReg);

        db = Room.databaseBuilder(this, AppDatabase.class, DATABASE_NAME)
                .allowMainThreadQueries().build();

        Intent intent = getIntent();
        long profesionalId = intent.getLongExtra("idProfesional", 0);

        //registerProfesionalPresenter = new RegisterProfesionalPresenter(this);
        detailsProfesionalPresenter.loadProfesional(profesionalId);
    }




    private void fillData(Profesional profesional) {
        tilNombre.getEditText().setText(profesional.getNombre());
        tilApellidos.getEditText().setText(profesional.getApellidos());
        tilCategoria.getEditText().setText(profesional.getCategoria());
        tilDni.getEditText().setText(profesional.getDni());
        //if (profesional.getFechaNacimiento() != null) {
        //    etFechaNac.setText(profesional.getFechaNacimiento().toString());
        //} else {
        //    etFechaNac.setText(""); // or provide a default value or handle the case when fechaNacimiento is null
        //}
        loadImage(profesional.getPhotoUri());
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
                    .load(R.drawable.profesional)
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




    public void cancel(View view) {
        onBackPressed();
    }

    public void showProfesional(Profesional profesional) {
        fillData(profesional);
    }

    public void showError(String errorMessage) {
        Snackbar.make((findViewById(R.id.etTitulo)), errorMessage,
                BaseTransientBottomBar.LENGTH_LONG).show();


    }

    @Override
    public void showMessage(String message) {

    }
}

