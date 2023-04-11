package com.example.career.domain.consult.Controller;

import com.example.career.domain.consult.Entity.Consult;
import com.example.career.domain.consult.Repository.ConsultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ConsultController {
    private final ConsultRepository consultRepository;
    @GetMapping ("test/get-consult")
    public List<Consult> consultTest(){
        return consultRepository.findAll();
    }
}
