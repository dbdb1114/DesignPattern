package part5_singleton_pattern.ex;

public class TicketMaker {
    private static int ticket = 1000;
    private static TicketMaker singleton = new TicketMaker();
    private TicketMaker(){}

    public static TicketMaker getInstance(){
        return singleton;
    }
    public synchronized int getNextTicketNumber() {
        return ticket++;
    }
}
