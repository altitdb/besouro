package besouro.classifier;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import besouro.model.DevelopmentType;
import besouro.model.Episode;
import besouro.model.action.Action;
import besouro.model.action.ProductionCodingAction;
import besouro.model.action.TestCodingAction;
import besouro.model.action.TestCreationAction;
import besouro.model.action.TestFailureAction;
import besouro.model.action.TestSuccessfullAction;

public class RefactoringClassifierTest {

	private Classifier classifier = new RefactoringClassifier();

	@Test
	public void shoudReturnRefactoringEpisodeWithSuccessForCodeEditing() {
		List<Action> actions = new ArrayList<Action>();
		actions.add(new ProductionCodingAction(new Date(), null));
		actions.add(new TestSuccessfullAction(new Date(), null));
		Episode episode = classifier.classify(actions);
		Assert.assertEquals(DevelopmentType.REFACTORING, episode.getCategory());
	}
	
	@Test
	public void shoudReturnRefactoringEpisodeWithTestAndCodeEditing() {
		List<Action> actions = new ArrayList<Action>();
		actions.add(new TestCodingAction(new Date(), null));
		actions.add(new TestFailureAction(new Date(), null));
		actions.add(new ProductionCodingAction(new Date(), null));
		actions.add(new TestSuccessfullAction(new Date(), null));
		Episode episode = classifier.classify(actions);
		Assert.assertEquals(DevelopmentType.REFACTORING, episode.getCategory());
	}
	
	@Test
	public void shoudReturnRefactoringEpisodeWithCodeAndTestEditing() {
		List<Action> actions = new ArrayList<Action>();
		actions.add(new ProductionCodingAction(new Date(), null));
		actions.add(new TestFailureAction(new Date(), null));
		actions.add(new TestCodingAction(new Date(), null));
		actions.add(new TestSuccessfullAction(new Date(), null));
		Episode episode = classifier.classify(actions);
		Assert.assertEquals(DevelopmentType.REFACTORING, episode.getCategory());
	}

	@Test
	public void shoudReturnRefactoringEpisodeWithSuccessForTestEditing() {
		List<Action> actions = new ArrayList<Action>();
		actions.add(new TestCodingAction(new Date(), null));
		actions.add(new TestSuccessfullAction(new Date(), null));
		Episode episode = classifier.classify(actions);
		Assert.assertEquals(DevelopmentType.REFACTORING, episode.getCategory());
	}
	
	@Test
	public void shoudReturnRefactoringEpisodeWithErrorForCodeEditing() {
		List<Action> actions = new ArrayList<Action>();
		actions.add(new ProductionCodingAction(new Date(), null));
		actions.add(new TestFailureAction(new Date(), null));
		actions.add(new ProductionCodingAction(new Date(), null));
		actions.add(new TestSuccessfullAction(new Date(), null));
		Episode episode = classifier.classify(actions);
		Assert.assertEquals(DevelopmentType.REFACTORING, episode.getCategory());
	}
	
	@Test
	public void shoudReturnRefactoringEpisodeWithErrorForTestEditing() {
		List<Action> actions = new ArrayList<Action>();
		actions.add(new TestCodingAction(new Date(), null));
		actions.add(new TestFailureAction(new Date(), null));
		actions.add(new TestCodingAction(new Date(), null));
		actions.add(new TestSuccessfullAction(new Date(), null));
		Episode episode = classifier.classify(actions);
		Assert.assertEquals(DevelopmentType.REFACTORING, episode.getCategory());
	}
	
	@Test
	public void shoudReturnRefactoringEpisodeWithManyErrorsForCodeEditing() {
		List<Action> actions = new ArrayList<Action>();
		actions.add(new ProductionCodingAction(new Date(), null));
		actions.add(new TestFailureAction(new Date(), null));
		actions.add(new ProductionCodingAction(new Date(), null));
		actions.add(new TestFailureAction(new Date(), null));
		actions.add(new ProductionCodingAction(new Date(), null));
		actions.add(new TestFailureAction(new Date(), null));
		actions.add(new ProductionCodingAction(new Date(), null));
		actions.add(new TestSuccessfullAction(new Date(), null));
		Episode episode = classifier.classify(actions);
		Assert.assertEquals(DevelopmentType.REFACTORING, episode.getCategory());
	}
	
	@Test
	public void shoudReturnRefactoringEpisodeWithManyErrorsForTestEditing() {
		List<Action> actions = new ArrayList<Action>();
		actions.add(new TestCodingAction(new Date(), null));
		actions.add(new TestFailureAction(new Date(), null));
		actions.add(new TestCodingAction(new Date(), null));
		actions.add(new TestFailureAction(new Date(), null));
		actions.add(new TestCodingAction(new Date(), null));
		actions.add(new TestFailureAction(new Date(), null));
		actions.add(new TestCodingAction(new Date(), null));
		actions.add(new TestSuccessfullAction(new Date(), null));
		Episode episode = classifier.classify(actions);
		Assert.assertEquals(DevelopmentType.REFACTORING, episode.getCategory());
	}
	
	@Test
	public void shoudDoesntReturnRefactoringEpisode() {
		List<Action> actions = new ArrayList<Action>();
		actions.add(new TestCreationAction(new Date(), null));
		actions.add(new TestSuccessfullAction(new Date(), null));
		actions.add(new TestCodingAction(new Date(), null));
		actions.add(new TestFailureAction(new Date(), null));
		actions.add(new TestCodingAction(new Date(), null));
		actions.add(new TestFailureAction(new Date(), null));
		actions.add(new ProductionCodingAction(new Date(), null));
		actions.add(new TestCodingAction(new Date(), null));
		actions.add(new TestFailureAction(new Date(), null));
		actions.add(new TestCodingAction(new Date(), null));
		actions.add(new TestSuccessfullAction(new Date(), null));
		Episode episode = classifier.classify(actions);
		Assert.assertNull(episode);
	}
	
}
