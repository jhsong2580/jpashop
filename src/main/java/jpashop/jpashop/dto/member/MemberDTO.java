package jpashop.jpashop.dto.member;

import jpashop.jpashop.domain.Member;

public class MemberDTO {

    private String identification;
    private String city;
    private String street;

    public MemberDTO(String identification, String city, String street) {
        this.identification = identification;
        this.city = city;
        this.street = street;
    }

    public static MemberDTO from(Member member) {
        return new MemberDTO(member.getName(),
            member.getAddress() != null ? member.getAddress().getCity() : "",
            member.getAddress() != null ? member.getAddress().getStreet() : ""
        );
    }
}
