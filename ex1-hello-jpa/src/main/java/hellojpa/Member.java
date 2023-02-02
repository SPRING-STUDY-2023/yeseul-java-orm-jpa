package hellojpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity //중요! 애노테이션 붙여야 jpa 인식 가능
//@Table(name = "USER") -> 실제 클래스명과 테이블명이 다를때 매핑해주면 가능
public class Member {
    @Id
    private Long id;
    //@Column(name = "userName") -> 컬럼명이 다를때도 마찬가지로 어노테이션을 이용해서 매핑 가능
    private String name;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
