package com.usunified;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PhoneBilling {
    private static final int FIVE_MINUTES_IN_SEC = 5 * 60;

    public static void main(String[] args) {
//        String S = "00:01:07,400-234-090 00:05:01,701-080-080 00:01:06,701-080-080 00:05:00,400-234-090";
        String S = "07:01:07,400-234-090 10:05:01,701-080-080 00:05:00,400-234-090";
        System.out.println(solution(S));
    }

    static int solution(String S) {
        Map<String, List<LocalTime>> callMap = parse(S);
        long maxSeconds = 0;
        int minNumber = Integer.MAX_VALUE;
        int total = 0;
        for (Map.Entry<String, List<LocalTime>> e : callMap.entrySet()) {
            long toSeconds = toSeconds(e.getValue());
            maxSeconds = Math.max(maxSeconds, toSeconds);
            total += e.getValue()
                      .stream()
                      .mapToInt(lt -> compute(lt))
                      .sum();
        }
        String freeCall = "";
        for (Map.Entry<String, List<LocalTime>> e : callMap.entrySet()) {
            long toSeconds = toSeconds(e.getValue());
            if (toSeconds == maxSeconds) {
                int number = parse3FirstNumberOfPhoneNumber(e.getKey());
                if (number < minNumber) {
                    minNumber = number;
                    freeCall = e.getKey();
                }
            }
        }
        int freePrice = callMap.get(freeCall)
                               .stream()
                               .mapToInt(lt -> compute(lt))
                               .sum();
        return total - freePrice;
    }

    static int parse3FirstNumberOfPhoneNumber(String phoneNumber) {
        return Integer.parseInt(phoneNumber.split("-")[0]);
    }

    static long toSeconds(List<LocalTime> localTimes) {
        return localTimes.stream()
                         .mapToLong(LocalTime::toSecondOfDay)
                         .sum();
    }

    static Map<String, List<LocalTime>> parse(String S) {
        String[] phoneCalls = S.split(" ");
        Map<String, List<LocalTime>> callMap = new HashMap<>();
        for (String call : phoneCalls) {
            String[] durationAndPhoneNumber = call.split(",");
            String phoneNumber = durationAndPhoneNumber[1];
            String callDuration = durationAndPhoneNumber[0];
            List<LocalTime> durationPerNumber = callMap.getOrDefault(phoneNumber, new ArrayList<>());
            durationPerNumber.add(LocalTime.parse(callDuration));
            callMap.put(phoneNumber, durationPerNumber);
        }
        return callMap;
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
