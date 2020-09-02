package com.square.common;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.square.dao.IUsersDao;
import com.square.model.UsersModel;

@Component
public class CommandLineAppStartupRunner implements CommandLineRunner {

	@Autowired
	ICommonService commonService;
	
	@Autowired
	IUsersDao userDao;
	
    @Override
    public void run(String...args) throws Exception {
    	UsersModel admin = createDefaultAdmin();
    	System.out.println("Admin username ----------------> "+ admin.getUserName());
    	System.out.println("Admin password----------------> "+ admin.getPassword());
    //	System.out.println("Note : Copy the password, decode it in online from decode base64, then remove the added salt [sqr] from the begining, then try to login from browser. ");
    }
    
    public UsersModel createDefaultAdmin() {
		
		String userName =  RandomStringUtils.randomAlphanumeric(5).toUpperCase();
		String password =  RandomStringUtils.randomAlphanumeric(10).toUpperCase();
		
		//String adminPassword = commonService.encodeString(password, "sqr");
		
		UsersModel admin = new UsersModel();
		admin.setUserName(userName);
		admin.setActive(true);
		admin.setRoles("ROLE_ADMIN"); 
		admin.setPassword(password);
		admin.setCreatedBy("SYSTEM");
		
		try {
			List<UsersModel> users = userDao.findByCreatedByAndRoles("SYSTEM", "ROLE_ADMIN");
			System.out.println("QUERY RESULT FOR SYSTEM ADMIN ----------- "+ users.size());
			UsersModel createdAdmin = null;
			if (users!=null && users.size()>0) { // already exists system admin
				System.out.println("ALREADY SYSTEM ADMIN EXISTS");
				int deletedRow = userDao.removeSystemGeneratedAdmin("SYSTEM", "ROLE_ADMIN");
				if (deletedRow>0) {
					createdAdmin = userDao.save(admin);
				}
				
			}else {
				System.out.println("NO SYSTEM ADMIN EXISTS");
				createdAdmin = userDao.save(admin);
			}
			return createdAdmin;
		} catch (Exception e) {e.printStackTrace();}
		return null;
	}
}
