package com.square.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.square.model.LikesDislikesModel;


@Repository
@Transactional
public interface ILikesDislikesDao extends JpaRepository<LikesDislikesModel, Integer>{

	List<LikesDislikesModel> findByAction(String action);

	
	List<LikesDislikesModel> findByUserIdAndPostIdAndAction(int userId, int postId, String action);
	
	@Modifying
	@Query("DELETE FROM LikesDislikesModel WHERE userId=:userId AND postId=:postId AND action=:action ")
	int deleteCommentsDetail(@Param("userId") int userId , @Param("postId") int postId, @Param("action") String action);
	
}
