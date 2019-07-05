package org.eqasim.simulation.mode_choice.components.constraints;

import java.util.Collection;
import java.util.List;

import org.matsim.api.core.v01.population.Person;

import ch.ethz.matsim.discrete_mode_choice.model.DiscreteModeChoiceTrip;
import ch.ethz.matsim.discrete_mode_choice.model.constraints.AbstractTripConstraint;
import ch.ethz.matsim.discrete_mode_choice.model.trip_based.TripConstraint;
import ch.ethz.matsim.discrete_mode_choice.model.trip_based.TripConstraintFactory;

public class OutsideConstraint extends AbstractTripConstraint {
	public static final String OUTSIDE_MODE = "outside";

	@Override
	public boolean validateBeforeEstimation(DiscreteModeChoiceTrip trip, String mode, List<String> previousModes) {
		if (trip.getInitialMode().equals(OUTSIDE_MODE)) {
			if (!mode.equals(OUTSIDE_MODE)) {
				return false;
			}
		}

		if (mode.equals(OUTSIDE_MODE)) {
			if (!trip.getInitialMode().equals(OUTSIDE_MODE)) {
				return false;
			}
		}

		return true;
	}

	static public class Factory implements TripConstraintFactory {
		@Override
		public TripConstraint createConstraint(Person person, List<DiscreteModeChoiceTrip> planTrips,
				Collection<String> availableModes) {
			return new OutsideConstraint();
		}
	}
}
