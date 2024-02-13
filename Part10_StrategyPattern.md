## Strategy 패턴 <hr>
strategy 패턴이란 '전략'이라는 뜻입니다. 적을 해치우는 작전, 군대를 움직이는 방안, 
문제를 풀어나가는 방법등의 의미가 있습니다. 프로그래밍의 경우에는 '알고리즘'이라고
생각해도 좋습니다. 
모든 프로그램은 문제를 해결하고자 만들어지며, 문제를 풀기 위한 특정 알고리즘으로 구현됩니다.
Strategy패턴에서는 구현한 알고리즘을 모조리 교환할 수 있습니다. 스위치를 전환하듯
알고리즘을 바꿔서, 같은 문제를 다른 방법으로 해결하기 쉽게 만들어주는 패턴입니다. 

## 예제 프로그램 <hr>
가위바위보를 하는 프로그램을 만들어봅니다. 가위바위보는 '전략'으로 두 가지 방법이 있습니다.
하나는 이기면 다음에도 같은 손을 내는 다소 어리석은 방법(WinningStrategy)과 다른 하나는 직전 손에서
다음 손을 확률적으로 계산하는 방법(ProbStrategy)입니다.

```java
public interface Strategy {
    /**
     * nextHand는 '다음에 낼 손을 얻기 위한' 메소드입니다. 이 메소드를 호출하면, 인터페이스를 구현하는 클래스는
     * 다음에 낼 손을 결정합니다.
     *
     * study는 '직전에 낸 손으로 이겼는지 졌는지'를 학습하는 메소드입니다.
     * 직전 nextHand 메소드 호출에서 이긴 경우네느 study(true)로 호출하고, 진 경우에는 study(false)로 호출합니다.
     * */
    Hand nextHand();
    void study(boolean win);
}
```

```java
public class WinningStrategy implements Strategy{
    private Random random;
    private boolean won = false;
    private Hand prevHand;

    public WinningStrategy(int seed) {
        random = new Random(seed);
    }

    @Override
    public Hand nextHand() {
        if(!won){
            prevHand = Hand.getHand(random.nextInt(3));
        }
        return prevHand;
    }

    @Override
    public void study(boolean win) {
        won = win;
    }
}
```
```java
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
```
```java
public class Player {
    /**
     * Player 클래스는 가위바위보하는 사람을 표현한 클래스입니다.
     * Player 클래스는 주어진 '이름'과 '전략으로 인스턴스를 만듭니다.
     *
     * nextHand는 다음 손을 얻는 메소드인데, 실제로 다음 손을 결정하는 것은 자신의 '전략'입니다.
     * 전략의 nextHand 메소드의 반환값이 그대로 Player의 NextHand메소드의 반환값이 됩니다.
     * nextHand 메소드는 자신이 해야 할 처리를 Strategy에 맡기고 있습니다.
     *
     * 이기거나 지거나 비기거나 한 승부 결과를 다음 승부에 활용하고자 Player 클래스는
     * startegy 필드를 통해 study 메소드를 호출합니다. study 메소드로 전략의 내부 상태를
     * 변화시키는 것입니다. wincount, losecount, gamecount는 플레이어의 승수를 기록합니다.
     * */
    private String name;
    private Strategy strategy;
    private int wincount;
    private int losecount;
    private int gamecount;

    // 이름과 전략을 받아서 플레이어를 만든다.
    public Player(String name, Strategy strategy){
        this.name = name;
        this.strategy = strategy;
    }

    //전략에 따라 다음 손을 결정한다.
    public Hand nextHand() {
        return this.strategy.nextHand();
    }

    // 승리
    public void win() {
        strategy.study(true);
        wincount++;
        gamecount++;
    }

    // 패배
    public void lose() {
        strategy.study(false);
        losecount++;
        gamecount++;
    }

    // 무승부
    public void even() {
        gamecount++;
    }

    @Override
    public String toString() {
        return "[" + name + ':' +
                gamecount + " games " +
                wincount + " wins " +
                losecount + " lose " +
                ']';
    }
}
```

## Strategy 패턴의 구성 <hr>
### Strategy 
전략을 이용하기 위한 인터페이스를 결정합니다. 예제 프로그램에서는 Strategy 인터페이스가
이 역할을 맡았습니다. 
### ConcreteStrategy 
Strategy 역의 인터페이스를 실제로 구현합니다. 여기서 구체적인 전략을 실제로 프로그래밍합니다.
예쩨 프로그램에서는 WinningStrategy 클래스와 ProbStrategy 클래스가 이 역할을 맡았습니다.
### Context
Strategy역을 이용합니다. ConcreteStrategy의 인스턴스를 가지고 있다가 필요에 따라서 이용합니다. 
예쩨 프로그램에서는 Plyaer 클래스가 이 역할을 맡았습니다.

## 사고 넓히기 
### 일부러 Strategy 역할을 만들 필요가 있을까?
보통 프로그래밍을 하다보면 메소드 안에 녹아드는 형태로 알고리즘을 구현해 버리기 쉽지만,
Strategy 패턴에서는 알고리즘 부분을 다른 부분과 의도적으로 분리합니다. 알고리즘이 있는 
인터페이스 부분만 규정해 두고 프로그램에서 위임을 통해 알고리즘을 이용합니다. 

이런 방식이 프로그램을 복잡하게 만드는 것처럼 보이지만 그렇지 않습니다. 예를 들어, 알고리즘을
개량해서 더 빠르게 만들고 싶다고 가정합시다. Strategy 패턴을 사용하면 Strategy 역의 인터페이스를
변경하지 않도록 주의하고 ConcreteStrategy 역만 수정하면 됩니다. 위임이라는 약한 결합을
사용하므로 알고리즘을 용이하게 전환할 수 있습니다. 원래 알고리즘과 개량한 알고리즘의 속도를
비교하고 싶을 때도 간단히 전환해서 시험해 볼 수 있습니다. 

### 실행 중 교체도 가능
Strategy 패턴을 사용하면 프로그램 동작 중에 ConcreteStrategy 역을 전환할 수도 
있습니다. 예를 들어 메모리가 적은 환경에서는 SlowButLessMemoryStrategy를
사용하고, 메모리가 많은 환경에서는 FastButMoreMemoryStrategy를 사용하는 전략도
생각할 수 있습니다.

### 다양한 난수 생성기
이 책의 예제 프로그램은 Java에서 매우 일반적으로 사용되는 java.util.Random 클래스를
사용하여 난수를 생성합니다. 그러나 용도에 따라서는 다른 클래스를 사용해야 할 수도 있습니다.

## 관련패턴 <hr>
### Flywight패턴
ConcreteStrategy 역은 Flyweight 패턴을 사용해 여러 곳에서 공유할 수도 있습니다. 
### AbstractFacotry 패턴
Strategy패턴에서는 알고리즘을 완전히 전환할 수 있습니다. 
AbstractFactory 패턴에서는 구체적인 공장, 부품, 제품을 완전히 전환할 수 있습니다.
### State패턴
Strategy패턴이나 State패턴 모두 위임하는 곳을 전환하는 패턴이고 클래스 간의 관계도 매우
비슷하지만, 목적은 다릅니다. Strategy 패턴은 '알고리즘'을 표현하는 클래스를 만들어 
ConcreteStrategy 역으로 합니다. Strategy 패턴에서는 클래스를 전환할 수 있지만, 필요 없으면
바꾸지 않아도 됩니다. 반면에, State 패턴에서는 '상태'를 표현하는 클래스를 만들어
ConcreteState 역으로 합니다. State패턴에서는 상태가 변화할 때마다 위임하는 곳의 클래스가
반드시 전환됩니다. 
