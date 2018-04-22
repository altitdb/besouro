package besouro.model.action;

import java.util.Date;
import java.util.StringTokenizer;

public class TestSuccessfullAction extends UnitTestAction {

	private String testcase;
	protected String failureMessage;
	
	public TestSuccessfullAction(Date clock, String workspaceFile) {
		super(clock, workspaceFile);
	}

	public TestSuccessfullAction(StringTokenizer tok) {
		super(tok);
	}

	public TestSuccessfullAction(Date date, String string, boolean b) {
		super(date, string, b);
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
