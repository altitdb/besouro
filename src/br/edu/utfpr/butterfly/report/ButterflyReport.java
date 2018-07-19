package br.edu.utfpr.butterfly.report;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class ButterflyReport {

    public static void main(String[] args) throws IOException, URISyntaxException {
        String basePath = "D:\\Dropbox\\tdd\\arquivos-experimento-ii";
        File file = new File(basePath);
        String[] directories = file.list();
        for (String directory : directories) {
            if (!directory.contains(".zip")) {
                System.out.println("Analysing directories from " + directory);
                Report report = new Report(directory);
                File files = new File(String.format("%s\\%s\\bowlinggame\\.butterfly", basePath, directory));
                String[] listEpisodes = files.list();
                for (String episode : listEpisodes) {
                    List<String> lines = Files.readAllLines(Paths.get(String
                            .format("%s\\%s\\bowlinggame\\.butterfly\\%s\\episodes.txt", basePath, directory, episode)));
                    for (String line : lines) {
                        if (!line.startsWith("Actions")) {
                            System.out.println("Processing action " + line);
                            if (line.startsWith("Refactoring")) {
                                report.addRefactoring();
                            } else if (line.startsWith("Test Addition")) {
                                report.addTestAddition();
                            } else if (line.startsWith("Test Driven Development")) {
                                report.addTestDrivenDevelopment();
                            } else if (line.startsWith("Test-first")) {
                                report.addTestFirst();
                            } else if (line.startsWith("Test-last")) {
                                report.addTestLast();
                            } else {
                                report.addUnknown();
                            }
                        } else {
                            //System.out.println(line);
                        }
                    }
                }
                System.out.println(report);
            }
        }
    }
}
