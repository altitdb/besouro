package besouro.classifier;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import besouro.model.action.Action;
import besouro.model.action.ProductionCodingAction;
import besouro.model.action.TestCodingAction;
import besouro.model.action.TestSuccessfullAction;

public class AbstractClassifierTest {

	@Test
	public void shouldRemoveDuplicateActions() {
		List<Action> actions = new ArrayList<Action>();
		actions.add(new ProductionCodingAction(new Date(), null));
		actions.add(new ProductionCodingAction(new Date(), null));
		actions.add(new TestCodingAction(new Date(), null));
		actions.add(new ProductionCodingAction(new Date(), null));
		actions.add(new TestCodingAction(new Date(), null));
		actions.add(new TestCodingAction(new Date(), null));
		actions.add(new TestSuccessfullAction(new Date(), null));
		actions.add(new TestSuccessfullAction(new Date(), null));
		
		AbstractClassifier classifier = new AbstractClassifier() {
			
		};
		
		List<Action> expected = classifier.slimming(actions);
		Assert.assertEquals(5, expected.size());
	}

}
