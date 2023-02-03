package org.acme.repositories;

import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.subscription.MultiSubscriber;
import org.acme.models.User;

import javax.enterprise.context.ApplicationScoped;

/**
 * User PanacheBase Repository
 */
@ApplicationScoped
public class UserRepository implements PanacheRepository<User> {
    /**
     * Find User By Email ID
     */
    public Uni<User> findUserByEmailId(String emailId){
        return find("emailId",emailId).firstResult();
    }


}
