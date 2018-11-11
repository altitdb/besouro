package br.edu.utfpr.butterfly.classifier;

import java.util.List;

import br.edu.utfpr.butterfly.model.Episode;
import br.edu.utfpr.butterfly.model.action.Action;

public class EpisodeClassifier extends AbstractClassifier implements Classifier {

	public Episode classify(List<Action> news) {
		Episode episode = null;
		
		if (!news.isEmpty()) {
			
			TestFirstClassifier testFirst = new TestFirstClassifier();
			episode = testFirst.classify(news);
			
			if (episode == null) {
				TestLastClassifier testLast = new TestLastClassifier();
				episode = testLast.classify(news);
			}
			
			if (episode == null) {
				TestAdditionClassifier testAddition = new TestAdditionClassifier();
				episode = testAddition.classify(news);
			}
			
			if (episode == null) {
				RefactoringClassifier refactoring = new RefactoringClassifier();
				episode = refactoring.classify(news);
			}
			
			if (episode == null) {
				UnknownClassifier unknown = new UnknownClassifier();
				episode = unknown.classify(news);
			}
		}
		return episode;
	}

}
