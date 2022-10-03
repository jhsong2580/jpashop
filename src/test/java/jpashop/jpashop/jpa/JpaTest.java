package jpashop.jpashop.jpa;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import jpashop.jpashop.domain.Item;
import jpashop.jpashop.domain.Member;
import jpashop.jpashop.domain.Movie;
import jpashop.jpashop.dto.item.form.ItemAddDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
public class JpaTest {

    @Autowired
    EntityManagerFactory emf;

    EntityManager em;

    @BeforeEach
    public void init() {

        em = emf.createEntityManager();
    }

    @Test
    public void 없는key값으로_reference를가져오기() {
        em.getTransaction().begin();
        ItemAddDTO itemAddDTO = new ItemAddDTO("name", 1000, 3, "MOVIE", null, null, null, null,
            "director", "actor");

        Movie from = Movie.from(itemAddDTO);
        em.persist(from);
        em.flush();
        em.clear();

        Item reference = em.getReference(Item.class, 999L); // 없는 key값이여도 reference는 얻어짐

        assertAll(
            () -> assertThatThrownBy(() -> reference.getPrice()).isInstanceOf(EntityNotFoundException.class),
            () -> assertThatThrownBy(() -> reference.getId()).isInstanceOf(EntityNotFoundException.class)
        );
    }

    @Test
    public void typedQuery (){
        TypedQuery<Member> typedQuery = em.createQuery("select m from Member m where m.id = :id",
                Member.class)
            .setParameter("id", 1L); //반환 타입이 명확하면 typed Query
        Query query = em.createQuery("select m from Member m where m.id = :id")
            .setParameter("id", 1L); //반환타입이 불명확하면 query
    }
}
