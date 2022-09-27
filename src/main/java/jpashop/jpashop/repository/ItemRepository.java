package jpashop.jpashop.repository;

import java.util.Optional;
import javax.persistence.LockModeType;
import jpashop.jpashop.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

public interface ItemRepository extends JpaRepository<Item, Long>, ItemCustomRepository {
}
