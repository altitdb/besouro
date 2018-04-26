package besouro.classifier;

import java.util.List;

import besouro.model.Episode;
import besouro.model.action.Action;

public class EpisodeClassifier extends AbstractClassifier implements Classifier {

	public Episode classify(List<Action> actions) {
		List<Action> news = slimming(actions);
		
		Episode episode = null;
		
		if (news.size() > 5) {
			TestDrivenDevelopmentClassifier testDrivenDevelopment = new TestDrivenDevelopmentClassifier();
			episode = testDrivenDevelopment.classify(actions);
			
			if (episode == null) {
				TestFirstClassifier testFirst = new TestFirstClassifier();
				episode = testFirst.classify(actions);
			}
			
			if (episode == null) {
				TestLastClassifier testLast = new TestLastClassifier();
				episode = testLast.classify(actions);
			}
			
			if (episode == null) {
				TestAdditionClassifier testAddition = new TestAdditionClassifier();
				episode = testAddition.classify(actions);
			}
				
			if (episode == null) {
				RefactoringClassifier refactoring = new RefactoringClassifier();
				episode = refactoring.classify(actions);
			}
			
			if (episode == null) {
				ProductionClassifier production = new ProductionClassifier();
				episode = production.classify(actions);
			}
			
			if (episode == null) {
				UnknownClassifier unknown = new UnknownClassifier();
				episode = unknown.classify(actions);
			}
			
		}
		
		return episode;
	}

}
