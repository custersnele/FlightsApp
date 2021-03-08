package be.pxl.paj.flights;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Itinerary implements Comparable<Itinerary> {

	/** Stores the date on which the flights occur. */
	private final LocalDate date;

	/** Stores the list of flights on that date. */
	private final List<Flight> flights;

	/** Stores the total flight time of all the flights. */
	private final int timeMinutes;

	/** Creates an itinerary with the given properties. */
	public Itinerary(Collection<Flight> flights) {
		assert flights.size() > 0;
		Flight first = flights.iterator().next();

		int timeMinutes = 0;
		for (Flight f : flights) {
			timeMinutes += f.getTimeMinutes();
		}
		this.timeMinutes = timeMinutes;
		this.date = first.getDate();
		this.flights = new ArrayList<>(flights);

	}

	@Override
	public int compareTo(Itinerary other) {
		return timeMinutes - other.timeMinutes;
	}

	public int getTimeMinutes() {
		return timeMinutes;
	}

	public LocalDate getDate() {
		return date;
	}

	public List<Flight> getFlights() {
		return flights;
	}
}
