package rs.aleksa.membership.web;

import org.springframework.web.bind.annotation.*;
import rs.aleksa.membership.dto.ApiDtos.*;
import rs.aleksa.membership.service.PlanService;
import java.util.List;

@RestController
@RequestMapping("/api/plans")
public class PlanController {
    private final PlanService service;
    public PlanController(PlanService service){this.service=service;}
    @GetMapping public List<PlanDto> list(){return service.list();}
    @PostMapping public PlanDto create(@RequestBody PlanRequest r){return service.save(r);}    
    @PutMapping("/{id}") public PlanDto update(@PathVariable Long id,@RequestBody PlanRequest r){return service.update(id,r);}    
}
