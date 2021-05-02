package recruiter.test.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Results {
	
	private String title;
	private double distance;
	private String outputForUser;
	
	public Results(String title, double distance, String outputForUser) {
		this.title = title;
		this.distance = distance;
		this.outputForUser = outputForUser;
	}

}
