package jpashop.jpashop.dto.member.form;

import javax.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberLoginDTO {

    @Length(min = 5, message = "id는 최소 5글자 이상이여야 합니다")
    private String identification;

    @Length(min = 6, message = "password는 최소 6글자 이상이어야 합니다.")
    private String password;

    public MemberLoginDTO(String identification, String password) {
        this.identification = identification;
        this.password = password;
    }
}
