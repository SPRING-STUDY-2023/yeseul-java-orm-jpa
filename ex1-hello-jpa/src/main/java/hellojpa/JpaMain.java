package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
       EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");//EntityManagerFactory -> 애플리케이션 로딩 시점에 딱 한번만 선언

        EntityManager em = emf.createEntityManager(); //트랜잭션 단위로 생성 해줘야함.

        EntityTransaction tx = em.getTransaction(); //jpa는 트랜잭션 단위 안에서 DB 접근해야함.
        tx.begin();

        //* 회원 생성
        try {

            Address address = new Address("city", "street", "zipcode");

            Member member = new Member();
            member.setUsername("member1");
            member.setHomeAddress(address);
            member.setPeriod(new Period());
            em.persist(member );

            Member member2 = new Member();
            member2.setUsername("member2");
            member2.setHomeAddress(member.getHomeAddress());
            em.persist(member2);

            //이러면 이제 member2의 주소도 같이 바뀌는 문제가 생김 -> 불변 객체로 만들면 수정 안됨. (setter를 아예 없애던지, private로 만들거나)
            //member.getHomeAddress().setCity("newCity");

            //그렇다면 실제로 값을 바꾸고 싶다면? -> 그냥 임베디드 객체 자체를 새로 만드세요 ^^,,

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
