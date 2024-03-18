package projekt.FSM;

public interface StateMachineListerner {
    void onMessageAndTimeReceived(String massage, int time);
    public void onSwitchedMessage();


    public void onSwitchedTime();
}
