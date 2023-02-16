package hellojpa;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity //중요! 애노테이션 붙여야 jpa 인식 가능
public class Member {
    @Id
    @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;
    @Column(name = "USERNAME") //객체는 username, DB의 컬럼명은 name
    private String username;

    //Period
    @Embedded
    private Period period;

    //Address
    @Embedded
    private Address homeAddress;

    //Address
//    @Embedded
//    private Address wordAddress; // 에러 !!!

    @Embedded
    @AttributeOverrides({
            @AttributeOverride( name ="city", column=@Column(name = "WORK_CITY")),
            @AttributeOverride( name ="street", column=@Column(name = "WORK_STREET")),
            @AttributeOverride( name ="zipcode", column=@Column(name = "WORK_ZIPCODE"))})
    private Address workAddress;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Period getPeriod() {
        return period;
    }

    public void setPeriod(Period period) {
        this.period = period;
    }

    public Address getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(Address address) {
        this.homeAddress = address;
    }
}
