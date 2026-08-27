package test.java.net.kallen.solaris.example;

import main.java.net.kallen.solaris.math.sieve.Eratosthenes;

import java.util.Arrays;

public class SieveDemo {
    public static void main(String[] args) {
        System.out.println("Eratosthenes Sieve, n=50: " + Arrays.toString(Eratosthenes.sieve(50)));
    }
}
