public class ValidParan1 {
    public boolean isValid(String s) {
        if(s.length()==0) return true;
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
                while(!stack.isEmpty() && stack.peek()!='('){
                    stack.pop();
                }
                if(stack.isEmpty()) return false; // failed to find the '('
                else stack.pop(); // remove the '('
            }
            else if (currentChar == '}') {
                if(stack.isEmpty()) return false;
                while(!stack.isEmpty() && stack.peek()!='{'){
                    stack.pop();
                }
                if(stack.isEmpty()) return false;
                else stack.pop();
            }
            else if (currentChar == ']') {
                if(stack.isEmpty()) return false;
                while(!stack.isEmpty() && stack.peek()!='['){
                    stack.pop();
                }
                if(stack.isEmpty()) return false;
                else stack.pop();
                
            }
            else return false;
        }
        
        return stack.isEmpty();
        
    }
}
