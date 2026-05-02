package rs.aleksa.membership.web;

import org.springframework.web.bind.annotation.*;
import rs.aleksa.membership.dto.AccessDtos.*;
import rs.aleksa.membership.service.AccessDecisionService;

@RestController
@RequestMapping("/api/access")
public class AccessController {
    private final AccessDecisionService service;
    public AccessController(AccessDecisionService service){this.service=service;}
    @PostMapping("/check")
    public AccessCheckResponse check(@RequestBody AccessCheckRequest req){ return service.check(req.cardId()); }
}
