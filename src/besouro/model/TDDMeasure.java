package besouro.model;

import java.util.ArrayList;
import java.util.List;

public class TDDMeasure {
	
	private float numberOfTDDEpisodes;
	private float numberOfNonTDDEpisodes;
	private float durationOfTDDEpisodes;
	private float durationOfNonTDDEpisodes;
	private List<Episode> episodes = new ArrayList<Episode>();
	
	public TDDMeasure() {
	}

	public float getTDDPercentageByNumber() {
		update(this.episodes);
		float totalEpisodes = numberOfNonTDDEpisodes + numberOfTDDEpisodes;
		if (totalEpisodes == 0) {
			return 0;
		} else {
			return numberOfTDDEpisodes / totalEpisodes;
		}
	}

	public float getTDDPercentageByDuration() {
		update(this.episodes);
		float totalDuration = durationOfNonTDDEpisodes + durationOfTDDEpisodes;
		if (totalDuration == 0) {
			return 0;
		} else {
			return durationOfTDDEpisodes / totalDuration;
		}
	}

	public int countEpisodes() {
		return episodes.size();
	}

	public void update(List<Episode> episodes) {
		this.episodes = episodes;
		
		numberOfNonTDDEpisodes = 0;
		numberOfTDDEpisodes = 0;
		durationOfNonTDDEpisodes = 0;
		durationOfTDDEpisodes = 0;
		
		for (int i = 0 ; i< episodes.size(); i++) {
			if (episodes.get(i).isTdd()) {
				numberOfTDDEpisodes += 1;
				durationOfTDDEpisodes += episodes.get(i).getDuration();
			} else {
				numberOfNonTDDEpisodes += 1;
				durationOfNonTDDEpisodes += episodes.get(i).getDuration();
			}
		}
	}
	
}
