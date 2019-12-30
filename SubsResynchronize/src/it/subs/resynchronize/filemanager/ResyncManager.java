package it.subs.resynchronize.filemanager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

import it.subs.resynchronize.common.CommonConstants;

public class ResyncManager extends FileManager {

	public ResyncManager(String folder, String fileName) {
		super(folder, fileName);
	}

	public void createResyncVersion(long secondToResync, String sign) {
		String[] splittedPath = getFile().getAbsolutePath().split(Pattern.quote(CommonConstants.DOT));

		// creo il file "old" con lo stesso nome dell'originale
		File newVersion = new File(
				getNameWithoutExtension(null) + "new" + CommonConstants.DOT + splittedPath[splittedPath.length - 1]);

		try {
			BufferedReader bReader = new BufferedReader(new FileReader(getFile()));
			BufferedWriter bWriter = new BufferedWriter(new FileWriter(newVersion));

			if (bReader.ready()) {
				String lineToRead = null;
				String[] subsTimes;
				SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss,SSS");
				while ((lineToRead = bReader.readLine()) != null) {
					if (lineToRead.contains(CommonConstants.SUBS_TIMES_CONCAT)) {
						subsTimes = lineToRead.split(Pattern.quote(CommonConstants.SUBS_TIMES_CONCAT));
						if (subsTimes != null) {
							Date time1 = null;
							Date time2 = null;

							if (subsTimes.length > 1) {
								time1 = sdf.parse(subsTimes[0].trim()); // new Date(subsTimes[0].trim());
								time2 = sdf.parse(subsTimes[1].trim()); // new Date(subsTimes[1].trim());
							}

							if (time1 != null && time2 != null) {
								long newTime1 = time1.getTime();
								long newTime2 = time2.getTime();
								if (CommonConstants.PLUS.equals(sign)) {
									newTime1 += (secondToResync);
									newTime2 += (secondToResync);
								} else {
									newTime1 -= (secondToResync);
									newTime2 -= (secondToResync);
								}
								Date date1 = new Date(newTime1);
								Date date2 = new Date(newTime2);
								lineToRead = sdf.format(date1).toString() + " " + CommonConstants.SUBS_TIMES_CONCAT
										+ " " + sdf.format(date2);
							}
						}
					}
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
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
