package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {
    public static void main(String[] args) {
       EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");//EntityManagerFactory -> 애플리케이션 로딩 시점에 딱 한번만 선언

        EntityManager em = emf.createEntityManager(); //트랜잭션 단위로 생성 해줘야함.

        EntityTransaction tx = em.getTransaction(); //jpa는 트랜잭션 단위 안에서 DB 접근해야함.
        tx.begin();

        //* 회원 생성
        try {
            Member member = new Member();
            member.setId(1L);
            member.setName("HelloA");

            em.persist(member);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        //* 회원 조회
        EntityManager em2 = emf.createEntityManager();
        EntityTransaction tx2 = em2.getTransaction();
        tx2.begin();
        try {
            Member findMember = em2.find(Member.class, 1L);
            System.out.println("findMember.id = " + findMember.getId());
            System.out.println("findMember.name = " + findMember.getName());

            tx2.commit();
        } catch (Exception e) {
            tx2.rollback();
        } finally {
            em2.close();
        }

        //* 회원 삭제
        EntityManager em3 = emf.createEntityManager();
        EntityTransaction tx3 = em3.getTransaction();
        tx3.begin();
        try {
            Member findMember = em3.find(Member.class, 1L);

            em3.remove(findMember);

            tx3.commit();
        } catch (Exception e) {
            tx3.rollback();
        } finally {
            em3.close();
        }

        //* 회원 수정
        EntityManager em4 = emf.createEntityManager();
        EntityTransaction tx4 = em4.getTransaction();
        tx4.begin();
        try {
            Member findMember = em4.find(Member.class, 2L);
            findMember.setName("HelloJPA");

            tx4.commit();
        } catch (Exception e) {
            tx4.rollback();
        } finally {
            em4.close();
        }

        emf.close();
    }
}
