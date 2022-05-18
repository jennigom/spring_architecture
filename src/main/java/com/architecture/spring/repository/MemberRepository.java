package com.architecture.spring.repository;

import com.architecture.spring.entity.MemberEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * member 테이블 repository
 * @author JENNI
 * @version 1.0
 * @since 2022.05.09
 */

public interface MemberRepository extends CrudRepository<MemberEntity, String> {
    Optional<MemberEntity> findByRegNo(String regNo);

    @Query(value="select * from member m where m.name like %:name%", nativeQuery = true)
    List<MemberEntity> findByNameContaining(@Param("name") String name);
}
