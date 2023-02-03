package org.acme.repositories;

import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import org.acme.models.Role;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class RoleRepository implements PanacheRepository<Role> {
}
