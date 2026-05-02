package rs.aleksa.membership.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
public class HealthController {
    @GetMapping("/healthz") public Map<String,Object> health(){return Map.of("ok", true);}    
    @GetMapping("/api/test/ping") public Map<String,Object> ping(){return Map.of("ok", true, "service", "membership-rfid-backend");}
}
