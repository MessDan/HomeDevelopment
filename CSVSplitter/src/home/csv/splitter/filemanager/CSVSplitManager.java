package home.csv.splitter.filemanager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;

import home.csv.splitter.common.CommonConstants;
import home.csv.splitter.utility.Utility;

public class CSVSplitManager extends FileManager {

	public CSVSplitManager(String folder, String fileName, boolean toCopy) {
		super(folder, fileName, toCopy);
	}

	public void splitEachLines(int lineToSplit) {
		File innFile = super.getFile();
		if (innFile != null) {

			int lineNum = super.getFileLineNumber();
			if (lineNum > lineToSplit) {
				try {
					BufferedReader bReader = getBufferReader(innFile);
					BufferedWriter bWriter;
					int lineCount = 0;
					int fileCount = 1;

					if (bReader.ready()) {
						while (lineCount < lineNum) {
							bWriter = getBufferWriter(createNewFileByName(innFile, String.valueOf(fileCount)));

							String lineToRead = null;

							while ((++lineCount % lineToSplit) != 0 && (lineToRead = bReader.readLine()) != null) {
								bWriter.write(lineToRead);
								bWriter.newLine();

								// lineCount++;
							}

							fileCount++;

							bWriter.flush();
							bWriter.close();
						}

					}

					bReader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}
	}

	private File createNewFileByName(File fileName, String toConcat) {
		if (fileName != null) {
			// File cpFile = new File(fileName.getAbsolutePath(), fileName.getName());
			String fileNoExt = super.getNameWithoutExtension(fileName);
			String fileExt = super.getFileExtension(fileName);

			File newFile = new File(
					fileNoExt + (Utility.isValid(toConcat) ? toConcat + CommonConstants.DOT : "") + fileExt);

			return newFile;
		}
		return null;
	}

}
