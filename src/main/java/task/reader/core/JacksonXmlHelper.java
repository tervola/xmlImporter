package task.reader.core;

import task.reader.core.dto.Transaction;
import task.reader.core.dto.TransactionElement;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.*;
import java.util.List;

/**
 * Created by user on 3/3/2019.
 */
class JacksonXmlHelper {

    static List<TransactionElement> fromXml(final File file) {
        XMLStreamReader xsr  = null;
        FileReader fileReader = null;
        try {
            final XMLInputFactory xif = XMLInputFactory.newFactory();
            fileReader = new FileReader(file);
            xsr = xif.createXMLStreamReader(fileReader);

            xsr.nextTag(); // Advance to Envelope tag
            xsr.nextTag(); // Advance to Body tag
            xsr.nextTag(); // Advance to getTransactionResponse tag
            xsr.nextTag(); // Advance to transactions tag

            JAXBContext jc = JAXBContext.newInstance(Transaction.class);
            Unmarshaller unmarshaller = jc.createUnmarshaller();
            JAXBElement<Transaction> je = unmarshaller.unmarshal(xsr, Transaction.class);
            return je.getValue().getTransactionElement();
        } catch (XMLStreamException | JAXBException | FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                fileReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                xsr.close();
            } catch (XMLStreamException e) {
                e.printStackTrace();
            }
        }

        return null;
    }
}
