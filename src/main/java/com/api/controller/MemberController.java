package com.api.controller;

import com.api.entity.Member;
import com.api.payload.MemberDto;
import com.api.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/member")
public class MemberController{
    @Autowired
    private MemberService memberService;
    @PostMapping
    public ResponseEntity<?> createMember(@Valid @RequestBody MemberDto memberDto, BindingResult result){
        if(result.hasErrors()){
            return new ResponseEntity<>(result.getFieldError().getDefaultMessage(), HttpStatus.CREATED);
        }
        MemberDto dto = memberService.createMember(memberDto);
        return new ResponseEntity<>(dto,HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<List<MemberDto>> getAllMembers(){
        List<MemberDto> members = memberService.getAllMembers();
        return new ResponseEntity<>(members,HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<MemberDto> getMemberById(@PathVariable long id){
        MemberDto dto = memberService.getMember(id);
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }
    @DeleteMapping
    public ResponseEntity<String> deleteMember(@RequestParam long id){
        memberService.deleteMember(id);
        return new ResponseEntity<>("Record deleted",HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<MemberDto> updateMember(@PathVariable long id,@RequestBody MemberDto memberDto){
        MemberDto dto = memberService.updateMember(id,memberDto);
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }
}
