package br.edu.utfpr.butterfly.classifier;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import br.edu.utfpr.butterfly.model.DevelopmentType;
import br.edu.utfpr.butterfly.model.Episode;
import br.edu.utfpr.butterfly.model.action.Action;
import br.edu.utfpr.butterfly.model.action.CodeEditingAction;
import br.edu.utfpr.butterfly.model.action.TestEditingAction;
import br.edu.utfpr.butterfly.model.action.TestCreationAction;
import br.edu.utfpr.butterfly.model.action.TestFailureAction;
import br.edu.utfpr.butterfly.model.action.TestPassAction;

public class RefactoringClassifierTest {

	private Classifier classifier = new RefactoringClassifier();

	@Test
	public void shoudReturnRefactoringEpisodeWithSuccessForCodeEditing() {
		List<Action> actions = new ArrayList<Action>();
		actions.add(new CodeEditingAction(new Date(), null));
		actions.add(new TestPassAction(new Date(), null));
		Episode episode = classifier.classify(actions);
		Assert.assertEquals(DevelopmentType.REFACTORING, episode.getCategory());
	}
	
	@Test
	public void shoudReturnRefactoringEpisodeWithTestAndCodeEditing() {
		List<Action> actions = new ArrayList<Action>();
		actions.add(new TestEditingAction(new Date(), null));
		actions.add(new TestFailureAction(new Date(), null));
		actions.add(new CodeEditingAction(new Date(), null));
		actions.add(new TestPassAction(new Date(), null));
		Episode episode = classifier.classify(actions);
		Assert.assertEquals(DevelopmentType.REFACTORING, episode.getCategory());
	}
	
	@Test
	public void shoudReturnRefactoringEpisodeWithCodeAndTestEditing() {
		List<Action> actions = new ArrayList<Action>();
		actions.add(new CodeEditingAction(new Date(), null));
		actions.add(new TestFailureAction(new Date(), null));
		actions.add(new TestEditingAction(new Date(), null));
		actions.add(new TestPassAction(new Date(), null));
		Episode episode = classifier.classify(actions);
		Assert.assertEquals(DevelopmentType.REFACTORING, episode.getCategory());
	}

	@Test
	public void shoudReturnRefactoringEpisodeWithSuccessForTestEditing() {
		List<Action> actions = new ArrayList<Action>();
		actions.add(new TestEditingAction(new Date(), null));
		actions.add(new TestPassAction(new Date(), null));
		Episode episode = classifier.classify(actions);
		Assert.assertEquals(DevelopmentType.REFACTORING, episode.getCategory());
	}
	
	@Test
	public void shoudReturnRefactoringEpisodeWithErrorForCodeEditing() {
		List<Action> actions = new ArrayList<Action>();
		actions.add(new CodeEditingAction(new Date(), null));
		actions.add(new TestFailureAction(new Date(), null));
		actions.add(new CodeEditingAction(new Date(), null));
		actions.add(new TestPassAction(new Date(), null));
		Episode episode = classifier.classify(actions);
		Assert.assertEquals(DevelopmentType.REFACTORING, episode.getCategory());
	}
	
	@Test
	public void shoudReturnRefactoringEpisodeWithErrorForTestEditing() {
		List<Action> actions = new ArrayList<Action>();
		actions.add(new TestEditingAction(new Date(), null));
		actions.add(new TestFailureAction(new Date(), null));
		actions.add(new TestEditingAction(new Date(), null));
		actions.add(new TestPassAction(new Date(), null));
		Episode episode = classifier.classify(actions);
		Assert.assertEquals(DevelopmentType.REFACTORING, episode.getCategory());
	}
	
	@Test
	public void shoudReturnRefactoringEpisodeWithManyErrorsForCodeEditing() {
		List<Action> actions = new ArrayList<Action>();
		actions.add(new CodeEditingAction(new Date(), null));
		actions.add(new TestFailureAction(new Date(), null));
		actions.add(new CodeEditingAction(new Date(), null));
		actions.add(new TestFailureAction(new Date(), null));
		actions.add(new CodeEditingAction(new Date(), null));
		actions.add(new TestFailureAction(new Date(), null));
		actions.add(new CodeEditingAction(new Date(), null));
		actions.add(new TestPassAction(new Date(), null));
		Episode episode = classifier.classify(actions);
		Assert.assertEquals(DevelopmentType.REFACTORING, episode.getCategory());
	}
	
	@Test
	public void shoudReturnRefactoringEpisodeWithManyErrorsForTestEditing() {
		List<Action> actions = new ArrayList<Action>();
		actions.add(new TestEditingAction(new Date(), null));
		actions.add(new TestFailureAction(new Date(), null));
		actions.add(new TestEditingAction(new Date(), null));
		actions.add(new TestFailureAction(new Date(), null));
		actions.add(new TestEditingAction(new Date(), null));
		actions.add(new TestFailureAction(new Date(), null));
		actions.add(new TestEditingAction(new Date(), null));
		actions.add(new TestPassAction(new Date(), null));
		Episode episode = classifier.classify(actions);
		Assert.assertEquals(DevelopmentType.REFACTORING, episode.getCategory());
	}
	
	@Test
	public void shoudDoesntReturnRefactoringEpisode() {
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
