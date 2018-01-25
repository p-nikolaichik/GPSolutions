import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

public class Task557 {
    public static void main(String[] args){
        new Task557().start();
    }
    void start() {
        try (
                Scanner in = new Scanner(System.in);
                PrintWriter out = new PrintWriter(System.out)
        ) {
            solution(in, out);
        }
    }
    void solution(Scanner in, PrintWriter out) {
        int countOfMatrix = in.nextInt();
        int sizeOfMatrix = in.nextInt();
        in.nextLine();
        int nRow = in.nextInt()-1;
        int nColumn = in.nextInt()-1;
        in.nextLine();
        int p = in.nextInt();
        in.nextLine();
        int count = countOfMatrix * sizeOfMatrix;
        int[][][] allMatrix = new int[countOfMatrix][sizeOfMatrix][sizeOfMatrix];
        int currentMatrix = 0;
        while (count > 0) {
            int[][] nextMatrix = new int[sizeOfMatrix][sizeOfMatrix];
            for (int i = 0; i < sizeOfMatrix;) {
                String line = in.nextLine();
                if (line.equals("")) {
                    i = 0;
                    continue;
                }
                nextMatrix[i] = Arrays.stream(line.split(" ")).mapToInt(Integer::parseInt).toArray();
                i++;
            }
            allMatrix[currentMatrix++] = nextMatrix;
            count -= sizeOfMatrix;
        }
        int[][] c = allMatrix[0];
        for (int i = 1; i < allMatrix.length; i++) {
            c = multiply(c, allMatrix[i], p, nRow);
        }
        int result = c[nRow][nColumn];
        out.write(result+"");
    }
    int[][] multiply(int[][] a, int[][] b, int p, int nRow) {
        int[][] c = new int[a.length][a.length];
        int i = nRow;
            for (int j = 0; j < a[i].length; j++) {
                int currentA = a[i][j];
                for (int k = 0; k < b.length; k++) {
                    int currentB = b[j][k];
                    int multiply = currentA * currentB;
                    if (multiply >= p) {
                        multiply = multiply % p;
                    }
                    c[i][k] += multiply;
                    if (c[i][k] >= p) {
                        c[i][k] = c[i][k] % p;
                    }
                }
            }
        return c;
    }
}
