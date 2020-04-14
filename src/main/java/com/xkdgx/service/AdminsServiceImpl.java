package com.xkdgx.service;

import com.xkdgx.dao.AdminsDAO;
import com.xkdgx.entity.Admins;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional(rollbackFor = Exception.class)
public class AdminsServiceImpl implements AdminsService {
    @Autowired
    private AdminsDAO adminsDAO;
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<Admins> showAll() {
        return adminsDAO.showAll();
    }
}
