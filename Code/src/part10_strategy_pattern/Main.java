package part10_strategy_pattern;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        int seed1 = Integer.parseInt(bufferedReader.readLine());
        int seed2 = Integer.parseInt(bufferedReader.readLine());

        Player player1 = new Player("KIM", new WinningStrategy(seed1));
        Player player2 = new Player("LEE", new ProbStrategy(seed2));

        for (int i = 0; i < 10000; i++) {
            Hand nextHnad1 = player1.nextHand();
            Hand nextHnad2 = player2.nextHand();

            if( nextHnad1.isStrongerThan(nextHnad2)){
                System.out.println("Winner:" + player1);
                player1.win();
                player2.lose();
            } else if (nextHnad2.isStrongerThan(nextHnad1)) {
                System.out.println("Winner:" + player2);
                player2.win();
                player1.lose();
            } else {
                System.out.println("Even...");
                player1.even();
                player2.even();
            }
        }

        System.out.println("Total result : ");
        System.out.println(player1);
        System.out.println(player2);
    }
}
