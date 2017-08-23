package module3;

//Java utilities libraries
import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Comparator;
import java.util.List;

//Processing library
import processing.core.PApplet;

//Unfolding libraries
import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import de.fhpotsdam.unfolding.providers.Google;
import de.fhpotsdam.unfolding.providers.MBTilesMapProvider;
import de.fhpotsdam.unfolding.utils.MapUtils;

//Parsing library
import parsing.ParseFeed;

/** EarthquakeCityMap
 * An application with an interactive map displaying earthquake data.
 * Author: UC San Diego Intermediate Software Development MOOC team
 * @author Krzysztof Jurecki
 * Date: July 25, 2017
 * */
public class EarthquakeCityMapVer_2 extends PApplet {

	// You can ignore this.  It's to keep eclipse from generating a warning.
	private static final long serialVersionUID = 1L;

	// IF YOU ARE WORKING OFFLINE, change the value of this variable to true
	private static final boolean offline = false;
	
	// Less than this threshold is a light earthquake
	public static final float THRESHOLD_MODERATE = 5;
	// Less than this threshold is a minor earthquake
	public static final float THRESHOLD_LIGHT = 4;

	/** This is where to find the local tiles, for working without an Internet connection */
	public static String mbTilesString = "blankLight-1-3.mbtiles";
	
	// The map
	private UnfoldingMap map;
	
	//feed with magnitude 2.5+ Earthquakes
	private String earthquakesURL = "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/2.5_week.atom";

	
	public void setup() {
		size(950, 600, OPENGL);

		if (offline) {
		    map = new UnfoldingMap(this, 200, 50, 700, 500, new MBTilesMapProvider(mbTilesString));
		    //earthquakesURL = "2.5_week.atom"; 	// Same feed, saved Aug 7, 2015, for working offline
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

	    //Use provided parser to collect properties for each earthquake
	    //PointFeatures have a getLocation method
	    
	    //Blow: new list that adds markers from URL data feed
	    List<PointFeature> earthquakes = ParseFeed.parseEarthquake(this, earthquakesURL);
    
	    // These print statements show you (1) all of the relevant properties 
	    // in the features, and (2) how to get one property and use it
	    if (earthquakes.size() > 0) {
	    //	PointFeature f = earthquakes.get(0);
	    //	System.out.println(f.getProperties());
	    //	Object magObj = f.getProperty("magnitude");
	    //	float mag = Float.parseFloat(magObj.toString());
	    	
	    	//for (PointFeature f : earthquakes){
	    	//	markers.add(createMarker(f));
	    	//}
	    	
	    	// PointFeatures also have a getLocation method
	    
	    for (PointFeature i : earthquakes){
	    	SimplePointMarker marker = createMarker(i);
	    	map.addMarker(marker);
	    	}
	    }

	    // Here is an example of how to use Processing's color method to generate 
	    // an int that represents the color yellow.  
	    
	    //int yellow = color(255, 255, 0);
	    //int gray = color(150, 150, 150);
	    
	    //*for (Marker mk : markers){
	    //	if ((int) mk.getProperty("year") > 2000 ) {
	    //		mk.setColor(yellow);
	    //	}
	    //	else {
	    //		mk.setColor(gray);
	    //	}	
	    //}
	    
	    //TODO: Add code here as appropriate
	}
		
	// A suggested helper method that takes in an earthquake feature and 
	// returns a SimplePointMarker for that earthquake
	// TODO: Implement this method and call it from setUp, if it helps
	private SimplePointMarker createMarker(PointFeature feature){
	// finish implementing and use this method, if it helps.
		
	    SimplePointMarker myMarker = new SimplePointMarker(feature.getLocation(), feature.getProperties());
	    	    	
	    int yellow = color(255, 255, 0);
		int gray = color(150, 150, 150);
		int red = color(255, 0, 0);
		int blue = color(0, 128, 255);
	    	
	    Object magObj = myMarker.getProperty("magnitude");
	    float mag = Float.parseFloat(magObj.toString());
	    	
	    if (mag < 4.0){
	    	myMarker.setColor(blue);
	    	myMarker.setRadius(5);
	    }
	    else if (mag >= 4.0f && mag <= 4.9f){
	    	myMarker.setColor(yellow);
	    	myMarker.setRadius(10);
	    }
	    else {
	    	myMarker.setColor(red);
	    	myMarker.setRadius(15);
	    }
	    //map.addMarker(myMarker);
	    return myMarker;
	    }
	
	public void draw() {
	    background(10);
	    map.draw();
	    addKey();
	}


	// helper method to draw key in GUI
	// TODO: Implement this method to draw the key
	private void addKey() 
	{	
		// Remember you can use Processing's graphics methods here
		fill (255, 255, 204);
		rect (5, 50, 190, 200, 7);
		textSize(13);
		fill (0, 0, 0);
		text ("Earthquakes Description", 25, 65);
		text ("Magnitude:", 30, 95);
		
		fill (0, 128, 255);
		ellipse(35, 110, 10, 10);
		textSize(13);
		fill(0,0,0);
		text ("Below 4.0", 55, 115);
		
		fill (255, 255, 0);
		ellipse (35, 135, 15, 15);
		textSize (13);
		fill (0,0,0);
		text ("4.0+", 55, 140);
		
		fill (255, 0, 0);
		ellipse (35, 160, 20, 20);
		textSize (13);
		fill (0,0,0);
		text ("5.0+", 55, 165);
		
	}
}
