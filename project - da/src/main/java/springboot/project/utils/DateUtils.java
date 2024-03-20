package springboot.project.utils;

import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    public static SimpleDateFormat sdtf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
    public static SimpleDateFormat dateUpFile = new SimpleDateFormat("ddMMyyyyhhmmss");
    public static long oneHour = 1000L * 60 * 60;
    public static long oneDay = 1000L * 60 * 60 * 24;
    public static Date now() {
        return new Date();
    }

    public static Date today() {
        try {
            return sdf.parse(sdf.format(new Date()));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public String todayStr() {
        return sdf.format(new Date());
    }

    public static String nowStr() {
        return sdf.format(new Date());
    }
    
    public static String dateUpFile() {
        return dateUpFile.format(new Date());
    }

    public static String formattedDate(Date date) {
        return date != null ? sdf.format(date) : nowStr();
    }

	public static Path todayFilename() {
		return null;
	}
}
