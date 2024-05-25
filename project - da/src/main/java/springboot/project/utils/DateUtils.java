package springboot.project.utils;

import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class DateUtils {

    public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    public static SimpleDateFormat sdtf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
    public static SimpleDateFormat dateUpFile = new SimpleDateFormat("ddMMyyyyhhmmss");
    public static long oneHour = 1000L * 60 * 60;
    public static long oneDay = 1000L * 60 * 60 * 24;
    public static long oneMinute = 1000L * 60;
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

    public static Date todayGMT7 () {
        try {
            return new Date(sdf.parse(sdf.format(new Date())).getTime() + hoursToTime(7));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static Integer getAge(Date birthDate) {
        LocalDate localBirthdate = new java.sql.Date(birthDate.getTime()).toLocalDate();
        LocalDate now = LocalDate.now();
        return  now.getYear() - localBirthdate.getYear();
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

    public static Long minutesToTime (int minute) {
        return minute * oneMinute;
    }

    public static Long hoursToTime (int hour) {
        return hour * oneHour;
    }

    public static Long daysToTime (int day) {
        return day * oneDay;
    }
}
