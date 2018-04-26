package besouro.stream;

import besouro.model.action.Action;

public interface ActionOutputStream {

	void addAction(Action action);
	
	default Action getFirstAction() {
		return null;
	}
	
	default Action getLastAction() {
		return null;
	}
	
}
