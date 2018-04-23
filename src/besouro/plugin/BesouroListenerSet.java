package besouro.plugin;

import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.junit.JUnitCore;

import besouro.listeners.JUnitListener;
import besouro.listeners.JavaStructureChangeListener;
import besouro.listeners.ResourceChangeListener;
import besouro.listeners.WindowListener;
import besouro.model.action.Action;
import besouro.stream.ActionOutputStream;

public class BesouroListenerSet implements ActionOutputStream {

	private static final BesouroListenerSet BESOURO_LISTENER = new BesouroListenerSet();
	
	public static BesouroListenerSet getSingleton() {
		return BESOURO_LISTENER;
	}

	private WindowListener windowListener;
	private ActionOutputStream output;
	private ResourceChangeListener resourceListener;
	private JavaStructureChangeListener javaListener;
	private JUnitListener junitListener;
	
	private BesouroListenerSet(){
		windowListener = new WindowListener(this);
		resourceListener = new ResourceChangeListener(this);
		javaListener = new JavaStructureChangeListener(this);
		junitListener = new JUnitListener(this);
	}
	
	public WindowListener getWindowListener() {
		return windowListener;
	}

	public void registerListenersInEclipse() {
		ResourcesPlugin.getWorkspace().addResourceChangeListener(resourceListener, IResourceChangeEvent.POST_CHANGE);
		JavaCore.addElementChangedListener(javaListener);
		JUnitCore.addTestRunListener(junitListener);
		Activator.getDefault().getWorkbench().addWindowListener(windowListener);
		windowListener.windowOpened(null);
	}
	
	public void unregisterListenersInEclipse() {
		ResourcesPlugin.getWorkspace().removeResourceChangeListener(resourceListener);
		JavaCore.removeElementChangedListener(javaListener);
		JUnitCore.removeTestRunListener(junitListener);
		Activator.getDefault().getWorkbench().removeWindowListener(windowListener);
	}
	
	public void setOutputStream(ActionOutputStream actionOutputStream) {
		this.output = actionOutputStream;
	}

	public void addAction(Action action) {
		if (output != null) {
			output.addAction(action);
		}
	}
	
}
