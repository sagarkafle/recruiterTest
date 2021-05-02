package recruiter.test.controller;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;



@RestController
public class MainController {

	private double inputLattitue;
	private double inputLongitude;
	
	
	
	@GetMapping("/jsonParse")
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

		for (JsonNode resourceNode : resourcesNode ) {
			JsonNode propertiesNode = resourceNode.path("properties");
			
			JsonNode geometryNode = resourceNode.path("geometry");
			JsonNode coordinates = geometryNode.path("coordinates");
			
			String title = propertiesNode.path("title").toString();
			double longitudeApi = coordinates.get(0).asDouble();
			double lattitudeApi = coordinates.get(1).asDouble();
			double distance = calculateDistance(40.730610,-73.935242,lattitudeApi,longitudeApi);
			System.out.println(title + "||"+ distance+"km");
		}
		
		return"Parsed";
	}	
	
	public String getCoordinates() {
		
		return"Coordinates";
	}
	@RequestMapping(value = "/coordinates", method = RequestMethod.POST)
	@ResponseBody
	public String getCoordinates(@RequestBody ObjectNode objectNode) {
	   // And then you can call parameters from objectNode
	   double lattitude = objectNode.get("lattitude").asDouble();
	   inputLattitue = lattitude;
	   double longitude = objectNode.get("longitude").asDouble();
	   inputLongitude = longitude;
	   return("Got two Coordinates"+inputLattitue+"::"+inputLongitude);

	   // When you using ObjectNode, you can pas other data such as:
	   // instance object, array list, nested object, etc.
	}
	
	public static double calculateDistance(double inputLat, double inputLong, double apiLat, double apiLong ) {
		double earthRadius = 6371; //Kilometers
	    double dLat = Math.toRadians(apiLat-inputLat);
	    double dLng = Math.toRadians(apiLong-inputLong);
	    double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
	               Math.cos(Math.toRadians(inputLat)) * Math.cos(Math.toRadians(apiLat)) *
	               Math.sin(dLng/2) * Math.sin(dLng/2);
	    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
	    float distance = (float) (earthRadius * c);
		
		return distance;
	}
	
}
