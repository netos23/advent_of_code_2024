package ru.fbtw;

import java.awt.*;
import java.io.*;
import java.util.ArrayDeque;
import java.util.BitSet;
import java.util.Queue;
import java.util.StringTokenizer;


record Point(int x, int y, int dist) {
}

public class Main {
    public static void main(String[] args) throws IOException {
        final PrintWriter out = new PrintWriter("output.txt");
        final BufferedReader reader = new BufferedReader(new FileReader("input.txt"), 32000);

        BitSet brokenBits = new BitSet();

        reader.lines()
                .limit(1024)
                .map((s) -> s.split(","))
                .map((a) -> Integer.parseInt(a[0]) + 70 * Integer.parseInt(a[1]))
                .forEach(brokenBits::set);


        BitSet visited = new BitSet();
        Queue<Point> queue = new ArrayDeque<>();

        queue.add(new Point(0, 0, 0));
        visited.set(0);

        int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

        while (!queue.isEmpty()) {
            Point c = queue.poll();

            if (c.x() == 70 && c.y() == 70) {
                out.write(c.dist());
                break;
            }

            for (int[] child : directions) {
                int nextX = c.x() + child[0];
                int nextY = c.y() + child[1];
                int next = nextX + 70 * nextY;

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

        out.write("Missing path");

        out.close();
        reader.close();
    }
}