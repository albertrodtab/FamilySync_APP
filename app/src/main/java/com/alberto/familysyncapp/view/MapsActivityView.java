package com.alberto.familysyncapp.view;



import static com.mapbox.core.constants.Constants.PRECISION_6;

import android.annotation.SuppressLint;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.alberto.familysyncapp.R;
import com.alberto.familysyncapp.adapter.CentroAdapter;
import com.alberto.familysyncapp.contract.centro.CentrosListMapsContract;
import com.alberto.familysyncapp.domain.Centro;
import com.alberto.familysyncapp.presenter.centro.CentrosListMapsPresenter;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.appbar.MaterialToolbar;
import com.mapbox.api.directions.v5.DirectionsCriteria;
import com.mapbox.api.directions.v5.MapboxDirections;
import com.mapbox.api.directions.v5.models.DirectionsResponse;
import com.mapbox.api.directions.v5.models.DirectionsRoute;
import com.mapbox.api.directions.v5.models.RouteOptions;
import com.mapbox.core.constants.Constants;
import com.mapbox.geojson.LineString;
import com.mapbox.geojson.Point;
import com.mapbox.maps.CameraOptions;
import com.mapbox.maps.MapView;
import com.mapbox.maps.Style;
import com.mapbox.maps.extension.style.layers.LayerUtils;
import com.mapbox.maps.extension.style.layers.generated.LineLayer;
import com.mapbox.maps.extension.style.sources.SourceUtils;
import com.mapbox.maps.extension.style.sources.generated.GeoJsonSource;
import com.mapbox.maps.plugin.annotation.AnnotationConfig;
import com.mapbox.maps.plugin.annotation.AnnotationPlugin;
import com.mapbox.maps.plugin.annotation.AnnotationPluginImplKt;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationManager;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationManagerKt;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapsActivityView extends AppCompatActivity implements CentrosListMapsContract.view,
        Style.OnStyleLoaded, Callback<DirectionsResponse> {

    private MapView mapView;

    private double gpsLatitude;
    private double gpsLongitude;

    private FusedLocationProviderClient fusedLocationClient;
    private PointAnnotationManager pointAnnotationManager;
    public static List<Centro> centroList = new ArrayList<>();
    public Centro lastCentro;
    private CentrosListMapsPresenter centrosListMapsPresenter;
    private CentroAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        adapter = new CentroAdapter(this, centroList);

        mapView = findViewById(R.id.mapView); //cargamos el mapa
        mapView.getMapboxMap().loadStyleUri(Style.MAPBOX_STREETS, this);
        initializePointManager(); // inicializamos el pointmanager
//        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
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

        centrosListMapsPresenter = new CentrosListMapsPresenter(this);

        gps();
    }

    @Override
    protected void onResume() {
        super.onResume();
        centrosListMapsPresenter.loadAllCentros();
        adapter.notifyDataSetChanged();
    }

    /**
     * Inicializamos el pointmanager
     */
    private void initializePointManager() {
        AnnotationPlugin annotationPlugin = AnnotationPluginImplKt.getAnnotations(mapView);
        AnnotationConfig annotationConfig = new AnnotationConfig();
        pointAnnotationManager = PointAnnotationManagerKt.createPointAnnotationManager(annotationPlugin, annotationConfig);

    }

    /*
    *
     * Para poder crear un marker y que lo pinte por cada centro
     *
     * @param point  Pasamos el point
     * @param nombre el nombre del centro
     *//*
    private void addMarker(Point point, String nombre) {
        PointAnnotationOptions pointAnnotationOptions = new PointAnnotationOptions()
                .withPoint(point)
                .withTextField(nombre) //asi aparece el nombre en el mapa
                .withIconImage(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_centro_mayores_foreground));
        pointAnnotationManager.create(pointAnnotationOptions);
    }*/

    private void addMarker(double latitude, double longitude, String title, int id) {
        PointAnnotationOptions pointAnnotationOptions = new PointAnnotationOptions()
                .withPoint(Point.fromLngLat(longitude, latitude))
                .withIconImage(BitmapFactory.decodeResource(getResources(), id))
                .withTextField(title);
        pointAnnotationManager.create(pointAnnotationOptions);
    }

    /**
     * Fija la camara del mapa donde nosotros queramos, asi el mapa arranca desde ese punto
     *
     * @param
     */
/*    private void setCameraPosition(Point point) {
        CameraOptions cameraPosition = new CameraOptions.Builder()
                .center(point)
                .pitch(0.0)
                .zoom(13.5)
                .bearing(-17.6)
                .build();
        mapView.getMapboxMap().setCamera(cameraPosition);

    }*/

    private void setCameraPosition(double latitude, double longitude) {
        CameraOptions cameraPosition = new CameraOptions.Builder()
                .center(Point.fromLngLat(longitude, latitude))
                .pitch(45.0)
                .zoom(15.5)
                .bearing(0.0)
                .build();
        mapView.getMapboxMap().setCamera(cameraPosition);
    }

    @SuppressLint("MissingPermission")
    private void gps() {
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, location -> {
                    // Got last known location. In some rare situations this can be null.
                    if (location != null) {
                        // Logic to handle location object
                        gpsLongitude = location.getLongitude();
                        gpsLatitude = location.getLatitude();
                        Log.i("gps: ", "+++++++++++");
                        Log.i("gps: ", String.valueOf(location.getLongitude()));
                        Log.i("gps: ", String.valueOf(location.getLatitude()));
                        Log.i("gps: ", String.valueOf(location));

                        setCameraPosition(gpsLatitude, gpsLongitude);



                        addMarker(gpsLatitude, gpsLongitude, "Estás Aquí", R.mipmap.ic_residente_foreground);

                    }
                });

    }

    private void addCenterToMap(List<Centro> centros) {
        for (Centro centro : centros) {
            Point point = Point.fromLngLat(centro.getLongitude(), centro.getLatitude());
            //addMarker(point, centro.getNombre()); //le pasamos el metodo que crea el marker y ponemos el point y nombre del centro       }

            addMarker(centro.getLatitude(), centro.getLongitude(), centro.getNombre(), R.mipmap.ic_centro_mayores_foreground);

            Centro lastCentro = centros.get(centros.size() - 1); // recogemos el ultimo centro
            setCameraPosition(lastCentro.getLatitude(), lastCentro.getLongitude()); //Fijamos la camara del mapa en el ultimo centro
        }
    }

    public void getRoute(View view){
            Point origin = Point.fromLngLat(lastCentro.getLongitude(), lastCentro.getLatitude());
            Point destination = Point.fromLngLat(gpsLongitude, gpsLatitude);
            calculateRoute(origin, destination);
        }

    private void calculateRoute(Point origin, Point destination) {
        RouteOptions routeOptions = RouteOptions.builder()
                .baseUrl(Constants.BASE_API_URL)
                .user(Constants.MAPBOX_USER)
                .profile(DirectionsCriteria.PROFILE_DRIVING)
                .steps(true)
                .coordinatesList(List.of(origin, destination))
                .build();
        MapboxDirections directions = MapboxDirections.builder()
                .routeOptions(routeOptions)
                .accessToken(getString(R.string.mapbox_access_token))
                .build();
        directions.enqueueCall(this);
    }

    @Override
    public void showCentros(List<Centro> centros) {
        centroList.clear();
        centroList.addAll(centros);
        lastCentro = centroList.get(centroList.size() - 1);
        addCenterToMap(centroList);

    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void onStyleLoaded(@NonNull Style style) {
           /* addMarker(lastCentro.getLatitude(), lastCentro.getLongitude(), String.valueOf(lastCentro.getId()), R.mipmap.ic_centro_mayores_foreground);
            setCameraPosition(lastCentro.getLatitude(), lastCentro.getLongitude());
            gps();*/
    }

    @Override
    public void onResponse(Call<DirectionsResponse> call, Response<DirectionsResponse> response) {
        DirectionsRoute mainRoute = response.body().routes().get(0);
        mapView.getMapboxMap().getStyle(style -> {
            LineString routeLine = LineString.fromPolyline(mainRoute.geometry(), PRECISION_6);

            GeoJsonSource routeSource = new GeoJsonSource.Builder("trace-source")
                    .geometry(routeLine)
                    .build();
            LineLayer routeLayer = new LineLayer("trace-leyer", "trace-source")
                    .lineWidth(7.f)
                    .lineColor(Color.BLUE)
                    .lineOpacity(1f);
            SourceUtils.addSource(style, routeSource);
            LayerUtils.addLayer(style, routeLayer);
        });
    }


    @Override
    public void onFailure(Call<DirectionsResponse> call, Throwable t) {

    }
}





