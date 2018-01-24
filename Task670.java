import java.io.PrintWriter;
import java.util.Scanner;

public class Task670 {
    public static void main(String[] args){
        new Task670().start();
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
        int N = in.nextInt();
        for (int i = 1; i <= N; i++) {
            int count = 0;
            String number = String.valueOf(i);
            int[] charInt = new int[number.length()];
            for (int j = 0; j < charInt.length; j++) {
                charInt[j] = Integer.valueOf(number.charAt(j));
            }
            for (int k = 0; k < charInt.length; k++) {
                for (int j = 0; j < charInt.length; j++) {
                    if (k == j) continue;
                    if (charInt[k] == charInt[j]){
                        count++;
                        if (count == 1) {
                            break;
                        }
                    }
                }
                if (count == 1) {
                    break;
                }
                count = 0;
            }
            if (count == 1) {
                N++;
            }
        }
        out.write(N+"");
    }

}
