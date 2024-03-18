package projekt.telegram;


import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import projekt.FSM.StateMachine;
import projekt.FSM.StateMachineListerner;
import projekt.telegram.command.StartCommand;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class BotConnection extends TelegramLongPollingCommandBot {
    private Map<String, StateMachine> stateMashins;
    public BotConnection(){
        register(new StartCommand());
        stateMashins = new ConcurrentHashMap<>();
    }

    @Override
    public void processNonCommandUpdate(Update update) {


        //   Exo=======================================
//            String message1 = update.getMessage().getText();
//            System.out.println("message1 = " + message1);
//            String responseText = new String(("Vi napisaly" + message1).getBytes(), StandardCharsets.UTF_8);
//            SendMessage sendMessage = new SendMessage();
//            sendMessage.setText(responseText);
//            sendMessage.setChatId(Long.toString(update.getMessage().getChatId()));
//            try {
//                execute(sendMessage);
//            } catch (TelegramApiException e) {
//                throw new RuntimeException(e);
//            }

        //================================================
        if (update.hasMessage()) {
            String chatId = Long.toString(update.getMessage().getChatId());
            System.out.println("chatId = " + chatId);
            if (!stateMashins.containsKey(chatId)) {

                StateMachine fsm = new StateMachine();

                fsm.addListerner(new MassageListener(chatId));
                stateMashins.put(chatId, fsm);

            }
            String message = update.getMessage().getText();
            stateMashins.get(chatId).handle(message);
        }
    }



    @Override
    public String getBotUsername() {
        return BotConstans.BOT_NAME;
    }

    @Override
    public String getBotToken() {
        return BotConstans.BOT_TOKEN;
    }

private class MassageListener implements StateMachineListerner{
    private String chatId;

    public MassageListener(String chatId) {
        this.chatId = chatId;
    }

    @Override
    public void onSwitchedMessage() {
    sendText("Napishite text zametki");
    }

    @Override
    public void onSwitchedTime() {
sendText("Koly napomnyt ?");
    }

    @Override
    public void onMessageAndTimeReceived(String massage, int time) {
        sendText("Zametka postavlena do srabatniy " + time);
    System.out.println("massage" + massage + "time :" + time + "chatId" + chatId);
    }
    private void sendText (String text){
            SendMessage sendMessage = new SendMessage();
            sendMessage.setText(text);

            sendMessage.setChatId(chatId);
            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
    }
  }

}
