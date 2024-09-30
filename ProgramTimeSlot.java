package p3;

/**
 * @author Sam Graham
 * 40426180
 */
import java.time.*;
public class ProgramTimeSlot {
	
	
	//instance vars
	private DayOfWeek day;
	private LocalTime startTIme;
	private String channel;
	private String name;
	private Category category;
	
	
	
	
	/**
	 * Default Constructor 
	 */
	public ProgramTimeSlot() {
		
	}
	/**
	 * @param day
	 * @param startTIme
	 * @param channel
	 * @param name
	 * @param category
	 */
	public ProgramTimeSlot(DayOfWeek day, LocalTime startTIme, String channel, String name, Category category) {
		
		this.setDay(day);
		this.setStartTIme(startTIme);
		this.setChannel(channel);
		this.setName(name);
		this.setCategory(category);
	}

	/**
	 * @return the day
	 */
	public DayOfWeek getDay() {
		return day;
	}
	/**
	 * @param day the day to set
	 */
	public void setDay(DayOfWeek day) {
		this.day = day;
	}
	/**
	 * @return the startTIme
	 */
	public LocalTime getStartTIme() {
		return startTIme;
	}
	/**
	 * @param startTIme the startTIme to set
	 */
	public void setStartTIme(LocalTime startTIme) {
		this.startTIme = startTIme;
	}
	/**
	 * @return the channel
	 */
	public String getChannel() {
		return channel;
	}
	/**
	 * @param channel the channel to set
	 */
	public void setChannel(String channel) {
		this.channel = channel;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the category
	 */
	public Category getCategory() {
		return category;
	}
	/**
	 * @param category the category to set
	 */
	public void setCategory(Category category) {
		this.category = category;
	}
	
	/**
	 * Simple method to print details of the current objects instance
variables to the console in a formatted manner.
	 */
	public void printDetails() {
		StringBuilder sb = new StringBuilder();
		String newLine = "\n";
		
		sb.append(this.getDay().toString());
		sb.append(" - ");
		sb.append(this.getStartTIme() + " ");
		sb.append("Ch: ");
		sb.append(this.getChannel());
		sb.append(newLine);
		sb.append(this.getName());
		sb.append("(");
		sb.append(this.getCategory().toString());
		sb.append(")");
		sb.append(newLine);
		
		System.out.println(sb);
		
		
	}
	
	
	

}
