package jpashop.jpashop.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@AttributeOverrides({
    @AttributeOverride(
        name = "createDate",
        column = @Column(name = "MEMBER_CREATE_DATE")
    ),
    @AttributeOverride(
        name = "updateDate",
        column = @Column(name = "MEMBER_UPDATE_DATE")
    )
})
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_ID")
    private Long id;

    private String name;

    @Embedded
    private Address address = new Address();
}
