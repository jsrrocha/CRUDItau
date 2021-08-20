package com.tcc.CRUDItau.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.tcc.CRUDItau.model.Comment;
import com.tcc.CRUDItau.model.User;

public interface CommentRepository extends CrudRepository<Comment, Long>{ 
	public List<Comment> findByNotificationActiveAndUserReceivedOrderByIdAsc(Boolean notificationActive,User user);
	public List<Comment> findAllByOrderByIdAsc(); 
}
