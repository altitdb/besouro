package br.edu.utfpr.butterfly.stream;

import br.edu.utfpr.butterfly.model.Episode;

public interface EpisodesRecognizerActionStream extends ActionOutputStream {
	
	public Episode[] getEpisodes();

}
