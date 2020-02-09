import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Countries {
   private int attendeeCount;
   private String[] attendees;
   private String name;
   private String startDate;
   private Map <String, Integer>surveyDates ;
   
   public Countries(int attendeeCount,String[] attendees,String name,String startDate,Map<String, Integer>surveyDates){
	   this.attendeeCount= attendeeCount;
	   this.attendees=attendees;
	   this.name= name;
	   this.startDate= startDate;
	   this.surveyDates= surveyDates;
	   
    

  }
   

public Map<String, Integer> getSurveyDates() {
	return surveyDates;
}


public void setSurveyDates(Map<String, Integer> surveyDates) {
	this.surveyDates = surveyDates;
}


@Override
public String toString() {
	return "Countries [attendeeCount=" + attendeeCount + ", attendees=" + Arrays.toString(attendees) + ", name=" + name
			+ ", startDate=" + startDate + "]";
}


public int getAttendeeCount() {
	return attendeeCount;
}

public void setAttendeeCount(int attendeeCount) {
	this.attendeeCount = attendeeCount;
}

public String[] getAttendees() {
	return attendees;
}

public void setAttendees(String[] attendees) {
	this.attendees = attendees;
}

public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}

public String getStartDate() {
	return startDate;
}

public void setStartDate(String startDate) {
	this.startDate = startDate;
}
   
}
