package besouro.model;

import java.util.ArrayList;
import java.util.List;

import besouro.model.action.Action;

public class Episode {

	private String category;
	private Integer duration;
	private List<Action> actions = new ArrayList<Action>();
	private Episode previousEpisode;
	private Long timestamp;
	private Boolean disagree = false;
	
	public Episode() {
		
	}

	public void setClassification(String category) {
		this.category = category;
	}
	
	public List<Action> getActions() {
		return actions;
	}

	public Action getLastAction() {
		if (actions.size() == 0) {
			return null;
		}
		return actions.get(actions.size() - 1);
	}
	
	public String getCategory() {
		return category;
	}
	
	public Boolean isTestFirst() {
		return DevelopmentType.TEST_FIRST.equals(category);
	}
	
	public Boolean isRefactoring() {
		return DevelopmentType.REFACTORING.equals(category);
	}

	public Boolean isTdd() {
		return DevelopmentType.TEST_DRIVEN_DEVELOPMENT.equals(category);
	}
	
	public Boolean isUnknown() {
		return DevelopmentType.UNKNOWN.equals(category);
	}

	public void setDuration(int i) {
		this.duration = i;
	}

	public int getDuration() {
		long first;
		
		if (previousEpisode != null && previousEpisode.getLastAction()!=null){
			first = previousEpisode.getLastAction().getClock().getTime();
		} else if (actions.size()>0) {
			first = actions.get(0).getClock().getTime();
		} else {
			return duration;
		}
		
		long last = actions.get(actions.size()-1).getClock().getTime();
		return (int) (last-first)/1000;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(getCategory());
		builder.append(" (");
		builder.append(getDuration());
		builder.append("s)");
		return String.valueOf(builder);
	}

	public void addActions(List<Action> actions) {
		this.actions.addAll(actions);
	}

	public long getTimestamp() {
		if (getLastAction() != null) {
			return getLastAction().getClock().getTime();
		} else {
			return this.timestamp;
		}
	}

	public void setTimestamp(long time) {
		this.timestamp = time;
	}

	public void setDisagree(boolean b) {
		this.disagree = b;
	}
	
	public boolean isDisagree() {
		return disagree;
	}
	
}
