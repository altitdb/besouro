package besouro.model;

import java.util.ArrayList;
import java.util.List;

import besouro.model.action.Action;

public class Episode {

	private String category;
	private String subtype;
	private Integer duration;
	private List<Action> actions = new ArrayList<Action>();
	private Episode previousEpisode;
	private Long timestamp;
	private Boolean disagree = false;
	
	public Episode() {
		
	}

	public void setPreviousEpisode(Episode previousEpisode) {
		this.previousEpisode = previousEpisode;
	}
	
	public void setClassification(String category, String subtype) {
		this.category = category;
		this.subtype = subtype;
	}
	
	public List<Action> getActions() {
		return actions;
	}

	public Action getLastAction() {
		if (actions.size()==0) return null;
		return actions.get(actions.size()-1);
	}
	
	public String getCategory() {
		return category;
	}

	public String getSubtype() {
		return subtype;
	}

	public Boolean isTDD() {
		return DevelopmentType.TEST_DRIVEN_DEVELOPMENT.equals(category);
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
		StringBuilder sb = new StringBuilder();
		
		sb.append(getCategory());
		sb.append(" ");
		sb.append(getSubtype());
		
		sb.append(" ");
		sb.append("(");
		sb.append(getDuration());
		sb.append("s) ")
		;
		return sb.toString();
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
