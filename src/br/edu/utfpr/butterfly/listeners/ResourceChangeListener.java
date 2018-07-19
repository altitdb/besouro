package br.edu.utfpr.butterfly.listeners;

import java.util.Date;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.runtime.CoreException;

import br.edu.utfpr.butterfly.measure.JavaStatementMeter;
import br.edu.utfpr.butterfly.model.action.EditAction;
import br.edu.utfpr.butterfly.stream.ActionOutputStream;


/**
 * Provides "Open Project, "Close Project", and "Save File" events. Note that
 * this implementing class uses Visitor pattern so that key point to gather
 * these event information is inside the visitor method which is implemented
 * from <code>IResourceDeltaVisitor</code> class.
 * 
 * @author Takuya Yamashita
 */
public class ResourceChangeListener implements IResourceChangeListener, IResourceDeltaVisitor {

	private ActionOutputStream sensor;
	private JavaStatementMeter measurer = new JavaStatementMeter();

	public ResourceChangeListener(ActionOutputStream sensor) {
		this.sensor = sensor;
	}

	public void resourceChanged(IResourceChangeEvent event) {
		if ((event.getType() & IResourceChangeEvent.POST_CHANGE) != 0) {
			try {
				IResourceDelta rootDelta = event.getDelta();
				rootDelta.accept(this);
			} catch (CoreException e) {
				e.printStackTrace();
			}
		}
	}

	public boolean visit(IResourceDelta delta) throws CoreException {
		IResource resource = delta.getResource();
		if (resource instanceof IFile && resource.getLocation().toString().endsWith(".java")) {
			int flag = delta.getFlags();
			int kind = delta.getKind();
			
			if ((kind == IResourceDelta.CHANGED) && (flag == IResourceDelta.CONTENT || flag == IResourceDelta.MARKERS)) {
					IFile changedFile = (IFile) resource;
					EditAction action = new EditAction(new Date(), changedFile.getName());
					
					JavaStatementMeter meter = this.measurer.measureJavaFile(changedFile);
					
					action.setFileSize((int) changedFile.getLocation().toFile().length());
					action.setIsTestEdit(meter.isTest());
					action.setMethodsCount(meter.getNumOfMethods());
					action.setStatementsCount(meter.getNumOfStatements());
					action.setTestMethodsCount(meter.getNumOfTestMethods());
					action.setTestAssertionsCount(meter.getNumOfTestAssertions());
					sensor.addAction(action);
			}
			
		}
		return true;
	}
	
	public void setMeasurer(JavaStatementMeter meter) {
		this.measurer = meter;
	}

}