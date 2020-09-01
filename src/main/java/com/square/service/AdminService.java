package com.square.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.square.common.ICommonService;
import com.square.dao.IBlogPostDao;
import com.square.dao.IUsersDao;
import com.square.model.UsersModel;
import com.square.payload.CommonRequestViewModel;

@Service
public class AdminService implements IAdminService {

	@Autowired
	ICommonService commonService;
	
	@Autowired
	IUsersDao userDao;
	
	@Autowired
	IBlogPostDao blogDao;
	
	@Override
	public Map<String, Object> createNewAdmin(CommonRequestViewModel viewModel) {
		Map<String, Object> data = new HashMap<>();
		
		List<String> message = new ArrayList<>();
		String userName = commonService.getCurrentUser();
		UsersModel admin = null;
		UsersModel user = userDao.findByUserName(userName).get();
		//Minimum value for creating user is userName, personName, password
		if (user.getRoles().equals("ROLE_ADMIN")) {
			Map<String, Object> map = validateViewModelDataToCreateAdmin(viewModel);
			if (map.get("isValid").equals(true)) {
				//------------Ready to save
				UsersModel createdAdmin = new UsersModel();
				createdAdmin.setUserName(viewModel.getUserName());
				createdAdmin.setFullName(viewModel.getPersonName());
				createdAdmin.setPassword(viewModel.getPassword());
				createdAdmin.setRoles("ROLE_ADMIN");
				createdAdmin.setActive(true);
				
				admin = userDao.save(createdAdmin);
				message.add("New admin created.");
				
				data.put("data", admin);
				data.put("message", message);
				data.put("responseCode", "200");
			}else {
				data.put("data", admin);
				data.put("message", map.get("message"));
				data.put("responseCode", "412");
			}
		}else {
			message.add("You do not have permission to add admin");
			
			data.put("data", admin);
			data.put("message", message);
			data.put("responseCode", "412");
		}
		
		return data;
	}
	
	public Map<String, Object> validateViewModelDataToCreateAdmin(CommonRequestViewModel viewModel) {
		
		Map<String, Object> data = new HashMap<>();
		List<String> message = new ArrayList<>();
		boolean isValid = true;
		
		if (viewModel.getUserName()==null || viewModel.getUserName().isEmpty()) {
			isValid = false;
			message.add("userName is required, but not found");
		}else {
			List<UsersModel> users = userDao.getByUserName(viewModel.getUserName());
			if (users.size()>0) {
				isValid = false;
				message.add("userName already exists");
			}
		}
		if (viewModel.getPersonName()==null || viewModel.getPersonName().isEmpty()) {
			isValid = false;
			message.add("fullName is required, but not found");
		}
		
		if (viewModel.getPassword()==null || viewModel.getPassword().isEmpty()) {
			isValid = false;
			message.add("password is required, but not found");
		}//------------ Check if password is less than 10

		data.put("isValid", isValid);
		data.put("message", message);
		
		return data;
	}

	@Override
	public Map<String, Object> enableBlogger(CommonRequestViewModel viewModel) {
		Map<String, Object> data = new HashMap<>();
		
		List<String> message = new ArrayList<>();
		String userName = commonService.getCurrentUser();
		UsersModel user = userDao.findByUserName(userName).get();
		if (user.getRoles().equals("ROLE_ADMIN")) {
				if (viewModel.getIds()!=null || viewModel.getIds().length>0) {
					for (String id : viewModel.getIds()) {
						try {
							userDao.activateUser(true, Integer.valueOf(id));
							message.add("Enabled user, ID: "+id);
						} catch (Exception e) {
							e.printStackTrace();
							message.add("Exception Details: "+e.getLocalizedMessage());
						}
						
					}
					data.put("data", "");
					data.put("message", message);
					data.put("responseCode", "200");
				}
		}else {
			message.add("You do not have permission to enable blogger");
			
			data.put("data", "");
			data.put("message", message);
			data.put("responseCode", "412");
		}
		
		return data;
	}
	
	@Override
	public Map<String, Object> disableBlogger(CommonRequestViewModel viewModel) {
		Map<String, Object> data = new HashMap<>();
		
		List<String> message = new ArrayList<>();
		String userName = commonService.getCurrentUser();
		UsersModel user = userDao.findByUserName(userName).get();
		if (user.getRoles().equals("ROLE_ADMIN")) {
				if (viewModel.getIds()!=null || viewModel.getIds().length>0) {
					for (String id : viewModel.getIds()) {
						try {
							userDao.activateUser(false, Integer.valueOf(id));
							message.add("Disabled user, ID: "+id);
						} catch (Exception e) {
							e.printStackTrace();
							message.add("Exception Details: "+e.getLocalizedMessage());
						}
						
					}
					data.put("data", "");
					data.put("message", message);
					data.put("responseCode", "200");
				}
		}else {
			message.add("You do not have permission to disable blogger");
			
			data.put("data", "");
			data.put("message", message);
			data.put("responseCode", "412");
		}
		
		return data;
	}
	
	@Override
	public Map<String, Object> approvePost(CommonRequestViewModel viewModel) {
		Map<String, Object> data = new HashMap<>();
		
		List<String> message = new ArrayList<>();
		String userName = commonService.getCurrentUser();
		UsersModel user = userDao.findByUserName(userName).get();
		if (user.getRoles().equals("ROLE_ADMIN")) {
				if (viewModel.getIds()!=null || viewModel.getIds().length>0) {
					for (String id : viewModel.getIds()) {
						try {
							blogDao.approveBlog(true, Integer.valueOf(id));
							message.add("Approved post, ID: "+id);
						} catch (Exception e) {
							e.printStackTrace();
							message.add("Exception Details: "+e.getLocalizedMessage());
						}
						
					}
					data.put("data", "");
					data.put("message", message);
					data.put("responseCode", "200");
				}
		}else {
			message.add("You do not have permission to approve post");
			
			data.put("data", "");
			data.put("message", message);
			data.put("responseCode", "412");
		}
		
		return data;
	}
	
	@Override
	public Map<String, Object> removePost(CommonRequestViewModel viewModel) {
		Map<String, Object> data = new HashMap<>();
		
		List<String> message = new ArrayList<>();
		String userName = commonService.getCurrentUser();
		UsersModel user = userDao.findByUserName(userName).get();
		if (user.getRoles().equals("ROLE_ADMIN")) {
				if (viewModel.getIds()!=null || viewModel.getIds().length>0) {
					for (String id : viewModel.getIds()) {
						try {
							blogDao.removePost(Integer.valueOf(id));
							message.add("Removed post, ID: "+id);
						} catch (Exception e) {
							e.printStackTrace();
							message.add("Exception Details: "+e.getLocalizedMessage());
						}
						
					}
					data.put("data", "");
					data.put("message", message);
					data.put("responseCode", "200");
				}
		}else {
			message.add("You do not have permission to remove post");
			
			data.put("data", "");
			data.put("message", message);
			data.put("responseCode", "412");
		}
		
		return data;
	}
	
	

}
