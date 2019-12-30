package home.csv.splitter.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import home.csv.splitter.filemanager.CSVSplitManager;

public class CSVMain {

	public static void main(String[] args) {

		String folder;
		String file;
		int lineNumber;

		// se vengono passati i params
		if (args != null && args.length > 3) {
			folder = args[0];
			file = args[1];
			lineNumber = Integer.parseInt(args[2]);
		} else {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			try {
				System.out.println("Cartella del file: ");
				folder = br.readLine();
				System.out.println("Nome del file: ");
				file = br.readLine();
				System.out.println("Numer di righe per file: ");
				lineNumber = Integer.parseInt(br.readLine());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				return;
			}
		}

		CSVSplitManager split = new CSVSplitManager(folder, file, false);

		split.splitEachLines(lineNumber);
	}

}
