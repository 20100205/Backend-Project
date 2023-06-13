package BackendProject.entities.enums;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public enum Role {
    ADMIN(Set.of(Permission.USER_EDIT, Permission.USER_READ, Permission.PRODUCT_READ)),
    SELLER(Set.of(Permission.PRODUCT_ADD, Permission.PRODUCT_EDIT, Permission.PRODUCT_READ)),
    BUYER(Set.of(Permission.PRODUCT_READ)),
    SUSPENDED(Set.of(Permission.NONE));

    private final Set<Permission> permissions;

    Role(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public List<SimpleGrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = getPermissions()
                .stream()
                .map(i -> new SimpleGrantedAuthority(i.getPermission()))
                .collect(Collectors.toList());

        authorities.add(new SimpleGrantedAuthority(name()));
        return authorities;
    }
}
