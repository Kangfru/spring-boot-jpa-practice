package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain2 {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            // 비영속
//            Member member = new Member();
//            member.setId(101L);
//            member.setName("HelloJPA");

            // 영속 상태로 변경
//            System.out.println("===BEFORE===");
//            em.persist(member);
//            System.out.println("===AFTER===");

            // DB 조회 해야함 (영속성 컨텍스트에 넣음)
            Member findMember = em.find(Member.class, 101L);
            System.out.println("member.id = " + findMember.getId());
            // 1차 캐시 안에서 가져옴 (영속성 컨텍스트에서 가져옴
            Member findMember2 = em.find(Member.class, 101L);
            System.out.println("member.id = " + findMember2.getId());

            // 영속성 엔티티에서의 동일성 보장 == true
            System.out.println("result == " + (findMember == findMember2));

            // Java Collection 처럼 값만 변경해도 Update Query
            // 변경 감지 기능
            // em.update는 없음
            Member member = em.find(Member.class, 150L);
            member.setName("zzzzz");

            // commit 하는 순간 insert 및 update SQL을 전송
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }

}
