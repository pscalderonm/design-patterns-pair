package oscarblancarte.ipd.decorator.impl.message;

public class Mail implements  IMessage{

    @Override
    public String toString() {
        return "Mail{}";
    }

    @Override
    public IMessage processMessage() {
        return this;
    }

    @Override
    public String getContent() {
        return toString();
    }

    @Override
    public void setContent(String content) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
