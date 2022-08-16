package jpashop.jpashop.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import jpashop.jpashop.dto.member.form.MemberJoinDTO;
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
    private String password;
    @Embedded
    private Address address = new Address();

    @Embedded
    private OrderListManager orders = new OrderListManager();

    public Member(String name, String password, Address address) {
        this.name = name;
        this.password = password;
        this.address = address;
    }

    public static Member from(MemberJoinDTO memberJoinDTO) {
        return new Member(memberJoinDTO.getIdentification(), memberJoinDTO.getPassword(), null);
    }

    public boolean validPassword(String password){
        return this.password.equals(password);
    }
}
