package org.ureca.pinggubackend.domain.member.dto;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.ureca.pinggubackend.domain.member.entity.Member;

import java.util.Collection;
import java.util.List;

public class CustomMemberDetails implements UserDetails {

    private final Member member;
    private final List<GrantedAuthority> authorities;

    public CustomMemberDetails(Member member) {
        this.member = member;
        this.authorities = List.of(); // 권한이 없다면 비워도 OK
    }

    public Long getId() {
        return member.getId();
    }

    public Member getMember() {
        return this.member;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return ""; // 소셜 로그인은 비밀번호 없음
    }

    @Override
    public String getUsername() {
        // memberId를 문자열로 반환
        return String.valueOf(member.getId());
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
