package com.example.career.domain.user.Dto;

import com.example.career.domain.user.Entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.tomcat.util.http.fileupload.IOUtils;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignUpReqDto {
    private String name;
    private String username;
    private String telephone;
    private String password;
    private String profileImg;
    private Boolean gender;
    private String nickname;


    public User toUserEntity(){
        return User.builder().name(name)
                .username(username)
                .nickname(nickname)
                .password(password)
                .telephone(telephone)
                .gender(gender)
                .role("USER")
                .profileImg(imageToByteArray(profileImg))
                .authType(1)
                .build();
    }
    public byte[] imageToByteArray (String filePath)
    {
        byte[] returnValue = null;

        ByteArrayOutputStream baos = null;
        FileInputStream fis = null;

        try
        {
            baos = new ByteArrayOutputStream();
            fis  = new FileInputStream(filePath);

            byte[] buf = new byte[1024];
            int read = 0;

            while ((read=fis.read(buf, 0, buf.length)) != -1)
            {
                baos.write(buf, 0, read);
            }

            returnValue = baos.toByteArray();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (baos != null)
            {
                try {
                    baos.close();
                } catch (IOException e) {
                    System.out.println("파일이 존재하지 않습니다.");
                }
            }
            if (fis != null)
            {
                try {
                    fis.close();
                } catch (IOException e) {
                    System.out.println("파일이 존재하지 않습니다.");
                }
            }
        }

        return returnValue;
    }

}
