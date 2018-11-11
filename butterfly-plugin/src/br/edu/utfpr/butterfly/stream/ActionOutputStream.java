package br.edu.utfpr.butterfly.stream;

import br.edu.utfpr.butterfly.model.action.Action;

public interface ActionOutputStream {

	void addAction(Action action);
	
	default Action getFirstAction() {
		return null;
	}
	
	default Action getLastAction() {
		return null;
	}
	
}
