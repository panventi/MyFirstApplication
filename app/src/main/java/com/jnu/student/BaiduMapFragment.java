package com.jnu.student;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.tencent.tencentmap.mapsdk.maps.CameraUpdate;
import com.tencent.tencentmap.mapsdk.maps.CameraUpdateFactory;
import com.tencent.tencentmap.mapsdk.maps.MapView;
import com.tencent.tencentmap.mapsdk.maps.TencentMap;
import com.tencent.tencentmap.mapsdk.maps.model.BitmapDescriptorFactory;
import com.tencent.tencentmap.mapsdk.maps.model.CameraPosition;
import com.tencent.tencentmap.mapsdk.maps.model.LatLng;
import com.tencent.tencentmap.mapsdk.maps.model.Marker;
import com.tencent.tencentmap.mapsdk.maps.model.MarkerOptions;


public class BaiduMapFragment extends Fragment {

    private MapView mapView;
    private TencentMap tencentMap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_baidu_map, container, false);
        mapView = view.findViewById(R.id.mapView);
        tencentMap = mapView.getMap();
        // 设置起始位置为暨南大学珠海校区
        LatLng jinanUniversity = new LatLng(22.251, 113.5345); // 暨南大学珠海校区的经纬度
        // 创建CameraPosition.Builder对象，并设置起始位置和初始显示层级
        CameraPosition.Builder builder = new CameraPosition.Builder();
        builder.target(jinanUniversity); // 设置起始位置
        builder.zoom(17); // 设置初始显示层级，可以根据需要调整
        // 移动地图到指定位置并设置初始显示层级
        tencentMap.moveCamera(CameraUpdateFactory.newCameraPosition(builder.build()));

        MarkerOptions markerOptions = new MarkerOptions(jinanUniversity)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.maker2))
                .title("暨南大学珠海校区");

        Marker marker = tencentMap.addMarker(markerOptions);
        marker.showInfoWindow(); // 显示标记的信息窗口

        tencentMap.setOnMarkerClickListener(new TencentMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Toast.makeText(getContext(), "点击了珠海暨南大学的标记", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        return view;
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
