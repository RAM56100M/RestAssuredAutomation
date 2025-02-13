package api;

import files.Payload;
import io.restassured.path.json.JsonPath;

public class ComplexJsonParse {
	public static void main(String[] args) {
		
		JsonPath js= new JsonPath(Payload.coursePrice());
		
		//Print number of courses returned by API
		int count= js.getInt("courses.size()");
		System.out.println("Total Number of Courses are =" + count);
		
		//Print Purchase Amount
		int totalAmount= js.getInt("dashboard.purchaseAmount");
		System.out.println("Total Purchase Amount =" + totalAmount);
		
		//Print title of First course
		String title= js.get("courses[0].title");
		System.out.println("Title of first course is: " + title);
		
		//Print title of Second course
				String title1= js.get("courses[1].title");
				System.out.println("Title of Second course is: " + title1);
				
				//Print title of Third course
				String title2= js.get("courses[2].title");
				System.out.println("Title of Third course is: " + title2);
	}

}
