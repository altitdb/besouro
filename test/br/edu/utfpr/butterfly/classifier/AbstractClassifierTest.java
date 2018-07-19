package br.edu.utfpr.butterfly.classifier;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import br.edu.utfpr.butterfly.classifier.AbstractClassifier;
import br.edu.utfpr.butterfly.model.action.Action;
import br.edu.utfpr.butterfly.model.action.CodeEditingAction;
import br.edu.utfpr.butterfly.model.action.TestEditingAction;
import br.edu.utfpr.butterfly.model.action.TestPassAction;

public class AbstractClassifierTest {

	@Test
	public void shouldRemoveDuplicateActions() {
		List<Action> actions = new ArrayList<Action>();
		actions.add(new CodeEditingAction(new Date(), null));
		actions.add(new CodeEditingAction(new Date(), null));
		actions.add(new TestEditingAction(new Date(), null));
		actions.add(new CodeEditingAction(new Date(), null));
		actions.add(new TestEditingAction(new Date(), null));
		actions.add(new TestEditingAction(new Date(), null));
		actions.add(new TestPassAction(new Date(), null));
		actions.add(new TestPassAction(new Date(), null));
		
		AbstractClassifier classifier = new AbstractClassifier() {
			
		};
		
		List<Action> expected = classifier.slimming(actions);
		Assert.assertEquals(5, expected.size());
	}

}
