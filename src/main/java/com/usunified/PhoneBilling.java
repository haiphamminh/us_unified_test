package com.usunified;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PhoneBilling {
    private static final int FIVE_MINUTES_IN_SEC = 5 * 60;

    public static void main(String[] args) {
//        String S = "00:01:07,400-234-090 00:05:01,701-080-080 00:05:00,400-234-090";
        String S = "00:01:07,400-234-090 00:05:01,701-080-080 00:01:06,701-080-080 00:05:00,400-234-090";
//        String S = "07:01:07,400-234-090 10:05:01,701-080-080 00:05:00,400-234-090";
        System.out.println(solution(S));
    }

    static int solution(String S) {
        String[] phoneCalls = S.split(" ");
        Map<String, List<LocalTime>> phoneLogsMap = new HashMap<>();
        long longestTotalDuration = 0;
        int totalAmount = 0;
        for (String call : phoneCalls) {
            // parse call duration and phone number, then add all durations to the map
            String[] durationAndPhoneNumber = call.split(",");
            String phoneNumber = durationAndPhoneNumber[1];
            String callDuration = durationAndPhoneNumber[0];
            LocalTime duration = LocalTime.parse(callDuration);
            List<LocalTime> durationPerNumber = phoneLogsMap.getOrDefault(phoneNumber, new ArrayList<>());
            durationPerNumber.add(duration);
            phoneLogsMap.put(phoneNumber, durationPerNumber);

            // find the longest total duration
            long totalSecPerNumber = computeTotalSecPerNumber(durationPerNumber);
            longestTotalDuration = Math.max(longestTotalDuration, totalSecPerNumber);

            // compute amount
            totalAmount += compute(duration);
        }

        // find the smallest phone number that shares the longest total duration
        String freePhoneNumber = "";
        int smallestPhoneNumber = Integer.MAX_VALUE;
        for (Map.Entry<String, List<LocalTime>> e : phoneLogsMap.entrySet()) {
            long totalSecPerNumber = computeTotalSecPerNumber(e.getValue());
            if (totalSecPerNumber == longestTotalDuration) {
                int number = Integer.parseInt(e.getKey()
                                               .split("-")[0]);
                if (number < smallestPhoneNumber) {
                    smallestPhoneNumber = number;
                    freePhoneNumber = e.getKey();
                }
            }
        }

        // re-compute the free amount for the free promotion
        int freeAmount = compute(phoneLogsMap.get(freePhoneNumber));

        return totalAmount - freeAmount;
    }

    static long computeTotalSecPerNumber(List<LocalTime> localTimes) {
        return localTimes.stream()
                         .mapToLong(LocalTime::toSecondOfDay)
                         .sum();
    }

    static int compute(List<LocalTime> localTimes) {
        return localTimes.stream()
                         .mapToInt(PhoneBilling::compute)
                         .sum();
    }

    static int compute(LocalTime lt) {
        int amount = 0;
        int sec = lt.toSecondOfDay();
        if (sec < FIVE_MINUTES_IN_SEC) {
            amount += sec * 3;
        } else if (sec == FIVE_MINUTES_IN_SEC) {
            amount += 5 * 150;
        } else {
            int addedMin = lt.getSecond() > 0 ? 1 : 0;
            amount += (lt.getHour() * 60 + lt.getMinute() + addedMin) * 150;
        }
        return amount;
    }
}
