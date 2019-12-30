package it.subs.resynchronize.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import it.subs.resynchronize.filemanager.ResyncManager;

public class SubsResynchronizeMain {

	private static long shiftTime = 144450;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String folder;
		String file;
		long time;
		String sign;

		// se richiamato da riga di comando
		if (args != null && args.length > 3) {
			folder = args[0];
			file = args[1];
			time = Long.parseLong(args[2]);
			sign = args[3];
		} else {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			try {
				System.out.println("Cartella del file: ");
				folder = br.readLine(); // System.console().readLine("Cartella del file: ");
				System.out.println("Nome del file: ");
				file = br.readLine(); // System.console().readLine("Nome del file: ");
				System.out.println("Tempo da sincronizzare (in millis): ");
				time = Long.parseLong(br.readLine());
				// Long.parseLong(System.console().readLine("Tempo da sincronizzare (in millis):
				// "));
				System.out.println("Sommare o sottrarre (+ o -): ");
				sign = br.readLine(); // System.console().readLine("Sommare o sottrarre (+ o -): ");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				return;
			}
		}

		ResyncManager fileManager = new ResyncManager(folder, file);

		fileManager.createResyncVersion(time, sign);
	}

}
