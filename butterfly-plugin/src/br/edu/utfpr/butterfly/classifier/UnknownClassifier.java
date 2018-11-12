package br.edu.utfpr.butterfly.classifier;

import java.util.List;

import br.edu.utfpr.butterfly.model.DevelopmentType;
import br.edu.utfpr.butterfly.model.Episode;
import br.edu.utfpr.butterfly.model.action.Action;

public class UnknownClassifier extends AbstractClassifier implements Classifier {

	public Episode classify(List<Action> paramActions) {
		List<Action> actions = slimming(paramActions);

		Episode episode = null;
		if (actions.size() > 2 && actions.get(actions.size() - 1).isTestSuccessfullAction()) {
			Boolean isFailureAction = actions.get(0).isTestFailureAction();
			Boolean isUnorderedAction = unordered(actions);
			if (isFailureAction || isUnorderedAction) {
			episode = createEpisode(paramActions);
			}
		}
		
		return episode;
	}

	private Boolean unordered(List<Action> actions) {
		if (actions.get(0).isTestCreationAction() && actions.get(actions.size() - 1).isTestSuccessfullAction()) {
			for (int i = 1; i < actions.size(); i++) {
				if (actions.get(i).isTestCodingAction() || actions.get(i).isProductionCodingAction()) {
					return false;
				}
			}
		}
		return true;
	}

	private Episode createEpisode(List<Action> actions) {
		Episode episode = new Episode();
		episode.addActions(actions);
		episode.setClassification(DevelopmentType.UNKNOWN);
		return episode;
	}

}
