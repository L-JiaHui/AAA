package com.xkdgx.controller;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.URLEncoder;

@Controller
@RequestMapping("up")
public class UpController {
    @RequestMapping("upload")
    public String upload(MultipartFile file, HttpSession session) throws IOException {
        String originalFilename = file.getOriginalFilename();
        String realPath = session.getServletContext().getRealPath("/file");
        File dir = new File(realPath);
        if (!dir.exists()){
            dir.mkdir();
        }
        /// file.transferTo(new File(realPath+"/"+originalFilename));

        InputStream inputStream = file.getInputStream();
        FileOutputStream fileOutputStream = new FileOutputStream(realPath + "/" + originalFilename);
        int len = 0;
        byte[] bytes = new byte[1024];
        while ((len=inputStream.read(bytes))!=-1){
            fileOutputStream.write(bytes,0,len);
        }
        inputStream.close();
        fileOutputStream.close();

        return "index";
    }

    @RequestMapping("download")
    public void download(String name,HttpServletResponse response,HttpSession session) throws IOException {
        String realPath = session.getServletContext().getRealPath("/file");
        File file = new File(realPath + "/" + name);
        response.setHeader("content-disposition","attachment;fileName ="+ URLEncoder.encode(name,"utf-8"));
        ServletOutputStream outputStream = response.getOutputStream();
        byte[] bytes = FileUtils.readFileToByteArray(file);
        outputStream.write(bytes);
    }
}
