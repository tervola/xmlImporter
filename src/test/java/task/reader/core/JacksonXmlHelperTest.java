package task.reader.core;

import org.apache.commons.lang.SystemUtils;
import org.hamcrest.MatcherAssert;
import org.junit.Assert;
import org.junit.Test;
import task.reader.core.dto.Client;
import task.reader.core.dto.TransactionElement;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;

/**
 * Created by user on 3/4/2019.
 */
public class JacksonXmlHelperTest {

    @Test
    public void fromXmlTest() throws IOException {
        File tmp = Paths.get(SystemUtils.getJavaIoTmpDir().getAbsolutePath()).resolve("tmp.xml").toFile();

        String content = "<?xml version='1.0' encoding='UTF-8'?>\n" +
                "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                "\t<soap:Body>\n" +
                "\t\t<ns2:GetTransactionsResponse xmlns:ns2=\"http://dbo.qulix.com/ukrsibdbo\">\n" +
                "\t\t  <transactions>\n" +
                "\t\t    <transaction>\n" +
                "\t\t      <place>A PLACE 1</place>\n" +
                "\t\t      <amount>10.01</amount>\n" +
                "\t\t      <currency>UAH</currency>\n" +
                "\t\t      <card>123456****1234</card>\n" +
                "\t\t      <client>\n" +
                "\t\t\t<firstName>Ivan</firstName>\n" +
                "\t\t\t<lastName>Ivanoff</lastName>\n" +
                "\t\t\t<middleName>Ivanoff</middleName>\n" +
                "\t\t\t<inn>1234567890</inn>\n" +
                "\t\t      </client>\n" +
                "\t\t    </transaction>\n" +
                "\t\t  </transactions>\n" +
                "\t\t</ns2:GetTransactionsResponse>\n" +
                "\t</soap:Body>\n" +
                "</soap:Envelope>";

        try(FileWriter fileWriter = new FileWriter(tmp)){
            fileWriter.write(content);
        }

        List<TransactionElement> transactionElements = JacksonXmlHelper.fromXml(tmp);
        Assert.assertEquals(1,transactionElements.size());

        TransactionElement transactionElement = transactionElements.get(0);

        MatcherAssert.assertThat("A PLACE 1", is(transactionElement.getPlace()));
        MatcherAssert.assertThat(10.01, is(transactionElement.getAmount()));
        MatcherAssert.assertThat("UAH", is(transactionElement.getCurrency()));
        MatcherAssert.assertThat("123456****1234", is(transactionElement.getCard()));

        Client client = transactionElement.getClient();
        MatcherAssert.assertThat("Ivan", is(client.getFirstName()));
        MatcherAssert.assertThat("Ivanoff", is(client.getLastName()));
        MatcherAssert.assertThat("Ivanoff", is(client.getMiddleName()));
        MatcherAssert.assertThat("1234567890", is(client.getInn()));

        Files.deleteIfExists(tmp.toPath());
    }
}
