package org.acme.web.rest;

import io.quarkus.hibernate.reactive.panache.Panache;
import io.smallrye.mutiny.Uni;
import org.acme.repositories.RoleRepository;
import org.acme.utils.GlobalConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.util.HashMap;

@Path("/api/role")
@ApplicationScoped
public class RoleResource {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Inject
    RoleRepository roleRepository;

    /**
     * GET Roles
     */
    @GET
    @Path("/view")
    @RolesAllowed({"ADMIN","PRO_USER","PRO_ADMIN"})
    public Uni<Response> roles(){
        log.info("REST - get all roles");
        HashMap<String, Object> response = new HashMap<>();
        return Panache.withTransaction(() -> roleRepository.findAll().list()
                .onItem()
                .ifNotNull()
                .transform(roles -> {
                    response.put(GlobalConstant.MESSAGE,"ALL ROLE IS GET");
                    response.put("ROLES",roles);
                    response.put(GlobalConstant.STATUS,GlobalConstant.SUCCESS);
                    return Response.ok(response).build();
                })
        )
                .onItem()
                .ifNull()
                .continueWith(() -> {
                    response.put(GlobalConstant.MESSAGE,"SOMETHING WANT WRONG");
                    response.put(GlobalConstant.STATUS,GlobalConstant.FAILURE);
                    return Response.ok(response).build();
                });
    }
}
