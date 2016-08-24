public class ValidParan {
    public boolean isValid(String s) {
        if(s.length()%2 == 1) return false; // for string of odd length return false
        Deque<Character> stack = new ArrayDeque<Character>();
        char currentChar;
        for(int i=0; i<s.length(); i++){
            currentChar = s.charAt(i); 
            if(currentChar == '(' || currentChar == '{' || currentChar == '[') {
                stack.push(currentChar);
            }
            else if(currentChar == ')') {
                if(stack.isEmpty()) return false;
                if(stack.peek()!='(') return false;
                stack.pop(); // remove the '('
            }
            else if (currentChar == '}') {
                if(stack.isEmpty()) return false;
                if(stack.peek()!='{') return false;
                stack.pop();                
            }
            else if (currentChar == ']') {
                if(stack.isEmpty()) return false;
                if(stack.peek()!='[') return false;
                stack.pop();
            }
            else return false;
        }
        return stack.isEmpty();
    }
}

