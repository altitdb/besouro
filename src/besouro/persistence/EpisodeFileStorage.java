package besouro.persistence;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import besouro.model.Episode;
import besouro.stream.EpisodeListener;

public class EpisodeFileStorage implements EpisodeListener {

	private FileWriter writer;

	public EpisodeFileStorage(File file) {
		try {
			if (!file.exists()) {
				file.createNewFile();
			}
			
			writer = new FileWriter(file, true);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void episodeRecognized(Episode e) {
		try {
			saveEpisodeToFile(e);
		} catch (IOException e1) {
			throw new RuntimeException(e1);
		}
	}
	
	private void saveEpisodeToFile(Episode episode) throws IOException {
		writer.append(String.valueOf(episode.getTimestamp()));
		writer.append(String.valueOf(episode.getCategory()));
		writer.append(String.valueOf(episode.getDuration()));
		writer.append(String.valueOf(episode.isTdd()));
		writer.append("\n");
		writer.append("Actions");
		writer.append(String.valueOf(episode.getActions()));
		writer.append("\n");
		writer.flush();
	}
	
	public static Episode[] loadEpisodes(File file) {
		try {
			BufferedReader reader;
			reader = new BufferedReader(new FileReader(file));
		
			List<Episode> list = new ArrayList<Episode>();
			String line = reader.readLine();
			
			while (line != null){
				StringTokenizer tok = new StringTokenizer(line, " ");
				Episode e = new Episode();
				e.setTimestamp(Long.parseLong(tok.nextToken()));
				e.setClassification(tok.nextToken());
				e.setDuration(Integer.parseInt(tok.nextToken()));
				list.add(e);
				line = reader.readLine();
			}
			
			reader.close();
			return list.toArray(new Episode[list.size()]);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
