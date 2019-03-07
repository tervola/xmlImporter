package task.reader.core.dto;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by user on 3/3/2019.
 */

@Data
@XmlRootElement(name = "transaction")
@XmlAccessorType(XmlAccessType.FIELD)
public class TransactionElement {
    String place;
    private double amount;
    private String currency;
    private String card;
    @XmlElement(name = "client")
    private Client Client;
}
