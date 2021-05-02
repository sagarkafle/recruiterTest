package recruiter.test.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Coordinates {
	
	private String title;
	private double lattitude;
	private double longitude;
	
	public Coordinates(String title, double lattitude, double longitude) {
		this.title = title;
		this.lattitude = lattitude;
		this.longitude = longitude;
	}

}
