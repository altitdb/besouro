package br.edu.utfpr.butterfly.classifier;

import java.util.List;

import br.edu.utfpr.butterfly.model.Episode;
import br.edu.utfpr.butterfly.model.action.Action;

public interface Classifier {

	Episode classify(List<Action> actions);
	
}
