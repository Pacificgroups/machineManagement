package com.wipro.mms.utils;

import java.util.concurrent.ThreadLocalRandom;

public class AppUtils {
    public static Long generateRandomUserId() {
        return (long) ThreadLocalRandom.current().nextInt(100000, 1000000);
    }
}
