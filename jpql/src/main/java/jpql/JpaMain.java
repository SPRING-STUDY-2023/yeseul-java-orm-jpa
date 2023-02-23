package jpql;

import javax.persistence.*;
import java.sql.SQLOutput;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");//EntityManagerFactory -> 애플리케이션 로딩 시점에 딱 한번만 선언

        EntityManager em = emf.createEntityManager(); //트랜잭션 단위로 생성 해줘야함.

        EntityTransaction tx = em.getTransaction(); //jpa는 트랜잭션 단위 안에서 DB 접근해야함.
        tx.begin();

        try {
            Team teamA = new Team();
            teamA.setName("TeamA");
            em.persist(teamA);

            Team teamB = new Team();
            teamB.setName("TeamB");
            em.persist(teamB);

            Member member1 = new Member();
            member1.setUsername("member1");
            member1.setAge(10);
            member1.setType(MemberType.ADMIN);
            member1.setTeam(teamA);
            em.persist(member1);

            Member member2 = new Member();
            member2.setUsername("member2");
            member2.setAge(15);
            member2.setType(MemberType.USER);
            member2.setTeam(teamB);
            em.persist(member2);

            em.flush();
            em.clear();

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
            //em.createQuery("select o.addresses from Order o", Address.class).getResultList();
            //* 스칼라 타입 프로젝션
            //em.createQuery("select new jpql.MemberDTO(m.userName, m.age) from Member m", MemberDTO.class).getResultList();

            //* 페이징
//            for (int i = 0; i < 100; i++) {
//                Member member2 = new Member();
//                member2.setUsername("member" + i);
//                member2.setAge(i);
//                em.persist(member2);
//            }
//
//            em.flush();
//            em.clear();
//
//            List<Member> results = em.createQuery("select m from Member m order by m.age desc", Member.class)
//                    .setFirstResult(6)
//                    .setMaxResults(10)
//                    .getResultList();
//            System.out.println("result size = " + results.size());
//
//            for (Member m : results) {
//                System.out.println("member = " + m.getUsername());
//            }

            //* 조인
//            Team team = new Team();
//            team.setName("teamA");
//            em.persist(team);

//            Member member_join = new Member();
//            member_join.setUsername("member_join");
//            member_join.setAge(10);
//            member_join.setTeam(team);
//
//            em.persist(member_join);
//
//            String join_query = "select m from Member m inner join m.team t"; //내부 조인
//            //List<Member> result = em.createQuery(join_query, Member.class).getResultList();
//
//            //* 서브 쿼리
//            String sub_query = "select (select avg(m.age) from Member m) as avgAge from Member m join Teem t on m.userName = t.name";
//            List<Member> result = em.createQuery(sub_query, Member.class).getResultList();

            //* JPQL 타입 표현
//            String query = "SELECT m.username, 'HELLO', TRUE FROM Member m" +
////                    "where m.type = ADMIN"; 이렇게 바로 쓸수는 없음 -> 패키지명 포함해야됨
////                    "WHERE m.type = jpql.MemberType.ADMIN";
//                    " where m.type = :userType";
//
//            List<Object[]> result2 = em.createQuery(query)
//                    .setParameter("userType", MemberType.ADMIN)
//                    .getResultList(); //문자, Boolean 가능
//
//            for (Object[] objects : result2) {
//                System.out.println("objects = " + objects[0]);
//                System.out.println("objects = " + objects[1]);
//                System.out.println("objects = " + objects[2]);
//            }

            //엔티티 타입은 상속관계에서 사용
//          Book book = new Book();
//          book.setName("JPA");
//          book.setAuthor("김영한");
//
//          em.persist(book);

//          em.createQuery("select i from Item i where type(i) = Book", Item.class)
//            .getResultList(); // Discriminator Value값이 Book인 것

            //* 조건식
//          String query = "select "
//              + "case when m.age <= 10 then '학생요금' "
//              + "   when m.age >= 60 then '경로요금' "
//              + "   else '일반요금' "
//              + "end " + "from Member m";

//          String query = "select coalesce(m.username, '이름없는 회원') as userName "
//              + "from Member m ";
//          List<String> result = em.createQuery(query, String.class).getResultList();

            //* 경로표현식
//          String query = "select t.members.size From Team t"; //size는 가능
//          Integer result = em.createQuery(query, Integer.class)
//                  .getSingleResult();
//          System.out.println("size = " + result);


//          //묵시적 join - 잘 사용하지 않음
//          String query1 = "select m.team From Member m";
//          List<Team> result1 = em.createQuery(query1, Team.class)
//              .getResultList();

//          for (Team s : result) {
//            System.out.println("S = ", s);
//        }

            //명시적 조인
//          String query2 = "select m.age From Team t join t.members m";
//          List<Collection>  result2 = em.createQuery(query2, Collection.class)
//              .getResultList();

            //* FETCH 조인 -> 실무에서 엄청 많이 씀
//          String query = "select m From Member m join fetch m.team";
//          List<Member> result = em.createQuery(query, Member.class).getResultList();
//
//          for(Teem teem : result) {
//              System.out.println("Teem = " + teem.getName() + "| members= " + teem.getMembers().size());
//                  for(Member member: teem.getMembers()){
//                      System.out.println("-> member = " + member);
//              }
//          }

            //* 엔티티 직접 사용
//          String query = "select m From Member m where m =: member";
//          Member findMember = em.createQuery(query, Member.class)
//                  .setParameter("member", member1)
//                  .getSingleResult();
//          System.out.println("findMember = " + findMember);

            //* Named 쿼리
//          List<Member> resultList = em.createNamedQuery("Member.findByUsername")
//                  .setParameter("username", "member1")
//                  .getResultList();
//
//          for(Member m : resultList) {
//               System.out.println("member : " + m);
//          }

            //* 벌크 연산
            //FLUSH 자동 호출
            int resultCount = em.createQuery("update Member m set m.age = 20")
                    .executeUpdate();

            em.clear();

            Member findMember = em.find(Member.class, member1.getId());
            System.out.println("findMember = " + findMember.getAge());

            tx.commit();
        } catch (Exception e) {
            System.out.println("exception");
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}

