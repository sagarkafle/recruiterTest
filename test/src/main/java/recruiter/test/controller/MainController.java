package recruiter.test.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.node.ObjectNode;


@RestController
public class MainController {

	@GetMapping("/jsonParse")
	public String jsonParse(){		
		
		return "Json Parsed";
	}	
	
	public String getCoordinates() {
		
		return"Coordinates";
	}
	@RequestMapping(value = "/coordinates", method = RequestMethod.POST)
	@ResponseBody
	public String getCoordinates(@RequestBody ObjectNode objectNode) {
	   // And then you can call parameters from objectNode
	   double lattitute = objectNode.get("lattitude").asDouble();
	   double longitude = objectNode.get("longitude").asDouble();
	   return("Got two Coordinates"+lattitute+"::"+longitude);

	   // When you using ObjectNode, you can pas other data such as:
	   // instance object, array list, nested object, etc.
	}
	
}
