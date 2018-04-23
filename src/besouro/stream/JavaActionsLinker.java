package besouro.stream;

import java.util.HashMap;
import java.util.Map;

import besouro.model.action.JavaFileAction;

public class JavaActionsLinker {

	private Map<String, JavaFileAction> previousEditActionPerFile = new HashMap<String, JavaFileAction>();
	
	public void linkActions(JavaFileAction linkedAction) {
		String path = linkedAction.getResource();
		linkedAction.setPreviousAction(previousEditActionPerFile.get(path));
		previousEditActionPerFile.put(path, linkedAction);
	}
	
}
