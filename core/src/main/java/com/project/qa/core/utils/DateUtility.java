package com.project.qa.core.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author : Vikas S.
 * @since : 10-08-2019, Sat
 **/
public class DateUtility {

    public static String getTodaysDate(String format) {
        DateFormat dateFormat = new SimpleDateFormat();
        return dateFormat.format(new Date());
    }

    public static String getRelativeDate(String format, String dateValue, int noOfDays) {
        Calendar calendar = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat(format);
        try {
            calendar.setTime(dateFormat.parse(dateValue));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        calendar.add(Calendar.DAY_OF_MONTH, noOfDays);
        return dateFormat.format(calendar.getTime());
    }
}
