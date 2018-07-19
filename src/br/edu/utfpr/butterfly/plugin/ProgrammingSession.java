package br.edu.utfpr.butterfly.plugin;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import br.edu.utfpr.butterfly.classifier.EpisodeClassifierStream;
import br.edu.utfpr.butterfly.model.Episode;
import br.edu.utfpr.butterfly.model.action.Action;
import br.edu.utfpr.butterfly.persistence.ActionFileStorage;
import br.edu.utfpr.butterfly.persistence.EpisodeFileStorage;
import br.edu.utfpr.butterfly.persistence.GitRecorder;
import br.edu.utfpr.butterfly.stream.ActionOutputStream;
import br.edu.utfpr.butterfly.stream.EpisodeListener;

public class ProgrammingSession implements ActionOutputStream {

	private ButterflyConfigureListener eclipseListenerSet;
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
		return newSession(basedir, ButterflyConfigureListener.getSingleton());
	}
	
	public static ProgrammingSession newSession(File basedir, ButterflyConfigureListener listeners) {
		if(currentSession!=null){
			currentSession.close();
		}
		currentSession = new ProgrammingSession(basedir, listeners);
		return currentSession;
	}
	
	private ProgrammingSession(File basedir, ButterflyConfigureListener listeners) {
		String timestamp = new SimpleDateFormat("yyyyMMddHHmmssS").format(new Date());
		
		File butterflyDir = new File(basedir, ".butterfly");
		butterflyDir.mkdir();
		
		File sessionDir = new File(butterflyDir, timestamp);
		sessionDir.mkdir();
		
		actionsFile = new File(sessionDir, "actions.txt");
		actionStorage = new ActionFileStorage(actionsFile);
		
		episodeClassfierFile = new File(sessionDir, "episodes.txt");
		episodeClassfierStorage = new EpisodeFileStorage(episodeClassfierFile);
		episodeClassifierStream = new EpisodeClassifierStream();
		episodeClassifierStream.addEpisodeListener(episodeClassfierStorage);
		
		disagreementsFile = new File(sessionDir, "disagreements.txt");
		disagreementsStorage = new EpisodeFileStorage(disagreementsFile);
		
		userCommentsFile = new File(sessionDir, "comments.txt");
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
