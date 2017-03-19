import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.UIManager;

import org.geotools.data.DataUtilities;
import org.geotools.data.DefaultTransaction;
import org.geotools.data.Transaction;
import org.geotools.data.collection.ListFeatureCollection;
import org.geotools.data.shapefile.ShapefileDataStore;
import org.geotools.data.shapefile.ShapefileDataStoreFactory;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.data.simple.SimpleFeatureStore;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.geotools.feature.simple.SimpleFeatureTypeBuilder;
import org.geotools.geometry.jts.JTSFactoryFinder;
import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.geotools.swing.data.JFileDataStoreChooser;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;

public class CSV2SHP  {
	
		SimpleFeatureType TYPE;
		
	
 public static void main(String[] args) throws Exception {
	 
	 
	 
	 File file = JFileDataStoreChooser.showOpenFile("csv", null);
	 
	 if (file == null) {
		 return;
	 }
	 List<SimpleFeature> features = new ArrayList<>();
	 
	 SimpleFeatureType TYPE = DataUtilities.createType("Location", 
				"location:Point:srid=4326," + "name:String," + "number;") ;
	 System.out.print("TYPE:" + TYPE);
	 
	 GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory(null);
	 
	 SimpleFeatureBuilder featureBuilder = new SimpleFeatureBuilder(TYPE);
	 
	 try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
		 String line = reader.readLine();
		 System.out.println("Header:" + line);
		 
		 for (line = reader.readLine(); line != null; line = reader.readLine()) {
			 if(line.trim().length() > 0 ) {
				 String tokens[] = line.split("\\,");
				 
				 double latitude = Double.parseDouble(tokens[0]);
				 double longitude = Double.parseDouble(tokens[1]);
				 String name = tokens[2].trim();
				 int number = Integer.parseInt(tokens[3].trim());
				 
				 Point point = geometryFactory.createPoint(new Coordinate(
						 longitude, latitude));
				 featureBuilder.add(point);
				 featureBuilder.add(name);
				 featureBuilder.add(number);
				 SimpleFeature feature = featureBuilder.buildFeature(null);
				 features.add(feature);
						 
			 }
		 }
	 }
	 
	 
	 
	 
	 
 }
 
 
}