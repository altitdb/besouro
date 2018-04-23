package besouro.classifier;

import java.util.List;

import besouro.model.Episode;
import besouro.model.action.Action;

public interface Classifier {

	Episode classify(List<Action> actions);
	
}
