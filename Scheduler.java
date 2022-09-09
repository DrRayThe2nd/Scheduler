// Import Statements

import java.util.Calendar;
import java.util.LinkedList;

// Classes

class Employee {
	String name; // Employee's name
	byte leadership; // Employee's leadership ability where 0 = TM, 1 = SME, 2 = Manager
	LinkedList<Position> positions = new LinkedList<Position>(); // List of positions and ability
	LinkedList<Availability> availabilities = new LinkedList<Availability>(); // List of current and future, approved and pending availabilities
	LinkedList<TimeOff> timeOff = new LinkedList<TimeOff>(); // List of current and future, approved and pending time off requests

	Employee(String newName, byte newLeadership, Availability newAvailability) {
		name = newName;
		leadership = newLeadership;
		availabilities.add(newAvailability);
	}

	private void refreshAvailabilities() {
		availabilities.sort((a1, a2) -> a1.startDate.getTime().compareTo(a2.startDate.getTime())); // Sort availabilities by start date

		Calendar current = Calendar.getInstance(); // Get current date

		for(int i = 0; i < availabilities.size() - 1; i++) {
			if(availabilities.get(i + 1).startDate.before(current) & availabilities.get(i + 1).approved) availabilities.remove(i); // If the next availability is in effect, delete this one
			else break; // If the next availability is not in effect, this one is; stop the loop
		}
	}

	Availability getAvailability() {
		refreshAvailabilities(); // Remove old availabilities, placing the current one at index 0

		return(availabilities.getFirst()); // Return index 0
	}

	private void refreshTimeOff() {
		timeOff.sort((t1, t2) -> t1.endTime.getTime().compareTo(t2.endTime.getTime())); // Sort time off by end date

		Calendar current = Calendar.getInstance(); // Get current date

		for(int i = 0; i < timeOff.size(); i++) {
			if(timeOff.get(i).endTime.before(current)) timeOff.remove(i); // If the time off has already ended, remove it
			else break; //
		}
	}
}

class Position {
	String name; // Name of the position
	byte leadership; // Leadership ability required to hold the position
	byte aptitude = 0; // Aptitude in the position as determined by managers' votes
	byte experience = 0; // Experience in the position as determined by number of times in the position
	int competence() { return aptitude + experience; } // Total competence in the position as determined by the sum of aptitude and experience

	Position(String newName, byte newLeadership) {
		name = newName;
		leadership = newLeadership;
	}
}

class Availability {
	Calendar startDate; // When the availability first takes effect
	boolean approved; // Has the availability been approved by management
	LinkedList<AvailabilityDay> days; // Sunday through Saturday

	Availability(Calendar newStartDate, boolean newApproved, LinkedList<AvailabilityDay> newDays) {
		startDate = newStartDate;
		approved = newApproved;
		days = newDays;
	}
}

class AvailabilityDay {
	LinkedList<AvailabilityTime> times; // A list of time ranges allowed

	AvailabilityDay(LinkedList<AvailabilityTime> newTimes) { times = newTimes; } // The braces might be unnecessary 

	boolean isAllowed(Calendar jobStartTime, Calendar jobEndTime) {
		for(AvailabilityTime time : times) {
			if(time.isAllowed(jobStartTime, jobEndTime)) return true; // If the job fits in any time range, it is valid
		}

		return false; // If the job fits in no time range, it is invalid
	}
}

class AvailabilityTime {
	Calendar startTime;
	Calendar endTime;

	AvailabilityTime(Calendar newStartTime, Calendar newEndTime) {
		startTime = newStartTime;
		endTime = newEndTime;
	}

	boolean isAllowed(Calendar jobStartTime, Calendar jobEndTime) {
		// Set the dates so only the time is relevant
		startTime.set(Calendar.DATE, jobStartTime.get(Calendar.DATE));
		startTime.set(Calendar.MONTH, jobStartTime.get(Calendar.MONTH));
		startTime.set(Calendar.YEAR, jobStartTime.get(Calendar.YEAR));
		endTime.set(Calendar.DATE, jobEndTime.get(Calendar.DATE));
		endTime.set(Calendar.MONTH, jobEndTime.get(Calendar.MONTH));
		endTime.set(Calendar.YEAR, jobEndTime.get(Calendar.YEAR));

		if((startTime.before(jobStartTime) | startTime.equals(jobStartTime)) & (endTime.after(jobEndTime) | endTime.equals(jobEndTime))) return true; // If job falls within availability, return true
		else return false;
	}
}

class TimeOff {
	Calendar startTime; // When the time off starts
	Calendar endTime; // When the time off ends
	boolean approved; // Has the time off been approved by management
	boolean paid; // Is the time off paid

	TimeOff(Calendar newStartTime, Calendar newEndTime, boolean newApproved, boolean newPaid) {
		startTime = newStartTime;
		endTime = newEndTime;
		approved = newApproved;
		paid = newPaid;
	}
}

// Main Class

class Scheduler {
	public static void main(String[] args) {
		System.out.println("Test");
	}
}