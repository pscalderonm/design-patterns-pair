package oscarblancarte.ipd.decorator.impl.decorators;

import oscarblancarte.ipd.decorator.impl.message.IMessage;
import oscarblancarte.ipd.decorator.impl.message.TextMessage;

public class BuildMailMessage extends MailMessageDecorator{

    public BuildMailMessage(IMessage messageBody, IMessage messageFooter) {
        super(messageBody,messageFooter);
    }

    @Override
    public IMessage processMessage() {
        bodyMessage.processMessage();
        footerMessage.processMessage();
        mailMessage.processMessage();
        mailMessage = concatMessage();
        return mailMessage;
    }

    public IMessage concatMessage(){
        return new TextMessage(bodyMessage.getContent()+"\n"+footerMessage.getContent());
    }
}
