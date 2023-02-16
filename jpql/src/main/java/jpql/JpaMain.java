package jpql;

import javax.persistence.*;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");//EntityManagerFactory -> 애플리케이션 로딩 시점에 딱 한번만 선언

        EntityManager em = emf.createEntityManager(); //트랜잭션 단위로 생성 해줘야함.

        EntityTransaction tx = em.getTransaction(); //jpa는 트랜잭션 단위 안에서 DB 접근해야함.
        tx.begin();

        try {
            Member member = new Member();
            member.setUsername("member1");
            member.setAge(10);
            em.persist(member);

            //* TypeQuery, Query
            TypedQuery<Member> query1 = em.createQuery("SELECT m FROM Member m where m.age > 18", Member.class); //반환 타입이 명확할 때
            Query query2 = em.createQuery("SELECT m.username, m.age from Member m"); //반환 타입이 명확하지 않을 때 (username, age 타입 다름)

            //* 결과 조회 API
            //query1.getSingleResult(); //쿼리의 결과하 정화히 하나 -> 여러 개일 떄는 getResultList() 사용

            //* 파라미터 바인딩 - 이름 기준, 위치 기준 (위치 기반은 되도록 사용하지 x)
            Member singleResult = em.createQuery("SELECT m FROM Member m where m.username = :username", Member.class)
                    .setParameter("username", "member1")
                    .getSingleResult();

            System.out.println("singleResult = " + singleResult.getUsername());

            //* 임베디드 타입 프로젝션
            em.createQuery("select o.addresses from Order o", Address.class).getResultList();
            //* 스칼라 타입 프로젝션
            em.createQuery("select new jpql.MemberDTO(m.userName, m.age) from Member m", MemberDTO.class).getResultList();






            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}

