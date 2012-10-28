package vn.com.atm;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import vn.com.atm.database.UserFunctions;
import vn.com.atm.map.CustomItemizedOverlay;
import vn.com.atm.map.CustomOverlayItem;
import vn.com.atm.map.RoutePathOverlay;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

public class MapViewActivity extends MapActivity{
    MapView mapView;
	private MapController mapController;
	private GeoPoint geoPoint,geoPoint1;
	List<Overlay> mapOverlays;
	Drawable drawable, drawable2;
	RoutePathOverlay rp;
	private TextView tvXP, tvDich, tvThoiGian, tvDoDai;
	
	CustomItemizedOverlay<CustomOverlayItem> itemizedOverlay, itemizedOverlay1;
	@Override
    public void onCreate(Bundle savedInstanceState) {
		
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        
        mapView = (MapView)findViewById(R.id.mapView);
        mapView.setBuiltInZoomControls(true);
        mapOverlays = mapView.getOverlays();
        
       tvXP = (TextView)findViewById(R.id.tvXP);
       tvDich = (TextView)findViewById(R.id.tvDich);
       tvThoiGian = (TextView)findViewById(R.id.tvThoiGian);
       tvDoDai = (TextView)findViewById(R.id.tvDoDai);
       int mAtm = R.drawable.atm1;
       Bitmap bm = BitmapFactory.decodeResource(getResources(),mAtm);
     // first overlay
     		drawable = getResources().getDrawable(R.drawable.marker);
     		
     		
     		itemizedOverlay = new CustomItemizedOverlay<CustomOverlayItem>(drawable, mapView);
     		
        
        String[] position = {"21.785463","105.574788"};
        String[] position1 = {"21.185466","105.574795"};
        
        
        double latitude = Double.parseDouble(position[0]);
        double longtitude = Double.parseDouble(position[1]);
        double latitude1 = Double.parseDouble(position1[0]);
        double longtitude1 = Double.parseDouble(position1[1]);
        
        
        UserFunctions us = new UserFunctions();
     	
		JSONObject jsonObject = new JSONObject();
        jsonObject = us.getPoints(latitude, longtitude, latitude1, longtitude1);
     	JSONArray routeArray = null;
		try {
			routeArray = jsonObject.getJSONArray("routes");
		} catch (JSONException e) {
			e.printStackTrace();
		}
     	JSONObject routes = null;
     	JSONArray legs = null;
		try {
			routes = routeArray.getJSONObject(0);
			legs = routes.getJSONArray("legs");
		} catch (JSONException e) {
			e.printStackTrace();
		}
     	JSONObject overviewPolylines = null;
     	JSONObject line = null;
     	String addressStart = "";
     	String addressEnd = "";
     	JSONObject distance = null;
     	JSONObject time = null;
     	
		try {
			overviewPolylines = routes.getJSONObject("overview_polyline");
			line = legs.getJSONObject(0);
			addressStart = line.getString("start_address");
			addressEnd = line.getString("end_address");
			distance = line.getJSONObject("distance");
			time = line.getJSONObject("duration");
			
			tvXP.setText("Xuất phát: "+addressStart);
			tvDich.setText("Điểm cuối: " + addressEnd);
			tvDoDai.setText("Độ dài: " + distance.getString("text"));
			tvThoiGian.setText("Thời gian: " +time.getString("text"));
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
     	String encodedString = null;
		try {
			encodedString = overviewPolylines.getString("points");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
     	List<GeoPoint> pointToDraw = decodePoly(encodedString);
     	mapView.getOverlays().add(new RoutePathOverlay(pointToDraw));
        BitmapDrawable bd = (BitmapDrawable)drawable;
        int width = bd.getBitmap().getWidth();
        int height = bd.getBitmap().getHeight();
        
       
        geoPoint = new GeoPoint((int)(latitude*1E6)-width, (int)(longtitude*1E6)-height);
        CustomOverlayItem overlayItem = new CustomOverlayItem(geoPoint, "ATM Ngan hang TECHCOMBANK", 
				"(Dia chi: "+ addressStart, 
				bm);
        
        itemizedOverlay.addOverlay(overlayItem);
        mapOverlays.add(itemizedOverlay);
        
        
      /*  drawable2 = getResources().getDrawable(R.drawable.atm);
        
      
        String[] position1 = {"21.785466","105.574795"};
        
        double latitude1 = Double.parseDouble(position1[0]);
        double longtitude1 = Double.parseDouble(position1[1]);
        Bitmap bm1 = BitmapFactory.decodeResource(getResources(),mAtm1);
        
        geoPoint = new GeoPoint((int)(latitude1*1E6), (int)(longtitude1*1E6));
        CustomOverlayItem overlayItem1 = new CustomOverlayItem(geoPoint1, "ATM Ngan hang VIETCOMBANK", 
				"(Dia chi: 334 Nguyen Trai, Thanh Xuan Ha Noi)", 
				bm1);*/
        
     // second overlay
     		drawable2 = getResources().getDrawable(R.drawable.ic_launcher);
     		itemizedOverlay1 = new CustomItemizedOverlay<CustomOverlayItem>(drawable2, mapView);
     		
     		
            
     		GeoPoint point3 = new GeoPoint((int)(latitude1*1E6),(int)(longtitude1*1E6));
     		CustomOverlayItem overlayItem3 = new CustomOverlayItem(point3, "ATM Ngan hang VIETCOMBANK", 
     				"Dia chi:" +  addressEnd, null);
     		itemizedOverlay1.addOverlay(overlayItem3);
     		
     		/*GeoPoint point4 = new GeoPoint((int)(51.51738*1E6),(int)(-0.08186*1E6));
     		CustomOverlayItem overlayItem4 = new CustomOverlayItem(point4, "Mission: Impossible (1996)", 
     				"(Ethan & Jim cafe meeting)", 
     				"http://ia.media-imdb.com/images/M/MV5BMjAyNjk5Njk0MV5BMl5BanBnXkFtZTcwOTA4MjIyMQ@@._V1._SX40_CR0,0,40,54_.jpg");		
     		itemizedOverlay2.addOverlay(overlayItem4);*/
     		
     		mapOverlays.add(itemizedOverlay1);
       
     	

      
        //mapOverlays.add(itemizedOverlay1);
        
        
		mapController = mapView.getController();
		mapController.setZoom(11);
		mapController.animateTo(geoPoint);
	}
	private List<GeoPoint> decodePoly(String encoded) {

        List<GeoPoint> poly = new ArrayList<GeoPoint>();
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

            GeoPoint p = new GeoPoint((int) (((double) lat / 1E5) * 1E6), (int) (((double) lng / 1E5) * 1E6));
            poly.add(p);
        }

        return poly;
    }
	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}
	

}
