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
import besouro.model.action.TestFailureAction;
import besouro.model.action.TestSuccessfullAction;

public class UnknownClassifierTest {

	private Classifier classifier = new UnknownClassifier();

	@Test
	public void shoudReturnUnknownEpisodeWithSuccess() {
		List<Action> actions = new ArrayList<Action>();
		actions.add(new TestFailureAction(new Date(), null));
		actions.add(new ProductionCodingAction(new Date(), null));
		actions.add(new TestSuccessfullAction(new Date(), null));
		Episode episode = classifier.classify(actions);
		Assert.assertEquals(DevelopmentType.UNKNOWN, episode.getCategory());
	}
	
}
