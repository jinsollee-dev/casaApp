package com.example.casaapp.dto;

import com.example.casaapp.domain.Member;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseMemberDTO {

    private String memberid;
    private String password;
    private String username;
    private String email;
    private String phone;
    private String address;
    private String birthday;


    public static ResponseMemberDTO fromEntity(@NonNull Member member) {
        return ResponseMemberDTO.builder()
                .memberid(member.getMemberid())
                .password(member.getPassword())
                .username(member.getUsername())
                .email(member.getEmail())
                .phone(member.getPhone())
                .address(member.getAddress())
                .birthday(member.getBirthday())
                .build();
    }
}
