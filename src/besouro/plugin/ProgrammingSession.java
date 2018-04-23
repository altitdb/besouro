package besouro.plugin;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import besouro.classifier.EpisodeClassifierStream;
import besouro.model.Episode;
import besouro.model.action.Action;
import besouro.persistence.ActionFileStorage;
import besouro.persistence.EpisodeFileStorage;
import besouro.persistence.GitRecorder;
import besouro.stream.ActionOutputStream;
import besouro.stream.EpisodeListener;

public class ProgrammingSession implements ActionOutputStream {

	private BesouroListenerSet eclipseListenerSet;
	private ActionFileStorage actionStorage;
	private EpisodeFileStorage disagreementsStorage;
	private EpisodeFileStorage userCommentsEpisodesStorage;
	private EpisodeFileStorage episodeClassfierStorage;
	private File actionsFile;
	private File episodeClassfierFile;
	private File disagreementsFile;
	private File userCommentsFile;
	private GitRecorder git;
	private static ProgrammingSession currentSession;
	private EpisodeClassifierStream episodeClassifierStream;

	public static ProgrammingSession newSession(File basedir) {
		return newSession(basedir, BesouroListenerSet.getSingleton());
	}
	
	public static ProgrammingSession newSession(File basedir, BesouroListenerSet listeners) {
		if(currentSession!=null){
			currentSession.close();
		}
		currentSession = new ProgrammingSession(basedir, listeners);
		return currentSession;
	}
	
	private ProgrammingSession(File basedir, BesouroListenerSet listeners) {
		String timestamp = new SimpleDateFormat("yyyyMMddHHmmssS").format(new Date());
		
		File besouroDir = new File(basedir, ".besouro");
		besouroDir.mkdir();
		
		File sessionDir = new File(besouroDir, timestamp);
		sessionDir.mkdir();
		
		actionsFile = new File(sessionDir, "actions.txt");
		actionStorage = new ActionFileStorage(actionsFile);
		
		episodeClassfierFile = new File(sessionDir, "episodeClassfierFile.txt");
		episodeClassfierStorage = new EpisodeFileStorage(episodeClassfierFile);
		episodeClassifierStream = new EpisodeClassifierStream();
		episodeClassifierStream.addEpisodeListener(episodeClassfierStorage);
		
		disagreementsFile = new File(sessionDir, "disagreements.txt");
		disagreementsStorage = new EpisodeFileStorage(disagreementsFile);
		
		userCommentsFile = new File(sessionDir, "userComments.txt");
		userCommentsEpisodesStorage = new EpisodeFileStorage(userCommentsFile);
		
		eclipseListenerSet = listeners;
		eclipseListenerSet.setOutputStream(this);
		
		git = new GitRecorder(basedir);
	}
	
	public void start() {
		eclipseListenerSet.registerListenersInEclipse();
		git.createRepoIfNeeded();
	}

	public void addAction(Action action) {
		System.out.println(action);
		actionStorage.addAction(action);
		episodeClassifierStream.addAction(action);
		git.addAction(action);
	}

	public void disagreeFromEpisode(Episode episode) {
		disagreementsStorage.episodeRecognized(episode);
	}

	public void commentEpisode(Episode episode) {
		userCommentsEpisodesStorage.episodeRecognized(episode);
	}
	
	public void addEpisodeListeners(EpisodeListener episodeListener) {
		episodeClassifierStream.addEpisodeListener(episodeListener);
	}

	public Episode[] getEpisodes() {
		return episodeClassifierStream.getEpisodes();
		
		/**Episode episode = new Episode();
		episode.setClassification("test-first", "abc");
		episode.setDuration(1000);
		List<Action> actions = new ArrayList<Action>();
		actions.add(new TestCreationAction(new Date(), null));
		episode.addActions(actions);
		return new Episode[] { episode };*/
	}
	
	public void close() {
		eclipseListenerSet.unregisterListenersInEclipse();
		git.close();
	}

	public File getActionsFile() {
		return actionsFile;
	}

	public File getDisagreementsFile() {
		return disagreementsFile;
	}
	
	public void setGitRecorder(GitRecorder git) {
		this.git = git;
	}

}
