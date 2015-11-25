package com.hci4;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Button mapsButton;
    private String username;

    // hardcoded points for directions
    private static final LatLng UNI = new LatLng(55.8719, -4.2875);
    private static final LatLng Home = new LatLng(55.8691926, -4.2761092);

    // nextbike stands
    private static final LatLng stand_Central_Station = new LatLng(55.86078768935794, -4.258602261543274);
    private static final LatLng stand_Waterloo_Street = new LatLng(55.860767, -4.264083);
    private static final LatLng stand_Queen_Street_Railway_Station = new LatLng(55.861967, -4.252283);
    private static final LatLng stand_George_Square = new LatLng(55.86155, -4.2494);
    private static final LatLng stand_Merchant_Square = new LatLng(55.858167, -4.245483);
    private static final LatLng stand_St_George_Cross = new LatLng(55.871367, -4.269117);
    private static final LatLng stand_St_Enoch_Square = new LatLng(55.85682862583277, -4.2552924156188965);
    private static final LatLng stand_Glasgow_Cathedral = new LatLng(55.862883, -4.237467);
    private static final LatLng stand_Glasgow_Green = new LatLng(55.84945, -4.23375);
    private static final LatLng stand_Broomielaw = new LatLng(55.85665, -4.26375);
    private static final LatLng stand_SECC = new LatLng(55.85912884568613, -4.2867279052734375);
    private static final LatLng stand_Riverside_Museum = new LatLng(55.865683, -4.305367);
    private static final LatLng stand_Butanic_Gardens = new LatLng(55.87827800626413, -4.288487434387207);
    private static final LatLng stand_KelvinBridge_Subway = new LatLng(55.87505, -4.28035);
    private static final LatLng stand_Kelvingrove_Art_Gallery = new LatLng(55.868283, -4.28945);
    private static final LatLng stand_Charing_Cross_Railway_Station = new LatLng(55.8646, -4.27005);
    private static final LatLng stand_University_of_Glasgow_West = new LatLng(55.8728, -4.2927);
    private static final LatLng stand_Mitchell_Library = new LatLng(55.864788492724344, -4.272061586380005);
    private static final LatLng stand_University_of_Strathclyde_North = new LatLng(55.862983, -4.241);
    private static final LatLng stand_Bridge_Street_Subway = new LatLng(55.8525, -4.25885);
    private static final LatLng stand_Argyle_Street_Railway_Station = new LatLng(55.857567, -4.249717);
    private static final LatLng stand_Glasgow_Caledonian_University = new LatLng(55.865817, -4.251367);
    private static final LatLng stand_Emirates_Arena = new LatLng(55.846367, -4.208);
    private static final LatLng stand_Gallery_of_Modern_Art = new LatLng(55.859733, -4.251917);
    private static final LatLng stand_Trongate = new LatLng(55.856083, -4.24465);
    private static final LatLng stand_University_of_Strathclyde_South = new LatLng(55.860633, -4.2419);
    private static final LatLng stand_University_of_Glasgow_East = new LatLng(55.87165, -4.27795);
    private static final LatLng stand_Partick_Interchange = new LatLng(55.86995, -4.308433);
    private static final LatLng stand_Glasgow_Science_Centre = new LatLng(55.8585, -4.292517);
    private static final LatLng stand_Finnieston_Street = new LatLng(55.85871939242914, -4.281738996505737);
    private static final LatLng stand_Bellgrove_Railway_Station_North = new LatLng(55.858227, -4.224286);
    private static final LatLng stand_Barrowlands = new LatLng(55.855247, -4.235172);
    private static final LatLng stand_Bridgeton_Cross = new LatLng(55.848881, -4.226744);
    private static final LatLng stand_City_of_Glasgow_College_Riverside_Campus = new LatLng(55.850554, -4.248127);
    private static final LatLng stand_Paisley_Road_Toll = new LatLng(55.85378, -4.279158);
    private static final LatLng stand_Cessnock_Subway_Station = new LatLng(55.851964, -4.294308);
    private static final LatLng stand_Govan_Cross = new LatLng(55.863331, -4.311153);
    private static final LatLng stand_Eglinton_Toll = new LatLng(55.842696, -4.262674);
    private static final LatLng stand_Queens_Park_Railway_Station = new LatLng(55.834567,-4.265429);
    private static final LatLng stand_Queens_Park_West = new LatLng(55.835022,-4.272439);
    private static final LatLng stand_Buchanan_Street_Bus_Station = new LatLng(55.864986,-4.252261);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mMap = mapFragment.getMap();

        Intent intent = getIntent();
        username = intent.getStringExtra("username");


        FloatingActionButton mapsButton = (FloatingActionButton) findViewById(R.id.mapsButton);
        mapsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapsActivity.this, MainActivity.class);
                intent.putExtra("username", username);
                startActivity(intent);
                finish();
            }
        });

        mMap.addMarker(new MarkerOptions().position(stand_Central_Station).title("Central Station"));
        mMap.addMarker(new MarkerOptions().position(stand_Waterloo_Street).title("Waterlo Street"));
        mMap.addMarker(new MarkerOptions().position(stand_Queen_Street_Railway_Station).title("Queen Street Railway Station"));
        mMap.addMarker(new MarkerOptions().position(stand_George_Square).title("George Square"));
        mMap.addMarker(new MarkerOptions().position(stand_Merchant_Square).title("Merchant Square"));
        mMap.addMarker(new MarkerOptions().position(stand_St_George_Cross).title("St George`s Cross"));
        mMap.addMarker(new MarkerOptions().position(stand_St_Enoch_Square).title("St Enoch Square"));
        mMap.addMarker(new MarkerOptions().position(stand_Glasgow_Cathedral).title("Glasgow Cathedral"));
        mMap.addMarker(new MarkerOptions().position(stand_Glasgow_Green).title("Glasgow Green"));
        mMap.addMarker(new MarkerOptions().position(stand_Broomielaw).title("Broomielaw"));
        mMap.addMarker(new MarkerOptions().position(stand_SECC).title("S.E.C.C"));
        mMap.addMarker(new MarkerOptions().position(stand_Riverside_Museum).title("Riverside Museum"));
        mMap.addMarker(new MarkerOptions().position(stand_Butanic_Gardens).title("Butanic Gardens"));
        mMap.addMarker(new MarkerOptions().position(stand_KelvinBridge_Subway).title("Kelvinbridge Subway"));
        mMap.addMarker(new MarkerOptions().position(stand_Kelvingrove_Art_Gallery).title("Kelvingrove Art Gallery"));
        mMap.addMarker(new MarkerOptions().position(stand_Charing_Cross_Railway_Station).title("Charing Cross Railway Station"));
        mMap.addMarker(new MarkerOptions().position(stand_University_of_Glasgow_West).title("University of Glasgow (West)"));
        mMap.addMarker(new MarkerOptions().position(stand_Mitchell_Library).title("Mitchell Library"));
        mMap.addMarker(new MarkerOptions().position(stand_University_of_Strathclyde_North).title("University of Strathclyde (North)"));
        mMap.addMarker(new MarkerOptions().position(stand_Bridge_Street_Subway).title("Bridge Street Subway"));
        mMap.addMarker(new MarkerOptions().position(stand_Argyle_Street_Railway_Station).title("Argyle Street Railway Station"));
        mMap.addMarker(new MarkerOptions().position(stand_Glasgow_Caledonian_University).title("Glasgow Caledonian University"));
        mMap.addMarker(new MarkerOptions().position(stand_Emirates_Arena).title("Emirates Arena"));
        mMap.addMarker(new MarkerOptions().position(stand_Gallery_of_Modern_Art).title("Gallery of Modern Art"));
        mMap.addMarker(new MarkerOptions().position(stand_Trongate).title("Trongate"));
        mMap.addMarker(new MarkerOptions().position(stand_University_of_Strathclyde_South).title("University of Strathclyde (South)"));
        mMap.addMarker(new MarkerOptions().position(stand_University_of_Glasgow_East).title("University of Glasgow (East)"));
        mMap.addMarker(new MarkerOptions().position(stand_Partick_Interchange).title("Partick Interchange"));
        mMap.addMarker(new MarkerOptions().position(stand_Glasgow_Science_Centre).title("Glasgow Science Center"));
        mMap.addMarker(new MarkerOptions().position(stand_Finnieston_Street).title("Finnieston Street"));
        mMap.addMarker(new MarkerOptions().position(stand_Bellgrove_Railway_Station_North).title("Bellgrove Railway Station (North)"));
        mMap.addMarker(new MarkerOptions().position(stand_Barrowlands).title("Barrowlands"));
        mMap.addMarker(new MarkerOptions().position(stand_Bridgeton_Cross).title("Bridgeton Cross"));
        mMap.addMarker(new MarkerOptions().position(stand_City_of_Glasgow_College_Riverside_Campus).title("City of Glasgow College (Riverside Campus)"));
        mMap.addMarker(new MarkerOptions().position(stand_Paisley_Road_Toll).title("Paisley Road Toll"));
        mMap.addMarker(new MarkerOptions().position(stand_Cessnock_Subway_Station).title("Cessnock Subway Station"));
        mMap.addMarker(new MarkerOptions().position(stand_Govan_Cross).title("Govan Cross"));
        mMap.addMarker(new MarkerOptions().position(stand_Eglinton_Toll).title("Eglinton Toll"));
        mMap.addMarker(new MarkerOptions().position(stand_Queens_Park_Railway_Station).title("Queens Park Railway Station"));
        mMap.addMarker(new MarkerOptions().position(stand_Queens_Park_West).title("Queens Park West"));
        mMap.addMarker(new MarkerOptions().position(stand_Buchanan_Street_Bus_Station).title("Buchanan Street Bus Station"));

        String route = makeURL(UNI.latitude, UNI.longitude, Home.latitude,Home.longitude);
        Log.i("URL_LINK za serveRA ", route);
        new connectAsyncTask(route).execute();
        mMap.addMarker(new MarkerOptions().position(UNI).title("Uni"));
        mMap.addMarker(new MarkerOptions().position(Home).title("Home"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(UNI, 17));

        mMap.setMyLocationEnabled(true);
        Location myLocation = mMap.getMyLocation();

        mMap.setOnMyLocationChangeListener(myLocationChanged());


    }

    private GoogleMap.OnMyLocationChangeListener myLocationChanged(){
        return new GoogleMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
                System.out.println("MITKO: Location changed");
                System.out.println("MITKO: lat = " + location.getLatitude());
                System.out.println("MITKO: lng = " + location.getLongitude());
                System.out.println("MITKO:====================");
            }
        };
    }


    public String makeURL (double sourcelat, double sourcelog, double destlat, double destlog ){
        StringBuilder urlString = new StringBuilder();
        urlString.append("https://maps.googleapis.com/maps/api/directions/json");
        urlString.append("?origin=");// from
        urlString.append(Double.toString(sourcelat));
        urlString.append(",");
        urlString
                .append(Double.toString(sourcelog));
        urlString.append("&destination=");// to
        urlString
                .append(Double.toString( destlat));
        urlString.append(",");
        urlString.append(Double.toString( destlog));
        urlString.append("&sensor=false&mode=driving&alternatives=true");
        urlString.append("&key=AIzaSyB1ObQ929yZ9Jzjklt0r5H9JTFNuOFj630");
        return urlString.toString();
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
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

    }


    public void drawPath(String result) {

        try {
            //Transform the string into a json object
            final JSONObject json = new JSONObject(result);
            JSONArray routeArray = json.getJSONArray("routes");
            JSONObject routes = routeArray.getJSONObject(0);
            JSONObject overviewPolylines = routes.getJSONObject("overview_polyline");
            String encodedString = overviewPolylines.getString("points");
            List<LatLng> list = decodePoly(encodedString);
            Polyline line = mMap.addPolyline(new PolylineOptions()
                            .addAll(list)
                            .width(12)
                            .color(Color.parseColor("#05b1fb"))//Google maps blue color
                            .geodesic(true)
            );
           /*
           for(int z = 0; z<list.size()-1;z++){
                LatLng src= list.get(z);
                LatLng dest= list.get(z+1);
                Polyline line = mMap.addPolyline(new PolylineOptions()
                .add(new LatLng(src.latitude, src.longitude), new LatLng(dest.latitude,   dest.longitude))
                .width(2)
                .color(Color.BLUE).geodesic(true));
            }
           */
        }
        catch (JSONException e) {

        }
    }


    private List<LatLng> decodePoly(String encoded) {

        List<LatLng> poly = new ArrayList<LatLng>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng( (((double) lat / 1E5)),
                    (((double) lng / 1E5) ));
            poly.add(p);
        }

        return poly;
    }

    private class connectAsyncTask extends AsyncTask<Void, Void, String> {
        private ProgressDialog progressDialog;
        String url;
        connectAsyncTask(String urlPass){
            url = urlPass;
        }
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            progressDialog = new ProgressDialog(MapsActivity.this);
            progressDialog.setMessage("Fetching route, Please wait...");
            progressDialog.setIndeterminate(true);
            progressDialog.show();
        }
        @Override
        protected String doInBackground(Void... params) {
            JSONParser jParser = new JSONParser();
            String json = jParser.getJSONFromUrl(url);
            return json;
        }
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.hide();
            if(result!=null){
                //System.out.println(result);

                drawPath(result);

            }
        }
    }


    public void onLocationChanged(Location location) {
        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(), location.getLongitude())));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));

        //onLocationChanged(myLoc);

    }


}
