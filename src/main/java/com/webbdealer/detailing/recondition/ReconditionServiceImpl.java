package com.webbdealer.detailing.recondition;

import com.webbdealer.detailing.company.CompanyService;
import com.webbdealer.detailing.company.dao.Company;
import com.webbdealer.detailing.company.dao.CompanyRepository;
import com.webbdealer.detailing.job.dao.Job;
import com.webbdealer.detailing.recondition.dao.Recondition;
import com.webbdealer.detailing.recondition.dao.ReconditionRepository;
import com.webbdealer.detailing.recondition.dto.ReconditionCreateForm;
import com.webbdealer.detailing.recondition.dto.ReconditionServiceResponse;
import com.webbdealer.detailing.recondition.dto.ReconditionWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.*;

@Service
public class ReconditionServiceImpl implements ReconditionService {

    private static final Logger logger = LoggerFactory.getLogger(ReconditionServiceImpl.class);

    private ReconditionRepository reconditionRepository;

    private CompanyService companyService;

    @Autowired
    public ReconditionServiceImpl(ReconditionRepository reconditionRepository, CompanyService companyService) {
        this.reconditionRepository = reconditionRepository;
        this.companyService = companyService;
    }

    @Override
    public Optional<Recondition> fetchById(Long id) {

        return reconditionRepository.findById(id);
    }

    @Override
    public Recondition fetchByIdReference(Long id) {
        return reconditionRepository.getOne(id);
    }

    @Override
    public Job attachReconServicesToJob(List<Long> serviceIds, Job job) {
        List<Recondition> reconditionList = new ArrayList<>();

        serviceIds.forEach(id -> {
            Recondition recon = reconditionRepository.getOne(id);
            reconditionList.add(recon);
        });
        job.setReconditioningServices(reconditionList);
        return job;
    }

    @Override
    public List<Recondition> fetchServices(Long companyId) {
        return reconditionRepository.findAllByCompanyId(companyId);
    }

    @Override
    public List<Recondition> storeReconditionServiceFromRequest(Long companyId, ReconditionWrapper reconditionCreateForm) {

        List<Recondition> reconditionList = new ArrayList<>();

        List<ReconditionCreateForm> reconditionCreateFormList = reconditionCreateForm.getServices();

        reconditionCreateFormList.forEach(reconService -> {
            Recondition reconditionService = new Recondition();
            reconditionService.setName(reconService.getName());
            reconditionService.setDescription(reconService.getDescription());
            reconditionService.setPrice(new BigDecimal(reconService.getPrice()));
            Recondition recon = storeReconditionServices(companyId, reconditionService);
            reconditionList.add(recon);
        });

        return reconditionList;
    }

    @Override
    public Recondition storeReconditionServices(Long companyId, Recondition recondition) {
        Optional<Company> optionalCompany = companyService.fetchById(companyId);
        Company company = optionalCompany.orElseThrow(() -> new EntityNotFoundException("Company with ID of ["+companyId+"] not found!"));
        recondition.setCompany(company);

        return reconditionRepository.save(recondition);
    }

    @Override
    public List<ReconditionServiceResponse> mapReconditionListToResponseList(List<Recondition> reconditionList) {
        List<ReconditionServiceResponse> reconditionResponseList = new ArrayList<>();

        reconditionList.forEach(recondition -> {
            reconditionResponseList.add(mapReconditionServiceToResponse(recondition));
        });
        return reconditionResponseList;
    }

    @Override
    public ReconditionServiceResponse mapReconditionServiceToResponse(Recondition recondition) {
        ReconditionServiceResponse reconditionResponse = new ReconditionServiceResponse();
        reconditionResponse.setId(recondition.getId());
        reconditionResponse.setName(recondition.getName());
        reconditionResponse.setDescription(recondition.getDescription());
        reconditionResponse.setPrice(recondition.getPrice().toPlainString());
        reconditionResponse.setCreatedAt(recondition.getCreatedAt().toString());
//        reconditionResponse.setUpdatedAt(recondition.getUpdatedAt().toString());
        return reconditionResponse;
    }
}
