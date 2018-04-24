package besouro.classifier;

import java.util.List;

import besouro.model.DevelopmentType;
import besouro.model.Episode;
import besouro.model.action.Action;

public class RefactoringClassifier extends AbstractClassifier implements Classifier {

	public Episode classify(List<Action> paramActions) {
		List<Action> actions = slimming(paramActions);

		Episode episode = null;
		
		if (actions.size() > 1 && (actions.get(0).isProductionCodingAction() || actions.get(0).isTestCodingAction()) && actions.get(actions.size() - 1).isTestSuccessfullAction()) {
			actions.remove(0);
			actions.remove(actions.size() - 1);
			
			boolean isRefactoring = true;
			if (!actions.isEmpty()) {
				for (int i = 0; i < actions.size(); i++) {
					Action action = actions.get(i);
					if ((i % 2 == 1 && 
						(!action.isProductionCodingAction()) && !action.isTestCodingAction()) ||
						(i % 2 == 0 && !action.isTestFailureAction())) {
						isRefactoring = false;
						break;
					}
				}
			}
			
			if (isRefactoring) {
				episode = createEpisode(paramActions);
			}
		}

		return episode;
	}

	private Episode createEpisode(List<Action> actions) {
		Episode episode = new Episode();
		episode.addActions(actions);
		episode.setClassification(DevelopmentType.REFACTORING);
		return episode;
	}

}
