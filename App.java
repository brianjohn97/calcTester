import java.util.LinkedList;
import java.util.Scanner;
import java.util.Stack;
import java.text.DecimalFormat;
import java.lang.Math;
import com.fathzer.soft.javaluator.DoubleEvaluator;
import java.util.Scanner;
import java.util.Random;

public class App 
{
    public static void main( String[] args ){
        //getting scanner for number of expressins to be tested and resuts varible
        Scanner sc = new Scanner(System.in);
        
        
        while(true){
            int[] results = {0, 0};
            String numExp = "";
            double num = 0;

            //get the number of expression the user wants to test and save it 
            System.out.print("How many expression would you like to test ");
            System.out.println("or enter stop");
            numExp = sc.nextLine();
            if(numExp.equals("stop")){break;}
            try{
                num = Double.parseDouble(numExp);
            } catch(NumberFormatException e){
                System.out.println("Not a number or stop; please try again");
                System.out.println("");
                System.out.println("");
                continue;
            }
            


             //test the that amount of expressions
             results = generateTestCases(num, results);
		 DecimalFormat formatter = new DecimalFormat("#,###");

            //print out the results of the expressions
             System.out.print("Out of "+formatter.format(num)+" expressions my eval was correct "+formatter.format(results[0])+" times");
            System.out.println(" and was incorrect "+formatter.format(results[1])+" times!");

        }
        
    }

    static int[] generateTestCases(Double numExp, int[] results) {
        //creating objects to be used for testing
        Random rand = new Random();
        calculator calc = new calculator();
        DoubleEvaluator eval = new DoubleEvaluator();

        //loops the amount of times the user entered
        for(int i=0; i < numExp; i++ ){

            //get the length of the expresion and pass it to the testing function
            int exprLen = rand.nextInt(40);
            String exp = testCases(exprLen);

            //print the the test expression to user for transparancy
            System.out.println("");
            System.out.println("Test Expression: "+exp);
            System.out.println("");

            //get the results of the expression and pass it to each calculator
            //and store the results in a string
            String result1 = calc.evalRPN(exp);
            double doubleResult = eval.evaluate(exp);
            String result2 = String.valueOf(doubleResult);

            //print out the different results of each calculator
            System.out.println("Oracle result: "+result2);
            System.out.println("My result: "+result1);
            System.out.println("");

            //store whether my calculator was correct or not accordingly 
            //in the results array
            if(!result1.equals(result2)){results[1]++;}else{results[0]++;} 
        }
        return results;
    }

    static String testCases(int numExp){
        //arrays to store the different operators and functions
        String[] operators = {" + ", " - ", " * ", " / ", "^"};
        String[] functions = {"sin(", "cos(", "tan(", "ln(", "log("};

        //StringBuilder variable to create the final math expression
        StringBuilder str = new StringBuilder();

        //flag to keep track of the decimal point
        int flag = 0;

        //isExp to keep track of if were dealing with an exponoent
        int isExp = 0;

        //parser to keep track of how many character in the math expression were at
        int curNumExp = 0;

        //start function to start creating the expression
        return start(operators, functions, str, flag, numExp, curNumExp, isExp);    
    }

    static String start(String[] operators, String[] functions, StringBuilder str, int flag, int numExp, int curNumExp, int isExp){
        //get random number
        Random rand = new Random();
        int test_int = rand.nextInt(2);

        switch (test_int) {
            case 0:
                //if its a zero it puts a number onto the expresion, increments current char of the expression and calls the digit function
                //since we just placed a digit
                str.append((rand.nextInt(10)));
                curNumExp++;
                digit(operators, functions, str, flag, numExp, curNumExp, isExp);
                return str.toString();
            case 1:
            //if it is one then we pick a function randomly, add it to the expresion and call the function method since we placed a function
                int test = rand.nextInt(5);
                str.append(functions[test]);
                function(operators, functions, str, flag, numExp, curNumExp, isExp);
                return str.toString();
        }
        return str.toString();
    }
    static String digit(String[] operators, String[] functions, StringBuilder str, int flag, int numExp, int curNumExp, int isExp){
        //get random number
        Random rand = new Random();
        int x = rand.nextInt(4);

        switch (x) {
            case 0:
                //if its a zero place a random number, increment current num of expression and call digit again
                str.append((rand.nextInt(10)));
                curNumExp++;
                digit(operators, functions, str, flag, numExp, curNumExp, isExp);
                return str.toString();
            case 1:
                //if its a 1 check if there is already a digit there then either call digit again or placed a decimal point with another number
                //raise the decimal point flag, increment current num of expression and call digit again
                if(flag == 1){digit(operators, functions, str, flag, numExp, curNumExp, isExp);return str.toString();}
                str.append(".");
                str.append(rand.nextInt(10));
                flag = 1;
                curNumExp++;
                digit(operators, functions, str, flag, numExp, curNumExp, isExp);
                return str.toString();
            case 2:
                //if case 2 check decimal point flag and set it back to zero, get a random operatr and place it in the expression
                //if it is a exponent set the exponent flag to 1 and call operator function
                if(flag==1){flag = 0;}
                int y = rand.nextInt(5);
                str.append(operators[y]);

                curNumExp++;
                if(y == 4){isExp = 1;}

                operator(operators, functions, str, flag, numExp, curNumExp, isExp);
                return str.toString();
            case 3:
                //if its a 3 then check if the current number of characters in the expression is greater or equal ot the specified variable
                //if its greater the math expression is done here and sent back to be tested or it calls digit to keep adding
                if(curNumExp >= numExp) {
                    return str.toString();
                }
                digit(operators, functions, str, flag, numExp, curNumExp, isExp);
                return str.toString();
        }
        return str.toString();
    }
    static String function(String[] operators, String[] functions, StringBuilder str, int flag, int numExp, int curNumExp, int isExp){
        //get random number and add it to the expressoin and increase the num of characters in the expression
        Random rand = new Random();
        int x = rand.nextInt(100);
        str.append(x+")");
        curNumExp++;

        //get random operator and increase currnumexp
        int y = rand.nextInt(5);
        str.append(operators[y]);
        curNumExp++;
        
        //add random number to expresion, increase currnumexp, then call digit
        str. append(rand.nextInt(10));
        curNumExp++;

        digit(operators, functions, str, flag, numExp, curNumExp, isExp);
        return str.toString();
    }
    static String operator(String[] operators, String[] functions, StringBuilder str, int flag, int numExp, int curNumExp, int isExp){
        //get random num
        Random rand = new Random();
        int x = rand.nextInt(4);

        switch (x) {
            case 0:
                //if its a zero add random num to expresion, increase currnumexp, set isExp back to 0 and call digit
                str.append((rand.nextInt(10)));
                curNumExp++;
                isExp = 0;
                digit(operators, functions, str, flag, numExp, curNumExp, isExp);
                return str.toString();
            case 1:
                //if its a 1 add a random function, set isExp back to 0 and call funciton method
                int test = rand.nextInt(5);
                str.append(functions[test]);
                isExp = 0;
                function(operators, functions, str, flag, numExp, curNumExp, isExp);
                return str.toString();
            case 2:
                //if case 2 check if there is a decimal point already there and if there is call the operator function again
                //if not add a decimal point and random number, increse currnumexp, set decimal flag to 1, set isExp back to 0
                //call digit
                if(flag == 1){operator(operators, functions, str, flag, numExp, curNumExp, isExp);break;}
                str.append(".");
                str.append(rand.nextInt(10));
                curNumExp++;
                flag = 1;
                isExp = 0;
                digit(operators, functions, str, flag, numExp, curNumExp, isExp);
                return str.toString();
            case 3:
                //for case 3 check if there is an exponent and call operators again if there is
                //if not add a negative symbol with a random number, increment currnumexp and call digit again
                if(isExp == 1){operator(operators, functions, str, flag, numExp, curNumExp, isExp);break;}
                str.append(" -");
                str.append(rand.nextInt(10));
                curNumExp++;
                digit(operators, functions, str, flag, numExp, curNumExp, isExp);
                return str.toString();
        }
        return str.toString();
    }
}

class calculator {
    public static void main(String[] args) {
        
        //loop to continually ask the user for an expression then check if they want to stop and evaluate that expresions
        //and return the final answer
        while (true) {
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter the Mathemcatical expression here(stop to quit): ");
            String num = sc.nextLine();

            if(num.equals("stop")){
                System.exit(0);
            }

            //evalRPN(num);
            System.out.println(" ");
        }               
    }

    //got infixToPostFix from rosettacode.org website
    //https://rosettacode.org/wiki/Parsing/Shunting-yard_algorithm#Java
    static String infixToPostfix(String infix) {
        /* To find out the precedence, we take the index of the
           token in the ops string and divide by 2 (rounding down). 
           This will give us: 0, 0, 1, 1, 2 */
        final String ops = "-+/*^ctsmoln";

        StringBuilder sb = new StringBuilder();
        Stack<Integer> s = new Stack<>();

        for (String token : infix.split("\\s")) {
            if (token.isEmpty()){
                continue;
            }
            char c = token.charAt(0);
            int idx = ops.indexOf(c);

            // check for operator
            if (idx != -1) {
                if (s.isEmpty())
                    s.push(idx);
          
                else {
                    while (!s.isEmpty()) {
                        int prec2 = s.peek() / 2;
                        int prec1 = idx / 2;
                        if (prec2 > prec1 || (prec2 == prec1 && c != '^'))
                            sb.append(ops.charAt(s.pop())).append(' ');
                        else break;
                    }
                    s.push(idx);
                }
            } 
            else if (c == '(' || c == '{' || c == '[') {
                s.push(-2); // -2 stands for '('
            } 
            else if (c == ')' || c == '}' || c == ']') {
                // until '(' on stack, pop operators.
                while (s.peek() != -2)
                    sb.append(ops.charAt(s.pop())).append(' ');
                s.pop();
            }
            else {
                sb.append(token).append(' ');
            }
        }
        //pushes the rest of the operators onto the stack
        //check is for the same amount of parenthesis
        while (!s.isEmpty()){
            if (s.peek() != -2){
                sb.append(ops.charAt(s.pop())).append(' ');
                continue;
            }
            return "";
        }
        return sb.toString();
    }

    //got evalRPN from Rosetta Code website
    //https://rosettacode.org/wiki/Parsing/RPN_calculator_algorithm#Java_2
    public String evalRPN(String expr){

        //get the expression from the user converted to post fix notation
        expr = infixToPostfix(expr);
        LinkedList<Double> stack = new LinkedList<Double>();

        //parses through the expression looking for the operators
        //then performing the corresponding action with
        for (String token : expr.split("\\s")){
            if (token.equals("*")) {
                if(stack.size() < 2){return "Error";}
                double secondOperand = stack.pop();
                double firstOperand = stack.pop();
                stack.push(firstOperand * secondOperand);
            } else if (token.equals("/")) {
                if(stack.size() < 2){return "Error";}
                double secondOperand = stack.pop();
                double firstOperand = stack.pop();
                if (secondOperand == 0){
                    return "e";
                }
                stack.push(firstOperand / secondOperand);
            } else if (token.equals("-")) {
                if(stack.size() < 2){return "Error";}
                double secondOperand = stack.pop();
                double firstOperand = stack.pop();
                stack.push(firstOperand - secondOperand);
            } else if (token.equals("+")) {
                if(stack.size() < 2){return "Error";}
                double secondOperand = stack.pop();
                double firstOperand = stack.pop();
                stack.push(firstOperand + secondOperand);
            } else if (token.equals("^")) {
                if(stack.size() < 2){return "Error";}
                double secondOperand = stack.pop();
                double firstOperand = stack.pop();
                long ans = (long) Math.pow(secondOperand, firstOperand);
                double as = (double) ans;
                stack.push(as);
            }else if (token.equals("c")){
                if(stack.size() < 2){return "Error";}
                double operand = stack.pop();
                operand = Math.toRadians(operand);
                stack.push(Math.cos(operand));
            }else if (token.equals("s")){
                if(stack.size() < 2){return "Error";}
                double operand = stack.pop();
                operand = Math.toRadians(operand);
                stack.push(Math.sin(operand));
            }else if (token.equals("t")){
                if(stack.size() < 2){return "Error";}
                double operand = stack.pop();
                operand = Math.toRadians(operand);
                stack.push(Math.tan(operand));
            }else if (token.equals("m")){
                double operand = stack.pop();
                operand = operand * -1;
                stack.push(operand);
            } else if (token.equals("o")){
                double operand = stack.pop();
                operand = Math.toRadians(operand);
                stack.push(1 / Math.tan(operand));
            }else if (token.equals("n")){
                if (stack.size() == 0){return "e";}
                double operand = stack.pop();
                stack.push(Math.log(operand));
            }else if (token.equals("l")){
                if (stack.size() == 0){return "e"; }
                double operand = stack.pop();
                stack.push(Math.log10(operand));
            } else {
                try {
                    stack.push(Double.parseDouble(token+""));
                } catch (NumberFormatException e) {
                        return "error";
                }
            }
        }
        if (stack.size() > 1) {
            return "error";
        }
        double result = stack.pop();
        String s = String.valueOf(result);
        return s;
    }
}
