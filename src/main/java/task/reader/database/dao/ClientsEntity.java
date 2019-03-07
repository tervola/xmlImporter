package task.reader.database.dao;

import javax.persistence.*;

/**
 * Created by user on 3/7/2019.
 */
@Entity
@javax.persistence.Table(name = "clients", schema = "public", catalog = "main_store")
public class ClientsEntity {
    private String firstName;
    private String secondName;
    private String middleName;
    private String inn;

    @Basic
    @Column(name = "first_name")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    @Basic
    @Column(name = "second_name")
    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(final String secondName) {
        this.secondName = secondName;
    }

    @Basic
    @Column(name = "middle_name")
    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(final String middleName) {
        this.middleName = middleName;
    }

    @Id
    @Column(name = "inn")
    public String getInn() {
        return inn;
    }

    public void setInn(final String inn) {
        this.inn = inn;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final ClientsEntity that = (ClientsEntity) o;

        if (firstName != null ? !firstName.equals(that.firstName) : that.firstName != null) return false;
        if (secondName != null ? !secondName.equals(that.secondName) : that.secondName != null) return false;
        if (middleName != null ? !middleName.equals(that.middleName) : that.middleName != null) return false;
        if (inn != null ? !inn.equals(that.inn) : that.inn != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = firstName != null ? firstName.hashCode() : 0;
        result = 31 * result + (secondName != null ? secondName.hashCode() : 0);
        result = 31 * result + (middleName != null ? middleName.hashCode() : 0);
        result = 31 * result + (inn != null ? inn.hashCode() : 0);
        return result;
    }
}
