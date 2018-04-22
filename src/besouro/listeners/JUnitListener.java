package besouro.listeners;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.junit.TestRunListener;
import org.eclipse.jdt.junit.model.ITestCaseElement;
import org.eclipse.jdt.junit.model.ITestElement;
import org.eclipse.jdt.junit.model.ITestElement.Result;
import org.eclipse.jdt.junit.model.ITestElementContainer;
import org.eclipse.jdt.junit.model.ITestRunSession;
import org.eclipse.jdt.junit.model.ITestSuiteElement;

import besouro.model.action.TestFailureAction;
import besouro.model.action.TestSuccessfullAction;
import besouro.model.action.UnitTestAction;
import besouro.stream.ActionOutputStream;


public class JUnitListener extends TestRunListener {

	private ActionOutputStream stream;

	public JUnitListener(ActionOutputStream stream) {
		this.stream = stream;
	}

	@Override
	public void sessionFinished(ITestRunSession session) {
		boolean isSuccessfull = true;
		for (UnitTestAction action: getTestFileActions(session, session.getLaunchedProject())) {
			//TODO stream.addAction(action);
			isSuccessfull &= action.isSuccessful();
		}
		
		IResource res = findTestResource(session.getLaunchedProject(), session.getTestRunName());
		String name = res != null ? res.getName() : session.getTestRunName();
		
		//TODO UnitTestSessionAction action = new UnitTestSessionAction(new Date(), name);
		UnitTestAction action;
		if (isSuccessfull) {
			action = new TestSuccessfullAction(new Date(), name + ".java");
		} else {
			action = new TestFailureAction(new Date(), name + ".java");
		}
		stream.addAction(action);
	}

	private Collection<UnitTestAction> getTestFileActions(ITestElement session, IJavaProject project) {
		List<UnitTestAction> list = new ArrayList<UnitTestAction>();
		
		if (session instanceof ITestSuiteElement) {
			ITestSuiteElement testCase = (ITestSuiteElement) session;
			IResource res = findTestResource(project, testCase.getSuiteTypeName());
			UnitTestAction action = new UnitTestAction(new Date(), res.getName());
			action.setSuccessValue(testCase.getTestResult(true).equals(Result.OK));
			list.add(action);
		} else if (session instanceof ITestCaseElement) {
			ITestCaseElement testCase = (ITestCaseElement) session;
			IResource res = findTestResource(project, testCase.getTestClassName());
			UnitTestAction action = new UnitTestAction(new Date(),res.getName());
			action.setSuccessValue(testCase.getTestResult(true).equals(Result.OK));
			list.add(action);
		} else if (session instanceof ITestElementContainer) {
			ITestElementContainer container = (ITestElementContainer) session; 
			for(ITestElement child: container.getChildren()){
				list.addAll(getTestFileActions(child, project));
			}
		}
		return list;
	}

	private IResource findTestResource(IJavaProject project, String className) {
		IPath path = new Path(className.replaceAll("\\.", "/") + ".java");
		try {
			IJavaElement element = project.findElement(path);
			if (element != null) {
				return element.getResource();
			} else { 
				return null;
			}
		} catch (JavaModelException e) {
			throw new RuntimeException(e);
		}
	}

}
