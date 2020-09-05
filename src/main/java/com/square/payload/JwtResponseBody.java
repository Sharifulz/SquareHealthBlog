package com.square.payload;

import java.util.Date;

public class JwtResponseBody {
	
	public String token;
	
	public Date date;
	
	public JwtResponseBody(String token,Date date){
        this.token=token;
        this.date=date;
    }
    public JwtResponseBody(String token){
        this.token=token;
    }
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    
}
