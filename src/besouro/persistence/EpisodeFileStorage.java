package besouro.persistence;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.TreeMap;

import besouro.model.Episode;
import besouro.stream.EpisodeListener;

public class EpisodeFileStorage implements EpisodeListener {

	private File file;
	private FileWriter writer;
	private TreeMap<Long, Episode> episodes = new TreeMap<Long, Episode>();

	public EpisodeFileStorage(File file) {
		this.file = file;
		try {
			file.createNewFile();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void episodeRecognized(Episode e) {
		episodes.put(e.getTimestamp(), e);
		save();
	}
	
	public void save() {
		try {
			for (Episode ep: episodes.values()) {
				saveEpisodeToFile(ep);
			}
		} catch (IOException e1) {
			throw new RuntimeException(e1);
		}
	}

	private void saveEpisodeToFile(Episode e) throws IOException {
		this.writer = new FileWriter(this.file);
		writer.append("" + e.getTimestamp());
		writer.append(" " + e.getCategory());
		writer.append(" " + e.getDuration());
		writer.append(" " + e.isTdd());
		writer.append("\n");
		writer.flush();
		writer.close();
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
