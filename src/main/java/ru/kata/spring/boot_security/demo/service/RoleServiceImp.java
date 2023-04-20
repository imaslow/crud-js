package ru.kata.spring.boot_security.demo.service;


import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.dao.RoleDaoImp;
import ru.kata.spring.boot_security.demo.models.Role;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class RoleServiceImp implements RoleService{
    private final RoleDaoImp roleDaoImp;

    public RoleServiceImp(RoleDaoImp roleDaoImp) {
        this.roleDaoImp = roleDaoImp;
    }

    public Role getRoleById(Long id) {
        return roleDaoImp.getRoleById(id);
    }
    public List<Role> getRolesList() {
        return roleDaoImp.getRolesList();
    }
}
