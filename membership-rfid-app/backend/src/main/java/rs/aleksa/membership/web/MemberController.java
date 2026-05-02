package rs.aleksa.membership.web;

import org.springframework.web.bind.annotation.*;
import rs.aleksa.membership.dto.ApiDtos.*;
import rs.aleksa.membership.service.MemberService;
import java.util.List;

@RestController
@RequestMapping("/api/members")
public class MemberController {
    private final MemberService service;
    public MemberController(MemberService service){this.service=service;}
    @GetMapping public List<MemberDto> list(@RequestParam(required=false) String q){return service.list(q);}    
    @GetMapping("/{id}") public MemberDto get(@PathVariable Long id){return service.get(id);}    
    @PostMapping public MemberDto create(@RequestBody MemberRequest r){return service.create(r);}    
    @PutMapping("/{id}") public MemberDto update(@PathVariable Long id,@RequestBody MemberRequest r){return service.update(id,r);}    
}
