package com.mapbox.mapboxsdk;

import android.content.Context;
import android.graphics.drawable.Drawable;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.overlay.OverlayItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A Marker object is a visible representation of a point on a Map that has a geographical place.
 */
public class Marker extends OverlayItem {
    private static Logger logger = LoggerFactory.getLogger(Marker.class);
    private Context context;
    private Tooltip tooltip;
    private MapView mapView;
    private MapBoxResourceProxyImpl mResourceProxy;

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
        mResourceProxy = new MapBoxResourceProxyImpl(context);
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
        String urlString = "/com/mapbox/" + makiString+"182x";
        logger.info("urlString = '" + urlString + "'");
        Drawable drawable = mResourceProxy.getMapBoxDrawable(urlString);
        logger.info("drawable = '" + drawable + "'");
        this.setMarker(drawable);

/*
        String urlString = makiString+"182x";
        int id = context.getResources().getIdentifier(urlString, "drawable", context.getPackageName());
        this.setMarker(context.getResources().getDrawable(id));
*/
    }

    public Marker setIcon(Icon icon) {
        icon.setMarker(this);
        return this;
    }

    public void setTooltipVisible() {
        tooltip.setVisible(true);
        mapView.invalidate();
    }

    public void setTooltipInvisible() {
        tooltip.setVisible(false);
    }
}
