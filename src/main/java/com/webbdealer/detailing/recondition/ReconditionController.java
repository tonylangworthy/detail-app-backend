package com.webbdealer.detailing.recondition;

import com.webbdealer.detailing.recondition.dao.Recondition;
import com.webbdealer.detailing.recondition.dto.ReconditionCreateForm;
import com.webbdealer.detailing.recondition.dto.ReconditionItemModel;
import com.webbdealer.detailing.recondition.dto.ReconditionItemModelAssembler;
import com.webbdealer.detailing.recondition.dto.ReconditionWrapper;
import com.webbdealer.detailing.security.JwtClaim;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/services")
public class ReconditionController {

    private ReconditionService reconditionService;

    private ReconditionItemModelAssembler reconditionItemModelAssembler;

    private PagedResourcesAssembler<Recondition> pagedResourcesAssembler;

    @Autowired
    public ReconditionController(
            ReconditionService reconditionService,
            ReconditionItemModelAssembler reconditionItemModelAssembler,
            PagedResourcesAssembler<Recondition> pagedResourcesAssembler) {
        this.reconditionService = reconditionService;
        this.reconditionItemModelAssembler = reconditionItemModelAssembler;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

    @GetMapping("")
//    @PreAuthorize("hasAuthority('WRITE_JOBS')")
//    @RolesAllowed("ROLE_DETAILER")
    public PagedModel<ReconditionItemModel> listAllReconditioningServices(Pageable pageable) {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication auth = context.getAuthentication();

        JwtClaim userDetails = (JwtClaim) auth.getPrincipal();

        System.out.println(userDetails.toString());
        System.out.println(auth.toString());
        Page<Recondition> reconditionPage = reconditionService.fetchServices(userDetails.getCompanyId(), pageable);
        return pagedResourcesAssembler.toModel(reconditionPage, reconditionItemModelAssembler);
    }

    @PostMapping("")
    public ResponseEntity<?> createReconditioningServices(@RequestBody ReconditionWrapper servicesList) {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication auth = context.getAuthentication();

        JwtClaim userDetails = (JwtClaim) auth.getPrincipal();

        System.out.println(servicesList.toString());
        List<Recondition> reconList = reconditionService.storeReconditionServiceFromRequest(userDetails.getCompanyId(), servicesList);

        return ResponseEntity.ok(reconList.toString());
    }
}
