package hellojpa;

import javax.persistence.*;
import java.util.Date;

@Entity //중요! 애노테이션 붙여야 jpa 인식 가능
//@Table(name = "USER") -> 실제 클래스명과 테이블명이 다를때 매핑해주면 가능
public class Member {
    @Id
    private Long id;
    @Column(name = "name") //객체는 username, DB의 컬럼명은 name
    private String username;
    private Integer age;
    @Enumerated(EnumType.STRING) //enum 타입
    private RoleType roleType;
    @Temporal(TemporalType.TIMESTAMP) //날짜 타입 (DB 닐짜 타입 3가지 - DATE, TIME, TIMESTAMP)
    private Date createdDate;
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;
    @Lob //varchar를 넘는 큰 컨텐츠
    private String description;
}
