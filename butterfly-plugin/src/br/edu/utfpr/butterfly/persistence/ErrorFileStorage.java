package br.edu.utfpr.butterfly.persistence;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ErrorFileStorage {

	private FileWriter writer;

	public ErrorFileStorage(File file) {
		try {
			if (!file.exists()) {
				file.createNewFile();
			}
			
			writer = new FileWriter(file, true);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void log(Exception ex) {
		try {
			writer.write(String.valueOf(ex.getMessage()));
			writer.write("\n");
			writer.write(String.valueOf(ex.getCause()));
            writer.write("\n");
			writer.flush();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
