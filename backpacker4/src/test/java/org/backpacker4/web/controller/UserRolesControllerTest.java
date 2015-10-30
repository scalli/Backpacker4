package org.backpacker4.web.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

//--- Entities
import org.backpacker4.bean.UserRoles;
import org.backpacker4.bean.Appuser;
import org.backpacker4.test.UserRolesFactoryForTest;
import org.backpacker4.test.AppuserFactoryForTest;

//--- Services 
import org.backpacker4.business.service.UserRolesService;
import org.backpacker4.business.service.AppuserService;

//--- List Items 
import org.backpacker4.web.listitem.AppuserListItem;

import org.backpacker4.web.common.Message;
import org.backpacker4.web.common.MessageHelper;
import org.backpacker4.web.common.MessageType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.MessageSource;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RunWith(MockitoJUnitRunner.class)
public class UserRolesControllerTest {
	
	@InjectMocks
	private UserRolesController userRolesController;
	@Mock
	private UserRolesService userRolesService;
	@Mock
	private MessageHelper messageHelper;
	@Mock
	private MessageSource messageSource;
	@Mock
	private AppuserService appuserService; // Injected by Spring

	private UserRolesFactoryForTest userRolesFactoryForTest = new UserRolesFactoryForTest();
	private AppuserFactoryForTest appuserFactoryForTest = new AppuserFactoryForTest();

	List<Appuser> appusers = new ArrayList<Appuser>();

	private void givenPopulateModel() {
		Appuser appuser1 = appuserFactoryForTest.newAppuser();
		Appuser appuser2 = appuserFactoryForTest.newAppuser();
		List<Appuser> appusers = new ArrayList<Appuser>();
		appusers.add(appuser1);
		appusers.add(appuser2);
		when(appuserService.findAll()).thenReturn(appusers);

	}

	@Test
	public void list() {
		// Given
		Model model = new ExtendedModelMap();
		
		List<UserRoles> list = new ArrayList<UserRoles>();
		when(userRolesService.findAll()).thenReturn(list);
		
		// When
		String viewName = userRolesController.list(model);
		
		// Then
		assertEquals("userRoles/list", viewName);
		Map<String,?> modelMap = model.asMap();
		assertEquals(list, modelMap.get("list"));
	}
	
	@Test
	public void formForCreate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		// When
		String viewName = userRolesController.formForCreate(model);
		
		// Then
		assertEquals("userRoles/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertNull(((UserRoles)modelMap.get("userRoles")).getIduserRoles());
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/userRoles/create", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<AppuserListItem> appuserListItems = (List<AppuserListItem>) modelMap.get("listOfAppuserItems");
		assertEquals(2, appuserListItems.size());
		
	}
	
	@Test
	public void formForUpdate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		UserRoles userRoles = userRolesFactoryForTest.newUserRoles();
		Integer iduserRoles = userRoles.getIduserRoles();
		when(userRolesService.findById(iduserRoles)).thenReturn(userRoles);
		
		// When
		String viewName = userRolesController.formForUpdate(model, iduserRoles);
		
		// Then
		assertEquals("userRoles/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertEquals(userRoles, (UserRoles) modelMap.get("userRoles"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/userRoles/update", modelMap.get("saveAction"));
		
		List<AppuserListItem> appuserListItems = (List<AppuserListItem>) modelMap.get("listOfAppuserItems");
		assertEquals(2, appuserListItems.size());
		
	}
	
	@Test
	public void createOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		UserRoles userRoles = userRolesFactoryForTest.newUserRoles();
		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		UserRoles userRolesCreated = new UserRoles();
		when(userRolesService.create(userRoles)).thenReturn(userRolesCreated); 
		
		// When
		String viewName = userRolesController.create(userRoles, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/userRoles/form/"+userRoles.getIduserRoles(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(userRolesCreated, (UserRoles) modelMap.get("userRoles"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void createBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		UserRoles userRoles = userRolesFactoryForTest.newUserRoles();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = userRolesController.create(userRoles, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("userRoles/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(userRoles, (UserRoles) modelMap.get("userRoles"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/userRoles/create", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<AppuserListItem> appuserListItems = (List<AppuserListItem>) modelMap.get("listOfAppuserItems");
		assertEquals(2, appuserListItems.size());
		
	}

	@Test
	public void createException() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(false);

		UserRoles userRoles = userRolesFactoryForTest.newUserRoles();
		
		Exception exception = new RuntimeException("test exception");
		when(userRolesService.create(userRoles)).thenThrow(exception);
		
		// When
		String viewName = userRolesController.create(userRoles, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("userRoles/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(userRoles, (UserRoles) modelMap.get("userRoles"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/userRoles/create", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "userRoles.error.create", exception);
		
		@SuppressWarnings("unchecked")
		List<AppuserListItem> appuserListItems = (List<AppuserListItem>) modelMap.get("listOfAppuserItems");
		assertEquals(2, appuserListItems.size());
		
	}

	@Test
	public void updateOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		UserRoles userRoles = userRolesFactoryForTest.newUserRoles();
		Integer iduserRoles = userRoles.getIduserRoles();

		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		UserRoles userRolesSaved = new UserRoles();
		userRolesSaved.setIduserRoles(iduserRoles);
		when(userRolesService.update(userRoles)).thenReturn(userRolesSaved); 
		
		// When
		String viewName = userRolesController.update(userRoles, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/userRoles/form/"+userRoles.getIduserRoles(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(userRolesSaved, (UserRoles) modelMap.get("userRoles"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void updateBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		UserRoles userRoles = userRolesFactoryForTest.newUserRoles();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = userRolesController.update(userRoles, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("userRoles/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(userRoles, (UserRoles) modelMap.get("userRoles"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/userRoles/update", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<AppuserListItem> appuserListItems = (List<AppuserListItem>) modelMap.get("listOfAppuserItems");
		assertEquals(2, appuserListItems.size());
		
	}

	@Test
	public void updateException() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(false);

		UserRoles userRoles = userRolesFactoryForTest.newUserRoles();
		
		Exception exception = new RuntimeException("test exception");
		when(userRolesService.update(userRoles)).thenThrow(exception);
		
		// When
		String viewName = userRolesController.update(userRoles, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("userRoles/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(userRoles, (UserRoles) modelMap.get("userRoles"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/userRoles/update", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "userRoles.error.update", exception);
		
		@SuppressWarnings("unchecked")
		List<AppuserListItem> appuserListItems = (List<AppuserListItem>) modelMap.get("listOfAppuserItems");
		assertEquals(2, appuserListItems.size());
		
	}
	

	@Test
	public void deleteOK() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		UserRoles userRoles = userRolesFactoryForTest.newUserRoles();
		Integer iduserRoles = userRoles.getIduserRoles();
		
		// When
		String viewName = userRolesController.delete(redirectAttributes, iduserRoles);
		
		// Then
		verify(userRolesService).delete(iduserRoles);
		assertEquals("redirect:/userRoles", viewName);
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"delete.ok"));
	}

	@Test
	public void deleteException() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		UserRoles userRoles = userRolesFactoryForTest.newUserRoles();
		Integer iduserRoles = userRoles.getIduserRoles();
		
		Exception exception = new RuntimeException("test exception");
		doThrow(exception).when(userRolesService).delete(iduserRoles);
		
		// When
		String viewName = userRolesController.delete(redirectAttributes, iduserRoles);
		
		// Then
		verify(userRolesService).delete(iduserRoles);
		assertEquals("redirect:/userRoles", viewName);
		Mockito.verify(messageHelper).addException(redirectAttributes, "userRoles.error.delete", exception);
	}
	
	
}
