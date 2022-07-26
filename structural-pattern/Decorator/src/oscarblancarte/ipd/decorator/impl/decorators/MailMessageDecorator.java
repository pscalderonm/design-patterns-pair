package oscarblancarte.ipd.decorator.impl.decorators;

import oscarblancarte.ipd.decorator.impl.message.IMessage;
import oscarblancarte.ipd.decorator.impl.message.Mail;

public abstract class MailMessageDecorator implements IMessage {

    protected IMessage bodyMessage;
    protected IMessage footerMessage;
    protected IMessage mailMessage;

    public MailMessageDecorator(IMessage bodyMessage, IMessage footerMessage) {
        this.bodyMessage = bodyMessage;
        this.footerMessage = footerMessage;
        this.mailMessage = new Mail();
    }

    @Override
    public String getContent() {
        return mailMessage.getContent();
    }

    @Override
    public void setContent(String content) {
        mailMessage.setContent(content);
    }
}
