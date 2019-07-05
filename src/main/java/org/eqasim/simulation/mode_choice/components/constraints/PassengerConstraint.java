package org.eqasim.simulation.mode_choice.components.constraints;

import java.util.Collection;
import java.util.List;

import org.matsim.api.core.v01.population.Person;

import ch.ethz.matsim.discrete_mode_choice.model.DiscreteModeChoiceTrip;
import ch.ethz.matsim.discrete_mode_choice.model.constraints.AbstractTripConstraint;
import ch.ethz.matsim.discrete_mode_choice.model.trip_based.TripConstraint;
import ch.ethz.matsim.discrete_mode_choice.model.trip_based.TripConstraintFactory;

public class PassengerConstraint extends AbstractTripConstraint {
	public static final String PASSENGER_MODE = "car_passenger";

	@Override
	public boolean validateBeforeEstimation(DiscreteModeChoiceTrip trip, String mode, List<String> previousModes) {
		if (trip.getInitialMode().equals(PASSENGER_MODE)) {
			if (!mode.equals(PASSENGER_MODE)) {
				return false;
			}
		}

		if (mode.equals(PASSENGER_MODE)) {
			if (!trip.getInitialMode().equals(PASSENGER_MODE)) {
				return false;
			}
		}

		return true;
	}

	static public class Factory implements TripConstraintFactory {
		@Override
		public TripConstraint createConstraint(Person person, List<DiscreteModeChoiceTrip> planTrips,
				Collection<String> availableModes) {
			return new PassengerConstraint();
		}
	}
}
