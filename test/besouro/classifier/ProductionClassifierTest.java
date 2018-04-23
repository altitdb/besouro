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

public class ProductionClassifierTest {

	private Classifier classifier = new ProductionClassifier();

	@Test
	public void shoudReturnProdutionEpisode() {
		List<Action> actions = new ArrayList<Action>();
		actions.add(new ProductionCodingAction(new Date(), null));
		Episode episode = classifier.classify(actions);
		Assert.assertEquals(DevelopmentType.PRODUCTION, episode.getCategory());
	}
	
	@Test
	public void shoudReturnProdutionEpisodeWithManyActions() {
		List<Action> actions = new ArrayList<Action>();
		actions.add(new ProductionCodingAction(new Date(), null));
		actions.add(new ProductionCodingAction(new Date(), null));
		actions.add(new ProductionCodingAction(new Date(), null));
		actions.add(new ProductionCodingAction(new Date(), null));
		Episode episode = classifier.classify(actions);
		Assert.assertEquals(DevelopmentType.PRODUCTION, episode.getCategory());
	}
	
	@Test
	public void shoudDoesntClassifierProdutionEpisode() {
		List<Action> actions = new ArrayList<Action>();
		actions.add(new ProductionCodingAction(new Date(), null));
		actions.add(new TestCodingAction(new Date(), null));
		Episode episode = classifier.classify(actions);
		Assert.assertNull(episode);
	}

}
