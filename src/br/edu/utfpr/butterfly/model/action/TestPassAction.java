package br.edu.utfpr.butterfly.model.action;

import java.util.Date;
import java.util.StringTokenizer;

public class TestPassAction extends UnitTestAction {

	private String testcase;
	private String failureMessage;
	
	public TestPassAction(Date clock, String workspaceFile) {
		super(clock, workspaceFile);
		super.setSuccessValue(true);
	}

	public TestPassAction(StringTokenizer tok) {
		super(tok);
		super.setSuccessValue(true);
	}

	public TestPassAction(Date date, String string, boolean b) {
		super(date, string, b);
		super.setSuccessValue(true);
	}

	public void setTestCase(String testcase) {
		this.testcase = testcase;
	}

	public String getTestCase() {
		return this.testcase;
	}

	public void setFailureMessage(String failureMessage) {
		this.setSuccessValue(false);
		this.failureMessage = failureMessage;
	}

	public String getFailureMessage() {
		return this.failureMessage;
	}
	
}
