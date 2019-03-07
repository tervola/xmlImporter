package task.reader.core.dto;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by user on 3/3/2019.
 */
@XmlRootElement(name = "client")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class Client {
    private String firstName;
    private String lastName;
    private String middleName;
    private String inn;
}
