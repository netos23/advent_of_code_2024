package ru.fbtw;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    static Set<Integer> parts;
    static int maxSize = 0;

    public static void main(String[] args) throws IOException {
        final PrintWriter out = new PrintWriter("output.txt");
        final BufferedReader reader = new BufferedReader(new FileReader("input.txt"), 32000);

        parts = Arrays.stream(reader.readLine().split(", "))
                .map(Main::hash)
                .collect(Collectors.toSet());

        reader.readLine();

        List<Long> variants = reader.lines()
                .map((p) -> hasVariant(p, parts))
                .toList();

        out.println(variants.stream().filter((p) -> p > 0).count());
        out.println(variants.stream().map((p) -> (long) p).reduce(Long::sum));

        out.close();
        reader.close();
    }

    private static int hash(String s) {
        int h = 0;
        maxSize = Integer.max(s.length(), maxSize);
        for (char ch : s.toCharArray()) {
            h = 31 * h + (ch & 0xff);
        }
        return h;
    }

    private static long hasVariant(String p, Set<Integer> parts) {
        Map<Integer, Long> visited = new HashMap<>();

        PriorityQueue<Integer> queue = new PriorityQueue<>();
        visited.put(0, 1L);
        queue.add(0);


        while (!queue.isEmpty()) {
            int offset = queue.poll();
            if (offset >= p.length()) {
                continue;
            }
            "".hashCode();

            int h = 0;
            for (int i = offset, l = 0; i < p.length() && l < maxSize; i++, l++) {
                char v = p.charAt(i);
                h = 31 * h + (v & 0xff);
                if (parts.contains(h)) {

                    Long arr = visited.getOrDefault(i + 1, 0L);
                    visited.put(i + 1, arr + visited.getOrDefault(offset, 0L));

                    if (arr == 0) {

                        queue.add(i + 1);
                    }
                }
            }
        }

        return visited.getOrDefault(p.length(), 0L);
    }
}