package com.alberto.familysyncapp.view.profesional;

import static com.alberto.familysyncapp.db.Constants.DATABASE_NAME;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.room.Room;

import com.alberto.familysyncapp.R;
import com.alberto.familysyncapp.contract.profesional.RegisterProfesionalContract;
import com.alberto.familysyncapp.db.AppDatabase;
import com.alberto.familysyncapp.domain.Profesional;
import com.alberto.familysyncapp.presenter.profesional.ModifyProfesionalPresenter;
import com.alberto.familysyncapp.presenter.profesional.RegisterProfesionalPresenter;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

public class RegisterProfesionalView extends AppCompatActivity implements RegisterProfesionalContract.view {

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
    private String selectedDate;
    private TextInputEditText etFechaNacimiento;
    private TextInputLayout tilFechaNacimiento;
    private ImageView imageView;

    private Profesional profesional;

    private ActivityResultLauncher<Intent> photoPickerLauncher;

    private RegisterProfesionalPresenter registerProfesionalPresenter;

    private ModifyProfesionalPresenter modifyProfesionalPresenter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register_profesional);

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

        checkCameraPermission();

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
                        } /*else {
                            // Foto capturada con la cámara
                            Uri photoUri = Uri.fromFile(createTempImageFile());
                            String photoUriString = photoUri.toString();
                            profesional.setPhotoUri(photoUriString);
                            loadImage(photoUriString);
                            Snackbar.make(imageView, R.string.fotoCapturada, BaseTransientBottomBar.LENGTH_LONG).show();
                        }*/
                    }
                }
        );

        tilNombre = findViewById(R.id.tilNombre);
        tilApellidos = findViewById(R.id.tilApellidos);
        tilDni = findViewById(R.id.tilDni);
        tilCategoria = findViewById(R.id.tilCategoria);
        tilFechaNacimiento = findViewById(R.id.tilFechaNacimiento);
        etNombre = findViewById(R.id.etTitulo);
        etApellidos = findViewById(R.id.etTitular);
        etDni = findViewById(R.id.etCuerpo);
        etFechaNacimiento = findViewById(R.id.etFechaNacimiento);
        etCategoria = findViewById(R.id.etCategoria);
        imageView = findViewById(R.id.ivProfesionalReg);

        db = Room.databaseBuilder(this, AppDatabase.class, DATABASE_NAME)
                .allowMainThreadQueries().build();

        etFechaNacimiento.setOnClickListener(view -> {
            CalendarConstraints.Builder constraintsBuilder = new CalendarConstraints.Builder();
            /*constraintsBuilder.setStart(Calendar.getInstance().getTimeInMillis()); // Puedes configurar una fecha de inicio si es necesario
            constraintsBuilder.setEnd(31/12/3000);*/

            // Supongamos que deseas establecer la fecha predeterminada como la fecha actual
            Long todayMillis = MaterialDatePicker.todayInUtcMilliseconds();

            MaterialDatePicker.Builder<Long> builder = MaterialDatePicker.Builder.datePicker();
            builder.setTitleText("Selecciona una fecha");
            builder.setSelection(todayMillis);
            builder.setCalendarConstraints(constraintsBuilder.build());

            MaterialDatePicker<Long> datePicker = builder.build();

            datePicker.addOnPositiveButtonClickListener(
                    selection -> {
                        // Aquí puedes obtener la fecha seleccionada en milisegundos
                        long selectedMillis = datePicker.getSelection();

                        // Convierte los milisegundos a un objeto LocalDate
                        LocalDate selectedLocalDate = Instant.ofEpochMilli(selectedMillis)
                                .atZone(ZoneOffset.UTC)
                                .toLocalDate();

                        // Formatea la fecha en el formato "dd-MM-yyyy"
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                        String formattedDate = selectedLocalDate.format(formatter);

                        // Guarda la fecha formateada en tu variable o base de datos
                        selectedDate = formattedDate;

                        // Muestra la fecha formateada en el EditText
                        etFechaNacimiento.setText(formattedDate);
                    }
            );

            datePicker.show(getSupportFragmentManager(), datePicker.toString());
        });

        Intent intent = getIntent();
        long profesionalId = intent.getLongExtra("id", -1);
        isModifyProfesional = (Profesional) intent.getSerializableExtra("modify_profesional");

        if (isModifyProfesional != null) {
            profesional = isModifyProfesional;
            fillData(profesional);
            loadImage(profesional.getPhotoUri());

        } else {
            profesional = new Profesional();

        }
        registerProfesionalPresenter = new RegisterProfesionalPresenter(this);
        modifyProfesionalPresenter = new ModifyProfesionalPresenter(this);
    }


    private void fillData(Profesional profesional) {
        tilNombre.getEditText().setText(profesional.getNombre());
        tilApellidos.getEditText().setText(profesional.getApellidos());
        tilCategoria.getEditText().setText(profesional.getCategoria());
        tilDni.getEditText().setText(profesional.getDni());
        tilFechaNacimiento.getEditText().setText(profesional.getFechaNacimiento());

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
        /*Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // Crea un archivo temporal para guardar la foto capturada por la cámara
        File photoFile = createTempImageFile();

        if (photoFile != null) {
            Uri photoUri = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".fileprovider", photoFile);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
        }*/

        Intent chooserIntent = Intent.createChooser(galleryIntent, "Seleccionar foto");
        //chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{cameraIntent});

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


    public void registerProfesional(View view) {
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
        String categoria = tilCategoria.getEditText().getText().toString();
        String fechaNac = selectedDate;




        //Conseguimos la ruta de almacenamiento, si no existe, la creamos
        File storageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "GesResFamilyApp");
        if (!storageDir.exists()) {
            storageDir.mkdirs();
        }

        //Le ponemos nombre al archivo y la extension
        File imageFile = new File(storageDir, System.currentTimeMillis() + ".jpg");
        Log.i("RegisterCentro", "register - filePath: " + imageFile);


        if (isModifyProfesional != null) {
            profesional.setNombre(nombre);
            profesional.setApellidos(apellidos);
            profesional.setDni(dni);
            //profesional.setFechaNacimiento(fechaNac);
            profesional.setCategoria(categoria);
            profesional.setFechaNacimiento(fechaNac);


            if (nombre == null || nombre.isEmpty()) {
                Toast.makeText(this, R.string.profesionalNombreVacio, Toast.LENGTH_LONG).show();
                etNombre.setText(nombre);
                etApellidos.setText(apellidos);
                etDni.setText(dni);
                etCategoria.setText(categoria);
            } else {
                modifyProfesionalPresenter.modifyProfesional(profesional);
                Toast.makeText(this, R.string.profesionalModificado, Toast.LENGTH_LONG).show();
                resetForm();
            }

        } else {
            profesional.setNombre(nombre);
            profesional.setApellidos(apellidos);
            profesional.setDni(dni);
            //profesional.setFechaNacimiento(fechaNac);
            profesional.setCategoria(categoria);
            profesional.setFechaNacimiento(fechaNac);


            if (nombre == null || nombre.isEmpty()) {
                Toast.makeText(this, R.string.profesionalNombreVacio, Toast.LENGTH_LONG).show();
                etNombre.setText(nombre);
                etApellidos.setText(apellidos);
                etDni.setText(dni);
                etCategoria.setText(categoria);
            } else {
                profesional.setNombre(nombre);
                profesional.setApellidos(apellidos);
                profesional.setDni(dni);
                //profesional.setFechaNacimiento(fechaNac);
                profesional.setCategoria(categoria);

                registerProfesionalPresenter.registerProfesional(profesional);
                Toast.makeText(this, R.string.profesionalRegistado, Toast.LENGTH_LONG).show();
                resetForm();
            }
        }
    }

    @Override
    public void resetForm() {
        etNombre.setText("");
        etApellidos.setText("");
        etDni.setText("");
        etCategoria.setText("");
        etNombre.requestFocus();

    }

    public void cancel(View view) {
        onBackPressed();
    }

    public void showMessage(String message) {
        Snackbar.make((findViewById(R.id.etTitulo)), message,
                BaseTransientBottomBar.LENGTH_LONG).show();
    }

    public void showError(String errorMessage) {
        Snackbar.make((findViewById(R.id.etTitulo)), errorMessage,
                BaseTransientBottomBar.LENGTH_LONG).show();


    }

    private void checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.CAMERA)) {
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},226);
            }
        }
    }
}

