package com.usunified;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PhoneBilling {
    private static final int FIVE_MINUTES_IN_SEC = 5 * 60;
    private static final int SECONDS_PER_HOUR = 1 * 60 * 60;
    private static final int SECONDS_PER_MINUTE = 1 * 60;

    public static void main(String[] args) {
        String S = "00:01:07,400-234-090 00:05:01,701-080-080 00:05:00,400-234-090"; // 900
        System.out.println(S + " = " + solution(S));
        S = "00:05:01,400-234-090 00:01:07,701-080-080 00:05:00,701-080-080 00:01:06,400-234-090"; // 951
        System.out.println(S + " = " + solution(S));
        S = "00:01:07,400-234-090 00:05:01,701-080-080 00:00:01,601-080-080 00:01:06,701-080-080 00:05:00," + "400" + "-234-090"; // 1101
        System.out.println(S + " = " + solution(S));
        S = "07:01:07,400-234-090 10:05:01,701-080-080 00:05:00,400-234-090"; // 64050
        System.out.println(S + " = " + solution(S));
        S = "00:01:07,400-234-090 00:05:00,400-234-090"; // 951
        System.out.println(S + " = " + solution(S));
        S = "00:01:07,400-234-090"; // 201
        System.out.println(S + " = " + solution(S));
        S = ""; // 0
        System.out.println(S + " = " + solution(S));
        S = null; // 0
        System.out.println(S + " = " + solution(S));
    }

    static int solution(String S) {
        if (S == null || S.isEmpty()) return 0;

        String[] phoneCalls = S.split(" ");
        Map<String, List<CallDuration>> callsPerNumber = new HashMap<>();

        int totalAmount = 0;
        for (String call : phoneCalls) {
            // parse call duration and phone number, then add all durations to the map
            String[] durationAndPhoneNumber = call.split(",");
            String phoneNumber = durationAndPhoneNumber[1];
            String callDuration = durationAndPhoneNumber[0];
            CallDuration duration = CallDuration.parse(callDuration);
            List<CallDuration> durationsPerNumber = callsPerNumber.getOrDefault(phoneNumber, new ArrayList<>());
            durationsPerNumber.add(duration);
            callsPerNumber.put(phoneNumber, durationsPerNumber);

            // compute amount
            totalAmount += computePrice(duration);
        }

        // in case there is only one phone number logged, it must pay
        if (callsPerNumber.size() == 1) {
            return totalAmount;
        }

        long longestTotalDuration = 0;
        for (Map.Entry<String, List<CallDuration>> e : callsPerNumber.entrySet()) {
            // find the longest total duration
            long totalDurationPerNumber = toSeconds(e.getValue());
            longestTotalDuration = Math.max(longestTotalDuration, totalDurationPerNumber);
        }

        // find the smallest phone number that shares the longest total duration
        String freePhoneNumber = "";
        for (Map.Entry<String, List<CallDuration>> e : callsPerNumber.entrySet()) {
            long totalDurationPerNumber = toSeconds(e.getValue());
            if (totalDurationPerNumber == longestTotalDuration) {
                if (freePhoneNumber.isEmpty() || freePhoneNumber.compareToIgnoreCase(e.getKey()) > 0) {
                    freePhoneNumber = e.getKey();
                }
            }
        }
        // re-compute the free amount for the free promotion
        int freeAmount = computePrice(callsPerNumber.get(freePhoneNumber));
        return totalAmount - freeAmount;
    }

    static int toSeconds(List<CallDuration> callDurations) {
        return callDurations.stream()
                            .collect(Collectors.summingInt(CallDuration::toSecond));
    }

    static int computePrice(List<CallDuration> callDurations) {
        return callDurations.stream()
                            .collect(Collectors.summingInt(PhoneBilling::computePrice));
    }

    static int computePrice(CallDuration callDuration) {
        int amount = 0;
        int sec = callDuration.toSecond();
        if (sec < FIVE_MINUTES_IN_SEC) {
            amount += sec * 3;
        } else if (sec == FIVE_MINUTES_IN_SEC) {
            amount += 5 * 150;
        } else {
            int addedMin = callDuration.getSecond() > 0 ? 1 : 0;
            amount += (callDuration.getHour() * 60 + callDuration.getMinute() + addedMin) * 150;
        }
        return amount;
    }

    static class CallDuration {
        private final int hour;
        private final int minute;
        private final int second;

        public CallDuration(int hour, int minute, int second) {
            this.hour = hour;
            this.minute = minute;
            this.second = second;
        }

        public int toSecond() {
            int total = hour * SECONDS_PER_HOUR;
            total += minute * SECONDS_PER_MINUTE;
            total += second;
            return total;
        }

        public int getHour() {
            return hour;
        }

        public int getMinute() {
            return minute;
        }

        public int getSecond() {
            return second;
        }

        public static CallDuration parse(String text) {
            String[] parsedText = text.split(":");
            int hour = Integer.valueOf(parsedText[0]);
            int minute = Integer.valueOf(parsedText[1]);
            int second = Integer.valueOf(parsedText[2]);
            return new CallDuration(hour, minute, second);
        }
    }
}
