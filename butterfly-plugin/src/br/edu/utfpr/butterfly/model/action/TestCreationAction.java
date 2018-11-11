package br.edu.utfpr.butterfly.model.action;

import java.util.Date;
import java.util.StringTokenizer;

/**
 * Defines unary refactoring action.
 * 
 * @author Hongbing Kou
 */
public class TestCreationAction extends TestAction {

	public TestCreationAction(Date clock, String workspaceFile) {
		super(clock, workspaceFile);
	}

	public TestCreationAction(StringTokenizer tok) {
		super(tok);
		setOperator(tok.nextToken());
		setSubjectName(tok.nextToken());
		setSubjectType(tok.nextToken());
	}

	public String toString() {
		return super.toString() + " " + getOperator() + " " + getSubjectName() + " " + getSubjectType(); 
	}

}
