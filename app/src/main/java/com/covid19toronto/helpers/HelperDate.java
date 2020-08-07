package com.covid19toronto.helpers;

import com.covid19toronto.Record;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static java.lang.Long.compare;

public class HelperDate {
    private final static long firstRecord = getDateLongFromYMD(2020, 1, 21); //patient zero in the GTA

    public static long getDateLongFromString(String dateString) {
        int dashIndex1 = dateString.indexOf("-");
        int dashIndex2 = dateString.lastIndexOf("-");

        int year = Integer.parseInt(dateString.substring(0, dashIndex1));
        int month = Integer.parseInt(dateString.substring(dashIndex1 + 1, dashIndex2));
        int day = Integer.parseInt(dateString.substring(dashIndex2 + 1));

        return new DateTime(year, month, day, 0, 0, 0).getMillis();
    }

    public static long getDateLongFromYMD(int year, int month, int day) {
        DateTime dateTime = new DateTime(year, month, day, 0, 0, 0);
        return dateTime.getMillis();
    }

    public static String getDateStringFromLong(long dateLong) {
        DateTime dateTime = new DateTime(dateLong);
        return dateTime.getYear() + "-" + dateTime.getMonthOfYear() + "-" + dateTime.getDayOfMonth();
    }

    public static int getDateMonthFromLong(long date) {
        DateTime dateTime = new DateTime(date);
        return dateTime.getMonthOfYear();
    }

    public static int getDateDayFromLong(long date) {
        DateTime dateTime = new DateTime(date);
        return dateTime.getDayOfMonth();
    }

    public static int getDateYearFromLong(long date) {
        DateTime dateTime = new DateTime(date);
        return dateTime.getYear();
    }

    private static long getLastRecordLong(List<Record> recordList) {
        long min = Long.MIN_VALUE;
        int index = -1;
        for (int i = 0; i < recordList.size(); i++) {
            long f = recordList.get(i).getEpisodeDateLong();
            if (compare(f, min) > 0) {
                min = f;
                index = i;
            }
        }
        return recordList.get(index).getEpisodeDateLong();
    }

    public static Map<Long, Integer> getCasesByDateLong(List<Record> recordList) {
        if (recordList == null | recordList.size() == 0)
            return null;
        
        long lastRecord = getLastRecordLong(recordList);
        Integer number;
        Map<Long, Integer> recordMap = new TreeMap<>();

        //populate recordmap with every day from beginning to end
        long dateLong = firstRecord;
        while (dateLong <= lastRecord) {
            recordMap.put(dateLong, 0);
            dateLong = getDateLongFromString(getNextDateString(getDateStringFromLong(dateLong)));
        }

        for (int i = 0; i < recordList.size(); i++) {
            number = recordMap.get(recordList.get(i).getEpisodeDateLong());
            recordMap.put(recordList.get(i).getEpisodeDateLong(), number + 1);
        }
        return recordMap;
    }

    public static String getNextDateString(String dateString) {
        DateTimeFormatter parser = ISODateTimeFormat.date();
        DateTime dateTime = parser.parseDateTime(dateString);
        return parser.print(dateTime.plusDays(1));
    }

    public static int getHighestNumberInMap(Map<Long, Integer> recordMap) {
        Map.Entry<Long, Integer> maxEntry = null;
        for (Map.Entry<Long, Integer> entry : recordMap.entrySet()) {
            if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0)
                maxEntry = entry;
        }
        return maxEntry.getValue();
    }


    public static String getPercentageStringFromInt(int value, int cases) {
        return String.format("%.1f", ((double) value / cases) * 100) + "%";
    }

    public static int getNumberMostCases(List<Record> recordList) {

        if (recordList == null || recordList.size() == 0)
            return 0;
        int mostMatches = 0;

        for(int i = 0; i < recordList.size(); i++) {
            int matches = 0;
            long holder = recordList.get(i).getEpisodeDateLong();

            for(int j=0; j<recordList.size(); j++) {
                long number = recordList.get(j).getEpisodeDateLong();
                if(number == holder) {
                    matches++;
                }
            }

            if(matches > mostMatches)
                mostMatches = matches;
        }
        return mostMatches;
    }

    public static String getDateStringMostCases(List<Record> recordList) {

        if (recordList == null || recordList.size() == 0)
            return "";

        int mostMatches = 0;
        int index = 0;

        for(int i = 0; i < recordList.size(); i++) {
            int matches = 0;
            long holder = recordList.get(i).getEpisodeDateLong();

            for(int j=0; j<recordList.size(); j++) {
                long number = recordList.get(j).getEpisodeDateLong();
                if(number == holder) {
                    matches++;
                }
            }

            if(matches > mostMatches) {
                mostMatches = matches;
                index = i;
            }
        }
        return recordList.get(index).getEpisodeDate();
    }
}


