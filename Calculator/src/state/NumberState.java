package state;

import token.NumberToken;

public class NumberState extends State {

    public NumberState(Tokenizer tokenizer) {
        super(tokenizer);
    }

    @Override
    public void nextState() {
        char ch = tokenizer.safeGetChar();
        if (Character.isDigit(ch)) {
            tokenizer.appendToAcc(ch);
            tokenizer.nextPos();
        } else {
            long number = Long.parseLong(tokenizer.pollAcc());
            tokenizer.addToken(new NumberToken(number));
            tokenizer.setState(new StartState(tokenizer));
        }
    }
}
