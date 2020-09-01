package com.jewelleryauction.jewelleryauctionhouse;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.jewelleryauction.jewelleryauctionhouse.model.LatitudeLongtitude;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends FragmentActivity implements OnMapReadyCallback {
private GoogleMap mMap;
private AutoCompleteTextView etCity;
private Button btnsearch;
private List<LatitudeLongtitude>latitudeLongtitudeList;
Marker markerName;
CameraUpdate center,zoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        SupportMapFragment mapFragment=(SupportMapFragment)getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        etCity=findViewById(R.id.etCity);
        btnsearch=findViewById(R.id.btnSearch);
        fillArrayListandSetAdapter();
        btnsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(etCity.getText().toString())){
                    etCity.setError("Please Enter a Place");
                    return;
                }
                int position=SearchArrayList(etCity.getText().toString());
                if(position>-1)
                    loadMap(position);
                else
                    Toast.makeText(SearchActivity.this, "Location not Found by Name: "+etCity.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap=googleMap;
        center= CameraUpdateFactory.newLatLng(new LatLng(27.706195,85.3300396));
        zoom=CameraUpdateFactory.zoomTo(15);
        mMap.moveCamera(center);
        mMap.animateCamera(zoom);
        mMap.getUiSettings().setZoomControlsEnabled(true);

    }

    private void loadMap(int position){
        if (markerName!=null){
            markerName.remove();
        }
        double latitude=latitudeLongtitudeList.get(position).getLat();
        double longtitude=latitudeLongtitudeList.get(position).getLon();
        String marker=latitudeLongtitudeList.get(position).getMarker();
        center=CameraUpdateFactory.newLatLng(new LatLng(latitude,longtitude));
        zoom=CameraUpdateFactory.zoomTo(17);
        markerName=mMap.addMarker(new MarkerOptions().position(new LatLng(latitude,longtitude)).title(marker));
        mMap.moveCamera(center);
        mMap.animateCamera(zoom);
        mMap.getUiSettings().setZoomControlsEnabled(true);

    }
    private void fillArrayListandSetAdapter(){
        latitudeLongtitudeList=new ArrayList<>();
        latitudeLongtitudeList.add(new LatitudeLongtitude(27.706195,85.3300396,"Softwarica College"));
        latitudeLongtitudeList.add(new LatitudeLongtitude(27.7079284,85.330543,"Galaxy Public School"));
        latitudeLongtitudeList.add(new LatitudeLongtitude(27.7392772,85.3877113,"Gokarneshwor Temple"));
        latitudeLongtitudeList.add(new LatitudeLongtitude(27.7373525,85.3854934,"Vidya Byayam English High School"));
        latitudeLongtitudeList.add(new LatitudeLongtitude(27.7282092,85.3331098,"Ashirbad Boarding School"));
        latitudeLongtitudeList.add(new LatitudeLongtitude(27.6887032,85.3347819,"Nmb Bank"));

        String[] data=new String[latitudeLongtitudeList.size()];
        for (int i=0;i<data.length;i++){
            data[i]=latitudeLongtitudeList.get(i).getMarker();
        }
        ArrayAdapter<String>adapter=new ArrayAdapter<>(SearchActivity.this,android.R.layout.simple_list_item_1,data);
        etCity.setAdapter(adapter);
        etCity.setThreshold(1);
    }
    public int SearchArrayList(String name){
        for (int i=0;i<latitudeLongtitudeList.size();i++){
            if(latitudeLongtitudeList.get(i).getMarker().contains(name)){
                return i;

            }
        }
        return -1;
    }

}
