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

public class TestDrivenDevelopmentClassifierTest {

	private Classifier classifier = new TestDrivenDevelopmentClassifier();

	@Test
	public void shoudReturnTestDrivenDevelopmentEpisodeWithSuccessWithCodeRefactoring() {
		List<Action> actions = new ArrayList<Action>();
		actions.add(new TestCreationAction(new Date(), null));
		actions.add(new TestFailureAction(new Date(), null));
		actions.add(new ProductionCodingAction(new Date(), null));
		actions.add(new TestSuccessfullAction(new Date(), null));
		actions.add(new ProductionCodingAction(new Date(), null));
		actions.add(new TestSuccessfullAction(new Date(), null));
		Episode episode = classifier.classify(actions);
		Assert.assertEquals(DevelopmentType.TEST_DRIVEN_DEVELOPMENT, episode.getCategory());
	}
	
	@Test
	public void shoudReturnTestDrivenDevelopmentEpisodeWithSuccessWithTestRefactoring() {
		List<Action> actions = new ArrayList<Action>();
		actions.add(new TestCreationAction(new Date(), null));
		actions.add(new TestFailureAction(new Date(), null));
		actions.add(new ProductionCodingAction(new Date(), null));
		actions.add(new TestSuccessfullAction(new Date(), null));
		actions.add(new TestCodingAction(new Date(), null));
		actions.add(new TestSuccessfullAction(new Date(), null));
		Episode episode = classifier.classify(actions);
		Assert.assertEquals(DevelopmentType.TEST_DRIVEN_DEVELOPMENT, episode.getCategory());
	}
	
	@Test
	public void shoudReturnTestDrivenDevelopmentEpisodeWithErrorWithCodeRefactoring() {
		List<Action> actions = new ArrayList<Action>();
		actions.add(new TestCreationAction(new Date(), null));
		actions.add(new TestFailureAction(new Date(), null));
		actions.add(new ProductionCodingAction(new Date(), null));
		actions.add(new TestFailureAction(new Date(), null));
		actions.add(new ProductionCodingAction(new Date(), null));
		actions.add(new TestSuccessfullAction(new Date(), null));
		actions.add(new ProductionCodingAction(new Date(), null));
		actions.add(new TestFailureAction(new Date(), null));
		actions.add(new ProductionCodingAction(new Date(), null));
		actions.add(new TestFailureAction(new Date(), null));
		actions.add(new ProductionCodingAction(new Date(), null));
		actions.add(new TestSuccessfullAction(new Date(), null));
		Episode episode = classifier.classify(actions);
		Assert.assertEquals(DevelopmentType.TEST_DRIVEN_DEVELOPMENT, episode.getCategory());
	}
	
	@Test
	public void shoudReturnTestDrivenDevelopmentEpisodeWithErrorWithTestRefactoring() {
		List<Action> actions = new ArrayList<Action>();
		actions.add(new TestCreationAction(new Date(), null));
		actions.add(new TestFailureAction(new Date(), null));
		actions.add(new ProductionCodingAction(new Date(), null));
		actions.add(new TestFailureAction(new Date(), null));
		actions.add(new ProductionCodingAction(new Date(), null));
		actions.add(new TestSuccessfullAction(new Date(), null));
		actions.add(new TestCodingAction(new Date(), null));
		actions.add(new TestFailureAction(new Date(), null));
		actions.add(new TestCodingAction(new Date(), null));
		actions.add(new TestFailureAction(new Date(), null));
		actions.add(new TestCodingAction(new Date(), null));
		actions.add(new TestSuccessfullAction(new Date(), null));
		Episode episode = classifier.classify(actions);
		Assert.assertEquals(DevelopmentType.TEST_DRIVEN_DEVELOPMENT, episode.getCategory());
	}
	
	@Test
	public void shoudDoesntReturnTestDrivenDevelopmentEpisode() {
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
