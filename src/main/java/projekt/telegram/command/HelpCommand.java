package projekt.telegram.command;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.nio.charset.StandardCharsets;


public class HelpCommand extends BotCommand {
    public HelpCommand() {
        super("help", "Help command");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {

        SendMessage helpText = new SendMessage();

        helpText.setText(new String("Бот помічник".getBytes(), StandardCharsets.UTF_8));
        helpText.setChatId(chat.getId().toString());
        try {
            absSender.execute(helpText);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }

    }

}
