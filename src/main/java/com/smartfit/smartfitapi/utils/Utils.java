package com.smartfit.smartfitapi.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Utils {
    public static String hashPassword(String password) {
        return Integer.toString(password.hashCode());
    }

    public static Date plusDayToCurrentDate(Integer day) {
        Date d = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        c.add(Calendar.DATE, day);
        d = c.getTime();
        return d;
    }

    public static Integer caloriesBurnedCalculate(Float met, Integer weightInKg, Float workoutDurationInHours) {
        return Math.round(met * weightInKg * workoutDurationInHours);
    }

    public static long getDateDiffInSeconds(Date date1, Date date2) {
        long diff = date2.getTime() - date1.getTime();
        return TimeUnit.MILLISECONDS.toSeconds(diff);
    }

    public static boolean isSameDay(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(date1);
        cal2.setTime(date2);
        return cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR) &&
                cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);
    }
}
