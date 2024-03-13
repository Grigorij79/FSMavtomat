package projekt.FSM;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class StateMachine {
    private State state = State.idl;
    private String massage;
    private int time;
    private List<StateMachineListerner> listeners = new ArrayList<>();
    public void addListerner(StateMachineListerner listerner){
        listeners.add(listerner);
    }
    public void handle (String text){
         if (text.equals("фіва")){
    onCreateNotificationPressed();
    return;
    }
    onTextReceived(text);
    try {
        int number = Integer.parseInt(text);
        onNumberReceived(number);
    }catch (Exception ex){
        ex.printStackTrace();
    }


    }
    private void onCreateNotificationPressed(){
        System.out.println("onCreateNotificationPressed");
        if (state == State.idl){
            switchState(State.waitFoMassadge);

        }
    }
    private  void onTextReceived (String text){
        System.out.println( "onTextReceived" + text);
        if (state == State.waitFoMassadge){
            massage = text;
            switchState(State.waitFoTime);
        }
      }
    private  void  onNumberReceived (int number){
        System.out.println( "onNumberReceived" + number);
        if (state == State.waitFoTime){
            this.time = number;
            switchState(State.idl);
            for (StateMachineListerner listerner:listeners){
        listerner.onMessageAndTimeReceived(massage,time);
            }

            System.out.println("Time to print notificftion");
        }

    }
    private void switchState (State newState){
        System.out.println("Swich state" + state + "=>" + newState);
        this.state = newState;

    }
}
