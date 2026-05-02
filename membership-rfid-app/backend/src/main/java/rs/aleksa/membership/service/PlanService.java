package rs.aleksa.membership.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.aleksa.membership.dto.ApiDtos.*;
import rs.aleksa.membership.model.MembershipPlan;
import rs.aleksa.membership.repo.MembershipPlanRepository;
import java.math.BigDecimal;
import java.util.List;

@Service
public class PlanService {
    private final MembershipPlanRepository repo; private final MapperService mapper;
    public PlanService(MembershipPlanRepository repo, MapperService mapper){this.repo=repo;this.mapper=mapper;}
    public List<PlanDto> list(){return repo.findAll().stream().map(mapper::plan).toList();}
    @Transactional public PlanDto save(PlanRequest r){ MembershipPlan p = r.code()!=null && repo.findByCode(r.code()).isPresent()?repo.findByCode(r.code()).get():new MembershipPlan(); apply(p,r); return mapper.plan(repo.save(p)); }
    @Transactional public PlanDto update(Long id, PlanRequest r){ MembershipPlan p=repo.findById(id).orElseThrow(); apply(p,r); return mapper.plan(repo.save(p)); }
    private void apply(MembershipPlan p, PlanRequest r){ p.setName(r.name()); p.setCode(r.code()); p.setDurationDays(r.durationDays()); p.setPrice(r.price()==null? BigDecimal.ZERO:r.price()); p.setActive(r.active()==null || r.active()); }
}
