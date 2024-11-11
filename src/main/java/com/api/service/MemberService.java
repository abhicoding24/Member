package com.api.service;

import com.api.entity.Member;
import com.api.exception.ResourceNotFoundException;
import com.api.payload.MemberDto;
import com.api.repository.MemberRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MemberService{
    private MemberRepository memberRepository;
    private ModelMapper modelMapper;
    public MemberService(MemberRepository memberRepository, ModelMapper modelMapper){
        this.memberRepository=memberRepository;
        this.modelMapper=modelMapper;
    }
    Member mapToEntity(MemberDto dto){
        Member member = modelMapper.map(dto,Member.class);
        return member;
    }
    MemberDto mapToDto(Member member){
        MemberDto dto = modelMapper.map(member, MemberDto.class);
        return dto;
    }
    public MemberDto createMember(MemberDto dto){
        Member member = memberRepository.save(mapToEntity(dto));
        return mapToDto(member);
    }
    public List<MemberDto> getAllMembers(){
        List<Member> members = memberRepository.findAll();
        List<MemberDto> dtos = members.stream().map(a->mapToDto(a)).collect(Collectors.toList());
        return dtos;
    }
    public MemberDto getMember(long id){
        Member member = memberRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Resource not found"));
        return mapToDto(member);
    }
    public void deleteMember(long id){
        memberRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Resource not found"));
        memberRepository.deleteById(id);
    }
    public MemberDto updateMember(long id,MemberDto dto){
        Member member = memberRepository.findById(id).get();
        member.setName(dto.getName());
        member.setEmail(dto.getEmail());
        member.setMobile(dto.getMobile());
        Member updatedMember = memberRepository.save(member);
        return mapToDto(updatedMember);
    }
}
