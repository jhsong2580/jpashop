package jpashop.jpashop.repository;

import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import jpashop.jpashop.domain.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class ItemCustomRepositoryImpl implements ItemCustomRepository{

    private final EntityManager em;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Optional<Item> findByIdWithLock(Long id) {
        return Optional.of(
            em.createQuery("select i from Item i where i.id = :id", Item.class)
                .setParameter("id", id)
                .setLockMode(LockModeType.PESSIMISTIC_WRITE)
                .getSingleResult()
        );
    }
}
