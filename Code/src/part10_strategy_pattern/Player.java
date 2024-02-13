package part10_strategy_pattern;

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
