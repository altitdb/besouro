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

public class TestFirstClassifierTest {

	private Classifier classifier = new TestFirstClassifier();

	@Test
	public void shoudReturnTestFirstEpisodeWithSuccess() {
		List<Action> actions = new ArrayList<Action>();
		actions.add(new TestCreationAction(new Date(), null));
		actions.add(new ProductionCodingAction(new Date(), null));
		actions.add(new TestSuccessfullAction(new Date(), null));
		Episode episode = classifier.classify(actions);
		Assert.assertEquals(DevelopmentType.TEST_FIRST, episode.getCategory());
	}
	
	@Test
	public void shoudReturnTestFirstEpisodeWithError() {
		List<Action> actions = new ArrayList<Action>();
		actions.add(new TestCreationAction(new Date(), null));
		actions.add(new TestFailureAction(new Date(), null));
		actions.add(new ProductionCodingAction(new Date(), null));
		actions.add(new TestSuccessfullAction(new Date(), null));
		Episode episode = classifier.classify(actions);
		Assert.assertEquals(DevelopmentType.TEST_FIRST, episode.getCategory());
	}
	
	@Test
	public void shoudReturnTestFirstEpisodeWithManyErrors() {
		List<Action> actions = new ArrayList<Action>();
		actions.add(new TestCreationAction(new Date(), null));
		actions.add(new TestFailureAction(new Date(), null));
		actions.add(new ProductionCodingAction(new Date(), null));
		actions.add(new TestFailureAction(new Date(), null));
		actions.add(new ProductionCodingAction(new Date(), null));
		actions.add(new TestFailureAction(new Date(), null));
		actions.add(new ProductionCodingAction(new Date(), null));
		actions.add(new TestSuccessfullAction(new Date(), null));
		Episode episode = classifier.classify(actions);
		Assert.assertEquals(DevelopmentType.TEST_FIRST, episode.getCategory());
	}
	
	@Test
	public void shoudReturnTestFirstEpisodeWithErrorAndFix() {
		List<Action> actions = new ArrayList<Action>();
		actions.add(new TestCreationAction(new Date(), null));
		actions.add(new TestCodingAction(new Date(), null));
		actions.add(new TestFailureAction(new Date(), null));
		actions.add(new TestCodingAction(new Date(), null));
		actions.add(new TestSuccessfullAction(new Date(), null));
		Episode episode = classifier.classify(actions);
		Assert.assertEquals(DevelopmentType.TEST_FIRST, episode.getCategory());
	}
	
	@Test
	public void shoudReturnTestFirstEpisodeWithManyErrorsAndFix() {
		List<Action> actions = new ArrayList<Action>();
		actions.add(new TestCreationAction(new Date(), null));
		actions.add(new TestCodingAction(new Date(), null));
		actions.add(new TestFailureAction(new Date(), null));
		actions.add(new TestCodingAction(new Date(), null));
		actions.add(new TestFailureAction(new Date(), null));
		actions.add(new TestCodingAction(new Date(), null));
		actions.add(new TestFailureAction(new Date(), null));
		actions.add(new TestCodingAction(new Date(), null));
		actions.add(new TestSuccessfullAction(new Date(), null));
		Episode episode = classifier.classify(actions);
		Assert.assertEquals(DevelopmentType.TEST_FIRST, episode.getCategory());
	}
	
	@Test
	public void shoudDoesntReturnTestFirstEpisode() {
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
