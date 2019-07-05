package org.eqasim.simulation.mode_choice.components.filters;

import java.util.List;

import org.matsim.api.core.v01.population.Person;

import ch.ethz.matsim.discrete_mode_choice.model.DiscreteModeChoiceTrip;
import ch.ethz.matsim.discrete_mode_choice.model.tour_based.TourFilter;

public class TourLengthFilter implements TourFilter {
	@Override
	public boolean filter(Person person, List<DiscreteModeChoiceTrip> tour) {
		return tour.size() <= 6;
	}
}
