package com.webbdealer.detailing.recondition;

import com.webbdealer.detailing.company.dao.Company;
import com.webbdealer.detailing.job.dao.Job;
import com.webbdealer.detailing.recondition.dao.Recondition;
import com.webbdealer.detailing.recondition.dto.ReconditionCreateForm;
import com.webbdealer.detailing.recondition.dto.ReconditionServiceResponse;
import com.webbdealer.detailing.recondition.dto.ReconditionWrapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ReconditionService {

    Recondition fetchById(Long id);

    Job attachReconServicesToJob(List<Long> serviceIds, Job job);

    Page<Recondition> fetchServices(Long companyId, Pageable pageable);

    List<Recondition> storeReconditionServiceFromRequest(Long companyId, ReconditionWrapper reconditionCreateForm);

    Recondition storeReconditionServices(Long companyId, Recondition recondition);

    List<ReconditionServiceResponse> mapReconditionListToResponseList(List<Recondition> reconditionList);
    ReconditionServiceResponse mapReconditionServiceToResponse(Recondition recondition);

}
