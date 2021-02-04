package test.matrix.draw;

import com.sun.javafx.binding.StringFormatter;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        int[][] a = new int[][]{
                {0, 1, 0, 0},
                {0, 1, 1, 0},
                {0, 0, 1, 1},
                {0, 0, 0, 3}};
        String expected = toString2dArray(new int[][]{
                {2, 1, 0, 0},
                {2, 1, 1, 0},
                {2, 2, 1, 1},
                {2, 2, 2, 3}});
        String current = fill(a, 0, 0, 2);
        System.out.println(StringFormatter.format("Expected: [%s]", expected.equals(current)).getValue());
        System.out.println(current);

        int[][] b = new int[][]{
                {0, 1, 0, 0},
                {0, 1, 1, 0},
                {0, 0, 1, 1},
                {0, 0, 0, 3}};
        expected = toString2dArray(new int[][]{
                {0, 2, 0, 0},
                {0, 2, 2, 0},
                {0, 0, 2, 2},
                {0, 0, 0, 3}});
        current = fill(b, 2, 2, 5);
        System.out.println(StringFormatter.format("Expected: [%s]", expected.equals(current)).getValue());
        System.out.println(current);
    }


    private static String toString2dArray(int[][] array) {
        return Arrays.stream(array).map(Arrays::toString).collect(Collectors.joining("\r\n"));
    }

    private static String fill(int[][] arg, int x, int y, int color) {
        int currentColor = arg[y][x];
        Set<Pair<Integer, Integer>> toDraw = new HashSet<>();
        toReDraw(arg, Collections.singletonList(new Pair<>(x, y)), currentColor, toDraw);
        toDraw.forEach(pair -> arg[pair.getValue()][pair.getKey()] = color);
        return toString2dArray(arg);
    }

    private static void toReDraw(int[][] matrix, List<Pair<Integer, Integer>> neighbor, int currentColor, Set<Pair<Integer, Integer>> toDraw) {
        for (Pair<Integer, Integer> test : neighbor) {
            if (toDraw.contains(test)) {
                continue;
            }
            int color = matrix[test.getValue()][test.getKey()];
            if (color == currentColor) {
                toDraw.add(test);
                toReDraw(matrix, getNeighbor(matrix, test.getValue(), test.getKey()), currentColor, toDraw);
            }
        }
    }

    private static List<Pair<Integer, Integer>> getNeighbor(int[][] matrix, int x, int y) {
        List<Pair<Integer, Integer>> rez = new ArrayList<>();
        for (int yO = y - 1; yO <= y + 1; yO++) {
            if (matrix.length == yO) {
                break;
            }
            if (yO < 0) {
                continue;
            }
            for (int xO = x - 1; xO <= x + 1; xO++) {
                if (matrix[yO].length == xO) {
                    break;
                }
                if (xO < 0 || (x == xO && yO == y)) {
                    continue;
                }
                rez.add(new Pair<>(yO, xO));
            }
        }
        return rez;
    }
}
