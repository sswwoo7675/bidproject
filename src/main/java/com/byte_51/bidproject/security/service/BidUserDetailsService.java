package com.byte_51.bidproject.security.service;

import com.byte_51.bidproject.entity.Member;
import com.byte_51.bidproject.repository.MemberRepository;
import com.byte_51.bidproject.security.dto.BidAuthMemberDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class BidUserDetailsService implements UserDetailsService { //인증 및 인가(UserDetails 반환)
    private final MemberRepository clubMemberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        log.info(username);
        Optional<Member> result = clubMemberRepository.findByEmail(username,false); //아이디 존재 확인(계정정보 db로부터 가져오기)

        if(!result.isPresent()){ //아이디 없으면?
            throw new UsernameNotFoundException("Check Email or Social");
        }

        Member member = result.get();

        BidAuthMemberDTO bidAuthMemberDTO = new BidAuthMemberDTO(
                member.getEmail(),
                member.getPassword(),
                member.isFromSocial(),
                member.getRoleSet().stream().map(role->new SimpleGrantedAuthority("ROLE_"+role.name())).collect(Collectors.toSet()) //권한
        ); //UserDetails 생성

        bidAuthMemberDTO.setName(member.getName());
        bidAuthMemberDTO.setFromSocial(member.isFromSocial());
        bidAuthMemberDTO.setPoint(member.getPoint());

        return bidAuthMemberDTO;
    }
}

