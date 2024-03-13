package projekt.FSM;

import java.util.Scanner;

public class StateMachineTest {

    public static void main(String[] args) {
        StateMachine fsm = new StateMachine();
        fsm.addListerner((massage, time) -> {
            System.out.println("Listener could");
            System.out.println("massage =" + massage);
            System.out.println("time =" + time);
        });
        System.out.println("\"Helo\" = " + "Helo");
        Scanner scanner = new Scanner(System.in);
        while (true){
            String text = scanner.nextLine();
            fsm.handle(text);
        }

    }
}
