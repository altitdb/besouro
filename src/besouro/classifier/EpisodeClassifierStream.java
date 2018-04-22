package besouro.classifier;

import java.util.ArrayList;
import java.util.List;

import besouro.model.Episode;
import besouro.model.TDDMeasure;
import besouro.model.action.Action;
import besouro.model.action.JavaFileAction;
import besouro.model.action.UnitTestAction;
import besouro.others.UnitTestSessionAction;
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
		System.out.println("[action] " + action);
		
		if (action instanceof JavaFileAction) {
			javaActionsLinker.linkActions((JavaFileAction) action);
		}

		actions.add(action);
		
		Episode episode = recognizeEpisode(action);

		if (episode != null) {
			episodeClassifier.classify(episode);
			measure.addEpisode(episode);
			episodes.add(episode);
			
			for (EpisodeListener lis: listeners) {
				lis.episodeRecognized(episode);
			}
			
			System.out.println(episode);
			System.out.println("-----------------");
			System.out.println("\t#episodes: " + measure.countEpisodes());
			System.out.println("\t duration: " + measure.getTDDPercentageByDuration());
			System.out.println("\t   number: " + measure.getTDDPercentageByNumber());
			System.out.println("-----------------");
		}
	}

	private Episode recognizeEpisode(Action action) {
		if (action instanceof UnitTestSessionAction) {
			if (((UnitTestAction) action).isSuccessful()) {
				Episode episode = new Episode();
				episode.addActions(actions);
				actions.clear();
				return episode;
			}
		}
		return null;
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
