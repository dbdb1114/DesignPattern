package part10_strategy_pattern.ex;

import java.util.Random;
import part10_strategy_pattern.Hand;
import part10_strategy_pattern.Strategy;

public class RandomStrategy implements Strategy {
    Random random;
    @Override
    public Hand nextHand() {
        return Hand.getHand(random.nextInt()%3);
    }

    @Override
    public void study(boolean win) {

    }
}
