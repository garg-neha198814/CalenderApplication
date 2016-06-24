package com.shivam.testing.calenderapplication.ui.fragments;


import android.graphics.BitmapFactory;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.shivam.testing.calenderapplication.R;
import com.shivam.testing.calenderapplication.ui.activities.HomeActivity;

import java.io.IOException;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class TrackerFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnCameraChangeListener, LocationListener,
        GoogleMap.OnInfoWindowClickListener,
        GoogleMap.OnMapLongClickListener,
        GoogleMap.OnMapClickListener,
        GoogleMap.OnMarkerClickListener {
    Location location;
    double latitude, longitude;
    private PolylineOptions mPolylineOptions;
    private GoogleMap mMap;

    private SupportMapFragment fragment;

    Marker marker;
    private LocationManager locationManager;
    private boolean isGPSEnabled = false, isNetworkEnabled = false, canGetLocation = false;
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 1; // 1 meters
    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000 * 1 * 1; //
    ArrayList<LatLng> route;


    public Location getLocation() {
        try {
            locationManager = (LocationManager)
                    getActivity().getSystemService(getActivity().LOCATION_SERVICE);

// getting GPS status
            isGPSEnabled = locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);

// getting network status
            isNetworkEnabled = locationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isGPSEnabled && !isNetworkEnabled) {
// no network provider is enabled
            } else {
                this.canGetLocation = true;
// First get location from Network Provider
                if (isNetworkEnabled) {
                    locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    Log.d("Network", "Network");
                    if (locationManager != null) {
                        location = locationManager
                                .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                            //txt1.setText(latitude + ">>>>" + longitude);
                            mMap.moveCamera(CameraUpdateFactory.newCameraPosition(
                                    CameraPosition.fromLatLngZoom(new LatLng(latitude, longitude), 18)));
                            System.out.println(" network latitude>>>>" + latitude + "network longitude>>>" + longitude);
                        }
                    }
                }
// if GPS Enabled get lat/long using GPS Services
                if (isGPSEnabled) {
                    if (location == null) {
                        locationManager.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER,
                                MIN_TIME_BW_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                        Log.d("GPS Enabled", "GPS Enabled");
                        if (locationManager != null) {
                            location = locationManager
                                    .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (location != null) {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                                //txt2.setText(latitude + ">>>>" + longitude);
                                System.out.println("gps latitude>>>>" + latitude + "gps longitude>>>" + longitude);
                                mMap.moveCamera(CameraUpdateFactory.newCameraPosition(
                                        CameraPosition.fromLatLngZoom(new LatLng(latitude, longitude), 18)));
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return location;
    }

    @Override
    public void onLocationChanged(Location location) {
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(
                CameraPosition.fromLatLngZoom(new LatLng(latitude, longitude), 18)));
        if (marker != null) {
            marker.remove();
        }
        marker = mMap.addMarker(new MarkerOptions().position(
                new LatLng(location.getLatitude(), location.getLongitude())));
        // updatePolyline(location);
    }


    private void initListeners() {
        mMap.setOnMarkerClickListener(this);
        mMap.setOnMapLongClickListener(this);
        mMap.setOnInfoWindowClickListener(this);
        mMap.setOnMapClickListener(this);
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {

    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        marker.hideInfoWindow();
    }

    @Override
    public void onMapClick(LatLng latLng) {
        MarkerOptions options = new MarkerOptions().position(latLng);
        options.title("address" + getAddressFromLatLng(latLng));

        options.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher));
        mMap.addMarker(options);
    }

    @Override
    public void onMapLongClick(LatLng latLng) {

    }

    public void goelist() {
        route.add(new LatLng(30.6745045, 76.85141539999999));
        route.add(new LatLng(30.6748846, 76.85099699999999));
        route.add(new LatLng(30.6719417, 76.85343449999999));
        route.add(new LatLng(30.6710088, 76.85456959999999));
        route.add(new LatLng(30.67294, 76.8566802));
        route.add(new LatLng(30.6717574, 76.8581269));
        route.add(new LatLng(30.6704722, 76.85719139999999));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        route = new ArrayList<>();
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mPolylineOptions = new PolylineOptions();
        mPolylineOptions.color(R.color.colorAccent).width(10);
        initListeners();
        goelist();
        getLocation();
        updatePolyline();
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        marker.showInfoWindow();
        return false;
    }

    private String getAddressFromLatLng(LatLng latLng) {
        Geocoder geocoder = new Geocoder(getActivity());

        String address = "";
        try {
            address = geocoder
                    .getFromLocation(latLng.latitude, latLng.longitude, 1)
                    .get(0).getAddressLine(0);
        } catch (IOException e) {
        }

        return address;
    }

    private void drawPolygon(LatLng startingLocation) {
        LatLng point2 = new LatLng(startingLocation.latitude + .001,
                startingLocation.longitude);
        LatLng point3 = new LatLng(startingLocation.latitude,
                startingLocation.longitude + .001);

        PolygonOptions options = new PolygonOptions();
        options.add(startingLocation, point2, point3);

        options.fillColor(R.color.colorAccent);
        options.strokeColor(R.color.colorPrimaryDark);
        options.strokeWidth(10);

        mMap.addPolygon(options);
    }

    private void drawCircle(LatLng location) {
        CircleOptions options = new CircleOptions();
        options.center(location);
        //Radius in meters
        options.radius(10);
        options.fillColor(R.color.colorAccent);
        options.strokeColor(R.color.colorPrimaryDark);
        options.strokeWidth(10);
        mMap.addCircle(options);
    }

    private void drawOverlay(LatLng location, int width, int height) {
        GroundOverlayOptions options = new GroundOverlayOptions();
        options.position(location, width, height);
        options.image(BitmapDescriptorFactory
                .fromBitmap(BitmapFactory
                        .decodeResource(getResources(),
                                R.mipmap.ic_launcher)));
        mMap.addGroundOverlay(options);
    }

    private void updatePolyline() {
      /*  mMap.clear();
        mMap.addPolyline(mPolylineOptions.add( new LatLng(location.getLatitude(), location.getLongitude())));*/
        for (int i = 0; i < route.size(); i++) {
            mMap.addPolyline(mPolylineOptions.add(route.get(i)));
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        System.out.println("on destroy view called>>>>>>>>>>>>");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("on destroy called>>>");
    }

    public TrackerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_tracker, null, false);
        ((HomeActivity) getActivity()).setActionBarTitle("Tracker");
        /*initilizeMap();*/
       /* AppCompatActivity appCompatActivity= (AppCompatActivity) getActivity();*/

        //   SupportMapFragment mapFragment = (SupportMapFragment)appCompatActivity.getSupportFragmentManager().findFragmentById(R.id.map);
        //  mapFragment.getMapAsync(this);


        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        FragmentManager fm = getChildFragmentManager();
        fragment = (SupportMapFragment) fm.findFragmentById(R.id.map);
        if (fragment == null) {
            fragment = SupportMapFragment.newInstance();
            fm.beginTransaction().replace(R.id.map, fragment).commit();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mMap == null) {
            mMap = fragment.getMap();
           /* route = new ArrayList<>();

            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            mPolylineOptions = new PolylineOptions();
            mPolylineOptions.color(R.color.colorAccent).width(10);
            initListeners();
            goelist();
            getLocation();
            updatePolyline();*/
            mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)));
        }
    }
   /* private void initilizeMap()
    {
        mSupportMapFragment = (SupportMapFragment) getFragmentManager().findFragmentById(R.id.map);
        if (mSupportMapFragment == null) {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            mSupportMapFragment = SupportMapFragment.newInstance();
            fragmentTransaction.replace(R.id.map, mSupportMapFragment).commit();
        }
        if (mSupportMapFragment != null)
        {
            mMap = mSupportMapFragment.getMap();
            if (mMap != null)
                mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener()
                {
                    @Override
                    public void onMapClick(LatLng point)
                    {
                        System.out.println("click called");
                        //TODO: your onclick stuffs
                    }
                });
        }
    }*/
}
