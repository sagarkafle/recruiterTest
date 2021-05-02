package recruiter.test.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import recruiter.test.model.Coordinates;
import recruiter.test.model.Results;



@RestController
public class MainController {

	
    List<Coordinates> coordinatesApi = new ArrayList<Coordinates>();
	List<Results> results = new ArrayList<Results>();
	List<String> topTenResults = new ArrayList<String>();
	
	
//	Get input coordinates and provide the results accordingly.
	@RequestMapping(value = "/coordinates", method = RequestMethod.POST)
	@ResponseBody
	public List<String> getCoordinates(@RequestBody ObjectNode objectNode) {

		double lattitude = objectNode.get("lattitude").asDouble();
	   double longitude = objectNode.get("longitude").asDouble();
	   jsonParse();
	   results.clear();
	   calculateDistance(lattitude,longitude);
	   results.sort(Comparator.comparingDouble(Results::getDistance));
	   getTenData(results);
	   
	   return topTenResults;
	}
	
	public void getTenData(List<Results> result) {
		topTenResults.clear();
		for(int i = 0; i<10;i++) {
			topTenResults.add(result.get(i).getOutputForUser());
		}
	}

//	Getting data from api and parsing it for distance calculation
	public String jsonParse(){		
		JsonNode resourcesNode = null;
		JsonNode rootNode = null;
		ObjectMapper mapper = new ObjectMapper();
		try {
			rootNode = mapper.readTree(new URL("https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_month.geojson"));
			resourcesNode = rootNode.path("features");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		coordinatesApi.clear();

		for (JsonNode resourceNode : resourcesNode ) {
			JsonNode propertiesNode = resourceNode.path("properties");
			
			JsonNode geometryNode = resourceNode.path("geometry");
			JsonNode coordinates = geometryNode.path("coordinates");
			
			String title = propertiesNode.path("title").toString();
			double longitudeApi = coordinates.get(0).asDouble();
			double lattitudeApi = coordinates.get(1).asDouble();
			
//			CHecking of duplicate data, if the earthquake occurs in same area 
//			twice(matching lattitude and longitude) then the later one is avoided
			
			boolean samePlaceExists = coordinatesApi.stream().anyMatch(item -> lattitudeApi == (item.getLattitude()) && longitudeApi == (item.getLongitude()) );
			if(samePlaceExists) {
//				System.out.println("Earthquake occur at same place");
				
			}else {
				coordinatesApi.add(new Coordinates(title, lattitudeApi, longitudeApi));
			}
			
	}
		
		return"Parsed";
	}	
	
	
//	Calculation of distance from lattitude and longitude
	public void calculateDistance(double inputLat, double inputLong) {
		
		
		for(int i = 0 ; i<coordinatesApi.size();i++) {
			String title = coordinatesApi.get(i).getTitle();
			double apiLat = coordinatesApi.get(i).getLattitude();
			double apiLong =coordinatesApi.get(i).getLongitude();
			double earthRadius = 6371; //Kilometers
		    double dLat = Math.toRadians(apiLat-inputLat);
		    double dLng = Math.toRadians(apiLong-inputLong);
		    double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
		               Math.cos(Math.toRadians(inputLat)) * Math.cos(Math.toRadians(apiLat)) *
		               Math.sin(dLng/2) * Math.sin(dLng/2);
		    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
		    float distance = (float) (earthRadius * c);
		    results.add(new Results(title, distance,title+"||"+distance+" Km"));
		    
		}

		
	}
	
}
