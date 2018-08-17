package com.example.nitinwithin.streetoo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;



public class customInfoWindowAdapter implements GoogleMap.InfoWindowAdapter{

    private final View mWindow;
    private Context mContext;

    public customInfoWindowAdapter(Context context) {
        this.mContext = context;
        mWindow = LayoutInflater.from(context).inflate(R.layout.custom_info_window,null);
    }

    private void renderWindowtext(Marker marker, View view)
    {
        String title = marker.getTitle();
        TextView infotitle = view.findViewById(R.id.infowindowtitle);

        if(!title.equals(""))
        {
            infotitle.setText(title);
        }
        String snippet = marker.getSnippet();
        TextView infotext = view.findViewById(R.id.infowindowtext);

        if(!snippet.equals(""))
        {
            infotext.setText(snippet);
        }
    }

    @Override
    public View getInfoWindow(Marker marker) {
        renderWindowtext(marker,mWindow);
        return mWindow;
    }

    @Override
    public View getInfoContents(Marker marker) {
        renderWindowtext(marker,mWindow);
        return mWindow;
    }
}
