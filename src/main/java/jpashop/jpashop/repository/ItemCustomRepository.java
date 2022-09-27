package jpashop.jpashop.repository;

import java.util.Optional;
import jpashop.jpashop.domain.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;


public interface ItemCustomRepository {


    Optional<Item> findByIdWithLock(Long id);
}
