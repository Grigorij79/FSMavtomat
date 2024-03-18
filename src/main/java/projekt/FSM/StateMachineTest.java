package projekt.FSM;

import java.util.Scanner;

public class StateMachineTest {

    public static void main(String[] args) {
        StateMachine fsm = new StateMachine();

        System.out.println("\"Helo\" = " + "Helo");
        Scanner scanner = new Scanner(System.in);
        while (true){
            String text = scanner.nextLine();
            fsm.handle(text);
        }

    }
}
