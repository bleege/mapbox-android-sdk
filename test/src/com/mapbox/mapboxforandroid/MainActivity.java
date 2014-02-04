package com.mapbox.mapboxforandroid;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import com.mapbox.mapboxsdk.MapView;
import com.mapbox.mapboxsdk.Marker;
import com.mapbox.mapboxsdk.Icon;
import com.mapbox.mapboxsdk.util.LatLng;
import com.mapbox.mapboxsdk.api.IMapController;
import com.mapbox.mapboxsdk.views.overlay.PathOverlay;
import com.mapbox.mapboxsdk.views.overlay.mylocation.MyLocationNewOverlay;

public class MainActivity extends Activity {
	private IMapController mapController;
	private LatLng startingPoint = new LatLng(51f, 0f);
	private MapView mv;
	private MyLocationNewOverlay myLocationOverlay;
    private Paint paint;
    private String satellite = "brunosan.map-cyglrrfu";
    private String street = "examples.map-vyofok3q";
    private String terrain = "examples.map-zgrqqx0w";
    private String currentLayer = "terrain";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        mv = (MapView)findViewById(R.id.mapview);
        mapController = mv.getController();
        mapController.setCenter(startingPoint);
        mapController.setZoom(4);
        mv.parseFromGeoJSON("https://gist.github.com/fdansv/8541618/raw/09da8aef983c8ffeb814d0a1baa8ecf563555b5d/geojsonpointtest");
        setButtonListeners();
        Marker m = new Marker(mv, "Hello", "World", new LatLng(0f, 0f));
        m.setIcon(new Icon(Icon.Size.l, "bus", "000"));
        mv.addMarker(m);

        mv.setOnTilesLoadedListener(new MapView.TilesLoadedListener() {
            @Override
            public boolean onTilesLoaded() {
                System.out.println("All tiles have been loaded");
                return false;
            }
        });

        mv.setOnTileLoadedListener(new MapView.TileLoadedListener(){
            @Override
            public Drawable onTileLoaded(Drawable d){
                d.setColorFilter( 0xffff0000, PorterDuff.Mode.MULTIPLY);
                return d;
            }
        });
        mv.setVisibility(View.VISIBLE);
    }

    private void setButtonListeners() {
        Button satBut = changeButtonTypeface((Button)findViewById(R.id.satbut));
        satBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!currentLayer.equals("satellite")) {
                    replaceMapView(satellite);
                    currentLayer = "satellite";
                }
            }
        });
        Button terBut = changeButtonTypeface((Button)findViewById(R.id.terbut));
        terBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!currentLayer.equals("terrain"))  {
                    replaceMapView(terrain);
                    currentLayer = "terrain";
                }
            }
        });
        Button strBut = changeButtonTypeface((Button)findViewById(R.id.strbut));
        strBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!currentLayer.equals("street")) {
                    replaceMapView(street);
                    currentLayer = "street";
                }
            }
        });
    }

    protected void replaceMapView(String layer) {
        mv.switchToLayer(layer);

    }

    private void addLocationOverlay() {
        // Adds an icon that shows location
        myLocationOverlay = new MyLocationNewOverlay(this, mv);
        myLocationOverlay.enableMyLocation();
        myLocationOverlay.setDrawAccuracyEnabled(true);
        mv.getOverlays().add(myLocationOverlay);
    }
    private void addLine() {
        // Configures a line
        PathOverlay po = new PathOverlay(Color.RED, this);
        Paint linePaint = new Paint();
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setColor(Color.BLUE);
        linePaint.setStrokeWidth(5);
        po.setPaint(linePaint);
        po.addPoint(startingPoint);
        po.addPoint(new LatLng(51.7, 0.3));
        po.addPoint(new LatLng(51.2, 0));

        // Adds line and marker to the overlay
        mv.getOverlays().add(po);
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		
		return true;
	}
    private Button changeButtonTypeface(Button button){
        //Typeface tf = Typeface.createFromAsset(this.getAssets(), "fonts/semibold.ttf");
        //button.setTypeface(tf);
        return button;
    }


}
