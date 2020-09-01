package com.square.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.square.common.ICommonService;
import com.square.jwtutil.JwtUtil;
import com.square.payload.AuthenticationRequest;
import com.square.payload.AuthenticationResponse;
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
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authReqViewModel) throws Exception {
		
		try {
			String password = commonService.decodeString(authReqViewModel.getPassword(), "");
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					authReqViewModel.getUserName(), password));
		} catch (BadCredentialsException e) {
			throw new Exception("Incorrect username or password given ----->" + e);
		}
		
		final UserDetails userDetails = userDetailsService.loadUserByUsername(authReqViewModel.getUserName());
		final String jwt = jwtUtil.generateToken(userDetails);
		
		return ResponseEntity.ok(new AuthenticationResponse(jwt));
	}
	
	
	
	
}
