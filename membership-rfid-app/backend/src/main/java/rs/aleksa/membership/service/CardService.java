package rs.aleksa.membership.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.aleksa.membership.dto.ApiDtos.*;
import rs.aleksa.membership.model.RfidCard;
import rs.aleksa.membership.repo.MemberRepository;
import rs.aleksa.membership.repo.RfidCardRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CardService {

    private final RfidCardRepository cards;
    private final MemberRepository members;
    private final MapperService mapper;

    public CardService(RfidCardRepository cards,
                       MemberRepository members,
                       MapperService mapper) {
        this.cards = cards;
        this.members = members;
        this.mapper = mapper;
    }

    @Transactional(readOnly = true)
    public List<CardDto> list() {
        return cards.findAllWithMember()
                .stream()
                .map(mapper::card)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<CardDto> byMember(Long memberId) {
        return cards.findByMemberIdWithMember(memberId)
                .stream()
                .map(mapper::card)
                .toList();
    }

    @Transactional
    public CardDto create(CardRequest r) {
        RfidCard c = new RfidCard();
        c.setCardId(r.cardId().trim());
        c.setMember(members.findById(r.memberId()).orElseThrow());
        c.setActive(true);

        RfidCard saved = cards.save(c);
        return mapper.card(saved);
    }

    @Transactional
    public CardDto deactivate(Long id) {
        RfidCard c = cards.findById(id).orElseThrow();
        c.setActive(false);
        c.setDeactivatedAt(LocalDateTime.now());

        RfidCard saved = cards.save(c);
        return mapper.card(saved);
    }
}