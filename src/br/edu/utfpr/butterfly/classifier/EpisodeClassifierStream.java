package br.edu.utfpr.butterfly.classifier;

import java.util.ArrayList;
import java.util.List;

import br.edu.utfpr.butterfly.model.DevelopmentType;
import br.edu.utfpr.butterfly.model.Episode;
import br.edu.utfpr.butterfly.model.TDDMeasure;
import br.edu.utfpr.butterfly.model.action.Action;
import br.edu.utfpr.butterfly.model.action.JavaFileAction;
import br.edu.utfpr.butterfly.stream.EpisodeListener;
import br.edu.utfpr.butterfly.stream.EpisodesRecognizerActionStream;
import br.edu.utfpr.butterfly.stream.JavaActionsLinker;

public class EpisodeClassifierStream implements EpisodesRecognizerActionStream {

	private EpisodeClassifier episodeClassifier = new EpisodeClassifier();
	private JavaActionsLinker javaActionsLinker = new JavaActionsLinker();
	private TDDMeasure measure = new TDDMeasure();
	private List<Action> actions = new ArrayList<Action>();
	private List<Episode> episodes = new ArrayList<Episode>();
	private List<EpisodeListener> listeners = new ArrayList<EpisodeListener>();

	public EpisodeClassifierStream() {
		
	}
	
	public Action getLastAction() {
		if (actions.isEmpty()) {
			return null;
		}
		return actions.get(actions.size() - 1);
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
				
				if (!episodes.isEmpty()) {
					int indexLastEpisode = episodes.size() - 1;
					Episode lastEpisode = episodes.get(indexLastEpisode);
					if ((lastEpisode.isTestFirst() || lastEpisode.isTdd()) && episode.isRefactoring()) {
						List<Action> episodeActions = new ArrayList<Action>();
						episodeActions.addAll(lastEpisode.getActions());
						episodeActions.addAll(episode.getActions());
						episode = new Episode();
						episode.setClassification(DevelopmentType.TEST_DRIVEN_DEVELOPMENT);
						episode.getActions().addAll(episodeActions);
						episodes.remove(indexLastEpisode);
					}
				}
				
				episodes.add(episode);
				measure.update(episodes);
				
				for (EpisodeListener episodeListener : listeners) {
					episodeListener.episodeRecognized(episode);
				}
				
				System.out.println("-----------------");
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
