package besouro.classifier;

import java.util.List;

import besouro.model.Episode;
import besouro.model.action.Action;

public class EpisodeClassifier implements Classifier {

	public Episode classify(List<Action> actions) {
		System.out.println(actions);
		
		TestAdditionClassifier testAddition = new TestAdditionClassifier();
		Episode episode = testAddition.classify(actions);
				
		if (episode == null) {
			ProductionClassifier production = new ProductionClassifier();
			episode = production.classify(actions);
		}
		
		
		return episode;
	}

}
