package com.square.security.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.square.common.ICommonService;
import com.square.dao.IUsersDao;
import com.square.model.UsersModel;

/*
 *  Step 2: Make a class that extends UserDetailsService so that you can call Jpa
 *  to authenticate user and return UserDetails 
 *  from org.springframework.security.core.userdetails.UserDetails
 */

@Service
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	IUsersDao userRepo;
	
	@Autowired
	ICommonService commonService;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<UsersModel> user = userRepo.findByUserName(username);
		//String password = commonService.decodeString(user.get().getPassword(), "sqr");
		user.get().setPassword(user.get().getPassword());
		user.orElseThrow(()->new UsernameNotFoundException("Not Found : "+ username));
		return user.map(MyUserDetails::new).get();
	}

}
