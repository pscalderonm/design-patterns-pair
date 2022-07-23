package oscarblancarte.ipd.decorator.impl.message;

public class CompanyMessage implements  IMessage{

    private String name;
    private String email;
    private String number;
    private String direction;

    public CompanyMessage(String name, String email, String number, String direction) {
        this.name = name;
        this.email = email;
        this.number = number;
        this.direction = direction;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "CompanyMessage{" +
                "name='" + name + '\'' +
                ", number='" + number + '\'' +
                ", direction='" + direction + '\'' +
                '}';
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
