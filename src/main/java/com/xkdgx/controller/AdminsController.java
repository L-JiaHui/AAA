package com.xkdgx.controller;

import com.xkdgx.entity.Admins;
import com.xkdgx.service.AdminsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("admin")
public class AdminsController {
    @Autowired
    private AdminsService adminsService;
    @RequestMapping("showAll")
    public String showAll(HttpServletRequest request){
        List<Admins> admins = adminsService.showAll();
        request.setAttribute("admins",admins);
        return "index";
    }
}
