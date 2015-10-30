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
import org.backpacker4.bean.Typeinfo;
import org.backpacker4.test.TypeinfoFactoryForTest;

//--- Services 
import org.backpacker4.business.service.TypeinfoService;


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
public class TypeinfoControllerTest {
	
	@InjectMocks
	private TypeinfoController typeinfoController;
	@Mock
	private TypeinfoService typeinfoService;
	@Mock
	private MessageHelper messageHelper;
	@Mock
	private MessageSource messageSource;

	private TypeinfoFactoryForTest typeinfoFactoryForTest = new TypeinfoFactoryForTest();


	private void givenPopulateModel() {
	}

	@Test
	public void list() {
		// Given
		Model model = new ExtendedModelMap();
		
		List<Typeinfo> list = new ArrayList<Typeinfo>();
		when(typeinfoService.findAll()).thenReturn(list);
		
		// When
		String viewName = typeinfoController.list(model);
		
		// Then
		assertEquals("typeinfo/list", viewName);
		Map<String,?> modelMap = model.asMap();
		assertEquals(list, modelMap.get("list"));
	}
	
	@Test
	public void formForCreate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		// When
		String viewName = typeinfoController.formForCreate(model);
		
		// Then
		assertEquals("typeinfo/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertNull(((Typeinfo)modelMap.get("typeinfo")).getId());
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/typeinfo/create", modelMap.get("saveAction"));
		
	}
	
	@Test
	public void formForUpdate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Typeinfo typeinfo = typeinfoFactoryForTest.newTypeinfo();
		Long id = typeinfo.getId();
		when(typeinfoService.findById(id)).thenReturn(typeinfo);
		
		// When
		String viewName = typeinfoController.formForUpdate(model, id);
		
		// Then
		assertEquals("typeinfo/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertEquals(typeinfo, (Typeinfo) modelMap.get("typeinfo"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/typeinfo/update", modelMap.get("saveAction"));
		
	}
	
	@Test
	public void createOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		Typeinfo typeinfo = typeinfoFactoryForTest.newTypeinfo();
		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		Typeinfo typeinfoCreated = new Typeinfo();
		when(typeinfoService.create(typeinfo)).thenReturn(typeinfoCreated); 
		
		// When
		String viewName = typeinfoController.create(typeinfo, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/typeinfo/form/"+typeinfo.getId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(typeinfoCreated, (Typeinfo) modelMap.get("typeinfo"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void createBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Typeinfo typeinfo = typeinfoFactoryForTest.newTypeinfo();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = typeinfoController.create(typeinfo, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("typeinfo/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(typeinfo, (Typeinfo) modelMap.get("typeinfo"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/typeinfo/create", modelMap.get("saveAction"));
		
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

		Typeinfo typeinfo = typeinfoFactoryForTest.newTypeinfo();
		
		Exception exception = new RuntimeException("test exception");
		when(typeinfoService.create(typeinfo)).thenThrow(exception);
		
		// When
		String viewName = typeinfoController.create(typeinfo, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("typeinfo/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(typeinfo, (Typeinfo) modelMap.get("typeinfo"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/typeinfo/create", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "typeinfo.error.create", exception);
		
	}

	@Test
	public void updateOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		Typeinfo typeinfo = typeinfoFactoryForTest.newTypeinfo();
		Long id = typeinfo.getId();

		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		Typeinfo typeinfoSaved = new Typeinfo();
		typeinfoSaved.setId(id);
		when(typeinfoService.update(typeinfo)).thenReturn(typeinfoSaved); 
		
		// When
		String viewName = typeinfoController.update(typeinfo, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/typeinfo/form/"+typeinfo.getId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(typeinfoSaved, (Typeinfo) modelMap.get("typeinfo"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void updateBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Typeinfo typeinfo = typeinfoFactoryForTest.newTypeinfo();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = typeinfoController.update(typeinfo, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("typeinfo/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(typeinfo, (Typeinfo) modelMap.get("typeinfo"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/typeinfo/update", modelMap.get("saveAction"));
		
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

		Typeinfo typeinfo = typeinfoFactoryForTest.newTypeinfo();
		
		Exception exception = new RuntimeException("test exception");
		when(typeinfoService.update(typeinfo)).thenThrow(exception);
		
		// When
		String viewName = typeinfoController.update(typeinfo, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("typeinfo/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(typeinfo, (Typeinfo) modelMap.get("typeinfo"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/typeinfo/update", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "typeinfo.error.update", exception);
		
	}
	

	@Test
	public void deleteOK() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		Typeinfo typeinfo = typeinfoFactoryForTest.newTypeinfo();
		Long id = typeinfo.getId();
		
		// When
		String viewName = typeinfoController.delete(redirectAttributes, id);
		
		// Then
		verify(typeinfoService).delete(id);
		assertEquals("redirect:/typeinfo", viewName);
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"delete.ok"));
	}

	@Test
	public void deleteException() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		Typeinfo typeinfo = typeinfoFactoryForTest.newTypeinfo();
		Long id = typeinfo.getId();
		
		Exception exception = new RuntimeException("test exception");
		doThrow(exception).when(typeinfoService).delete(id);
		
		// When
		String viewName = typeinfoController.delete(redirectAttributes, id);
		
		// Then
		verify(typeinfoService).delete(id);
		assertEquals("redirect:/typeinfo", viewName);
		Mockito.verify(messageHelper).addException(redirectAttributes, "typeinfo.error.delete", exception);
	}
	
	
}
