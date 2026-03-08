package br.com.inspectflow.application.services.auth;

import br.com.inspectflow.adapters.out.repositories.user.UserRepository;
import br.com.inspectflow.domain.auth.models.SecurityUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JpaUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email).map(SecurityUser::new)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + email)) ;
    }
}
