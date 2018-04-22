package besouro.model.action;

import java.util.Date;
import java.util.StringTokenizer;

/**
 * Defines unary refactoring action.
 * 
 * @author Hongbing Kou
 */
public class TestCodingAction extends TestAction {

	public TestCodingAction(Date clock, String workspaceFile) {
		super(clock, workspaceFile);
	}

	public TestCodingAction(StringTokenizer tok) {
		super(tok);
		setOperator(tok.nextToken());
		setSubjectName(tok.nextToken());
		setSubjectType(tok.nextToken());
	}

	public String toString() {
		return super.toString() + " " + getOperator() + " " + getSubjectName() + " " + getSubjectType(); 
	}

}