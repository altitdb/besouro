package besouro.classifier;

import java.util.List;

import besouro.model.DevelopmentType;
import besouro.model.Episode;
import besouro.model.action.Action;

public class UnknownClassifier extends AbstractClassifier implements Classifier {

	public Episode classify(List<Action> paramActions) {
		List<Action> actions = slimming(paramActions);

		Episode episode = null;
		if (!actions.isEmpty() && actions.get(0).isTestFailureAction()) {
			episode = createEpisode(paramActions);
		}
		
		return episode;
	}

	private Episode createEpisode(List<Action> actions) {
		Episode episode = new Episode();
		episode.addActions(actions);
		episode.setClassification(DevelopmentType.UNKNOWN);
		return episode;
	}

}
