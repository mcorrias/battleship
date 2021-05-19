package android.text.format;

import android.content.res.Configuration;
import android.content.res.Resources;

import java.util.Formatter;
import java.util.Locale;

public class DateUtils {
    private static final Object sLock = new Object();
    private static Configuration sLastConfig;
    private static String sElapsedFormatMMSS = "%02d:%02d";
    private static String sElapsedFormatHMMSS = "%d:%02d:%02d";

    public static String formatElapsedTime(long elapsedSeconds) {
        return formatElapsedTime(null, elapsedSeconds);
    }

    public static String formatElapsedTime(StringBuilder recycle, long elapsedSeconds) {
        // Break the elapsed seconds into hours, minutes, and seconds.
        long hours = 0;
        long minutes = 0;
        long seconds = 0;
        if (elapsedSeconds >= 3600) {
            hours = elapsedSeconds / 3600;
            elapsedSeconds -= hours * 3600;
        }
        if (elapsedSeconds >= 60) {
            minutes = elapsedSeconds / 60;
            elapsedSeconds -= minutes * 60;
        }
        seconds = elapsedSeconds;

        // Create a StringBuilder if we weren't given one to recycle.
        // TODO: if we cared, we could have a thread-local temporary StringBuilder.
        StringBuilder sb = recycle;
        if (sb == null) {
            sb = new StringBuilder(8);
        } else {
            sb.setLength(0);
        }

        // Format the broken-down time in a locale-appropriate way.
        // TODO: use icu4c when http://unicode.org/cldr/trac/ticket/3407 is fixed.
        Formatter f = new Formatter(sb, Locale.getDefault());
        //initFormatStrings();
        if (hours > 0) {
            return f.format(sElapsedFormatHMMSS, hours, minutes, seconds).toString();
        } else {
            return f.format(sElapsedFormatMMSS, minutes, seconds).toString();
        }
    }

//    private static void initFormatStrings() {
//        synchronized (sLock) {
//            initFormatStringsLocked();
//        }
//    }
//
//    private static void initFormatStringsLocked() {
//        Resources r = Resources.getSystem();
//        Configuration cfg = r.getConfiguration();
//        if (sLastConfig == null || !sLastConfig.equals(cfg)) {
//            sLastConfig = cfg;
//            sElapsedFormatMMSS = "%02d:%02d";
//            sElapsedFormatHMMSS = "%d:%02d:%02d";
//        }
//    }
}
