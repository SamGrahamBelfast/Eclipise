package p3;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * @author Sam Graham
 * 40426180
 */
public class BreakingNews implements Runnable {

	private static final LocalDate CURRENT_DATE = LocalDate.now();
	private static final DayOfWeek TODAY = CURRENT_DATE.getDayOfWeek();
	
	private static final int DELAY = 30;
	
	
	private String news;
	private LocalTime startTime;
	
	
	/**
	 * Constructor to create a breaking news instance
	 * @param startTime
	 * @param news
	 */
	public BreakingNews (LocalTime startTime, String news) {
		this.startTime = startTime;
		this.news = news;
	}
	@Override
	public void run() {
		
		insertNewShow(TODAY, "ABC1",  this.news, Category.NEWS);
		
		try {
			updateShowTimes();
		} catch (InterruptedException e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * Adds a new show to the program liist
	 * @param day
	 * @param startTIme
	 * @param channel
	 * @param name
	 * @param category
	 */
	private  void insertNewShow(DayOfWeek day, String channel, String name, Category category)  {
		
		if (day == null ||   channel == null || name == null || category == null) {
			throw new IllegalArgumentException("No fields can be null");
		}
		
		try {
			TVScheduleManager.schedule.add(new ProgramTimeSlot(day, this.startTime,   channel, name, category));
		} catch (Exception e) {
		System.out.println(e.getMessage());
		}
	
	}
	
	/**
	 * Updates show times after the breaking news event has been inserted into the schedul
	 * @throws InterruptedException 
	 */
	private void updateShowTimes() throws InterruptedException {
		
		for ( ProgramTimeSlot p :  TVScheduleManager.schedule) {
			
			
			if(Thread.currentThread().isInterrupted()) {
				throw new InterruptedException("This thead has been interrupted, termnating");
			}
			
			if (p.getDay().equals(TODAY) && p.getChannel().equals("ABC1") && p.getStartTIme().isAfter(this.startTime)) {
				
				p.setStartTIme(p.getStartTIme().plusMinutes(DELAY));
			}
		}
		
		
	}

}
