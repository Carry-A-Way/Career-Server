package com.example.career.global.valid;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
//TODO: jwt 세션
public class ValidRespDto {
    public ValidRespDto(boolean success) {
        this.success = success;
        if(success) { //true
            this.status = 200;
        }else {
            this.status = 400;
        }
    }
    public ValidRespDto(Object temp) {
        if(temp == null){
            this.success = false;
            this.status = 400;
        } else {
            this.success = true;
            this.status = 200;
        }
    }
    private boolean success;
    private int status;
    private Object validRef;
    private String jwt;
}
