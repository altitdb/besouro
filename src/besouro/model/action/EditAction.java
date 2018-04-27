package besouro.model.action;

import java.util.Date;
import java.util.StringTokenizer;

/**
 * Implements edit action on files.
 * @author Hongbing Kou
 */
public class EditAction extends JavaFileAction {

	public EditAction(Date clock, String workspaceFile) {
		super(clock, workspaceFile);
	}

	public EditAction(StringTokenizer tok) {
		super(tok);
	}

}
