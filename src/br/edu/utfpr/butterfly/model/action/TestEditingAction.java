package br.edu.utfpr.butterfly.model.action;

import java.util.Date;
import java.util.StringTokenizer;

/**
 * Defines unary refactoring action.
 * 
 * @author Hongbing Kou
 */
public class TestEditingAction extends TestAction {

	public TestEditingAction(Date clock, String workspaceFile) {
		super(clock, workspaceFile);
	}

	public TestEditingAction(StringTokenizer tok) {
		super(tok);
		setOperator(tok.nextToken());
		setSubjectName(tok.nextToken());
		setSubjectType(tok.nextToken());
	}

	public String toString() {
		return super.toString() + " " + getOperator() + " " + getSubjectName() + " " + getSubjectType(); 
	}

}