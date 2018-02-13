import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Calculator {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        String changedInput = replaceToDots(input);
        if (isNotValidString(changedInput)) {
            System.out.println("Невалидные данные!");
            System.exit(1);
        }
        String[] stringWithoutSpaces = changedInput.split(" ");
        String strWithoutSpaces = "";
        for (int i = 0; i < stringWithoutSpaces.length; i++) {
            strWithoutSpaces += stringWithoutSpaces[i];
        }
        String stringWithoutSignsRepeats = removeSignsRepeats(strWithoutSpaces);
        String stringWithChangedUnarMinus = changeUnarMinus(stringWithoutSignsRepeats);
        List<String> list = compile(stringWithChangedUnarMinus);
//        for (int i = 0; i < list.size(); i++) {
//            System.out.print(list.get(i));
//        }
//        System.out.println();
        try {
            System.out.println(calculate(list));
        } catch (Exception e) {
            System.out.println("Невалидные данные!");
        }
    }

    public static List<String> compile(String input){

        List<String> list = new ArrayList<>();
        Deque<String> stack = new ArrayDeque<>();
        for (int i = 0; i < input.length(); i++) {
            String currSymbol = input.charAt(i)+"";
            String nextSymbol = i != input.length()-1 ? input.charAt(i+1)+"" : "";
            if (currSymbol.equals(" ")) continue;
            if (isNumber(currSymbol) || (currSymbol.equals("-") && !nextSymbol.equals("("))) {
                do {
                    i++;
                    if (i == input.length()) break;
                    String actualSymbol = input.charAt(i)+"";
                    if (isNumber(actualSymbol) || actualSymbol.equals(".")) {
                        currSymbol += actualSymbol;
                    } else {
                        i--;
                        break;
                    }
                } while (true);
                list.add(currSymbol);
            } else {
                String lastElInStack = stack.peek();
                if (lastElInStack == null) stack.push(currSymbol);
                else {
                    if (currSymbol.equals(")")) {
                        while (!stack.peek().equals("(")) {
                            list.add(stack.pop());
                        }
                        stack.pop();
                    } else if (getPriority(lastElInStack) == 0 || getPriority(currSymbol) == 0) {
                        stack.push(currSymbol);
                    } else if (getPriority(currSymbol) == 3) {
                        stack.push(currSymbol);
                        currSymbol = "";
                        do {
                            i++;
                            if (i == input.length()) break;
                            String actualSymbol = input.charAt(i)+"";
                            if (actualSymbol.equals(" ")) continue;
                            if (isNumber(actualSymbol) || actualSymbol.equals(".") || (actualSymbol.equals("-") && !isNumber(input.charAt(i-1)+""))) {
                                currSymbol += actualSymbol;
                            } else {
                                i--;
                                break;
                            }
                        } while (true);
                        list.add(currSymbol);
                    } else if (getPriority(lastElInStack) == getPriority(currSymbol)) {
                        list.add(lastElInStack);
                        stack.pop();
                        stack.push(currSymbol);
                    } else if (getPriority(lastElInStack) > getPriority(currSymbol)) {
                        list.add(stack.pop());
                        stack.push(currSymbol);
                    }
                    else stack.push(currSymbol);
                }
            }
        }
        while (stack.peek()!= null) {
            list.add(stack.pop());
        }

        return list;
    }

    public static boolean isNumber(String symbol){
        Pattern p = Pattern.compile("^-?\\d*\\.?\\d+$");
        Matcher m = p.matcher(symbol);
        return m.matches();
    }

    public static int getPriority(String symbol){

        if (symbol.equals("+") || symbol.equals("-")) return 1;
        else if (symbol.equals("*") || symbol.equals("/")) return 2;
        else if (symbol.equals("^")) return 3;
        else return 0;
    }

    public static BigDecimal calculate(List<String> list){

        Deque<BigDecimal> stack = new ArrayDeque<>();
        for (int i = 0; i < list.size(); i++) {
            String currSymbol = list.get(i);
            if (isNumber(currSymbol)) {
                stack.offer(new BigDecimal(currSymbol));
            } else {
                BigDecimal second = stack.pollLast();
                BigDecimal first = stack.pollLast();
                switch (currSymbol){

                    case "+": stack.offer(first.add(second)); break;
                    case "-": stack.offer(first.subtract(second)); break;
                    case "*": stack.offer(first.multiply(second)); break;
                    case "/": {
                        try {
                            stack.offer(first.divide(second));
                        } catch (ArithmeticException e) {
                            System.out.println("Деление на 0");
                            System.exit(1);
                        }
                        break;
                    }
                    case "^": stack.offer(new BigDecimal(Math.pow(first.doubleValue(), second.doubleValue())));
                }
            }
        }
        return stack.pop();
    }

    public static boolean isNotValidString(String input){
        Pattern p1 = Pattern.compile("-?\\d*\\.?\\d+[ ]-?\\d*\\.?\\d+?");
        Pattern p2 = Pattern.compile("[^\\d\\+\\-\\*\\/\\^\\.() ]");
        Pattern p3 = Pattern.compile("[\\*\\/\\^\\.][\\*\\/\\^\\.]");
        Matcher m1 = p1.matcher(input);
        Matcher m2 = p2.matcher(input);
        Matcher m3 = p3.matcher(input);
        return (m1.find() || m2.find()|| m3.find()) == true ? true : false;

    }

    public static String replaceToDots(String input){
        Pattern p = Pattern.compile(",");
        Matcher m = p.matcher(input);
        String replace = "";
        if (m.find()){
            replace = m.replaceAll(".");
            return replace;
        }
        return input;
    }

    public static String removeSignsRepeats(String input) {
        String returnInput = input;
        for (int i = 0; i < returnInput.length(); i++) {
            String currSymbol = returnInput.charAt(i)+"";
            if (getPriority(currSymbol) == 1) {
                do {
                    i++;
                    if (i == returnInput.length()) break;
                    String actualSymbol = returnInput.charAt(i)+"";
                    if (actualSymbol.equals(" ")) continue;
                    if (getPriority(actualSymbol) == 1 && !isNumber(returnInput.charAt(i-1)+"")) {
                        if ((currSymbol.equals("+") && actualSymbol.equals("-")) || (currSymbol.equals("-") && actualSymbol.equals("+"))) {
                            currSymbol = "-";
                            String newString = "";
                            newString = returnInput.substring(0, i - 1);
                            newString += currSymbol.charAt(0);
                            newString += returnInput.substring(i + 1);
                            returnInput = newString;
                            i--;
                        } else {
                            currSymbol = "+";
                            String newString = "";
                            newString = returnInput.substring(0, i - 1);
                            newString += currSymbol.charAt(0);
                            newString += returnInput.substring(i + 1);
                            returnInput = newString;
                            i--;
                        }
                    } else {
                        break;
                    }
                } while (true);
            }
        }
        return returnInput;
    }

    public static String changeUnarMinus(String input) {
        String returnInput = "";
        for (int i = 0; i < input.length(); i++) {
            String currSymbol = input.charAt(i)+"";
            String prevSymbol = i != 0 ? input.charAt(i-1)+"" : "";
            String nextSymbol = i != input.length()-1 ? input.charAt(i+1)+"" : "";
            if (currSymbol.equals("-") && i != 0 && !prevSymbol.equals("(") && !prevSymbol.equals("^") && !prevSymbol.equals(")") && !nextSymbol.equals(")") && !nextSymbol.equals("(")){
                returnInput += "+-";
            } else {
                returnInput += currSymbol;
            }
        }
        String firstEl = returnInput.charAt(0)+"";
        returnInput = firstEl.equals("+") ? returnInput.substring(1) : returnInput;
        return returnInput;
    }
}
