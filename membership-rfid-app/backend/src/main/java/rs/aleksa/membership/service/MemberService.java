package rs.aleksa.membership.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.aleksa.membership.dto.ApiDtos.*;
import rs.aleksa.membership.model.Enums;
import rs.aleksa.membership.model.Member;
import rs.aleksa.membership.repo.MemberRepository;
import java.util.List;

@Service
public class MemberService {
    private final MemberRepository repo; private final MapperService mapper;
    public MemberService(MemberRepository repo, MapperService mapper) { this.repo=repo; this.mapper=mapper; }
    public List<MemberDto> list(String q) { return repo.search(q).stream().map(mapper::member).toList(); }
    public MemberDto get(Long id) { return mapper.member(repo.findById(id).orElseThrow()); }
    @Transactional public MemberDto create(MemberRequest r) { Member m = new Member(); apply(m,r); return mapper.member(repo.save(m)); }
    @Transactional public MemberDto update(Long id, MemberRequest r) { Member m = repo.findById(id).orElseThrow(); apply(m,r); return mapper.member(repo.save(m)); }
    private void apply(Member m, MemberRequest r) {
        m.setFirstName(r.firstName()); m.setLastName(r.lastName()); m.setPhone(r.phone()); m.setEmail(r.email()); m.setNote(r.note());
        if (r.status()!=null && !r.status().isBlank()) m.setStatus(Enums.MemberStatus.valueOf(r.status()));
    }
}
