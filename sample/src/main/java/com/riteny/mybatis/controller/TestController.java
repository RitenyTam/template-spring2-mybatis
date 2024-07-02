package com.riteny.mybatis.controller;

import com.mybatisflex.core.query.QueryWrapper;
import com.riteny.mybatis.entity.Account;
import com.riteny.mybatis.mapper.AccountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/test/mybatis")
public class TestController {

    @Autowired
    private AccountMapper accountMapper;

    @GetMapping("/")
    public void test() {
        QueryWrapper queryWrapper = new QueryWrapper()
                .select().eq("id", 1);

        Account create = new Account();
        create.setAccountName(UUID.randomUUID().toString());
        create.setPassword(UUID.randomUUID().toString());
        accountMapper.insertSelective(create);


        Account account = accountMapper.selectOneByQuery(queryWrapper);
        List<Account> accounts = accountMapper.selectAll();


        System.out.println(account);
        System.out.println(accounts);
    }
}
