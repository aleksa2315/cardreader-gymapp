package rs.aleksa.membership.web;

import org.springframework.web.bind.annotation.*;
import rs.aleksa.membership.dto.ApiDtos.AccessLogDto;
import rs.aleksa.membership.repo.AccessLogRepository;
import rs.aleksa.membership.service.MapperService;
import java.util.List;

@RestController
@RequestMapping("/api/access-logs")
public class AccessLogController {
    private final AccessLogRepository repo; private final MapperService mapper;
    public AccessLogController(AccessLogRepository repo, MapperService mapper){this.repo=repo;this.mapper=mapper;}
    @GetMapping public List<AccessLogDto> latest(){return repo.findTop50ByOrderByRequestTimeDesc().stream().map(mapper::log).toList();}
}
