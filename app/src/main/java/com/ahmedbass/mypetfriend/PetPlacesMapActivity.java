package com.ahmedbass.mypetfriend;

import android.content.pm.PackageManager;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import static com.ahmedbass.mypetfriend.BackgroundWorker.serverDomain;
import static com.ahmedbass.mypetfriend.LauncherActivity.CURRENT_USER_INFO_PREFS;

public class PetPlacesMapActivity extends AppCompatActivity implements
        OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private static final int DEFAULT_ZOOM = 15;

    // The geographical location where the device is currently located.
    private Location mCurrentLocation;
    private GoogleMap mMap;
    protected GoogleApiClient mGoogleApiClient;

    private final LatLng mDefaultLocation = new LatLng(0, 0);
    private boolean mLocationPermissionGranted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_places_map);

        buildGoogleApiClient();
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addConnectionCallbacks(this)
                .addApi(LocationServices.API)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .build();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }

        if (mLocationPermissionGranted) {
            mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        }

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                }
            }
        }
        updateLocationUI();
    }
    @SuppressWarnings("MissingPermission")
    private void updateLocationUI() {
        if (mMap == null) {
            return;
        }

        if (mLocationPermissionGranted) {
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);
        } else {
            mMap.setMyLocationEnabled(false);
            mMap.getUiSettings().setMyLocationButtonEnabled(false);
            mCurrentLocation = null;
        }
    }
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult result) {
        Toast.makeText(this, "Connection failed: ConnectionResult.getErrorCode() = " + result.getErrorCode(), Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onConnectionSuspended(int cause) {
        // The connection to Google Play services was lost for some reason. We call connect() to attempt to re-establish the connection.
        Toast.makeText(this, "Connection suspended", Toast.LENGTH_SHORT).show();
        mGoogleApiClient.connect();
    }

    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;

        // Turn on the My Location layer and the related control on the map.
        updateLocationUI();

        if (mCurrentLocation != null) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude()), DEFAULT_ZOOM));

            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if(networkInfo != null && networkInfo.isConnected()) {
                BackgroundWorker backgroundWorker = new BackgroundWorker();
                backgroundWorker.execute(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());
            } else {
                Toast.makeText(getBaseContext(), "No Internet Connection. Please connect to the internet to get points of interest around you", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "No location detected. Make sure location is enabled on the device.", Toast.LENGTH_LONG).show();
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mDefaultLocation, DEFAULT_ZOOM));
            mMap.getUiSettings().setMyLocationButtonEnabled(false);
        }

        // Use a custom info window adapter to handle multiple lines of text in the info window contents.
        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            // Return null here, so that getInfoContents() is called next.
            public View getInfoWindow(Marker arg0) {
                return null;
            }
            @Override
            public View getInfoContents(Marker marker) {
                // Inflate the layouts for the info window, title and snippet.
                View infoWindow = getLayoutInflater().inflate(R.layout.map_custom_info_contents, null);

                TextView title = ((TextView) infoWindow.findViewById(R.id.title));
                title.setText(marker.getTitle());
                TextView snippet = ((TextView) infoWindow.findViewById(R.id.snippet));
                snippet.setText(marker.getSnippet());

                return infoWindow;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu. this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_filter_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                Toast.makeText(this, "TODO: popup dialog for search refinement", Toast.LENGTH_SHORT).show();
                return true;

            case android.R.id.home: // Respond to the action bar's Up/Home button
                supportFinishAfterTransition();
                return true;

            default:
                // If we got here, the user's action was not recognized. Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }

    //----------------------------------------------------------------------------------------------
    private class BackgroundWorker extends AsyncTask<Double, Void, String> {
        @Override
        protected String doInBackground(Double... params) {
            String update_and_get_user_location_url = serverDomain + "update_and_get_user_location.php";
            int _id = getSharedPreferences(CURRENT_USER_INFO_PREFS, MODE_PRIVATE).getInt(MyPetFriendContract.UsersEntry.USER_ID, -1);
            double latitude = params[0];
            double longitude = params[1];
            try {
                //set the http connection
                HttpURLConnection httpURLConnection = setHttpConnection(update_and_get_user_location_url);
                //post data to the server
                String postData = URLEncoder.encode("_id", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(_id), "UTF-8") +
                        "&" + URLEncoder.encode("latitude", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(latitude), "UTF-8") +
                        "&" + URLEncoder.encode("longitude", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(longitude), "UTF-8");
                postDataToServer(httpURLConnection, postData);
                //read response back from server
                return readServerResponse(httpURLConnection);
            } catch (IOException e) {
                return "Error: Cannot Connect To The Server";
            }
        }
        private HttpURLConnection setHttpConnection(String _url) throws IOException{
            URL url = new URL(_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setReadTimeout(10000);
            httpURLConnection.setConnectTimeout(10000);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            return httpURLConnection;
        }

        private void postDataToServer(HttpURLConnection httpURLConnection, String postData) throws IOException {
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            bufferedWriter.write(postData);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();
        }

        private String readServerResponse(HttpURLConnection httpURLConnection) throws IOException {
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
            String result = "";
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                result += line;
            }
            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            if (result.trim().equals("Error: Cannot Connect To The Server")) {
                Toast.makeText(PetPlacesMapActivity.this, result, Toast.LENGTH_SHORT).show();
            } else {
                ArrayList<PetCareProvider> listOfPetCareProviders = getPetCareProvidersFromJson(result);
                for (PetCareProvider petCareProvider : listOfPetCareProviders) {
                    if (!(petCareProvider.getLatitude() == 0 && petCareProvider.getLongitude() == 0)) {
                        String title = petCareProvider.getFirstName() + " " + petCareProvider.getLastName() +
                                (petCareProvider.getUserId() == getSharedPreferences(CURRENT_USER_INFO_PREFS, MODE_PRIVATE).getInt("userId", -1) ?
                                " (Me)" : "");
                        String snippet = "Provides Service: " + petCareProvider.getServicesProvided() +
                                (petCareProvider.getServicesProvidedFor().isEmpty() ? "" : "\nProvides Service For: " + petCareProvider.getServicesProvidedFor()) +
                                (petCareProvider.getEmail().isEmpty() ? "" : "\nEmail: " + petCareProvider.getEmail()) +
                                (petCareProvider.getPhone().isEmpty() ? "" : "\nPhone: " + petCareProvider.getPhone());

                        //set different color for each type of service provided
                        if (petCareProvider.getServicesProvided().contains("Pet Boarding")) {
                            mMap.addMarker(new MarkerOptions()
                                    .position(new LatLng(petCareProvider.getLatitude(), petCareProvider.getLongitude()))
                                    .title(title)
                                    .snippet(snippet)
                                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
                        } else if (petCareProvider.getServicesProvided().contains("Veterinary")) {
                            mMap.addMarker(new MarkerOptions()
                                    .position(new LatLng(petCareProvider.getLatitude(), petCareProvider.getLongitude()))
                                    .title(title)
                                    .snippet(snippet)
                                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                        } else if (petCareProvider.getServicesProvided().contains("Pet Training")) {
                            mMap.addMarker(new MarkerOptions()
                                    .position(new LatLng(petCareProvider.getLatitude(), petCareProvider.getLongitude()))
                                    .title(title)
                                    .snippet(snippet)
                                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                        } else if (petCareProvider.getServicesProvided().contains("Pet Grooming")) {
                            mMap.addMarker(new MarkerOptions()
                                    .position(new LatLng(petCareProvider.getLatitude(), petCareProvider.getLongitude()))
                                    .title(title)
                                    .snippet(snippet)
                                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA)));
                        } else if (petCareProvider.getServicesProvided().contains("Pet Walking") ||
                                petCareProvider.getServicesProvided().contains("Pet Sitting")) {
                            mMap.addMarker(new MarkerOptions()
                                    .position(new LatLng(petCareProvider.getLatitude(), petCareProvider.getLongitude()))
                                    .title(title)
                                    .snippet(snippet));
                        }
                    }
                }
            }
        }

        private ArrayList<PetCareProvider> getPetCareProvidersFromJson(String jsonResult){
            ArrayList<PetCareProvider> petCareProviders = new ArrayList<>();
            JSONArray jsonArray = null;
            try {
                JSONObject jsonObject = new JSONObject(jsonResult);
                jsonArray = jsonObject.getJSONArray("server_response");
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(PetPlacesMapActivity.this, "Error: Data Cannot Be Retrieved", Toast.LENGTH_SHORT).show();
            }
            int n = 0;
            for (int i = 0; i < (jsonArray != null ? jsonArray.length() : 0); i++) {
                try {
                    JSONObject innerJsonObject = jsonArray.getJSONObject(i);
                    petCareProviders.add(new PetCareProvider(innerJsonObject.getInt("_id"), innerJsonObject.getLong("createDate"),
                            innerJsonObject.getString("userType"), innerJsonObject.getString("firstName"), innerJsonObject.getString("lastName"),
                            innerJsonObject.getString("email"), innerJsonObject.getString("password"), innerJsonObject.getLong("birthDate"),
                            innerJsonObject.getString("gender"), innerJsonObject.getString("country"), innerJsonObject.getString("city"),
                            innerJsonObject.getString("phone"), innerJsonObject.getString("profilePhoto"),
                            innerJsonObject.getString("profileDescription"), innerJsonObject.getString("availability"),
                            innerJsonObject.getString("yearsOfExperience"), innerJsonObject.getString("averageRatePerHour"),
                            innerJsonObject.getString("serviceProvidedFor"), innerJsonObject.getString("serviceProvided"),
                            innerJsonObject.getDouble("latitude"), innerJsonObject.getDouble("longitude")));

                } catch (JSONException e) {
                    e.printStackTrace();
                    n = 1;
                }
            }
            if (n == 1) {
                Toast.makeText(PetPlacesMapActivity.this, "Some Data Could Not Be Retrieved", Toast.LENGTH_SHORT).show();
            }
            return petCareProviders;
        }
    }

}