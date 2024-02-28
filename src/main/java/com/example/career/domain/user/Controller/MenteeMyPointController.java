package com.example.career.domain.user.Controller;


import com.example.career.domain.user.Dto.MyPointDto;
import com.example.career.domain.user.Entity.User;
import com.example.career.domain.user.Service.StudentDetailService;
import com.example.career.global.annotation.Authenticated;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("mentee/my_point")
@RequiredArgsConstructor
public class MenteeMyPointController {

    private final StudentDetailService studentDetailService;

    @Authenticated
    @PostMapping("add_delta")
    public ResponseEntity<Object> addDelta(@RequestBody MyPointDto myPointDto, HttpServletRequest request)  throws Exception {
        User userAop = (User) request.getAttribute("user");
        Long id = userAop.getId();
        int res = studentDetailService.updateMyPoint(myPointDto.getDelta(), id);

        return ResponseEntity.ok(res);
    }
}
