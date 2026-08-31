package main.java.net.kallen.solaris.math.sieve;

import java.util.Arrays;

public final class Eratosthenes {
    public static int[] sieve(int n) {
        if (n <= 1) {
            System.out.println("Prime Numbers: []");
            return new int[0];
        }
        boolean[] flags = new boolean[n + 1];
        // All flags start true
        Arrays.fill(flags, true);

        // Except 0 & 1, which are not prime nums
        flags[0] = false;
        flags[1] = false;

        // Starting at 2
        for (int i = 2; i * i <= n; i++) {
            // Check here if flag is true because it
            // avoids checking for multiples of values already checked
            if (flags[i]) {
                // Mark all multiples of i as false
                for (int mult = i * i; mult <= n; mult += i) {
                    flags[mult] = false;
                }
            }
        }

        int count = 0;
        for (boolean f : flags) if (f) count++;
        int[] primes = new int[count];
        int idx = 0;
        for (int i = 0; i < flags.length; i++) {
            if (flags[i]) primes[idx++] = i;
        }
        return primes;
    }
}
