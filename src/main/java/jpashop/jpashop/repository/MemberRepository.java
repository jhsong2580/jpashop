package jpashop.jpashop.repository;

import java.util.Optional;
import jpashop.jpashop.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByName(String name);
    Optional<Member> findByName(String name);
}
