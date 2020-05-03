package module3;


import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Comparator;
import java.util.List;

//Processing library
import processing.core.PApplet;
import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import de.fhpotsdam.unfolding.providers.AbstractMapProvider;
import de.fhpotsdam.unfolding.providers.Google;
import de.fhpotsdam.unfolding.providers.MBTilesMapProvider;
import de.fhpotsdam.unfolding.utils.MapUtils;

//Parsing library
import parsing.ParseFeed;


public class EarthquakeCityMap extends PApplet {

	
	private static final long serialVersionUID = 1L;


	private static final boolean offline = true;
	
	// Less than this threshold is a light earthquake
	public static final float THRESHOLD_MODERATE = 5;
	// Less than this threshold is a minor earthquake
	public static final float THRESHOLD_LIGHT = 4;

	public static String mbTilesString = "blankLight-1-3.mbtiles";
	
		private UnfoldingMap map;
	
	//feed with magnitude 2.5+ Earthquakes
	private String earthquakesURL = "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/2.5_week.atom";

	
	public void setup() {
		size(950, 600, OPENGL);

		if (offline) {
		    map = new UnfoldingMap(this, 200, 50, 700, 500, new MBTilesMapProvider(mbTilesString));
		    earthquakesURL = "2.5_week.atom"; 	// Same feed, saved Aug 7, 2015, for working offline
		}
		else {
			map = new UnfoldingMap(this, 200, 50, 700, 500, new Google.GoogleMapProvider());
			// IF YOU WANT TO TEST WITH A LOCAL FILE, uncomment the next line
			//earthquakesURL = "2.5_week.atom";
		}
		
	    map.zoomToLevel(2);
	    MapUtils.createDefaultEventDispatcher(this, map);	
			
	    // The List you will populate with new SimplePointMarkers
	    List<Marker> markers = new ArrayList<Marker>();


	    List<PointFeature> earthquakes = ParseFeed.parseEarthquake(this, earthquakesURL);
	    for(PointFeature pf : earthquakes) {
	    	SimplePointMarker mak = createMarker(pf);
	    	markers.add(mak);
	    }
	    map.addMarkers(markers);
	    fill(216, 196, 196);
	    quad(10, 60, 180, 60, 180, 500, 10, 500);
	    fill(0, 0, 0);
	    textSize(26);
	    text("Earthquakes", 20, 90); 
	    textSize(14);
	    text("<4 Magnitude", 60, 180); 
	    textSize(14);
	    text("4+ Magnitude", 60, 240); 
	    textSize(14);
	    text("5+ Magnitude", 60, 300); 
	    fill(0, 255, 0);
	    ellipse(30.0f, 175.0f, 10.0f, 10.0f);
	    fill(255, 255, 0);
	    ellipse(30.0f, 235.0f, 15.0f, 15.0f);
	    fill(255, 0, 0);
	    ellipse(30.0f, 295.0f, 20.0f, 20.0f);

	}
		
	/* createMarker: A suggested helper method that takes in an earthquake 
	 * feature and returns a SimplePointMarker for that earthquake
	 * 
	 * In step 3 You can use this method as-is.  Call it from a loop in the 
	 * setp method.  
	 * 
	 * TODO (Step 4): Add code to this method so that it adds the proper 
	 * styling to each marker based on the magnitude of the earthquake.  
	*/
	private SimplePointMarker createMarker(PointFeature feature)
	{  
		
		SimplePointMarker marker = new SimplePointMarker(feature.getLocation());
		
		Object magObj = feature.getProperty("magnitude");
		float mag = Float.parseFloat(magObj.toString());
		
		// Here is an example of how to use Processing's color method to generate 
	    // an int that represents the color yellow.  
	    int red = color(255, 0, 0);
	    int yellow = color(255, 255, 0);
	    int green = color(0, 255, 0);
		
	    if(mag <= THRESHOLD_LIGHT) {
	    	marker.setColor(green);
	    	}
	    if(mag > THRESHOLD_LIGHT && mag <= THRESHOLD_MODERATE){
	    	marker.setColor(yellow);
	    }
	    if(mag > THRESHOLD_MODERATE) {
	    	marker.setColor(red);
	    }
	    
	    // Finally return the marker
	    return marker;
	}
	
	public void draw() {
	    map.draw();
	    addKey();
	}


	// helper method to draw key in GUI
	// TODO: Implement this method to draw the key
	private void addKey() 
	{	
		// Remember you can use Processing's graphics methods here
	
	}
}
