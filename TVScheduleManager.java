package p3;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

import org.junit.experimental.theories.ParametersSuppliedBy;

/**
 * @author Sam Graham
 * 40426180
 */
public class TVScheduleManager {

	private static final int MINCHOICE = 1;
	private static final int QUITCHOICE = 8;
	private static final LocalTime CURRENT_TIME = LocalTime.now();
	private static final LocalTime NOW = LocalTime.of(CURRENT_TIME.getHour(), CURRENT_TIME.getMinute());
	private static final LocalDate CURRENT_DATE = LocalDate.now();
	private static final DayOfWeek TODAY = CURRENT_DATE.getDayOfWeek();
	
	private static  LocalTime BREAKING_NEWS_TIME = LocalTime.parse("12:00");
	private static String NEWS_BULLETIN = "Insert News Bulletin Here";
	
	
	static List<ProgramTimeSlot> schedule = new ArrayList<ProgramTimeSlot>();  //changed visibility to package-private for thread visibility

	/**
	 * Entry point to program
	 * Menu driven system loop until quit choice is selected
	 * Should not be necessary to modify
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("TV Schedule System");
		
		
		int choice = 0;
		do {
			System.out.println();
			showMenu();
			choice = getChoice();

			System.out.println("choice = " + choice);
			processMenuChoice(choice, schedule);
		} while (choice != QUITCHOICE);

		System.out.println("Thanks, goodbye");

	}

	/**
	 * A launcher method which drives menu choice selection to perform a number of tasks processing the received schedule data
	 * @param choice menu choice value
	 * @param schedule a list of ProgramTimeSlot objects to be processed
	 */
	private static void processMenuChoice(int choice, List<ProgramTimeSlot> schedule) {
		
		//TODO Implement and call required methods to achieve desired functionality
		
		try {
			switch (choice) {
			case 1:
				readData("SampleTVData.csv");
		
				break;
			case 2:	
				printDetails(SortedByChannelDayandTime(schedule));
				break;
			case 3:
				printDetails(sortByDayAndTime(showsByChannel(schedule, "ABC1")));
				break;
			case 4:
				printDetails(sortByTimeOnly(showsByChannelAndDay(schedule, "Premium Events", DayOfWeek.FRIDAY)));
				break;
			case 5:
				printDetails(showsByDayandTitle(schedule,  DayOfWeek.THURSDAY, "luNCh"))  ; 
				break;
			case 6:
				displayNowAndNext(schedule, "ABC1");
				break;
			case 7:
					Thread t1 = new Thread(new BreakingNews(BREAKING_NEWS_TIME, NEWS_BULLETIN));
					t1.start();
			
				break;
			case QUITCHOICE:
				System.out.println("Quitting");
				break;
			default:
				System.out.println("Unrecognised menu choice");
			}
		} catch (Exception e) {
			System.out.println("Exception caught in task "+choice);
			System.out.println("Exception message: "+e.getMessage());
			System.out.println("Try again");
		}

	}
	

	/**
	 * Read data from external csv into static List
	 * @param filename
	 */
	private static void readData(String filename) {
		
		schedule.clear();//empty existing data
		
		//TODO complete read method to populate static schedule List as appropriate
		
		if( filename == null || filename.isBlank() || filename.isEmpty()) {
			System.out.println("File name is null, empty or blank");
		}
		
		try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
			
			String line = reader.readLine(); //skip header
			line = reader.readLine();
			
			while (line != null) {
				
				try {
					String [] parts =  line.split(",");
					
					DayOfWeek day = DayOfWeek.valueOf(parts[0].toUpperCase());
					LocalTime startTime = LocalTime.parse(parts[1]);
					String channel = parts[2];
					String name = parts[3];
					Category category = Category.valueOf(parts[4].toUpperCase());
					
				schedule.add( new ProgramTimeSlot(day, startTime, channel, name, category));
					line = reader.readLine();
					
				} catch (Exception e) {
					System.out.println(e.getMessage());
					line = reader.readLine();
				}
				
				
			}
			
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}


	/**
	 * Get validated int choice between MIN and QUIT static variables
	 * Should not be necessary to modify
	 * @return
	 */
	@SuppressWarnings("resource")
	private static int getChoice() {
		int choice = -1;
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter Menu choice");
		try {
			choice = sc.nextInt();
			if (choice < MINCHOICE || choice > QUITCHOICE) {
				throw new Exception();//throw exception, caught immediately and retry
			}
		} catch (Exception e) {
			System.out.println("Invalid choice, please try again");
			return getChoice();//recursive call to try again and return that value
		}
		return choice;
	}

	/**
	 * Display menu options
	 */
	public static void showMenu() {
		System.out.println("1) Read Schedule Data");
		System.out.println("2) Display All Show Data - sorted by Channel, Day, Time");
		System.out.println("3) Display Show Data, specifically for ABC1 - sorted by Day, Time");
		System.out.println("4) Display Show Data, specifically for Premium Events Channel on Friday - sorted by Time");
		System.out.println("5) Display Show Data for any shows on Thursday where the title contains the word Lunch - sorted alphabetically by title");
		System.out.println("6) Now and Next, on channel ABC1");
		System.out.println("7) Breaking news, 12:45 Tue. Insert News Bulletin (ABC1). Delay all shows after News Bulletin by 30 mins (update start times)");
		System.out.println("8) Quit");
	}
	
/**
 * Simple print method  which prints all details of the object7
 * @param List<ProgramTimeSlot> in
 */
	public static void printDetails(List<ProgramTimeSlot> in) {
		
		if (in == null || in.isEmpty()) {
			throw new IllegalArgumentException("List can not br null or empty");
		}
		
		for (ProgramTimeSlot p :in ) {
			p.printDetails();
		}
	}
	
	/**
	 * Recevies a list, creates a  new refernece to that list.  Sorts the list by channel name and time slot. METHOD IS CURRENLY NOT IS USE
	 * @param List<ProgramTimeSlot> in
	 * @returnList<ProgramTimeSlot>
	 */
	private static List<ProgramTimeSlot> sortByNameAndTime(List<ProgramTimeSlot> in) {
		
		if (in == null || in.isEmpty()) {
			System.out.println("List is either null or empty. Please check list");
		}
		
		List<ProgramTimeSlot> out = new ArrayList<ProgramTimeSlot>(in);
		
		Collections.sort(out, new ComapreByChannel().thenComparing(new CompareByTimeSlot()));
		
		return out;
		
	}
	
	/**
	 * Recevies a list, creates a  new refernece to that list.  Sorts the list by day and time slot
	 * @param List<ProgramTimeSlot> in
	 * @returnList<ProgramTimeSlot>
	 */
	private static List<ProgramTimeSlot> sortByDayAndTime(List<ProgramTimeSlot> in) {
		
		if (in == null || in.isEmpty()) {
			System.out.println("List is either null or empty. Please check list");
		}
		
		List<ProgramTimeSlot> out = new ArrayList<ProgramTimeSlot>(in);
		
		Collections.sort(out, new CompareByDay().thenComparing(new CompareByTimeSlot()));
		
		return out;
		
	}
	
	/**
	 * Recevies a list, creates a  new refernece to that list.  Sorts the list by day and time slot
	 * @param List<ProgramTimeSlot> in
	 * @returnList<ProgramTimeSlot>
	 */
	private static List<ProgramTimeSlot> sortByTimeOnly(List<ProgramTimeSlot> in) {
		
		if (in == null || in.isEmpty()) {
			System.out.println("List is either null or empty. Please check list");
		}
		
		List<ProgramTimeSlot> out = new ArrayList<ProgramTimeSlot>(in);
		
		Collections.sort(out, new CompareByTimeSlot());
		
		return out;
		
	}
	
	
	/**
	 * Allows a search by category  name.  The result is a returned list with only objects that match the category on a specific day
	 * This method  is not currently used 
	 * @param in
	 * @param channel
	 * @return
	 */
private static List<ProgramTimeSlot> showsByCategory (List<ProgramTimeSlot> in, Category category, DayOfWeek day) {
		
	
		if (in == null || in.isEmpty()) {
			System.out.println("List is either null or empty. Please check list");
		}
		
		List<ProgramTimeSlot> out = new ArrayList<ProgramTimeSlot>();
		
		for (ProgramTimeSlot p : in) {
			if (p.getCategory() == category && p.getDay() == day){
				out.add(p);
			}
		} 
		
		return out;
		
}

/**
 * Allows a search by chanelll name.  The result is a returned list with only objects that match the channel name
 * @param in
 * @param channel
 * @return
 */
private static List<ProgramTimeSlot> showsByChannel (List<ProgramTimeSlot> in, String channel) {
	

	if (in == null || in.isEmpty()) {
		System.out.println("List is either null or empty. Please check list");
	}
	
	List<ProgramTimeSlot> out = new ArrayList<ProgramTimeSlot>();
	
	for (ProgramTimeSlot p : in) {
		if (p.getChannel().equalsIgnoreCase(channel)) {
			out.add(p);
		}
	}
	
	return out;
	
}
/**
 * Allows a search by chanel name and day.  The result is a returned list with only objects that match the channel name and day
 * @param in
 * @param channel
 * @return
 */
private static List<ProgramTimeSlot> showsByChannelAndDay(List<ProgramTimeSlot> in, String channel, DayOfWeek day) {
	

	if (in == null || in.isEmpty()) {
		System.out.println("List is either null or empty. Please check list");
	}
	
	List<ProgramTimeSlot> out = new ArrayList<ProgramTimeSlot>();
	
	for (ProgramTimeSlot p : in) {
		if (p.getChannel().equalsIgnoreCase(channel)  && p.getDay() == day) {
			out.add(p);
		}
	}
	
	return out;
}

/**
 * Allows a search by chanel text in the name and day. The result is a returned
 * list with only objects that match the channel name text and day
 * 
 * @param in
 * @param channel
 * @return
 */
private static List<ProgramTimeSlot> showsByDayandTitle(List<ProgramTimeSlot> in,  DayOfWeek day, String word) {

	if (in == null || in.isEmpty()) {
		System.out.println("List is either null or empty. Please check list");
	}

	List<ProgramTimeSlot> out = new ArrayList<ProgramTimeSlot>();

	// convert text to upper case to adhere to case insensitivity, come back with time

	
	for (ProgramTimeSlot p : in) {
		String lowerCaseName = p.getName().toLowerCase();
		if (p.getDay() == day  && lowerCaseName.contains(word.toLowerCase()) ) {
			out.add(p);
		}
	}
	return out;
}

/**
 * Returns details of all shows in the schedule list.
Sorted by channel (alphabetically) and chronologically
 * 
 * @param in
 * @param channel
 * @return
 */
private static List<ProgramTimeSlot> SortedByChannelDayandTime(List<ProgramTimeSlot> in) {

	if (in == null || in.isEmpty()) {
		System.out.println("List is either null or empty. Please check list");
	}

	List<ProgramTimeSlot> out = new ArrayList<ProgramTimeSlot>(in);;

	Collections.sort(out, new ComapreByChannel().thenComparing(new CompareByDay().thenComparing(new CompareByTimeSlot())));
	
	
	return out;
}

	


/**
 * Displays what is on NOW, and NEXT, on the ABC1 channel at the current time.

 * @param in
 * @param channel
 */
public static void displayNowAndNext (List<ProgramTimeSlot>  in, String channel) {
	
	if (in == null || in.isEmpty()) {
		System.out.println("List is either null or empty. Please check list");
	}
	
	//get shows on today to allow now and next for today
	List<ProgramTimeSlot> showsOnToday = showsByChannelAndDay(in, channel, TODAY);
	Collections.sort(showsOnToday, new CompareByTimeSlot());
	
	List <ProgramTimeSlot> toDisplayNow = new ArrayList<ProgramTimeSlot>();
	List <ProgramTimeSlot> toDisplayNext = new ArrayList<ProgramTimeSlot>();
	System.out.println("Now and Next - " + channel);

	ProgramTimeSlot now = new ProgramTimeSlot();
	for (ProgramTimeSlot p : showsOnToday) {

		if (p.getStartTIme().isAfter(NOW))  {
			now =p;
			toDisplayNow.add(now);
			break;
	}

		ProgramTimeSlot next = new ProgramTimeSlot();
		for (ProgramTimeSlot n : showsOnToday) {

			if (n.getStartTIme().isAfter(now.getStartTIme()))  {
				next=n;
				toDisplayNext.add(next);
				break;
		}
			System.out.println("Now:");
			now.printDetails();
			
			System.out.println("Next:");
			next.printDetails();

}
	
	
	
/**
 * Displays what is on NOW, and NEXT, on the ABC1 channel at the current time.  THIS WAS ANOTHER ATTEMPT.  WILL COME BACK WITH MORE TIME,

 * @param in
 * @param channel
 */

}



}
}


