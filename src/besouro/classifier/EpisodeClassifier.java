package besouro.classifier;

import java.util.List;

import besouro.model.Episode;
import besouro.model.action.Action;

public class EpisodeClassifier implements Classifier {

	public Episode classify(List<Action> actions) {
		System.out.println(actions);
		
		ProductionClassifier production = new ProductionClassifier();
		Episode episode = production.classify(actions);
		
		
		return episode;
	}

}
