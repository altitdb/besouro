package besouro.others;

import java.util.Date;
import java.util.StringTokenizer;

import besouro.model.action.UnitTestAction;

public class UnitTestSessionAction extends UnitTestAction {

	public UnitTestSessionAction(Date clock, String workspaceFile) {
		super(clock, workspaceFile);
	}

	public UnitTestSessionAction(StringTokenizer tok) {
		super(tok);
	}

	public UnitTestSessionAction(Date date, String string, boolean b) {
		super(date, string, b);
	}

	
}
