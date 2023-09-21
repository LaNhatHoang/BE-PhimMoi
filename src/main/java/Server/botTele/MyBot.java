package Server.botTele;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Component
public class MyBot extends TelegramLongPollingBot{
    public MyBot(){
        super("6608661289:AAEf_lnoCFmC135Rtl7tBsJKjonQx9J7kQg");
    }
    @PostConstruct
    public void registerBot() throws TelegramApiException {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        try {
            telegramBotsApi.registerBot(this);
        } catch (TelegramApiException e) {
            System.out.println(e.getMessage());
        }
    }
    @Override
    public void onUpdateReceived(Update update) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(update.getMessage().getChatId());
        sendMessage.setText(update.getMessage().getText());
        try{
            execute(sendMessage);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    @Override
    public String getBotUsername() {
        return "Bot";
    }
    public void sendText(String text){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(5372149700L);
        sendMessage.setText(text);
        try{
            execute(sendMessage);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
