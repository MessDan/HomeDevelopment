package it.subs.resynchronize.filemanager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Pattern;

import it.subs.resynchronize.common.CommonConstants;

public class FileManager {

	private File file;

	private String folder;

	private String fileName;

	public FileManager(String folder, String fileName) {
		this.folder = folder;
		this.fileName = fileName;
		this.file = openFile(folder, fileName);
	}

	private File openFile(String folder, String fileName) {
		String completePath = folder + CommonConstants.FOLDER_CONCAT + fileName;
		File strFile = new File(completePath);

		if (strFile.exists()) {
			copyFile(strFile);
		}

		return strFile;
	}

	protected String getNameWithoutExtension(File source) {
		String[] splittedPath;

		if (source != null) {
			splittedPath = source.getAbsolutePath().split(Pattern.quote(CommonConstants.DOT));
		} else {
			splittedPath = file.getAbsolutePath().split(Pattern.quote(CommonConstants.DOT));
		}
		// ricompongo il nome del file senza l'estensione
		String fileWhitoutExtencion = new String();
		for (int i = 0; i < splittedPath.length - 1; i++) {
			fileWhitoutExtencion = fileWhitoutExtencion + splittedPath[i] + CommonConstants.DOT;
		}
		return fileWhitoutExtencion;
	}

	private void copyFile(File source) {
		String[] splittedPath = source.getAbsolutePath().split(Pattern.quote(CommonConstants.DOT));

		// ricompongo il nome del file senza l'estensione
		String fileWhitoutExtencion = getNameWithoutExtension(source);

		// creo il file "old" con lo stesso nome dell'originale
		File oldVersion = new File(
				fileWhitoutExtencion + "old" + CommonConstants.DOT + splittedPath[splittedPath.length - 1]);

		try {
			BufferedReader bReader = new BufferedReader(new FileReader(source));
			BufferedWriter bWriter = new BufferedWriter(new FileWriter(oldVersion));

			if (bReader.ready()) {
				String lineToRead = null;

				while ((lineToRead = bReader.readLine()) != null) {
					bWriter.write(lineToRead);
					bWriter.newLine();
				}

				bWriter.flush();
			}
			bReader.close();
			bWriter.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected File getFile() {
		return this.file;
	}
}
