package es.justo.giiis.pi.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TimeUtil {
    static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
	public static String getTime() {
        Calendar calendar = Calendar.getInstance();
        
        return simpleDateFormat.format(calendar.getTime());
	}
}
