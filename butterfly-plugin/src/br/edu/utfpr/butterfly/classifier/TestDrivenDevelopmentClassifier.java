package br.edu.utfpr.butterfly.classifier;

import java.util.ArrayList;
import java.util.List;

import br.edu.utfpr.butterfly.model.DevelopmentType;
import br.edu.utfpr.butterfly.model.Episode;
import br.edu.utfpr.butterfly.model.action.Action;

public class TestDrivenDevelopmentClassifier extends AbstractClassifier implements Classifier {

	public Episode classify(List<Action> paramActions) {
		List<Action> actions = slimming(paramActions);

		Episode episode = null;

		if (actions.size() > 5 && actions.get(0).isTestCreationAction()
				&& actions.get(actions.size() - 1).isTestSuccessfullAction()) {
			actions.remove(0);

			boolean isTdd = true;
			List<Action> firstPhase = getNextSuccessfull(actions);
			for (int i = 0; i < firstPhase.size(); i++) {
				if ((i % 2 == 0 && !firstPhase.get(i).isTestFailureAction()) || 
				    (i % 2 == 1 && !firstPhase.get(i).isProductionCodingAction())) {
					isTdd = false;
					break;
				}
			}
			
			if (isTdd) {
				isTdd = validatePhase(actions);
			}

			if (isTdd) {
				episode = createEpisode(paramActions);
			}
		}
		return episode;
	}

	private boolean validatePhase(List<Action> actions) {
		List<Action> phase = getNextSuccessfull(actions);
		if (phase.size() == 0) {
			return true;
		}
		for (int i = 0; i < phase.size(); i++) {
			if ((i % 2 == 0 && !phase.get(i).isProductionCodingAction()) && 
				(i % 2 == 0 && !phase.get(i).isTestCodingAction()) ||
			    (i % 2 == 1 && !phase.get(i).isTestFailureAction())) {
					return false;
				}
		}
		return validatePhase(actions);
	}

	private List<Action> getNextSuccessfull(List<Action> actions) {
		List<Action> newActions = new ArrayList<Action>();
		for (Action action : actions) {
			if (action.isTestSuccessfullAction()) {
				break;
			}
			newActions.add(action);
		}
		for (int i = 0; i < newActions.size(); i++) {
			actions.remove(0);
		}
		if (!actions.isEmpty()) {
			actions.remove(0);
		}
		return newActions;
	}

	private Episode createEpisode(List<Action> actions) {
		Episode episode = new Episode();
		episode.addActions(actions);
		episode.setClassification(DevelopmentType.TEST_DRIVEN_DEVELOPMENT);
		return episode;
	}

}
