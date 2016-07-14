package com.toponort.costasgis;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.toponort.asyncextensions.AsyncTaskDelegate;
import com.toponort.asyncextensions.IAsyncDelegate;
import com.toponort.asyncextensions.TaskResult;
import com.toponort.costasgismodel.entities.Ocupation;
import com.toponort.costasgismodel.service.ocupationservice.IOcupationService;
import com.toponort.costasgismodel.service.ocupationservice.OcupationServiceImpl;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class MapsActivity extends AppCompatActivity implements IAsyncDelegate, OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, GoogleMap.OnInfoWindowClickListener
{
    public final static String ID_MUNICIPIO = "idMunicipio";

    private GoogleMap mMap;
    protected GoogleApiClient mGoogleApiClient;
    protected Location mLastLocation;
    protected LatLng currentLatLng;
    protected final int REQUEST_LOCATION = 1;
    private String findOcupationsId, findOcupationId;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        IOcupationService ocupationService = new OcupationServiceImpl(MapsActivity.this);
        mapFragment.getMapAsync(this);
        this.buildGoogleApiClient();

        Intent intent = this.getIntent();
        long idMunicipio = intent.getLongExtra(ID_MUNICIPIO, 0);
        if (idMunicipio > 0)
        {
            findOcupationsId  = ocupationService.findOcupationsByMunicipioAsync(idMunicipio);
        }
        else
        {
            findOcupationsId  = ocupationService.findOcupationsByMunicipioAsync(86);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_mapsactivity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add_vertsign)
        {
            Intent intent = new Intent(MapsActivity.this, SearchMenu.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.action_change_mapview)
        {
            if (mMap.getMapType() == GoogleMap.MAP_TYPE_HYBRID)
            {
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            }
            else
            {
                mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
            }
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Check Permissions Now
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION);
        } else {
            mMap.setMyLocationEnabled(true);
            mMap.setOnInfoWindowClickListener(MapsActivity.this);
            mMap.animateCamera(CameraUpdateFactory.zoomTo(16));
        }

    }

    protected synchronized void buildGoogleApiClient()
    {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults)
    {
        if (requestCode == REQUEST_LOCATION) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // We can now safely use the API we requested access to
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                mMap.setMyLocationEnabled(true);
                mMap.setOnInfoWindowClickListener(MapsActivity.this);
                mMap.animateCamera(CameraUpdateFactory.zoomTo(16));
            }
            else
            {
                // Permission was denied or request was cancelled
            }
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle)
    {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {
            currentLatLng = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
            mMap.animateCamera(CameraUpdateFactory.newLatLng(currentLatLng));
        }
    }

    @Override
    public void onSuccess(TaskResult taskResult)
    {
        if (taskResult.getMethodIdentifier().equals(findOcupationsId))
        {
            List<Ocupation> lOcupation = taskResult.getMethodResult();
            LatLng latLong = null;

            for (Ocupation ocupation:lOcupation)
            {
                latLong = new LatLng(ocupation.getLatitud(), ocupation.getLongitud());
                BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE);
                if (ocupation.getEstado().equals(Ocupation.Estado.CADUCADA_DENEGADA))
                {
                     bitmapDescriptor = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE);
                }
                else if (ocupation.getEstado().equals(Ocupation.Estado.SIN_DATOS))
                {
                    bitmapDescriptor = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET);
                }
                else if (ocupation.getEstado().equals(Ocupation.Estado.EN_TRAMITE))
                {
                    bitmapDescriptor = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW);
                }
                else if (ocupation.getEstado().equals(Ocupation.Estado.OTORGADA))
                {
                    bitmapDescriptor = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN);
                }
                else if (ocupation.getEstado().equals(Ocupation.Estado.SIN_INICIAR))
                {
                    bitmapDescriptor = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED);
                }
                else if (ocupation.getEstado().equals(Ocupation.Estado.INDETERMINADO))
                {
                    bitmapDescriptor = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE);
                }

                mMap.addMarker(new MarkerOptions()
                        .position(latLong)
                        .title("Ocupación")
                        .snippet(ocupation.getDescripcion())
                        .icon(bitmapDescriptor));
            }
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLong, 14));
        }
        if (taskResult.getMethodIdentifier().equals(findOcupationId))
        {
            Ocupation ocupation = taskResult.getMethodResult();
            Marker marker;
            LatLng latLong = null;

                latLong = new LatLng(ocupation.getLatitud(), ocupation.getLongitud());
                BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED);

//                if (ocupation.getSituacion().equals(Ocupation.Estado.getEnum(Ocupation.Estado.EN_TRAMITE.toString())))
//                {
//                    bitmapDescriptor = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW);
//                }
                marker = mMap.addMarker(new MarkerOptions()
                        .position(latLong)
                        .title("Senal vertical")
                        .snippet("Situacion (PK): " + ocupation.getDescripcion())
                        .icon(bitmapDescriptor));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLong, 14));
//            _googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom()
        }
    }

    @Override
    public void onFail(TaskResult taskResult)
    {

    }

    @Override
    public void onConnectionSuspended(int i)
    {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult)
    {

    }

    @Override
    public void onInfoWindowClick(Marker marker)
    {

    }
}
