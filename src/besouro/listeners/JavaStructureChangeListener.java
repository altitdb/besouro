package besouro.listeners;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jdt.core.ElementChangedEvent;
import org.eclipse.jdt.core.IElementChangedListener;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaElementDelta;

import besouro.model.action.Action;
import besouro.model.action.ProductionCodingAction;
import besouro.model.action.TestAction;
import besouro.model.action.TestCodingAction;
import besouro.model.action.TestCreationAction;
import besouro.stream.ActionOutputStream;


/**
 * Listens to the java element change events to get incremental work on java
 * objects and collect refactoring information for test-driven development
 * analysis purpose. It's declared as package private so that it can only be
 * instantiated by Eclipse sensor.
 * 
 * @author Hongbing Kou
 */
public class JavaStructureChangeListener implements IElementChangedListener {

	private static final String TEST_PACKAGE = "/test/";
	private static final String REMOVE_OPERATION = "REMOVE";
	private static final String ADD_OPERATION = "ADD";
	private static final String CHANGE_OPERATION = "CHANGE";
	private static final String METHOD_TYPE = "METHOD";
	private static final String PACKAGE_TYPE = "PACKAGE";
	private static final String CLASS_TYPE = "CLASS";
	public static final String JAVA = "java";
	public static final String CLASS = "Class";

	protected static final String PROP_CURRENT_SIZE = "Current-Size";
	protected static final String PROP_CLASS_NAME = "Class-Name";
	protected static final String PROP_CURRENT_METHODS = "Current-Methods";
	protected static final String PROP_CURRENT_STATEMENTS = "Current-Statements";
	protected static final String PROP_CURRENT_TEST_METHODS = "Current-Test-Methods";
	protected static final String PROP_CURRENT_TEST_ASSERTIONS = "Current-Test-Assertions";

	private ActionOutputStream stream;
	private Action lastAction;

	public JavaStructureChangeListener(ActionOutputStream stream) {
		this.stream = stream;
	}

	public void elementChanged(ElementChangedEvent event) {
		IJavaElementDelta[] childrenChanges = event.getDelta().getAffectedChildren();
		if (childrenChanges != null && childrenChanges.length > 0) {
			javaObjectChange(childrenChanges[0]);
		}
	}

	private void javaObjectChange(IJavaElementDelta jed) {
		List<IJavaElementDelta> additions = new ArrayList<IJavaElementDelta>();
		List<IJavaElementDelta> deletions = new ArrayList<IJavaElementDelta>();
		List<IJavaElementDelta> changes = new ArrayList<IJavaElementDelta>();
		traverse(jed, additions, deletions, changes);

		IResource javaFile = jed.getElement().getResource();

		if (deletions.size() == 1 && additions.size() == 1) {
			IJavaElementDelta fromDelta = deletions.get(0);
			IJavaElementDelta toDelta = additions.get(0);

			if (fromDelta.getElement().getParent().equals(toDelta.getElement().getParent())) {
				processRename(javaFile, fromDelta, toDelta);
			} else {
				processMove(fromDelta, toDelta);
			}
		} else if (!additions.isEmpty()) {
			for (IJavaElementDelta i : additions) {
				processUnary(javaFile, ADD_OPERATION, i);
			}
		} else if (!deletions.isEmpty()) {
			for (IJavaElementDelta i : deletions) {
				if (!i.toString().contains("{PRIMARY WORKING COPY}")) {
					processUnary(javaFile, REMOVE_OPERATION, i);
				}
			}
		} else if (!changes.isEmpty()){
			for (IJavaElementDelta i : changes) {
				processUnary(javaFile, CHANGE_OPERATION, i);
			}
		}
	}

	private void processUnary(IResource javaFile, String operation, IJavaElementDelta delta) {
		
		IJavaElement element = delta.getElement();
		if (javaFile == null || element == null || element.getResource() == null) {
			return;
		}

		String type = retrieveType(element);
		if (type == null) {
			return;
		}
 
		IPath classFileName = javaFile.getLocation();
		if (CLASS_TYPE.equals(type)) {
			classFileName = element.getResource().getLocation();
		}

		if (JAVA.equals(classFileName.getFileExtension())) {
			String name = buildElementName(element.toString());
			if (name != null && !"".equals(name)) {
				if (classFileName.toString().contains(TEST_PACKAGE)) {
					TestAction action;
					boolean isAddMethod = METHOD_TYPE.equals(type) && ADD_OPERATION.equals(operation);
					if (isAddMethod || (lastAction != null && lastAction.isTestCreationAction())) {
						action = new TestCreationAction(new Date(), element.getResource().getName());
					} else {
						action = new TestCodingAction(new Date(), element.getResource().getName());
					}
					action.setOperator(operation);
					action.setSubjectType(type);
					action.setSubjectName(name);
					this.stream.addAction(action);
					lastAction = action;
				} else {
					ProductionCodingAction action = new ProductionCodingAction(new Date(), element.getResource().getName());
					action.setOperator(operation);
					action.setSubjectType(type);
					action.setSubjectName(name);
					this.stream.addAction(action);
					lastAction = action;
				}
			}
		}
	}

	private void processRename(IResource javaFile, IJavaElementDelta fromDelta, IJavaElementDelta toDelta) {

		String type = retrieveType(toDelta.getElement());

		IPath classFileName = javaFile.getLocation();
		if (CLASS_TYPE.equals(type) || PACKAGE_TYPE.equals(type)) {
			classFileName = fromDelta.getElement().getResource().getLocation();
		}

		if (JAVA.equals(classFileName.getFileExtension())) {
			String fromName = buildElementName(fromDelta.getElement().toString());
			String toName = buildElementName(toDelta.getElement().toString());
			
			if (fromName != null && toName != null && !fromName.equals(toName)) {
				if (classFileName.toString().contains(TEST_PACKAGE)) {
					TestAction action;
					if (lastAction != null && lastAction.isTestCreationAction()) {
						action = new TestCreationAction(new Date(), javaFile.getName());
					} else {
						action = new TestCodingAction(new Date(), javaFile.getName());
					}
					action.setOperator("RENAME");
					action.setSubjectName(fromName + "=>" + toName);
					action.setSubjectType(type);
					this.stream.addAction(action);
					lastAction = action;
				} else {
					ProductionCodingAction action = new ProductionCodingAction(new Date(), javaFile.getName());
					action.setOperator("RENAME");
					action.setSubjectName(fromName + "=>" + toName);
					action.setSubjectType(type);
					this.stream.addAction(action);
					lastAction = action;
				}
			}
		}
	}

	private void processMove(IJavaElementDelta fromDelta, IJavaElementDelta toDelta) {

		String type = retrieveType(toDelta.getElement());
		
		IResource javaFile = fromDelta.getElement().getResource();
		IJavaElement from = fromDelta.getElement();
		IJavaElement to = toDelta.getElement().getParent();

		if (JAVA.equals(javaFile.getFileExtension())) {
			String fromName = buildElementName(from.toString());
			String toName = buildElementName(to.toString());
			
			if (fromName != null && toName != null && !fromName.equals(toName)) {
				if (javaFile.toString().contains(TEST_PACKAGE)) {
					TestAction action;
					if (lastAction != null && lastAction.isTestCreationAction()) {
						action = new TestCreationAction(new Date(), javaFile.getName());
					} else {
						action = new TestCodingAction(new Date(), javaFile.getName());
					}
					action.setOperator("MOVE");
					action.setSubjectName(fromName + "=>" + toName);
					action.setSubjectType(type);
					this.stream.addAction(action);
					lastAction = action;
				} else {
					ProductionCodingAction action = new ProductionCodingAction(new Date(), javaFile.getName());
					action.setOperator("MOVE");
					action.setSubjectName(fromName + "=>" + toName);
					action.setSubjectType(type);
					this.stream.addAction(action);
					lastAction = action;
				}
			}
		}
	}

	private String retrieveType(IJavaElement element) {
		int eType = element.getElementType();

		switch (eType) {
		case IJavaElement.FIELD:
			return "FIELD";
		case IJavaElement.METHOD:
			return METHOD_TYPE;
		case IJavaElement.IMPORT_DECLARATION:
			return "IMPORT";
		case IJavaElement.IMPORT_CONTAINER:
			return "IMPORT";
		case IJavaElement.COMPILATION_UNIT:
			return CLASS_TYPE;
		case IJavaElement.JAVA_PROJECT:
			return CLASS_TYPE;
		case IJavaElement.PACKAGE_FRAGMENT:
			return PACKAGE_TYPE;
		default:
			return null;
		}
	}

	private String buildElementName(String name) {
		if (name.startsWith("[Working copy] ")) {
			name = name.replace("[Working copy] ", "");
			int index = name.indexOf('[');
			if (index >=0) {
				name = name.substring(0, index);
			}
		} else {
			int index = name.indexOf('[');
			if (index >=0) {
				name = name.substring(0, index);
			}
			int pos = name.indexOf("(not open)");
			if (pos > 0) {
				name = name.substring(0, pos);
			}
		}

		name = name.replace('#', '/');
		return name.trim();
	}


	private void traverse(IJavaElementDelta delta, List<IJavaElementDelta> additions, List<IJavaElementDelta> deletions, List<IJavaElementDelta> changes) {
		if (delta.getKind() == IJavaElementDelta.ADDED) {
			additions.add(delta);
		} else if (delta.getKind() == IJavaElementDelta.REMOVED) {
			deletions.add(delta);
		} else if (delta.getKind() == IJavaElementDelta.CHANGED) {
			changes.add(delta);
		}

		IJavaElementDelta[] children = delta.getAffectedChildren();
		for (int i = 0; i < children.length; i++) {
			traverse(children[i], additions, deletions, changes);
		}
	}
}