package org.eqasim.los_angeles.mode_choice;

import java.io.File;
import java.io.IOException;

import org.eqasim.core.components.config.EqasimConfigGroup;
import org.eqasim.core.simulation.mode_choice.AbstractEqasimExtension;
import org.eqasim.core.simulation.mode_choice.ParameterDefinition;
import org.eqasim.core.simulation.mode_choice.parameters.ModeParameters;
import org.eqasim.los_angeles.mode_choice.costs.LosAngelesCarCostModel;
import org.eqasim.los_angeles.mode_choice.costs.LosAngelesPtCostModel;
import org.eqasim.los_angeles.mode_choice.parameters.LosAngelesCostParameters;
import org.eqasim.los_angeles.mode_choice.parameters.LosAngelesModeParameters;
import org.eqasim.los_angeles.mode_choice.utilities.estimators.LosAngelesPTUtilityEstimator;
import org.eqasim.los_angeles.mode_choice.utilities.estimators.LosAngelesWalkUtilityEstimator;
import org.eqasim.los_angeles.mode_choice.utilities.predictors.LosAngelesPersonPredictor;
import org.matsim.core.config.CommandLine;
import org.matsim.core.config.CommandLine.ConfigurationException;

import com.google.inject.Provides;
import com.google.inject.Singleton;

public class LosAngelesModeChoiceModule extends AbstractEqasimExtension {
	private final CommandLine commandLine;

	static public final String MODE_AVAILABILITY_NAME = "LosAngelesModeAvailability";

	static public final String CAR_COST_MODEL_NAME = "LosAngelesCarCostModel";
	static public final String PT_COST_MODEL_NAME = "LosAngelesPtCostModel";

	public LosAngelesModeChoiceModule(CommandLine commandLine) {
		this.commandLine = commandLine;
	}

	@Override
	protected void installEqasimExtension() {
		bindModeAvailability(MODE_AVAILABILITY_NAME).to(LosAngelesModeAvailability.class);

		bind(LosAngelesPersonPredictor.class);

		bindCostModel(CAR_COST_MODEL_NAME).to(LosAngelesCarCostModel.class);
		bindCostModel(PT_COST_MODEL_NAME).to(LosAngelesPtCostModel.class);
        bindUtilityEstimator("sfPTEstimator").to(LosAngelesPTUtilityEstimator.class);
        bindUtilityEstimator("sfWalkEstimator").to(LosAngelesWalkUtilityEstimator.class);
		bind(ModeParameters.class).to(LosAngelesModeParameters.class);
	}

	@Provides
	@Singleton
	public LosAngelesModeParameters provideModeChoiceParameters(EqasimConfigGroup config) throws IOException, ConfigurationException {
		LosAngelesModeParameters parameters = LosAngelesModeParameters.buildDefault();
		
		if (config.getModeParametersPath() != null) {
			ParameterDefinition.applyFile(new File(config.getModeParametersPath()), parameters);
		}
		
		ParameterDefinition.applyCommandLine("mode-parameter", commandLine, parameters);
		
		return parameters;
	}

	@Provides
	@Singleton
	public LosAngelesCostParameters provideCostParameters(EqasimConfigGroup config) {
		LosAngelesCostParameters parameters = LosAngelesCostParameters.buildDefault();
		
		if (config.getModeParametersPath() != null) {
			ParameterDefinition.applyFile(new File(config.getModeParametersPath()), parameters);
		}
		
		ParameterDefinition.applyCommandLine("cost-parameter", commandLine, parameters);
		
		return parameters;
	}
}
