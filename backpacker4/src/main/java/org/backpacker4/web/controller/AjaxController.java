package org.backpacker4.web.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.backpacker4.bean.Appuser;
import org.backpacker4.business.service.AppuserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;


@Controller
@RequestMapping("/Ajax")
public class AjaxController {
	
	//--- Main entity service
	@Resource
    private AppuserService appuserService; // Injected by Spring

	@RequestMapping(value = "/CheckUsername", method = RequestMethod.GET)
	@ResponseBody
	public boolean isValidUsername (HttpServletResponse response, 
			@RequestParam(value = "name", required = false) String name) {
		System.out.println("function isValidUsername entered...");
		List<Appuser> allAppusers = appuserService.findAll();
		for (Appuser appuser : allAppusers) {
			if (appuser.getUsername().equals(name))
				return false;
		}
		return true;
	}
}
