package br.edu.utfpr.butterfly.model.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Implements abstract command for build data or cli data.
 * 
 * @author Hongbing Kou
 */
public abstract class Action implements Comparable<Action> {

	private Date clock;

	public Action(Date clock) {
		this.clock = clock;
	}

	public Date getClock() {
		return this.clock;
	}

	public int compareTo(Action o) {
		return this.clock.compareTo(o.clock);
	}

	public String toString() {
		return getClass().getSimpleName() + " " + getClock().getTime();
	}

	public List<String> getActionDetails() {
		ArrayList<String> list = new ArrayList<String>();
		list.add(new SimpleDateFormat("HH:mm:ss").format(clock));
		return list;
	}

	public Boolean isProductionCodingAction() {
		return "ProductionCodingAction".equals(getClass().getSimpleName());
	}

	public Boolean isTestCodingAction() {
		return "TestCodingAction".equals(getClass().getSimpleName());
	}

	public Boolean isTestCreationAction() {
		return "TestCreationAction".equals(getClass().getSimpleName());
	}

	public Boolean isTestFailureAction() {
		return "TestFailureAction".equals(getClass().getSimpleName());
	}

	public Boolean isTestSuccessfullAction() {
		return "TestSuccessfullAction".equals(getClass().getSimpleName());
	}

	public Boolean isEditAction() {
		return "EditAction".equals(getClass().getSimpleName());
	}

	public static Action fromString(String line) {
		Action action = null;

		StringTokenizer tok = new StringTokenizer(line, " ");
		String className = tok.nextToken();

		if (className.equals("EditAction")) {
			action = new EditAction(tok);
		} else if (className.equals("ProductionCodingAction")) {
			action = new CodeEditingAction(tok);
		} else if (className.equals("TestCodingAction")) {
			action = new TestEditingAction(tok);
		} else if (className.equals("TestCreationAction")) {
			action = new TestCreationAction(tok);
		} else if (className.equals("TestFailureAction")) {
			action = new TestFailureAction(tok);
		} else if (className.equals("TestSuccessfullAction")) {
			action = new TestPassAction(tok);
		}

		return action;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((clock == null) ? 0 : clock.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Action other = (Action) obj;
		if (clock == null) {
			if (other.clock != null)
				return false;
		} else if (!clock.equals(other.clock))
			return false;
		return true;
	}

}