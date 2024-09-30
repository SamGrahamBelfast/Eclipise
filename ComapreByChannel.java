package p3;

import java.util.Comparator;

/**
 * @author Sam Graham 
 * 40426180
 */
public class ComapreByChannel implements Comparator<ProgramTimeSlot> {

	@Override
	public int compare(ProgramTimeSlot o1, ProgramTimeSlot o2) {
		// TODO Auto-generated method stub
		return o1.getChannel().compareTo(o2.getChannel());
	}

}
