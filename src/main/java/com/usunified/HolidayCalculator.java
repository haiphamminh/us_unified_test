package com.usunified;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;

public class HolidayCalculator {
    public static void main(String[] args) {
        /*long weeks = solution(2014, "April1", "May", "Wednesday");
        System.out.println("solution(2014, \"March\", \"May\", \"Wednesday\") = " + weeks);
        weeks = solution(2019, "August", "September", "Tuesday");
        System.out.println("solution(2019, \"August\", \"October\", \"Tuesday\") = " + weeks);*/
        long weeks = solution(2019, "March", "March", "Tuesday");
        System.out.println("solution(2000, \"August\", \"October\", \"Tuesday\") = " + weeks);
    }

    static int solution(int Y, String A, String B, String W) {
        if (Y < 2001 || Y > 2099) {
            System.err.println("The year is out of range [2001, 2099]");
            return -1;
        }
        // find the start week
        Month monthA = Month.valueOf(A.toUpperCase());
        Month monthB = Month.valueOf(B.toUpperCase());
        if (monthB.getValue() < monthA.getValue()) {
            System.err.println("Month B does not preceed month A");
            return -1;
        }
        LocalDate dateA = Year.of(Y)
                              .atMonth(monthA)
                              .atDay(1)
                              .with(TemporalAdjusters.firstInMonth(DayOfWeek.MONDAY));
        System.out.println(dateA);

        // find the end week
        LocalDate dateB = Year.of(Y)
                              .atMonth(monthB)
                              .atEndOfMonth()
                              .with(TemporalAdjusters.lastInMonth(DayOfWeek.SUNDAY));
        System.out.println(dateB);

        long weeks = ChronoUnit.WEEKS.between(dateA, dateB);
        long days = ChronoUnit.DAYS.between(dateA, dateB);
        System.out.println("weeks:" + weeks);
        System.out.println("days:" + days);

/*
        LocalDate d1 = LocalDate.of(2019, 11, 4);
        LocalDate d2 = LocalDate.of(2019, 11, 10);

        System.out.println(ChronoUnit.WEEKS.between(d1, d2));
        System.out.println(ChronoUnit.DAYS.between(d1, d2));
*/

        return (int) (days+1)/7;
    }
}
