package com.example.casaapp.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class RequestMemberDTO {


    @NotNull
    private String memberid;
    @NotNull
    private String password;
    @NotNull
    @Min(0)
    @Max(20)
    private String username;
    private String email;
    private String phone;
    private String address;
    private String birthday;


}
