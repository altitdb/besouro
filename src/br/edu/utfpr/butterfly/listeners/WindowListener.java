package br.edu.utfpr.butterfly.listeners;

import java.util.Date;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.text.DocumentEvent;
import org.eclipse.jface.text.IDocumentListener;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IWindowListener;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.texteditor.ITextEditor;

import br.edu.utfpr.butterfly.measure.JavaStatementMeter;
import br.edu.utfpr.butterfly.plugin.Activator;
import br.edu.utfpr.butterfly.stream.ActionOutputStream;

public class WindowListener implements IWindowListener, IPartListener, IDocumentListener {

	//TODO private ActionOutputStream stream;
	private IWorkbench workbench;
	private JavaStatementMeter measurer = new JavaStatementMeter();
	
	public WindowListener(ActionOutputStream stream) {
		//this.stream = stream;
	}

	public void setWorkbench(IWorkbench workbench) {
		this.workbench = workbench;
	}

	public void windowOpened(IWorkbenchWindow window) {
		if (workbench == null) {
			workbench = Activator.getDefault().getWorkbench();
		}

		IWorkbenchWindow[] activeWindows = workbench.getWorkbenchWindows();

		for (int i = 0; i < activeWindows.length; i++) {
			IWorkbenchPage activePage = activeWindows[i].getActivePage();
			if (activePage != null) {
				activePage.addPartListener(this);
				registerFileOpenAction(activePage.getActiveEditor());
			}

		}
	}

	public void windowDeactivated(IWorkbenchWindow window) {
	}

	public void windowActivated(IWorkbenchWindow window) {
	}

	public void partOpened(IWorkbenchPart part) {
		//registerFileOpenAction(part);
	}

	public void partActivated(IWorkbenchPart part) {
	}

	public void partBroughtToTop(IWorkbenchPart part) {
	}

	public void partClosed(IWorkbenchPart part) {
	}

	public void partDeactivated(IWorkbenchPart part) {
	}

	public void documentAboutToBeChanged(DocumentEvent event) {
	}

	public void documentChanged(DocumentEvent event) {
	}

	public void windowClosed(IWorkbenchWindow window) {
	}


	private void registerFileOpenAction(IWorkbenchPart part) {
		if (part instanceof ITextEditor) {
			ITextEditor textEditor = (ITextEditor) part;
			IEditorInput input = textEditor.getEditorInput();
			
			if (input instanceof IFileEditorInput) {
				IFileEditorInput fileInput = (IFileEditorInput) input;
				IFile file = fileInput.getFile();
				FileOpenedAction action = new FileOpenedAction(new Date(), file.getName());
				
				JavaStatementMeter meter = this.measurer.measureJavaFile(file);
				
				action.setFileSize((int) file.getLocation().toFile().length());
				action.setIsTestEdit(meter.isTest());
				action.setMethodsCount(meter.getNumOfMethods());
				action.setStatementsCount(meter.getNumOfStatements());
				action.setTestMethodsCount(meter.getNumOfTestMethods());
				action.setTestAssertionsCount(meter.getNumOfTestAssertions());
				//TODO stream.addAction(action);
			}
		}
	}
	
	public void setMeasurer(JavaStatementMeter meter) {
		this.measurer = meter;
	}
}