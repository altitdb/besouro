package besouro.classifier;

import java.util.List;

import besouro.model.DevelopmentType;
import besouro.model.Episode;
import besouro.model.action.Action;

public class ProductionClassifier implements Classifier {

	public Episode classify(List<Action> actions) {
		Episode episode = null;
		Boolean isProduction = true;
		for (Action action : actions) {
			if (!action.isProductionCodingAction()) {
				isProduction = false;
				break;
			}
		}
		
		if (isProduction) {
			episode = new Episode();
			episode.addActions(actions);
			episode.setClassification(DevelopmentType.PRODUCTION);
		}
		
		return episode;
	}

}
