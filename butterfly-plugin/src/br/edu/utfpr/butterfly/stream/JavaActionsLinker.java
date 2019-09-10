package br.edu.utfpr.butterfly.stream;

import java.util.HashMap;
import java.util.Map;

import br.edu.utfpr.butterfly.model.action.JavaFileAction;

public class JavaActionsLinker {

	private Map<String, JavaFileAction> previousEditActionPerFile = new HashMap<>();
	
	public void linkActions(JavaFileAction linkedAction) {
		String path = linkedAction.getResource();
		linkedAction.setPreviousAction(previousEditActionPerFile.get(path));
		previousEditActionPerFile.put(path, linkedAction);
	}
	
}
