package br.edu.utfpr.butterfly.classifier;

import java.util.List;

import br.edu.utfpr.butterfly.model.DevelopmentType;
import br.edu.utfpr.butterfly.model.Episode;
import br.edu.utfpr.butterfly.model.action.Action;

public class TestLastClassifier extends AbstractClassifier implements Classifier {

	public Episode classify(List<Action> paramActions) {
		List<Action> actions = slimming(paramActions);

		Episode episode = null;
		
		if (actions.size() > 2 && actions.get(0).isProductionCodingAction() && actions.get(1).isTestCreationAction() && actions.get(actions.size() - 1).isTestSuccessfullAction()) {
			actions.remove(0);
			actions.remove(0);
			actions.remove(actions.size() - 1);
			
			boolean isTestLast = true;
			if (!actions.isEmpty()) {
				for (int i = 0; i < actions.size(); i++) {
					if ((i % 2 == 1 && !actions.get(i).isTestCodingAction()) ||
						(i % 2 == 0 && !actions.get(i).isTestFailureAction())) {
						isTestLast = false;
						break;
					}
				}
			} 
			
			if (isTestLast) {
				episode = createEpisode(paramActions);
			}
		}

		return episode;
	}

	private Episode createEpisode(List<Action> actions) {
		Episode episode = new Episode();
		episode.addActions(actions);
		episode.setClassification(DevelopmentType.TEST_LAST);
		return episode;
	}

}
