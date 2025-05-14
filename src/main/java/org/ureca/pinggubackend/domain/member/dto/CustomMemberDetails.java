package org.ureca.pinggubackend.domain.member.dto;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.ureca.pinggubackend.domain.member.entity.Member;

import java.util.Collection;
import java.util.List;

public class CustomMemberDetails implements UserDetails {

    private final Member member;
    private final String email;
    private final List<GrantedAuthority> authorities;

    public CustomMemberDetails(Member member) {
        this.member = member;
        this.email = member.getEmail();
        this.authorities = List.of();
    }

    public Long getId() {
        return member.getId();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
