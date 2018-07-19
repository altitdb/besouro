package br.edu.utfpr.butterfly.classifier;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import br.edu.utfpr.butterfly.classifier.Classifier;
import br.edu.utfpr.butterfly.classifier.TestFirstClassifier;
import br.edu.utfpr.butterfly.model.DevelopmentType;
import br.edu.utfpr.butterfly.model.Episode;
import br.edu.utfpr.butterfly.model.action.Action;
import br.edu.utfpr.butterfly.model.action.CodeEditingAction;
import br.edu.utfpr.butterfly.model.action.TestEditingAction;
import br.edu.utfpr.butterfly.model.action.TestCreationAction;
import br.edu.utfpr.butterfly.model.action.TestFailureAction;
import br.edu.utfpr.butterfly.model.action.TestPassAction;

public class TestFirstClassifierTest {

	private Classifier classifier = new TestFirstClassifier();

	@Test
	public void shoudReturnTestFirstEpisodeWithSuccess() {
		List<Action> actions = new ArrayList<Action>();
		actions.add(new TestCreationAction(new Date(), null));
		actions.add(new CodeEditingAction(new Date(), null));
		actions.add(new TestPassAction(new Date(), null));
		Episode episode = classifier.classify(actions);
		Assert.assertEquals(DevelopmentType.TEST_FIRST, episode.getCategory());
	}
	
	@Test
	public void shoudReturnTestFirstEpisodeWithError() {
		List<Action> actions = new ArrayList<Action>();
		actions.add(new TestCreationAction(new Date(), null));
		actions.add(new TestFailureAction(new Date(), null));
		actions.add(new CodeEditingAction(new Date(), null));
		actions.add(new TestPassAction(new Date(), null));
		Episode episode = classifier.classify(actions);
		Assert.assertEquals(DevelopmentType.TEST_FIRST, episode.getCategory());
	}
	
	@Test
	public void shoudReturnTestFirstEpisodeWithManyErrors() {
		List<Action> actions = new ArrayList<Action>();
		actions.add(new TestCreationAction(new Date(), null));
		actions.add(new TestFailureAction(new Date(), null));
		actions.add(new CodeEditingAction(new Date(), null));
		actions.add(new TestFailureAction(new Date(), null));
		actions.add(new CodeEditingAction(new Date(), null));
		actions.add(new TestFailureAction(new Date(), null));
		actions.add(new CodeEditingAction(new Date(), null));
		actions.add(new TestPassAction(new Date(), null));
		Episode episode = classifier.classify(actions);
		Assert.assertEquals(DevelopmentType.TEST_FIRST, episode.getCategory());
	}
	
	@Test
	public void shoudReturnTestFirstEpisodeWithErrorAndFix() {
		List<Action> actions = new ArrayList<Action>();
		actions.add(new TestCreationAction(new Date(), null));
		actions.add(new TestEditingAction(new Date(), null));
		actions.add(new TestFailureAction(new Date(), null));
		actions.add(new TestEditingAction(new Date(), null));
		actions.add(new TestPassAction(new Date(), null));
		Episode episode = classifier.classify(actions);
		Assert.assertEquals(DevelopmentType.TEST_FIRST, episode.getCategory());
	}
	
	@Test
	public void shoudReturnTestFirstEpisodeWithManyErrorsAndFix() {
		List<Action> actions = new ArrayList<Action>();
		actions.add(new TestCreationAction(new Date(), null));
		actions.add(new TestEditingAction(new Date(), null));
		actions.add(new TestFailureAction(new Date(), null));
		actions.add(new TestEditingAction(new Date(), null));
		actions.add(new TestFailureAction(new Date(), null));
		actions.add(new TestEditingAction(new Date(), null));
		actions.add(new TestFailureAction(new Date(), null));
		actions.add(new TestEditingAction(new Date(), null));
		actions.add(new TestPassAction(new Date(), null));
		Episode episode = classifier.classify(actions);
		Assert.assertEquals(DevelopmentType.TEST_FIRST, episode.getCategory());
	}
	
	@Test
	public void shoudDoesntReturnTestFirstEpisode() {
		List<Action> actions = new ArrayList<Action>();
		actions.add(new TestCreationAction(new Date(), null));
		actions.add(new TestPassAction(new Date(), null));
		actions.add(new TestEditingAction(new Date(), null));
		actions.add(new TestFailureAction(new Date(), null));
		actions.add(new TestEditingAction(new Date(), null));
		actions.add(new TestFailureAction(new Date(), null));
		actions.add(new CodeEditingAction(new Date(), null));
		actions.add(new TestEditingAction(new Date(), null));
		actions.add(new TestFailureAction(new Date(), null));
		actions.add(new TestEditingAction(new Date(), null));
		actions.add(new TestPassAction(new Date(), null));
		Episode episode = classifier.classify(actions);
		Assert.assertNull(episode);
	}
	
}
