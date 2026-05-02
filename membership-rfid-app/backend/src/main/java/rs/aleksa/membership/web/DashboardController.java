package rs.aleksa.membership.web;

import org.springframework.web.bind.annotation.*;
import rs.aleksa.membership.dto.ApiDtos.DashboardDto;
import rs.aleksa.membership.service.DashboardService;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {
    private final DashboardService service;
    public DashboardController(DashboardService service){this.service=service;}
    @GetMapping public DashboardDto stats(){return service.stats();}
}
