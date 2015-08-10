package rmservicos.net.rmservicostwitterapp;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MapActivity extends Activity {

    // Create local variables
    private GoogleMap googleMap;
    List<Localizacoes> locais;
    private ImageButton btMapa;
    Bitmap bmp;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        // Get for source activity a list of twitts with coordinates
        Intent it = getIntent();
        locais = (ArrayList<Localizacoes>)it.getSerializableExtra("Localizacoes");

        try {
            // Load map
            initilizeMap();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void initilizeMap() {
        if (btMapa == null) {
            btMapa = (ImageButton) findViewById(R.id.idMapaMap);
        }
        // Disable button "Map"
        btMapa.setEnabled(false);

        // Load Map to "googleMap"
        if (googleMap == null) {
            googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();

            // check if map is created successfully or not
            if (googleMap == null) {
                Toast.makeText(getApplicationContext(), "Sorry! Unable to create maps", Toast.LENGTH_SHORT).show();
            } else {
                // Create two variables to position the map
                LatLng lat1 = new LatLng(-27.596944, -48.548889);

                // if exists twitts with coordinates create the markers on the map and
                // save the first position to position on center of map
                // else show message "No twitts"

                if (locais != null) {
                    // Create thread o save images into class Localizacoes and show in the map
                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            URL url;
                            try {
                                // Iterate to save images
                                if (locais != null) {
                                    for (int i = 0; i < locais.size(); i++) {

                                        url = new URL(locais.get(i).getsURLImagem());
                                        locais.get(i).setBitmap(BitmapFactory.decodeStream(url.openConnection().getInputStream()));
                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            // Add markers on map with thread
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (locais != null) {
                                        for (int i = 0; i < locais.size(); i++) {
                                            if (locais.get(i).getBitmap() != null) {
                                                googleMap.addMarker(new MarkerOptions()
                                                        .position(new LatLng(locais.get(i).getnLatitude(), locais.get(i).getnLongitude()))
                                                        .title(locais.get(i).getsAutor())
                                                        .snippet(locais.get(i).getsDescricao())
                                                        .alpha(locais.get(i).getnId())
                                                        .icon(BitmapDescriptorFactory.fromBitmap(locais.get(i).getBitmap())));
                                            } else {
                                                googleMap.addMarker(new MarkerOptions()
                                                        .position(new LatLng(locais.get(i).getnLatitude(), locais.get(i).getnLongitude()))
                                                        .title(locais.get(i).getsAutor())
                                                        .snippet(locais.get(i).getsDescricao())
                                                        .alpha(locais.get(i).getnId()));
                                            }
                                        }
                                    }
                                }
                            });
                        }
                    });
                    thread.start();

                } else {
                    Toast.makeText(getBaseContext(), "No twitts with coordinates to show in map!", Toast.LENGTH_SHORT).show();
                }
                // Set type of map to satellite
                googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                // Move map to first coordinate
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lat1, 15));
                // Enable animation camera to Zoom In
                googleMap.animateCamera(CameraUpdateFactory.zoomIn());
                // Zoom the map to 10 value
                googleMap.animateCamera(CameraUpdateFactory.zoomTo(10), 5000, null);
                // Save the camera position to set in function
                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(lat1)               // Sets the center of the map to First coordinate
                        .zoom(11)                   // Sets the zoom to 11
                        .build();                   // Creates a CameraPosition from the builder
                // Move the map to camera position saved
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }
        }
    }

    // Open the TimeLineList and finish the current Activity
    public void AbreLista(View v) {
        finish();
        Intent it = new Intent(getBaseContext(), TimeLineActivity.class);
        startActivity(it);
    }

}
