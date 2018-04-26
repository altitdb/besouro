package besouro.classifier;

import java.util.ArrayList;
import java.util.List;

import besouro.model.action.Action;

public abstract class AbstractClassifier {

	protected List<Action> slimming(List<Action> actions) {
		List<Action> newActions = new ArrayList<Action>();
		newActions.add(actions.get(0));

		Action last = newActions.get(0);
		for (Action action : actions) {
			if (!last.getClass().equals(action.getClass())) {
				newActions.add(action);
				last = action;
			}
		}
		
		if (newActions.get(0).isTestSuccessfullAction()) {
			newActions.remove(0);
		}
		
		return newActions;
	}
}
