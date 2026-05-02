package rs.aleksa.membership.web;

import org.springframework.web.bind.annotation.*;
import rs.aleksa.membership.dto.ApiDtos.*;
import rs.aleksa.membership.service.SubscriptionService;
import java.util.List;

@RestController
@RequestMapping("/api/subscriptions")
public class SubscriptionController {
    private final SubscriptionService service;
    public SubscriptionController(SubscriptionService service){this.service=service;}
    @GetMapping("/member/{memberId}") public List<SubscriptionDto> byMember(@PathVariable Long memberId){return service.byMember(memberId);}    
    @PostMapping public SubscriptionDto create(@RequestBody SubscriptionRequest r){return service.create(r);}    
}
