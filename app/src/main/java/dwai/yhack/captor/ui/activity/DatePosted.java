package dwai.yhack.captor.ui.activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Stefan on 11/1/2014.
 */
public class DatePosted implements Comparable<DatePosted>{

    private int year,month,day,hour,minute,second;


    public int getYear() {
        return year;
    }

    public DatePosted setYear(int year) {
        this.year = year;
        return this;
    }

    public int getMonth() {
        return month;
    }

    public DatePosted setMonth(int month) {
        this.month = month;
        return this;
    }

    public int getDay() {
        return day;
    }

    public DatePosted setDay(int day) {
        this.day = day;
        return this;
    }

    public int getHour() {
        return hour;
    }

    public DatePosted setHour(int hour) {
        this.hour = hour;
        return this;
    }

    public int getMinute() {
        return minute;
    }

    public DatePosted setMinute(int minute) {
        this.minute = minute;
        return this;
    }

    public int getSecond() {
        return second;
    }

    public DatePosted setSecond(int second) {
        this.second = second;
        return this;
    }

    @Override
    public int compareTo(DatePosted dp) {
        return dp.year - year
                + dp.month - month
                + dp.day - day
                + dp.hour - hour
                + dp.minute - minute
                + dp.second - second;
    }


//    @Override
//    public String toString() {
//        StringBuffer finalString = new StringBuffer();
//
//        List<Integer> allCombined = new ArrayList<Integer>();
//        allCombined.add(year);
//        allCombined.add(month);
//        allCombined.add(day);
//        allCombined.add(hour);
//        allCombined.add(minute);
//        allCombined.add(second);
//        String[] shortened = new String[]{"yr, ", "mnth, ", "d, ", "h, ", "min, ", "s"};
//
//        for(int i = 0; i < allCombined.size();i++){
//            int oneCombined = allCombined.get(i);
//            if(oneCombined >= 0){
//                finalString.append(oneCombined + " " + shortened[i]);
//            }
//        }
//        return finalString.toString();
//
//
//    }
    public String toString(){
        return month + "/" + day + "/" + year;
    }
}
