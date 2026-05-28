package com.aquienllamo.aquienllamo.model.auth.credentials;

import jakarta.transaction.Transactional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class UserDetailsServiceImpl /*implements UserDetailsService*/ {

    // resolver...
    private final CredentialsRepository credentialsRepository;

//    @Override -> gestionar
    public UserDetails loadUserByUsername(@NonNull String username) throws UsernameNotFoundException {
        return
                credentialsRepository.findByUsername(username)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

}
