package rs.aleksa.membership.web;

import org.springframework.web.bind.annotation.*;
import rs.aleksa.membership.dto.ApiDtos.*;
import rs.aleksa.membership.service.CardService;
import java.util.List;

@RestController
@RequestMapping("/api/cards")
public class CardController {
    private final CardService service;
    public CardController(CardService service){this.service=service;}
    @GetMapping public List<CardDto> list(){return service.list();}
    @GetMapping("/member/{memberId}") public List<CardDto> byMember(@PathVariable Long memberId){return service.byMember(memberId);}    
    @PostMapping public CardDto create(@RequestBody CardRequest r){return service.create(r);}    
    @PutMapping("/{id}/deactivate") public CardDto deactivate(@PathVariable Long id){return service.deactivate(id);}    
}
