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

public class TestAdditionClassifierTest {

	private Classifier classifier = new TestAdditionClassifier();

	@Test
	public void test() {
		Assert.assertSame(14,  14);
	}
	@Test
	public void shoudReturnTestAdditionEpisodeWithSuccess() {
		List<Action> actions = new ArrayList<Action>();
		actions.add(new TestCreationAction(new Date(), null));
		actions.add(new TestSuccessfullAction(new Date(), null));
		Episode episode = classifier.classify(actions);
		Assert.assertEquals(DevelopmentType.TEST_ADDITION, episode.getCategory());
	}
	
	@Test
	public void shoudReturnTestAdditionEpisodeWithError() {
		List<Action> actions = new ArrayList<Action>();
		actions.add(new TestCreationAction(new Date(), null));
		actions.add(new TestFailureAction(new Date(), null));
		actions.add(new TestCodingAction(new Date(), null));
		actions.add(new TestSuccessfullAction(new Date(), null));
		Episode episode = classifier.classify(actions);
		Assert.assertEquals(DevelopmentType.TEST_ADDITION, episode.getCategory());
	}
	
	@Test
	public void shoudReturnTestAdditionEpisodeWithErrorAndFix() {
		List<Action> actions = new ArrayList<Action>();
		actions.add(new TestCreationAction(new Date(), null));
		actions.add(new TestFailureAction(new Date(), null));
		actions.add(new TestCodingAction(new Date(), null));
		actions.add(new TestFailureAction(new Date(), null));
		actions.add(new TestCodingAction(new Date(), null));
		actions.add(new TestSuccessfullAction(new Date(), null));
		Episode episode = classifier.classify(actions);
		Assert.assertEquals(DevelopmentType.TEST_ADDITION, episode.getCategory());
	}
	
	@Test
	public void shoudReturnTestAdditionEpisodeWithManyErrorsAndFixes() {
		List<Action> actions = new ArrayList<Action>();
		actions.add(new TestCreationAction(new Date(), null));
		actions.add(new TestFailureAction(new Date(), null));
		actions.add(new TestCodingAction(new Date(), null));
		actions.add(new TestFailureAction(new Date(), null));
		actions.add(new TestCodingAction(new Date(), null));
		actions.add(new TestFailureAction(new Date(), null));
		actions.add(new TestCodingAction(new Date(), null));
		actions.add(new TestFailureAction(new Date(), null));
		actions.add(new TestCodingAction(new Date(), null));
		actions.add(new TestFailureAction(new Date(), null));
		actions.add(new TestCodingAction(new Date(), null));
		actions.add(new TestSuccessfullAction(new Date(), null));
		Episode episode = classifier.classify(actions);
		Assert.assertEquals(DevelopmentType.TEST_ADDITION, episode.getCategory());
	}
	
	@Test
	public void shoudDoesntReturnTestAdditionEpisode() {
		List<Action> actions = new ArrayList<Action>();
		actions.add(new TestCreationAction(new Date(), null));
		actions.add(new TestFailureAction(new Date(), null));
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
