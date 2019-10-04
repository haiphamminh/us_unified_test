package com.usunified;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PhoneBilling {
    private static final int FIVE_MINUTES_IN_SEC = 5 * 60;
    private static final int SECONDS_PER_HOUR = 1 * 60 * 60;
    private static final int SECONDS_PER_MINUTE = 1 * 60;

    public static void main(String[] args) {
//        String S = "00:01:07,400-234-090 00:05:01,701-080-080 00:05:00,400-234-090"; // 900
/*        String S = "00:01:07,400-234-090 00:05:01,701-080-080 00:00:01,601-080-080 00:01:06,701-080-080 00:05:00," +
                "400-234-090"; // 951*/
//        String S = "07:01:07,400-234-090 10:05:01,701-080-080 00:05:00,400-234-090"; // 64050
        String S = "00:01:07,400-234-090 00:05:00,400-234-090"; // 951
//        String S = "00:01:07,400-234-090"; // 201
//        String S = ""; // 0
        System.out.println(solution(S));
    }

    static int solution(String S) {
        if (S.isEmpty()) return 0;

        String[] phoneCalls = S.split(" ");
        Map<String, List<CallDuration>> phoneLogsMap = new HashMap<>();

        int totalAmount = 0;
        for (String call : phoneCalls) {
            // parse call duration and phone number, then add all durations to the map
            String[] durationAndPhoneNumber = call.split(",");
            String phoneNumber = durationAndPhoneNumber[1];
            String callDuration = durationAndPhoneNumber[0];
            CallDuration duration = CallDuration.parse(callDuration);
            List<CallDuration> durationPerNumber = phoneLogsMap.getOrDefault(phoneNumber, new ArrayList<>());
            durationPerNumber.add(duration);
            phoneLogsMap.put(phoneNumber, durationPerNumber);

            // compute amount
            totalAmount += compute(duration);
        }

        // in case there is only one phone number logged, it must pay
        if (phoneLogsMap.size() == 1) {
            return totalAmount;
        }

        int longestTotalDuration = 0;
        int freeAmount = 0;
        for (Map.Entry<String, List<CallDuration>> e : phoneLogsMap.entrySet()) {
            // find the longest total duration
            int totalSecPerNumber = compute(e.getValue());
            if (totalSecPerNumber > longestTotalDuration) {
                longestTotalDuration = totalSecPerNumber;
                freeAmount = totalSecPerNumber;
            }
        }
        return totalAmount - freeAmount;
    }

    static int compute(List<CallDuration> callDurations) {
        return callDurations.stream()
                            .mapToInt(PhoneBilling::compute)
                            .sum();
    }

    static int compute(CallDuration callDuration) {
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
