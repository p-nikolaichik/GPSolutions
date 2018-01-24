import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

public class Task579 {
    public static void main(String[] args){
        new Task579().start();
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
        in.nextLine();
        int[] arrayNumb = Arrays.stream(in.nextLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        int minSum = 0;
        int maxSum = 0;
        String minIndexArray = "";
        String maxIndexArray = "";
        int countMin = 0;
        int countMax = 0;
        for (int i = 0; i < arrayNumb.length; i++) {
            if (arrayNumb[i] >= 0) {
                maxSum += arrayNumb[i];
                maxIndexArray += i+1 + " ";
                countMax++;
            } else {
                minSum += arrayNumb[i];
                minIndexArray +=i+1 + " ";
                countMin++;
            }
        }
        int returnCount = maxSum >= minSum*-1 ? countMax : countMin;
        String returnIndexArray = maxSum >= minSum*-1 ? maxIndexArray : minIndexArray;
        out.write(returnCount+"");
        out.write("\n");
        out.write(returnIndexArray);
    }
}
