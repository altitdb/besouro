package br.edu.utfpr.butterfly.plugin;

import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.junit.JUnitCore;

import br.edu.utfpr.butterfly.listeners.JUnitListener;
import br.edu.utfpr.butterfly.listeners.JavaStructureChangeListener;
import br.edu.utfpr.butterfly.listeners.ResourceChangeListener;
import br.edu.utfpr.butterfly.listeners.WindowListener;
import br.edu.utfpr.butterfly.model.action.Action;
import br.edu.utfpr.butterfly.stream.ActionOutputStream;

public class ButterflyConfigureListener implements ActionOutputStream {

	private static final ButterflyConfigureListener BUTTERFLY_LISTENER = new ButterflyConfigureListener();
	
	public static ButterflyConfigureListener getSingleton() {
		return BUTTERFLY_LISTENER;
	}

	private WindowListener windowListener;
	private ActionOutputStream output;
	private ResourceChangeListener resourceListener;
	private JavaStructureChangeListener javaListener;
	private JUnitListener junitListener;
	
	private ButterflyConfigureListener(){
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
