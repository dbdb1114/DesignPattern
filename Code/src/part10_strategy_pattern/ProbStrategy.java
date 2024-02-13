package part10_strategy_pattern;

import java.util.Random;

/**
 * ProbStrategy 클래스는 또 하나의 구체적인 '전략'입니다. 이번 전략은 조금 더 머리를 썼습니다.
 * 다음 손을 항상 난수로 결정하는데, 과거의 이기고 진 이력을 활용해서 각각의 손을 낼 확률을 바꾸는 것입니다.
 *
 * history 필드는 과거의 승패를 반영한 확률 계산을 위한 표로 되어 있습니다.
 * history는 2차원 int 배열로, 각 차원의 첨자에는 다음과 같은 뜻이 있습니다.
 *
 * history[직전에 낸 손][이번에 낼 손]
 *
 * 직전에 내가 바위를 냈다고 했을 때, 다음에 내가 무엇을 낼지를 위의 history[0][0], history[0][1],
 * history[0][2] 값을 이용해 확률로 계산하자는 것입니다. 요컨대 이 세 가지 식의 값을 더하고 ( getSum 메소드),
 * 0부터 그 수까지의 난수를 계산한 후 그 결과를 바탕으로 다음 수를 결정합니다. ( nextHand 메소드 ).
 * */

public class ProbStrategy implements Strategy{


    private Random random;
    private int prevHandValue = 0;
    private int currentHandValue = 0;
    private int[][] history = {
            {1,1,1},
            {1,1,1},
            {1,1,1}
    };

    public ProbStrategy(int seed){
        random = new Random(seed);
    }

    @Override
    public Hand nextHand() {
        int bet = random.nextInt(getSum(currentHandValue));
        int handvalue;

        if(bet < history[currentHandValue][0]){
            handvalue = 0;
        } else if ( bet < history[currentHandValue][0] + history[currentHandValue][1]) {
            handvalue = 1;
        } else {
            handvalue = 2;
        }

        prevHandValue = currentHandValue;
        currentHandValue = handvalue;
        return Hand.getHand(handvalue);
    }

    private int getSum(int handvalue){
        int sum = 0;
        for (int i = 0; i < 3; i++) {
            sum += history[handvalue][i];
        }
        return sum;
    }

    @Override
    public void study(boolean win) {
        if(win){
            history[prevHandValue][currentHandValue]++;
        } else {
            history[prevHandValue][(currentHandValue + 1) % 3]++;
            history[prevHandValue][(currentHandValue + 2) % 3]++;
        }
    }
}
