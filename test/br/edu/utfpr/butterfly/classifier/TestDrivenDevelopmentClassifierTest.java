package br.edu.utfpr.butterfly.classifier;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import br.edu.utfpr.butterfly.classifier.Classifier;
import br.edu.utfpr.butterfly.classifier.TestDrivenDevelopmentClassifier;
import br.edu.utfpr.butterfly.model.DevelopmentType;
import br.edu.utfpr.butterfly.model.Episode;
import br.edu.utfpr.butterfly.model.action.Action;
import br.edu.utfpr.butterfly.model.action.CodeEditingAction;
import br.edu.utfpr.butterfly.model.action.TestEditingAction;
import br.edu.utfpr.butterfly.model.action.TestCreationAction;
import br.edu.utfpr.butterfly.model.action.TestFailureAction;
import br.edu.utfpr.butterfly.model.action.TestPassAction;

public class TestDrivenDevelopmentClassifierTest {

	private Classifier classifier = new TestDrivenDevelopmentClassifier();

	@Test
	public void shoudReturnTestDrivenDevelopmentEpisodeWithSuccessWithCodeRefactoring() {
		List<Action> actions = new ArrayList<Action>();
		actions.add(new TestCreationAction(new Date(), null));
		actions.add(new TestFailureAction(new Date(), null));
		actions.add(new CodeEditingAction(new Date(), null));
		actions.add(new TestPassAction(new Date(), null));
		actions.add(new CodeEditingAction(new Date(), null));
		actions.add(new TestPassAction(new Date(), null));
		Episode episode = classifier.classify(actions);
		Assert.assertEquals(DevelopmentType.TEST_DRIVEN_DEVELOPMENT, episode.getCategory());
	}
	
	@Test
	public void shoudReturnTestDrivenDevelopmentEpisodeWithSuccessWithTestRefactoring() {
		List<Action> actions = new ArrayList<Action>();
		actions.add(new TestCreationAction(new Date(), null));
		actions.add(new TestFailureAction(new Date(), null));
		actions.add(new CodeEditingAction(new Date(), null));
		actions.add(new TestPassAction(new Date(), null));
		actions.add(new TestEditingAction(new Date(), null));
		actions.add(new TestPassAction(new Date(), null));
		Episode episode = classifier.classify(actions);
		Assert.assertEquals(DevelopmentType.TEST_DRIVEN_DEVELOPMENT, episode.getCategory());
	}
	
	@Test
	public void shoudReturnTestDrivenDevelopmentEpisodeWithErrorWithCodeRefactoring() {
		List<Action> actions = new ArrayList<Action>();
		actions.add(new TestCreationAction(new Date(), null));
		actions.add(new TestFailureAction(new Date(), null));
		actions.add(new CodeEditingAction(new Date(), null));
		actions.add(new TestFailureAction(new Date(), null));
		actions.add(new CodeEditingAction(new Date(), null));
		actions.add(new TestPassAction(new Date(), null));
		actions.add(new CodeEditingAction(new Date(), null));
		actions.add(new TestFailureAction(new Date(), null));
		actions.add(new CodeEditingAction(new Date(), null));
		actions.add(new TestFailureAction(new Date(), null));
		actions.add(new CodeEditingAction(new Date(), null));
		actions.add(new TestPassAction(new Date(), null));
		Episode episode = classifier.classify(actions);
		Assert.assertEquals(DevelopmentType.TEST_DRIVEN_DEVELOPMENT, episode.getCategory());
	}
	
	@Test
	public void shoudReturnTestDrivenDevelopmentEpisodeWithErrorWithTestRefactoring() {
		List<Action> actions = new ArrayList<Action>();
		actions.add(new TestCreationAction(new Date(), null));
		actions.add(new TestFailureAction(new Date(), null));
		actions.add(new CodeEditingAction(new Date(), null));
		actions.add(new TestFailureAction(new Date(), null));
		actions.add(new CodeEditingAction(new Date(), null));
		actions.add(new TestPassAction(new Date(), null));
		actions.add(new TestEditingAction(new Date(), null));
		actions.add(new TestFailureAction(new Date(), null));
		actions.add(new TestEditingAction(new Date(), null));
		actions.add(new TestFailureAction(new Date(), null));
		actions.add(new TestEditingAction(new Date(), null));
		actions.add(new TestPassAction(new Date(), null));
		Episode episode = classifier.classify(actions);
		Assert.assertEquals(DevelopmentType.TEST_DRIVEN_DEVELOPMENT, episode.getCategory());
	}
	
	@Test
	public void shoudDoesntReturnTestDrivenDevelopmentEpisode() {
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
