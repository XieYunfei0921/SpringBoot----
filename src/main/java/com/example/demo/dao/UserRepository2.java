package com.example.demo.dao;

import com.example.demo.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.querydsl.QSort;

/**
 * 在之前的例子中，定义了一个示例接口，用于所有的domain类。并保留了@findById和@save方法，
 * 这两个方法会被路由到基本仓库的实现中。从而作为存储的选择，因为匹配了@CrudRepository的特征。
 * 所以@UserRepository 现在使用中间接口@MyBaseRepository的功能，可以保存用户信息@sava,且可以
 * 根据ID查找用户,且可以根据地址触发查找.
 */
public interface UserRepository2 extends MyBaseRepository<User,Long>{
	User findByAddress(String addr);

}
