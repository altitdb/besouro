package besouro.classifier;

import java.util.List;

import besouro.model.DevelopmentType;
import besouro.model.Episode;
import besouro.model.action.Action;

public class TestAdditionClassifier extends AbstractClassifier implements Classifier {

	public Episode classify(List<Action> paramActions) {
		List<Action> actions = slimming(paramActions);

		Episode episode = null;
		if (actions.size() % 2 == 0) {
			if (actions.get(0).isTestCreationAction() && actions.get(actions.size() - 1).isTestSuccessfullAction()) {
				boolean isTestAddition = true;
				for (int i = 1; i < actions.size() - 1; i++) {
					if ((i % 2 == 1 && !actions.get(i).isTestFailureAction()) ||
						(i % 2 == 0 && !actions.get(i).isTestCodingAction())) {
						isTestAddition = false;
						break;
					}
				}
				if (isTestAddition) {
					episode = createEpisode(actions);
				}
			}
		}

		return episode;
	}

	private Episode createEpisode(List<Action> actions) {
		Episode episode = new Episode();
		episode.addActions(actions);
		episode.setClassification(DevelopmentType.TEST_ADDITION);
		return episode;
	}

}
