package part10_strategy_pattern;


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
