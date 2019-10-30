package com.usunified;

import java.util.function.IntPredicate;

public class CountDigitsCharacters {
    public static void main(String[] args) {
        String str = "aZ$6:12,./`~*/-+57frDOQNZ";
        System.out.println(count(str, Character::isDigit));
        System.out.println(count(str, Character::isLetter));
    }

    static int count(String str, IntPredicate predicate) {
        if (str == null || str.isEmpty()) {
            return 0;
        }
        char[] chars = str.toCharArray();
        long count = str.chars()
                        .filter(predicate)
                        .count();
        return (int) count;
    }
}
