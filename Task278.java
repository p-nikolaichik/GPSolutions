import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Created by Nikolaichik on 24.01.2018.
 */
public class Task278 {
    public static void main(String[] args){
        new Task278().start();
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
        String dnk1 = in.nextLine().toUpperCase();
        String dnk2 = in.nextLine().toUpperCase();
        int gen1 = 0;
        int gen2 = 0;
        boolean result = false;
        for (;gen1 < dnk1.length(); gen1++) {
            if (gen2 == dnk2.length()) {
                result = false;
                break;
            }
            for (;gen2 < dnk2.length(); gen2++) {
                if (dnk1.charAt(gen1) == dnk2.charAt(gen2)) {
                    gen2++;
                    result = true;
                    break;
                } else result = false;
            }
        }
        out.write(result ? "YES" : "NO");
    }
}
