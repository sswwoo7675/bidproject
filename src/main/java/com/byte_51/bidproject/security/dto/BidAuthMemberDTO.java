package com.byte_51.bidproject.security.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
@Setter
@ToString
public class BidAuthMemberDTO extends User { //UserDetails 구현 클래스
    private String email;
    private String password;
    private String name;
    private int point;
    private boolean fromSocial;

    public BidAuthMemberDTO(String username, String password, boolean fromSocial, Collection<? extends GrantedAuthority> authorities){
        super(username, password, authorities);
        this.email = username;
        this.password = password;
        this.fromSocial = fromSocial;
    }
}
