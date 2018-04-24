package besouro.classifier;

import java.util.List;

import besouro.model.DevelopmentType;
import besouro.model.Episode;
import besouro.model.action.Action;

public class TestDrivenDevelopmentClassifier extends AbstractClassifier implements Classifier {

	public Episode classify(List<Action> paramActions) {
		
		createEpisode(paramActions);
		return null;
	}

	private Episode createEpisode(List<Action> actions) {
		Episode episode = new Episode();
		episode.addActions(actions);
		episode.setClassification(DevelopmentType.TEST_DRIVEN_DEVELOPMENT);
		return episode;
	}

}
