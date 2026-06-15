package com.aquienllamo.aquienllamo.model.auth.Credentials;

import com.aquienllamo.aquienllamo.model.auth.permissions.RoleEntity;
import com.aquienllamo.aquienllamo.model.entities.AdministradorEntity;
import com.aquienllamo.aquienllamo.model.entities.UsuarioEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Table(name = "Credencial")
public class CredentialsEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "credencial_id")
    private Integer id;

    @Column(unique = true, nullable = false, length = 50)
    private String username;

    @Column(nullable = false, length = 255)
    private String clave;

    @Column(nullable = false, columnDefinition = "TINYINT(1)")
    private Boolean enabled;

    @Column(name = "refresh_token", length = 2048)
    private String refreshToken;

    @OneToOne
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario", unique = true)
    private UsuarioEntity usuario;

    @OneToOne
    @JoinColumn(name = "id_admin", referencedColumnName = "id_admin", unique = true)
    private AdministradorEntity administrador;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(
            name = "Credencial_Rol",
            joinColumns = @JoinColumn(name = "credencial_id"),
            inverseJoinColumns = @JoinColumn(name = "id_role")
    )
    private Set<RoleEntity> roles = new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();
        roles.forEach(rol -> authorities.add(
                new SimpleGrantedAuthority(rol.getRole().name())));
        return authorities;
    }

    @Override
    public String getPassword() { return this.clave; }

    @Override
    public String getUsername() { return this.username; }

    @Override
    public boolean isEnabled() { return Boolean.TRUE.equals(this.enabled); }
}
