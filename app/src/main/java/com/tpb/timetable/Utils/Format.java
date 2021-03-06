package com.tpb.timetable.Utils;

import android.text.format.DateFormat;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by theo on 21/04/16.
 */
public class Format {
    
    private Format() {}

    public static String join(String[] strings, String separator) {
        final StringBuilder sb = new StringBuilder(strings.length * 2);
        for(int i = 0 ; i < strings.length; i++) {
            sb.append(strings[i]);
            if(i != strings.length - 1) sb.append(separator);
        }

        return sb.toString();
    }


    public static int hmToInt(int hour, int minute) {
        return (hour * 60) + minute;
    }

    /**
     * Formats a numeric time in the format 60*h + m
     * @param time The time to format
     * @param separator The separator to place between hours and minutes
     * @return The formatted time string
     */
    public static String format(int time, String separator) {
        if(time < 0 || time > 1440) {
            throw new IllegalArgumentException("Time must be between 0 and 1440. " + time + " is invalid");
        }
        final int mins = time%60;
        final int hours = (time-mins)/60;
        if(mins == 0) {
            return hours + separator + "00";
        } else if(mins < 10) {
            return hours + separator + "0" + mins;
        } else {
            return hours + separator + mins;
        }

    }

    /**
     * Forwards to format(int time, String separator) with separator of ':'
     * @param time The time to format
     * @return The formatted time string
     */
    public static String format(int time) {
        return format(time, ":");
    }

    /**
     * Forwards to format(int time, String separator) by converting hour and minute to four digit value
     * @param hour The hour of the time, 0 to 23
     * @param minute Minute of the time, 0 to 59
     * @param separator The separator string to place between the hour and minute
     * @return The formatted time string
     */
    /**
     * @param hour The hour of the time
     * @param minute The minute of the time
     * @param separator The string to place between the hour and minute
     * @return A formatted string of the time
     */
    public static String format(int hour, int minute, String separator) {
        return format((hour*60)+minute, separator);
    }

    /**
     * @param hour The hour of the time
     * @param minute The minute of the time
     * @return A formatted string of the time hour:minute
     */
    public static String format(int hour, int minute) {
        return format(hmToInt(hour, minute));
    }

    /**
     * @param time An integer time in the format 60*h + m
     * @return The hour of the time
     */
    public static int getHour(int time) {
        return (time -time%60)/60;
        
    }

    /**
     * @param time An integer time in the format 60*h + m
     * @return The minute of the time,
     */
    public static int getMinute(int time) {
        return time%60;
    }

    public static float getPercentageComplete(int time, int start, int end) {
        if(time < start || time > end) return  0;
        time -= start;
        end -= start;
        return (float) time/ end;
    }

    /**
     * @param day The day, of a week, month, year, etc
     * @return The correct suffix for the value
     */
    public static String getSuffix(int day) {
        if (day >= 11 && day <= 13) {
            return "th";
        }
        switch (day % 10) {
            case 1:
                return "st";
            case 2:
                return "nd";
            case 3:
                return "rd";
            default:
                return "th";
        }
    }

    public static String dateToString(Date d) {
        final Calendar  cal = Calendar.getInstance();
        cal.setTime(d);
        final int day = cal.get(Calendar.DAY_OF_MONTH);



        String format = DateFormat.format("EEEE", d) + " ";
        format +=  day + getSuffix(day);
        if(cal.get(Calendar.MONTH) != Calendar.getInstance().get(Calendar.MONTH)) {
            format += DateFormat.format("MMMM",d) + " ";
        }

        if(d.getYear() != new Date().getYear()) {
            format += " " + (1900+d.getYear());
        }
        return format;
    }

    /**
     * Calculates how many lines are needed to fit a given string into a text
     * -view of a given width
     * @param textView A reference to the text view being fitted
     * @param text The text to be fitted into the textview
     * @return The number of lines need to fit the text in the textview
     */
    public static int numLinesForTextView(TextView textView, String text) {
        final String[] lines = text.split("\n");
        int numLines = lines.length-1;
        for(String s : lines) {
            if(textView.getPaint().breakText(s,  0, s.length(),
                    true, textView.getWidth(), null) > s.length()) {
                numLines ++;
            }
        }
        return numLines;
    }
}
