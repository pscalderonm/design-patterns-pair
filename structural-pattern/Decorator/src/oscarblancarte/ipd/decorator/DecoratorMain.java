package oscarblancarte.ipd.decorator;

import oscarblancarte.ipd.decorator.impl.decorators.BuildMailMessage;
import oscarblancarte.ipd.decorator.impl.decorators.SOAPEnvelopMessage;
import oscarblancarte.ipd.decorator.impl.message.CompanyMessage;
import oscarblancarte.ipd.decorator.impl.message.IMessage;
import oscarblancarte.ipd.decorator.impl.decorators.EncryptMessage;
import oscarblancarte.ipd.decorator.impl.message.CustomerMessage;
import oscarblancarte.ipd.decorator.impl.decorators.XMLFormatterDecorate;

/**
 *
 * @author Oscar Javier Blancarte Iturralde
 * @see <a href="http://www.oscarblancarteblog.com">...</a>
 */
public class DecoratorMain {

    public static void main(String[] args) {
        
        CustomerMessage customerMessage = new CustomerMessage(
                "Oscar Blancarte", "oscarblancarte3@gmail.com", "554433445566");
        System.out.println("Original Message ==> " + customerMessage);
        CompanyMessage companyMessage = new CompanyMessage(
                "Kruger corp","kruger@kruger.com.ec","0985164142","Quito");
        System.out.println("Company Info ==> " + companyMessage);

        IMessage message1 = new EncryptMessage("user", "HG58YZ3CR9123456",
                new SOAPEnvelopMessage(new BuildMailMessage(
                        new XMLFormatterDecorate(customerMessage),
                        new XMLFormatterDecorate(companyMessage)))).processMessage();
        System.out.println("message1 =====================================>\n" 
                + message1.getContent() + "\n");

        IMessage message2 =  new SOAPEnvelopMessage(new BuildMailMessage(
                        new XMLFormatterDecorate(customerMessage),
                        new XMLFormatterDecorate(companyMessage))).processMessage();
        System.out.println("message2 =====================================>\n"
                + message2.getContent());

        IMessage message3 =  new SOAPEnvelopMessage(new EncryptMessage("user", "HG58YZ3CR9123456",new BuildMailMessage(
                new XMLFormatterDecorate(customerMessage),
                new XMLFormatterDecorate(companyMessage)))).processMessage();
        System.out.println("message3 =====================================>\n"
                + message3.getContent());
    }
    
}