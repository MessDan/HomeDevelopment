package home.csv.splitter.utility;

public class Utility {

	public static boolean isValid(String toValidate) {
		if (toValidate != null) {
			String newToVal = new String(toValidate);
			return !newToVal.replace(" ", "").equals("");
		}
		return false;
	}
}
