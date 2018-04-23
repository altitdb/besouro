package besouro.classifier;

import java.util.ArrayList;
import java.util.List;

import besouro.model.Episode;
import besouro.model.TDDMeasure;
import besouro.model.action.Action;
import besouro.model.action.JavaFileAction;
import besouro.stream.EpisodeListener;
import besouro.stream.EpisodesRecognizerActionStream;
import besouro.stream.JavaActionsLinker;

public class EpisodeClassifierStream implements EpisodesRecognizerActionStream {

	private EpisodeClassifier episodeClassifier = new EpisodeClassifier();
	private JavaActionsLinker javaActionsLinker = new JavaActionsLinker();
	private TDDMeasure measure = new TDDMeasure();
	private List<Action> actions = new ArrayList<Action>();
	private List<Episode> episodes = new ArrayList<Episode>();
	private List<EpisodeListener> listeners = new ArrayList<EpisodeListener>();

	public EpisodeClassifierStream() {
		
	}
	
	public void addAction(Action action) {
		if (!action.isEditAction()) {
			if (action instanceof JavaFileAction) {
				javaActionsLinker.linkActions((JavaFileAction) action);
			}
			
			actions.add(action);
			Episode episode = episodeClassifier.classify(actions);
			
			if (episode != null) {
				actions.clear();
				measure.addEpisode(episode);
				episodes.add(episode);
				
				for (EpisodeListener episodeListener : listeners) {
					episodeListener.episodeRecognized(episode);
				}
				
				System.out.println(episode);
				System.out.println("-----------------");
				System.out.println("episodes: " + measure.countEpisodes());
				System.out.println("duration: " + measure.getTDDPercentageByDuration());
				System.out.println("  number: " + measure.getTDDPercentageByNumber());
				System.out.println("-----------------");
			}
		}
	}

	public TDDMeasure getTDDMeasure() {
		return measure;
	}

	public JavaActionsLinker getJavaActionsMeasurer() {
		return javaActionsLinker;
	}

	public void addEpisodeListener(EpisodeListener listener) {
		this.listeners.add(listener);
	}

	public void removeEpisodeListener(EpisodeListener listener2) {
		this.listeners = null;
	}
	
	public Episode[] getEpisodes() {
		return episodes.toArray(new Episode[episodes.size()]);
	}

}
