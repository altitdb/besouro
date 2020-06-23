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
import br.edu.utfpr.butterfly.model.action.TestCreationAction;
import br.edu.utfpr.butterfly.model.action.TestFailureAction;
import br.edu.utfpr.butterfly.model.action.TestPassAction;

public class UnknownClassifierTest {

	private Classifier classifier = new UnknownClassifier();

	@Test
	public void shoudReturnUnknownEpisodeWithSuccess() {
		List<Action> actions = new ArrayList<Action>();
		actions.add(new TestFailureAction(new Date(), null));
		actions.add(new CodeEditingAction(new Date(), null));
		actions.add(new TestPassAction(new Date(), null));
		Episode episode = classifier.classify(actions);
		Assert.assertEquals(DevelopmentType.UNKNOWN, episode.getCategory());
	}
	
	@Test
	public void shoudReturnUnknownEpisodeWithSuccessAndError() {
		List<Action> actions = new ArrayList<Action>();
		actions.add(new TestCreationAction(new Date(), null));
		actions.add(new TestFailureAction(new Date(), null));
		actions.add(new TestCreationAction(new Date(), null));
		actions.add(new TestPassAction(new Date(), null));
		Episode episode = classifier.classify(actions);
		Assert.assertEquals(DevelopmentType.UNKNOWN, episode.getCategory());
	}
}
