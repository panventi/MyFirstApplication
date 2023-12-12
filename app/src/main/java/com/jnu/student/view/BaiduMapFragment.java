package com.jnu.student.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.jnu.student.R;
import com.tencent.tencentmap.mapsdk.maps.CameraUpdateFactory;
import com.tencent.tencentmap.mapsdk.maps.MapView;
import com.tencent.tencentmap.mapsdk.maps.TencentMap;
import com.tencent.tencentmap.mapsdk.maps.model.BitmapDescriptorFactory;
import com.tencent.tencentmap.mapsdk.maps.model.LatLng;
import com.tencent.tencentmap.mapsdk.maps.model.Marker;
import com.tencent.tencentmap.mapsdk.maps.model.MarkerOptions;

import java.util.ArrayList;

import com.jnu.student.data.ShopLocation;
import com.jnu.student.data.ShopDownLoader;

public class BaiduMapFragment extends Fragment {
    private MapView mapView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_baidu_map, container, false);
        mapView = view.findViewById(R.id.mapView);

        TencentMap tencentMap = mapView.getMap();

        LatLng point1 = new LatLng(22.255453, 113.54145);
        tencentMap.moveCamera(CameraUpdateFactory.newLatLng(point1));

        new Thread(() -> {
            String responseData=new ShopDownLoader().download("http://file.nidama.net/class/mobile_develop/data/bookstore2023.json");
            ArrayList<ShopLocation> shopLocationLocations = new ShopDownLoader().parseJsonObjects(responseData);
            requireActivity().runOnUiThread(() -> {
                TencentMap tencentMap1 = mapView.getMap();
                for (ShopLocation shopLocation : shopLocationLocations) {
                    LatLng point11 = new LatLng(shopLocation.getLatitude(), shopLocation.getLongitude());
                    MarkerOptions markerOptions = new MarkerOptions(point11)
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker2))
                            .title(shopLocation.getName());
                    Marker marker = tencentMap1.addMarker(markerOptions);
                    marker.showInfoWindow(); // 显示标记的信息窗口
                }
            });
        }).start();
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }
}