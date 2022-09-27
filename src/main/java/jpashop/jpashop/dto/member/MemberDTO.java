package jpashop.jpashop.dto.member;

import jpashop.jpashop.domain.Member;
import lombok.Getter;

@Getter
public class MemberDTO {
    private Long id;
    private String identification;
    private String city;
    private String street;

    public MemberDTO(Long id, String identification, String city, String street) {
        this.id = id;
        this.identification = identification;
        this.city = city;
        this.street = street;
    }

    public static MemberDTO from(Member member) {
        return new MemberDTO(member.getId(), member.getName(),
            member.getAddress() != null ? member.getAddress().getCity() : "",
            member.getAddress() != null ? member.getAddress().getStreet() : ""
        );
    }
}
