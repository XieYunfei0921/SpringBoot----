package com.example.demo.dao;

import com.example.demo.entity.Person;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

/**
 * 使用注解@RepositoryDefinition 进行仓库接口的定义
 * @RepositoryRestResource 注解直接使用Spring MVC在/people创建REST后台
 */

@RepositoryRestResource(collectionResourceRel = "people",path = "people")
public interface PersonRepository extends PagingAndSortingRepository<Person,Integer> {
	// 使用方法名称构建查询
	List<Person> findByName(@Param("name") String name);
	// 按照name和age查询
//	List<Person> findByNameAndAge(String name,int age);
	// 按照addr进行去重查询
//	List<Person> findDistinctByAddress(String address);
	// 忽略大小按照name查询
//	List<Person> findByNameIgnoreCase(String name);
	// 使用双关键词查询
//	List<Person> findByNameAndAddressIgnoreCase(String name,String address);
	// 使用order by查询
//	List<Person> findByNameOrderByAgeAsc(String name,int age);

//	List<Person> findByNameOrderByAddressDesc(String name,String address);
	// 使用参数表达式进行查询(内部属性的嵌套)
//	List<Person> findByAddress_ZipCode(String zipCode);

//	Page<Person> findByName(String name, Pageable pageable);
//
//	Slice<Person> findByAddress(String address,Pageable pageable);

//	List<Person> findByName(String name, Sort sort);

//	List<Person> findBy_Name(String name,Pageable pageable);

//	Person findFirstByOrderByNameAsc();

//	Person findTopByOrderByAgeDesc();

//	Page<Person> findAll(Pageable pageable);
	// 查询first 10
//	Page<Person> queryFirst10ByName(String name,Pageable pageable);
	// top3
//	Slice<Person> findTop3ByName(String name,Pageable pageable);
	// 查询first 10
//	List<Person> findFirst10ByName(String name,Sort sort);
	// 查询top10
//	List<Person> findTop10ByName(String name,Pageable pageable);
}
