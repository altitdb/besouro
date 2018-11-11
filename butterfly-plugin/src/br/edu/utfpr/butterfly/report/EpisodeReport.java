package br.edu.utfpr.butterfly.report;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import br.edu.utfpr.butterfly.classifier.EpisodeClassifier;
import br.edu.utfpr.butterfly.model.DevelopmentType;
import br.edu.utfpr.butterfly.model.Episode;
import br.edu.utfpr.butterfly.model.TDDMeasure;
import br.edu.utfpr.butterfly.model.action.Action;
import br.edu.utfpr.butterfly.model.action.JavaFileAction;
import br.edu.utfpr.butterfly.stream.JavaActionsLinker;

public class EpisodeReport {

	private EpisodeClassifier episodeClassifier = new EpisodeClassifier();
	private JavaActionsLinker javaActionsLinker = new JavaActionsLinker();
	private TDDMeasure measure = new TDDMeasure();
	private List<Action> actions = new ArrayList<Action>();
	private List<Episode> episodes = new ArrayList<Episode>();

	public static void main(String[] args) throws IOException {
	    EpisodeReport report = new EpisodeReport();
	    String basePath = "D:\\Dropbox\\tdd\\arquivos-experimento-ii";
        File file = new File(basePath);
        String[] directories = file.list();
        for (String directory : directories) {
            if (!directory.contains(".zip")) {
                System.out.println("Analysing directories from " + directory);
                File files = new File(String.format("%s\\%s\\bowlinggame\\.butterfly", basePath, directory));
                String[] listEpisodes = files.list();
                for (String episode : listEpisodes) {
                    List<String> lines = Files.readAllLines(Paths.get(String
                            .format("%s\\%s\\bowlinggame\\.butterfly\\%s\\actions.txt", basePath, directory, episode)));
                    for (String line : lines) {
                        Action action = Action.fromString(line);
                        report.addAction(action);
                    }
                }
            }
        }
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
				
				System.out.println("-----------------");
				System.out.println(episode);
				System.out.println("-----------------");
				System.out.println("episodes: " + measure.countEpisodes());
				System.out.println("  number: " + measure.getTDDPercentageByNumber());
				System.out.println("-----------------");
			}
		}
	}

}
