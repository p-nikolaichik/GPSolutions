import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class StringAnalyzer {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<String> list = new ArrayList<>();
        System.out.println("Введите параметр!");
        String parameter = scanner.nextLine();
        String[] wordsInParameter = parameter.split(" ");
        while (true) {
            String line = scanner.nextLine();
            if (line.equals("")) break;
            String[] wordsInLine = line.split(" ");
            for (int i = 0; i < wordsInLine.length; i++) {
                String currentWord = wordsInLine[i];
                if (isValidRegularExpression(parameter)) {
                    if (checkRegularExpression(currentWord, wordsInParameter)) {
                        list.add(line);
                        break;
                    }
                } else {
                    if (checkString(currentWord, wordsInParameter)) {
                        list.add(line);
                        break;
                    }
                }

            }
        }
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }
    }

    public static boolean checkRegularExpression(String word, String[] parameter) {
        boolean flag = false;
        for (int i = 0; i < parameter.length; i++) {
            Pattern p = Pattern.compile(parameter[i]);
            Matcher m = p.matcher(word);
            if (m.matches()) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    public static boolean checkString(String input, String[] parameter) {
        boolean flag = false;
        for (int i = 0; i < parameter.length; i++) {
            if (input.equals(parameter[i])) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    public static boolean isValidRegularExpression(String input) {
        boolean isRegex;
        try {
            Pattern.compile(input);
            isRegex = true;
        } catch (PatternSyntaxException e) {
            isRegex = false;
        }
        return isRegex;
    }
}
