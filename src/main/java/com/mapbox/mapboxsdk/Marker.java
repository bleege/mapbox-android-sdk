package com.mapbox.mapboxsdk;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.overlay.OverlayItem;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * A Marker object is a visible representation of a point on a Map that has a geographical place.
 */
public class Marker extends OverlayItem {
    private Context context;
    private Tooltip tooltip;
    private MapView mapView;

    /**
     * Initialize a new marker object
     *
     * @param aTitle the title of the marker, in a potential tooltip
     * @param aDescription the description of the marker, in a tooltip
     * @param aGeoPoint the location of the marker
     */
    public Marker(String aTitle, String aDescription, GeoPoint aGeoPoint) {
        this(null, aTitle, aDescription, aGeoPoint);
    }

    /**
     * Initialize a new marker object, adding it to a MapView and attaching a tooltip
     * @param mv a mapview
     * @param aTitle the title of the marker, in a potential tooltip
     * @param aDescription the description of the marker, in a tooltip
     * @param aGeoPoint the location of the marker
     */
    public Marker(MapView mv, String aTitle, String aDescription, GeoPoint aGeoPoint) {
        super(aTitle, aDescription, aGeoPoint);
        context = mv.getContext();
        mapView = mv;
        fromMaki("markerstroked");
        attachTooltip();
    }

    private void attachTooltip() {
        tooltip = new Tooltip(context, this, this.getTitle());
        mapView.getOverlays().add(tooltip);
        mapView.invalidate();
    }

    /**
     * Set this marker's icon to a marker from the Maki icon set.
     *
     * @param makiString the name of a Maki icon symbol
     */
    public void fromMaki(String makiString) {
        String urlString = makiString+"182x";
        int id = context.getResources().getIdentifier(urlString, "drawable", context.getPackageName());
        this.setMarker(context.getResources().getDrawable(id));
    }

    public void setTooltipVisible() {
        tooltip.setVisible(true);
        mapView.invalidate();
    }

    public void setTooltipInvisible() {
        tooltip.setVisible(false);
    }

    class BitmapLoader extends AsyncTask<String, Void,Bitmap> {

        @Override
        protected Bitmap doInBackground(String... src) {
            try {
                URL url = new URL(src[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);
                return myBitmap;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        @Override
        protected void onPostExecute(Bitmap bitmap){
            bitmap.setDensity(120);
            Marker.this.setMarker(new BitmapDrawable(bitmap));
        }
    }
}
