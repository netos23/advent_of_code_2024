package ru.fbtw;

import java.awt.*;
import java.io.*;
import java.util.ArrayDeque;
import java.util.BitSet;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;
import java.util.stream.Stream;


record Point(int x, int y, int dist) {
}

public class Main {
    public static void main(String[] args) throws IOException {
        final PrintWriter out = new PrintWriter("output.txt");
        final BufferedReader reader = new BufferedReader(new FileReader("input.txt"), 32000);

        BitSet brokenBits = new BitSet();

        List<Integer> inputBytes = reader.lines()
                .map((s) -> s.split(","))
                .map((a) -> Integer.parseInt(a[0]) + 71 * Integer.parseInt(a[1]))
                .toList();

        inputBytes.stream()
                .limit(1024)
                .forEach(brokenBits::set);

        Integer target = computePath(brokenBits);
        out.write("Part1: " + target + "\n");

        for (int i = 1024; i < inputBytes.size(); i++) {
            Integer brokenByte = inputBytes.get(i);
            brokenBits.set(brokenByte);

            if (computePath(brokenBits) == null) {
                out.write("Part2: " + brokenByte % 71 + " " + brokenByte / 71);
                break;
            }
        }

        out.close();
        reader.close();
    }

    private static Integer computePath(BitSet brokenBits) {
        BitSet visited = new BitSet();
        Queue<Point> queue = new ArrayDeque<>();

        queue.add(new Point(0, 0, 0));
        visited.set(0);

        int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

        while (!queue.isEmpty()) {
            Point c = queue.poll();

            if (c.x() == 70 && c.y() == 70) {
                return c.dist();
            }

            for (int[] child : directions) {
                int nextX = c.x() + child[0];
                int nextY = c.y() + child[1];
                int next = nextX + 71 * nextY;

                if (nextX < 0 || nextX > 70 || nextY < 0 || nextY > 70 || brokenBits.get(next)) {
                    continue;
                }

                if (visited.get(next)) {
                    continue;
                }

                visited.set(next);
                queue.add(new Point(nextX, nextY, c.dist() + 1));
            }

        }

        return null;
    }
}