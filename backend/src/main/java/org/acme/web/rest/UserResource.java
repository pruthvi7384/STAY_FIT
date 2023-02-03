package org.acme.web.rest;

import io.quarkus.hibernate.reactive.panache.Panache;
import io.smallrye.mutiny.Uni;
import org.acme.models.Role;
import org.acme.models.User;
import org.acme.repositories.UserRepository;
import org.acme.utils.DTO.LoginDTO;
import org.acme.utils.GlobalConstant;
import org.acme.utils.JwtToken;
import org.acme.utils.PBKDF2Encoder;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.hibernate.reactive.mutiny.Mutiny;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * USER MODEL RELATED API
 */

@Path("/api/user/")
@ApplicationScoped
public class UserResource {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Inject
    UserRepository userRepository;

    @Inject
    JwtToken jwtToken;

    @Inject
    PBKDF2Encoder pbkdf2Encoder;

    @Inject
    JsonWebToken jwt;

    /**
     *  USER REGISTRATION
     */

    @POST
    @Path("registration")
    @PermitAll
    public Uni<Response> registration(User user) {
        log.info("REST - user registration request - {}",user);
        HashMap<String,String> response = new HashMap<>();
        return Panache.withTransaction(() -> userRepository.findUserByEmailId(user.getEmailId())
                .onItem()
                .ifNotNull()
                .call(Mutiny::fetch)
                .onItem()
                .ifNotNull()
                .transform(user1 -> {
                    response.put(GlobalConstant.MESSAGE,"You are already registered, try to log in now!");
                    response.put(GlobalConstant.STATUS,GlobalConstant.FAILURE);
                    return Response.ok(response).build();
                })
        ).onItem().ifNull().switchTo(() -> Panache.withTransaction(() -> {
            /**
             * Set Default role =  USER
             */
            Role role = new Role();
            role.id = 1L;
            Set<Role> userRoles = new HashSet<>();
            userRoles.add(role);
            user.setRoles(userRoles);
            user.setPassword(pbkdf2Encoder.encode(user.getPassword()));
           return userRepository.persist(user);
        }).onItem().ifNotNull().transform(user1 -> {
            response.put(GlobalConstant.MESSAGE,"You are registered successfully, please log in now!");
            response.put(GlobalConstant.STATUS,GlobalConstant.SUCCESS);
            return Response.ok(response).build();
        }));
    }

    /**
     * USER LOGIN
     */

    @POST
    @Path("login")
    @PermitAll
    public Uni<Response> login(LoginDTO loginDTO){
        log.info("REST - user login request - {}",loginDTO);
        HashMap<String,String> response = new HashMap<>();
        return Panache.withTransaction(() -> userRepository.findUserByEmailId(loginDTO.getEmailId()))
                .onItem()
                .ifNotNull()
                .call(user1 -> Mutiny.fetch(user1.getRoles()))
                .onItem()
                .ifNotNull()
                .transform(user -> {
                    if (user.getPassword().equals(pbkdf2Encoder.encode(loginDTO.getPassword()))){
                        String token = "";
                        try {
                            token = jwtToken.generateToken(user.getEmailId(),user.getRoles(),user.id);
                        } catch (Exception e) {
                            log.error("error - ",e);
                        }
                        response.put(GlobalConstant.MESSAGE,"You are login successfully!");
                        response.put("ACCESS_TOKEN",token);
                        response.put(GlobalConstant.STATUS,GlobalConstant.SUCCESS);
                    }else {
                        response.put(GlobalConstant.MESSAGE,"Please enter the correct credentials!");
                        response.put(GlobalConstant.STATUS,GlobalConstant.FAILURE);
                    }
                    return Response.ok(response).build();
                })
                .onItem()
                .ifNull()
                .continueWith(() -> {
                    response.put(GlobalConstant.MESSAGE,"You are not registered, please register now");
                    response.put(GlobalConstant.STATUS,GlobalConstant.FAILURE);
                    return Response.ok(response).build();
                });
    }

    /**
     *  USER PROFILE VIEW
     */

    @GET
    @Path("profile/view")
    @RolesAllowed({"USER","ADMIN","PRO_USER","PRO_ADMIN"})
    public Uni<Response> profile(){
        log.info("REST - user profile view request ID - {}",7);
        HashMap<String, Object> response = new HashMap<>();
        return Panache.withTransaction(() -> userRepository.findById(7l)
                .onItem()
                .ifNotNull()
                .call(user -> Mutiny.fetch(user.getRoles()))
                .onItem()
                .ifNotNull()
                .transform(user -> {
                    response.put(GlobalConstant.MESSAGE,"USER PROFILE GET SUCCESSFULLY");
                    response.put("PROFILE",user);
                    response.put(GlobalConstant.STATUS,GlobalConstant.SUCCESS);
                    return Response.ok(response).build();
                })
            )
                .onItem()
                .ifNull()
                .continueWith(() -> {
                    response.put(GlobalConstant.MESSAGE,GlobalConstant.PROFILENOTFOUNDMESSAGE);
                    response.put(GlobalConstant.STATUS,GlobalConstant.FAILURE);
                    return Response.ok(response).build();
                });
    }

    /**
     * USER PROFILE UPDATE
     */

    @PUT
    @Path("profile/update")
    @RolesAllowed({"USER","ADMIN","PRO_USER","PRO_ADMIN"})
    public Uni<Response> update(User user){
        Long id = jwt.getClaim("USER_ID");
        log.info("REST - user profile update request ID - {} REQUEST - {}",id, user);
        HashMap<String, Object> response = new HashMap<>();
        return Panache.withTransaction(() -> userRepository.findById(id)
                .onItem()
                .ifNotNull()
                .call(user1 -> Mutiny.fetch(user1.getRoles()))
                .onItem()
                .ifNotNull()
                .transform(user1 -> {
                    user1.setFirstName(user.getFirstName());
                    user1.setLastName(user.getLastName());
                    user1.setEmailId(user.getEmailId());
                    response.put(GlobalConstant.MESSAGE,"USER PROFILE UPDATED SUCCESSFULLY");
                    response.put(GlobalConstant.STATUS,GlobalConstant.SUCCESS);
                    return Response.ok(response).build();
                })
        )
                .onItem()
                .ifNull()
                .continueWith(() -> {
                    response.put(GlobalConstant.MESSAGE,GlobalConstant.PROFILENOTFOUNDMESSAGE);
                    response.put(GlobalConstant.STATUS,GlobalConstant.FAILURE);
                    return Response.ok(response).build();
                });
    }

    /**
     *  USER ACCOUNT REMOVE
     */

    @DELETE
    @Path("account/remove")
    @RolesAllowed({"USER","ADMIN","PRO_USER","PRO_ADMIN"})
    public Uni<Response> remove(){
        Long id = jwt.getClaim("USER_ID");
        log.info("REST - user profile remove request ID - {}",id);
        HashMap<String, Object> response = new HashMap<>();
        return Panache.withTransaction(() -> userRepository.deleteById(id))
                .onItem()
                .ifNotNull()
                .transform(aBoolean -> {
                    if (aBoolean){
                        response.put(GlobalConstant.MESSAGE,"USER ACCOUNT REMOVED SUCCESSFULLY");
                        response.put(GlobalConstant.STATUS,GlobalConstant.SUCCESS);
                    }else {
                        response.put(GlobalConstant.MESSAGE,GlobalConstant.PROFILENOTFOUNDMESSAGE);
                        response.put(GlobalConstant.STATUS,GlobalConstant.FAILURE);
                    }
                    return Response.ok(response).build();
                });
    }

    /**
     *  ADMIN REGISTRATION
     */

    @POST
    @Path("registration/admin")
    @PermitAll
    public Uni<Response> adminRegistration(User user) {
        log.info("REST - user registration request - {}",user);
        HashMap<String,String> response = new HashMap<>();
        return Panache.withTransaction(() -> userRepository.findUserByEmailId(user.getEmailId())
                .onItem()
                .ifNotNull()
                .call(Mutiny::fetch)
                .onItem()
                .ifNotNull()
                .transform(user1 -> {
                    response.put(GlobalConstant.MESSAGE,"You are already registered, try to log in now!");
                    response.put(GlobalConstant.STATUS,GlobalConstant.FAILURE);
                    return Response.ok(response).build();
                })
        ).onItem().ifNull().switchTo(() -> Panache.withTransaction(() -> {
            /**
             * Set Default role =  ADMIN
             */
            Role role = new Role();
            role.id = 2L;
            Set<Role> userRoles = new HashSet<>();
            userRoles.add(role);
            user.setRoles(userRoles);
            user.setPassword(pbkdf2Encoder.encode(user.getPassword()));
            return userRepository.persist(user);
        }).onItem().ifNotNull().transform(user1 -> {
            log.info("REGISTERED ADMIN - {}",user1.getRoles());
            response.put(GlobalConstant.MESSAGE,"You are registered successfully, please log in now!");
            response.put(GlobalConstant.STATUS,GlobalConstant.SUCCESS);
            return Response.ok(response).build();
        }));
    }

    /**
     * USER PROFILE UPDATE VIA ADMIN
     */

    @PUT
    @Path("admin/profile/update")
    @RolesAllowed({"ADMIN","PRO_ADMIN"})
    public Uni<Response> updateByAdmin(User user){
        Long id = jwt.getClaim("USER_ID");
        log.info("REST - user profile update request ID - {} REQUEST - {}",id, user);
        HashMap<String, Object> response = new HashMap<>();
        return Panache.withTransaction(() -> userRepository.findById(id)
                        .onItem()
                        .ifNotNull()
                        .call(user1 -> Mutiny.fetch(user1.getRoles()))
                        .onItem()
                        .ifNotNull()
                        .transform(user1 -> {
                            user1.setFirstName(user.getFirstName());
                            user1.setLastName(user.getLastName());
                            user1.setEmailId(user.getEmailId());
                            user1.setRoles(user.getRoles());
                            response.put(GlobalConstant.MESSAGE,"USER PROFILE UPDATED SUCCESSFULLY");
                            response.put(GlobalConstant.STATUS,GlobalConstant.SUCCESS);
                            return Response.ok(response).build();
                        })
                )
                .onItem()
                .ifNull()
                .continueWith(() -> {
                    response.put(GlobalConstant.MESSAGE,GlobalConstant.PROFILENOTFOUNDMESSAGE);
                    response.put(GlobalConstant.STATUS,GlobalConstant.FAILURE);
                    return Response.ok(response).build();
                });
    }
}
