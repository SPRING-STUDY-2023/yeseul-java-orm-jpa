package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Set;

public class JpaMain {
    public static void main(String[] args) {
       EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");//EntityManagerFactory -> 애플리케이션 로딩 시점에 딱 한번만 선언

        EntityManager em = emf.createEntityManager(); //트랜잭션 단위로 생성 해줘야함.

        EntityTransaction tx = em.getTransaction(); //jpa는 트랜잭션 단위 안에서 DB 접근해야함.
        tx.begin();

        //* 회원 생성
        try {

            Member member = new Member();
            member.setUsername("member1");
            member.setHomeAddress(new Address("homecity", "street", "10000"));

            //컬렉션
            member.getFavoriteFoods().add("치킨");
            member.getFavoriteFoods().add("족발");
            member.getFavoriteFoods().add("피자");

            member.getAddressHistory().add(new Address("old1", "street", "10000"));
            member.getAddressHistory().add(new Address("old2", "street", "10000"));

            em.persist(member); //값타입 컬렉션을 따로 persist 하지 않아도 자동으로 DB 저장\

            em.flush();
            em.clear();

            System.out.println("============");
            Member findMember = em.find(Member.class, member.getId());

            List<Address> addressHistory = findMember.getAddressHistory();
            for (Address address : addressHistory) {
                System.out.println("address = " + address.getCity());
            }

            Set<String> favoriteFoods = findMember.getFavoriteFoods();
            for (String favoriteFood : favoriteFoods) {
                System.out.println("favorite fodd = " + favoriteFood);
            }
            System.out.println("============");

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
