// Import Statements

import java.util.Calendar;
import java.util.LinkedList;

// Classes

class Employee {
	String name;
	byte leadership;
	LinkedList<Position> positions = new LinkedList<Position>();
	LinkedList<Availability> availabilities = new LinkedList<Availability>();
	LinkedList<TimeOff> timeOff = new LinkedList<TimeOff>();

	Employee(String newName, byte newLeadership, Availability newAvailability) {
		name = newName;
		leadership = newLeadership;
		availabilities.add(newAvailability);
	}

	private void refreshAvailabilities() {
		Calendar current = Calendar.getInstance(); // Get current date

		while(true) {
			try {
				if(availabilities.get(i + 1).startDate.before(current)) { // If the next availability is in effect, delete this one
					availabilities.remove(i);
				} else { // If the next availability is not in effect, this one is; stop the loop
					break;
				}
			} catch(IndexOutOfBoundsException e) break; // If there is no next availability, this one is in effect; stop the loop
		}
	}

	public Availability currentAvailability() {
		refreshAvailabilities(); // Remove old availabilities, placing the current one at index 0

		return(availabilities.getFirst()); // Return index 0
	}
}

class Position {
	String name;
	byte leadership;
	byte aptitude = 0;
	byte experience = 0;
	byte competence() return aptitude + experience;

	Position(String newName, byte newLeadership) {
		name = newName;
		leadership = newLeadership;
	}
}

class Availability {
	Calendar startDate;

	Availability(Calendar newStartDate) {
		startDate = newStartDate;
	}
}

class TimeOff {

}

// Main Class

class Scheduler {
	public static void main(String[] args) {
		System.out.println("Test");
	}
}