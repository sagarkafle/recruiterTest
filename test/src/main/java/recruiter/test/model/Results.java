package recruiter.test.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Results {
	
	private String title;
	private double distance;
	private String outputForUser;
	private double lattitude;
	private double longitude;
	
	public Results(String title, double distance, String outputForUser,double lattitude, double longitude) {
		this.title = title;
		this.distance = distance;
		this.outputForUser = outputForUser;
	}

}
