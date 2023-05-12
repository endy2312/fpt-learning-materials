package com.fpt.g2.repository;

import com.fpt.g2.entity.User;
import org.apache.tomcat.util.http.fileupload.util.LimitedInputStream;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.email = :email or u.username = :email or u.mobile =:email")
    User findByEmail(@Param("email") String email);

    @Query("SELECT u FROM User u WHERE u.email = :email or u.username = :email or u.mobile =:email and u.deleteFlag = false")
    User findByEmailOrUsernameOrMobile(@Param("email") String email);

    @Query("SELECT u FROM User u WHERE u.email = :email or u.username = :email or u.mobile =:email and u.password =:password  and u.deleteFlag = false")
    User findByEmailOrUsernameOrMobileAndPassword(@Param("email") String email,String password);
    User findUserByUsernameAndDeleteFlagIsFalse(String username);

    User findUserByMobileAndDeleteFlagFalse(String mobile);
    User findUserByEmailAndDeleteFlagFalse(String email);

    @Query("select u from User u where (:keyword is null or u.fullName like :keyword or u.email like :keyword or u.mobile like :keyword) and (:status is null or u.deleteFlag = :status)")
    Page findAll(@Param("keyword") String keyword, @Param("status") Boolean status, Pageable pageable);


    User findUserByMobile(String mobile);
    User findUserByEmail(String email);

    @Query("select u from User u join u.roles ur where ur.setting.type = 'User Role' and ur.setting.title = 'Syllabus Designer'")
    List<User> findDesigner();

    @Query("select u from User u join u.roles ur where ur.setting.type = 'User Role' and ur.setting.title = 'Syllabus Reviewer'")
    List<User> findReviewer();
}
