package com.riteny.mybatis.mapper;


import com.mybatisflex.core.BaseMapper;
import com.riteny.mybatis.entity.Account;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 映射层。
 *
 * @author riten
 * @since 2024-06-13
 */
@Mapper
public interface AccountMapper extends BaseMapper<Account> {
    List<Account> selectAll();
}
