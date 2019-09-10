package br.edu.utfpr.butterfly.plugin;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Menu;

import br.edu.utfpr.butterfly.model.DevelopmentType;
import br.edu.utfpr.butterfly.model.Episode;

public class DisagreementPopupMenu {

	private TreeViewer viewer;
	private ProgrammingSession session;
	private MenuManager mngr;
	private Action nonConformAction;
	private Action conformAction;
	private Action testFirstAction;
	private Action testLastAction;
	private Action testAdditionAction;
	private Action refactoringAction;
	private Action unknownAction;
	private Action spacingAction;
	private Action commentAction;

	public DisagreementPopupMenu(TreeViewer view, ProgrammingSession session) {
		this.viewer = view;
		this.session = session;
		
		mngr = new MenuManager();
		mngr.setRemoveAllWhenShown(true);
		
		createActions();
		
		mngr.addMenuListener(new IMenuListener() {
			
			public void menuAboutToShow(IMenuManager manager) {
				IStructuredSelection selection = (IStructuredSelection) viewer.getSelection();
				if (!selection.isEmpty()) {
					
					Episode episode = (Episode) selection.getFirstElement();
					
					if (episode.isTdd()) {
						mngr.add(nonConformAction);
					} else {
						mngr.add(conformAction);
					}
					
					mngr.add(spacingAction);
					
					if (!DevelopmentType.TEST_FIRST.equals(episode.getCategory())) {
						mngr.add(testFirstAction);
					}
					
					if (!DevelopmentType.TEST_LAST.equals(episode.getCategory())) {
						mngr.add(testLastAction);
					}
					
					if (!DevelopmentType.TEST_ADDITION.equals(episode.getCategory())) {
						mngr.add(testAdditionAction);
					}
					
					if (!DevelopmentType.REFACTORING.equals(episode.getCategory())) {
						mngr.add(refactoringAction);
					}
					
					mngr.add(unknownAction);
					
					mngr.add(spacingAction);
					
					mngr.add(commentAction);
				}
			}
		});
		
	}
	
	private Episode getSelectedEpisode() {
		Episode e = (Episode) ((TreeSelection)viewer.getSelection()).getFirstElement();
		return e;
	}
	
	private void createActions() {
		nonConformAction = new Action(){
			public void run() {
				Episode e = getSelectedEpisode();
				e.setDisagree(true);
				session.disagreeFromEpisode(e);
				viewer.refresh();
			}
		};
		nonConformAction.setText("non-conformant");
		nonConformAction.setImageDescriptor(Activator.imageDescriptorFromPlugin("butterfly_plugin", "icons/episode_nonconformant.png"));
		
		conformAction = new Action(){
			public void run() {
				Episode e = getSelectedEpisode();
				e.setDisagree(true);
				session.disagreeFromEpisode(e);
				viewer.refresh();
			}

		};
		conformAction.setText("conformant");
		conformAction.setImageDescriptor(Activator.imageDescriptorFromPlugin("butterfly_plugin", "icons/episode_conformant.png"));
		
		
		spacingAction = new Action(){
			public void run() {				
			}
		};
		spacingAction.setText("----------");

		
		testFirstAction  = new Action(){
			public void run() {
				Episode e = getSelectedEpisode();
				e.setClassification(DevelopmentType.TEST_FIRST);
				e.setDisagree(true);
				session.disagreeFromEpisode(e);
				viewer.refresh();
			}
		};
		testFirstAction.setText(DevelopmentType.TEST_FIRST);
		
		testLastAction = new Action(){
			public void run() {
				Episode e = getSelectedEpisode();
				e.setClassification(DevelopmentType.TEST_LAST);
				e.setDisagree(true);
				session.disagreeFromEpisode(e);
				viewer.refresh();
			}
		};
		testLastAction.setText(DevelopmentType.TEST_LAST);
		
		testAdditionAction = new Action(){
			public void run() {
				Episode e = getSelectedEpisode();
				e.setClassification(DevelopmentType.TEST_ADDITION);
				e.setDisagree(true);
				session.disagreeFromEpisode(e);
				viewer.refresh();

			}
		};
		testAdditionAction.setText(DevelopmentType.TEST_ADDITION);
		
		refactoringAction = new Action(){
			public void run() {
				Episode e = getSelectedEpisode();
				e.setClassification(DevelopmentType.REFACTORING);
				session.disagreeFromEpisode(e);
				e.setDisagree(true);
				viewer.refresh();

			}
		};
		refactoringAction.setText(DevelopmentType.REFACTORING);
			
		unknownAction = new Action(){
			public void run() {
				Episode e = getSelectedEpisode();
				e.setClassification(DevelopmentType.UNKNOWN);
				e.setDisagree(true);
				session.disagreeFromEpisode(e);
				viewer.refresh();
				
			}
		};
		unknownAction.setText(DevelopmentType.UNKNOWN);
		
		commentAction = new Action(){
			public void run() {
				
				InputDialog dialog = new InputDialog(viewer.getControl().getShell(),"Comment", "Please enter your comment","",null);
				
				if(dialog.open() == IStatus.OK){ 
					String comment = dialog.getValue(); 
					
					Episode e = new Episode();
					e.setTimestamp(getSelectedEpisode().getTimestamp());
					e.setClassification(comment);
					e.setDisagree(true);
					session.commentEpisode(e);
				}
				
			}
		};
		commentAction.setText("Make a comment");
	}
	
	public Menu getMenu() {
		return mngr.createContextMenu(viewer.getControl());
	}
	
}
