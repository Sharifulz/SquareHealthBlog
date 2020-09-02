package com.square.dao;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.square.model.UsersModel;


@Repository
@Transactional
public interface IUsersDao extends CrudRepository<UsersModel, Integer> {
	
	Optional<UsersModel> findByUserName(String userName);
	
	@Query("FROM UsersModel WHERE userName =:userName ")
	List<UsersModel> getByUserName(@Param("userName") String userName);
	
	List<UsersModel> findByCreatedByAndRoles(@Param("createdBy") String createdBy, @Param("roles") String roles);
	
	UsersModel findById(int id);
	List<UsersModel> findByRolesAndActiveFalseOrderBySignupDate(String roles);
	List<UsersModel> findByRolesAndActiveTrueOrderBySignupDate(String roles);
	
	
	@Modifying(clearAutomatically = true)
    @Query("UPDATE UsersModel SET active =:active WHERE id =:id")
    int activateUser(@Param("active") boolean active, @Param("id") int id);
	
	@Modifying(clearAutomatically = true)
    @Query("DELETE FROM UsersModel WHERE createdBy=:createdBy AND roles=:roles ")
    int removeSystemGeneratedAdmin(@Param("createdBy") String createdBy, @Param("roles") String roles);
	
}
