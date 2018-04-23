package besouro.classifier;

import java.util.List;

import besouro.model.DevelopmentType;
import besouro.model.Episode;
import besouro.model.action.Action;

public class ProductionClassifier extends AbstractClassifier implements Classifier {

	public Episode classify(List<Action> paramActions) {
		List<Action> actions = slimming(paramActions);

		Episode episode = null;
		if (actions.size() == 1 && actions.get(0).isProductionCodingAction()) {
			episode = new Episode();
			episode.addActions(actions);
			episode.setClassification(DevelopmentType.PRODUCTION);
		}
		
		return episode;
	}

}
