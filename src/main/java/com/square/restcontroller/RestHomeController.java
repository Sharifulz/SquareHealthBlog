package com.square.restcontroller;


import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.square.common.ICommonService;
import com.square.jwtutil.JwtUtil;
import com.square.payload.JwtRequestBody;
import com.square.payload.JwtResponseBody;
import com.square.security.service.MyUserDetailsService;


@RestController
public class RestHomeController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired 
	private MyUserDetailsService userDetailsService;
	
	@Autowired 
	private JwtUtil jwtUtil;
	
	@Autowired
	ICommonService commonService;
	
	@PostMapping("/authenticate")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequestBody authReqViewModel) throws Exception {
		
		try {
			//String password = commonService.decodeString(authReqViewModel.getPassword(), "");
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					authReqViewModel.getUserName(), authReqViewModel.getPassword()));
		} catch (BadCredentialsException e) {
			throw new Exception("Incorrect username or password given ----->" + e);
		}
		
		final UserDetails userDetails = userDetailsService.loadUserByUsername(authReqViewModel.getUserName());
		final String jwt = jwtUtil.generateToken(userDetails);
		final Date date = jwtUtil.getExpirationDateFromToken(jwt);
				
		
		return ResponseEntity.ok(new JwtResponseBody(jwt,date));
	}
	
	
	
	
}
