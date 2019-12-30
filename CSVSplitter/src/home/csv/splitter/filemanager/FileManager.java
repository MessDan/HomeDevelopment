package home.csv.splitter.filemanager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.regex.Pattern;

import home.csv.splitter.common.CommonConstants;

public class FileManager {

	private File file;

	private String folder;

	private String fileName;

	public FileManager(String folder, String fileName) {
		this.folder = folder;
		this.fileName = fileName;
		this.file = openFile(folder, fileName, true);
	}

	public FileManager(String folder, String fileName, boolean toCopy) {
		this.folder = folder;
		this.fileName = fileName;
		this.file = openFile(folder, fileName, toCopy);
	}

	private File openFile(String folder, String fileName, boolean toCopy) {
		String completePath = folder + CommonConstants.FOLDER_CONCAT + fileName;
		File strFile = new File(completePath);

		if (toCopy && strFile.exists()) {
			copyFile(strFile);
		}

		return strFile;
	}

	/**
	 * @return file name without extension, with final dot
	 * @Example: @input= file.txt @output= file.
	 */
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

	/**
	 * @return file extension
	 */
	protected String getFileExtension(File source) {
		String[] splittedPath;

		if (source != null) {
			splittedPath = source.getAbsolutePath().split(Pattern.quote(CommonConstants.DOT));
		} else {
			splittedPath = file.getAbsolutePath().split(Pattern.quote(CommonConstants.DOT));
		}

		return splittedPath[splittedPath.length - 1];
	}

	/**
	 * @return create a copy of file with .old suffix before extension
	 * @Example: @input= file.txt @output= file.old.txt
	 */
	protected void copyFile(File source) {
		String[] splittedPath = source.getAbsolutePath().split(Pattern.quote(CommonConstants.DOT));

		// ricompongo il nome del file senza l'estensione
		String fileWhitoutExtencion = getNameWithoutExtension(source);

		// creo il file "old" con lo stesso nome dell'originale
		File oldVersion = new File(
				fileWhitoutExtencion + "old" + CommonConstants.DOT + splittedPath[splittedPath.length - 1]);

		try {
			BufferedReader bReader = getBufferReader(source);
			BufferedWriter bWriter = getBufferWriter(oldVersion);

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
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected File getFile() {
		return this.file;
	}

	protected Integer getFileLineNumber() {
		if (this.file != null) {
			FileReader fileR;
			try {
				fileR = new FileReader(this.file);
				LineNumberReader lineNum = new LineNumberReader(fileR);

				while (lineNum.skip(Long.MAX_VALUE) > 0) {
				}

				return lineNum.getLineNumber() + 1;

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return null;
	}

	protected BufferedReader getBufferReader(File source) throws IOException {
		return new BufferedReader(new FileReader(source));
	}

	protected BufferedWriter getBufferWriter(File source) throws IOException {
		return new BufferedWriter(new FileWriter(source));
	}
}
