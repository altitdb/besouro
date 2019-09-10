package br.edu.utfpr.butterfly.persistence;

import java.io.File;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.RepositoryBuilder;

import br.edu.utfpr.butterfly.model.action.Action;
import br.edu.utfpr.butterfly.model.action.EditAction;
import br.edu.utfpr.butterfly.stream.ActionOutputStream;

public class GitRecorder implements ActionOutputStream {

	private File gitDir;
	private Git git;

	public GitRecorder(File basedir) {
		try {
			gitDir = new File(basedir, ".git");
			
			RepositoryBuilder builder = new RepositoryBuilder();
			builder.setGitDir(gitDir);
			
			Repository repo = builder.build();
			git = new Git(repo);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void addAction(Action action) {
		if (action instanceof EditAction) {
			addAllAndCommit();
		}
	}
	
	public void close() {
		addAllAndCommit();
	}

	private void addAllAndCommit() {
		try {
			git.add().addFilepattern(".").call();
			git.commit()
				.setAll(true)
				.setCommitter("somename", "someemail")
				.setMessage("Butterfly automatic message")
				.call();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void setGit(Git git) {
		this.git = git;
	}

	public void createRepoIfNeeded() {
		try {
			if (!gitDir.exists()) {
				git.getRepository().create();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
