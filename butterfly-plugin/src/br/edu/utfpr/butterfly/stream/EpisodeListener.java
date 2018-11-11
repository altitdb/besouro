package br.edu.utfpr.butterfly.stream;

import br.edu.utfpr.butterfly.model.Episode;

public interface EpisodeListener {
	
	public void episodeRecognized(Episode e);
	
}
