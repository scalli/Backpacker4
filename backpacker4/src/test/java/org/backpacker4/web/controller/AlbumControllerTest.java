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
import org.backpacker4.bean.Album;
import org.backpacker4.bean.Appuser;
import org.backpacker4.bean.Photo;
import org.backpacker4.test.AlbumFactoryForTest;
import org.backpacker4.test.AppuserFactoryForTest;
import org.backpacker4.test.PhotoFactoryForTest;

//--- Services 
import org.backpacker4.business.service.AlbumService;
import org.backpacker4.business.service.AppuserService;
import org.backpacker4.business.service.PhotoService;

//--- List Items 
import org.backpacker4.web.listitem.AppuserListItem;
import org.backpacker4.web.listitem.PhotoListItem;

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
public class AlbumControllerTest {
	
	@InjectMocks
	private AlbumController albumController;
	@Mock
	private AlbumService albumService;
	@Mock
	private MessageHelper messageHelper;
	@Mock
	private MessageSource messageSource;
	@Mock
	private AppuserService appuserService; // Injected by Spring
	@Mock
	private PhotoService photoService; // Injected by Spring

	private AlbumFactoryForTest albumFactoryForTest = new AlbumFactoryForTest();
	private AppuserFactoryForTest appuserFactoryForTest = new AppuserFactoryForTest();
	private PhotoFactoryForTest photoFactoryForTest = new PhotoFactoryForTest();

	List<Appuser> appusers = new ArrayList<Appuser>();
	List<Photo> photos = new ArrayList<Photo>();

	private void givenPopulateModel() {
		Appuser appuser1 = appuserFactoryForTest.newAppuser();
		Appuser appuser2 = appuserFactoryForTest.newAppuser();
		List<Appuser> appusers = new ArrayList<Appuser>();
		appusers.add(appuser1);
		appusers.add(appuser2);
		when(appuserService.findAll()).thenReturn(appusers);

		Photo photo1 = photoFactoryForTest.newPhoto();
		Photo photo2 = photoFactoryForTest.newPhoto();
		List<Photo> photos = new ArrayList<Photo>();
		photos.add(photo1);
		photos.add(photo2);
		when(photoService.findAll()).thenReturn(photos);

	}

	@Test
	public void list() {
		// Given
		Model model = new ExtendedModelMap();
		
		List<Album> list = new ArrayList<Album>();
		when(albumService.findAll()).thenReturn(list);
		
		// When
		String viewName = albumController.list(model);
		
		// Then
		assertEquals("album/list", viewName);
		Map<String,?> modelMap = model.asMap();
		assertEquals(list, modelMap.get("list"));
	}
	
	@Test
	public void formForCreate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		// When
		String viewName = albumController.formForCreate(model);
		
		// Then
		assertEquals("album/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertNull(((Album)modelMap.get("album")).getId());
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/album/create", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<AppuserListItem> appuserListItems = (List<AppuserListItem>) modelMap.get("listOfAppuserItems");
		assertEquals(2, appuserListItems.size());
		
		@SuppressWarnings("unchecked")
		List<PhotoListItem> photoListItems = (List<PhotoListItem>) modelMap.get("listOfPhotoItems");
		assertEquals(2, photoListItems.size());
		
	}
	
	@Test
	public void formForUpdate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Album album = albumFactoryForTest.newAlbum();
		Long id = album.getId();
		when(albumService.findById(id)).thenReturn(album);
		
		// When
		String viewName = albumController.formForUpdate(model, id);
		
		// Then
		assertEquals("album/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertEquals(album, (Album) modelMap.get("album"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/album/update", modelMap.get("saveAction"));
		
		List<AppuserListItem> appuserListItems = (List<AppuserListItem>) modelMap.get("listOfAppuserItems");
		assertEquals(2, appuserListItems.size());
		
	}
	
	@Test
	public void createOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		Album album = albumFactoryForTest.newAlbum();
		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		Album albumCreated = new Album();
		when(albumService.create(album)).thenReturn(albumCreated); 
		
		// When
		String viewName = albumController.create(album, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/album/form/"+album.getId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(albumCreated, (Album) modelMap.get("album"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void createBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Album album = albumFactoryForTest.newAlbum();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = albumController.create(album, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("album/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(album, (Album) modelMap.get("album"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/album/create", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<AppuserListItem> appuserListItems = (List<AppuserListItem>) modelMap.get("listOfAppuserItems");
		assertEquals(2, appuserListItems.size());
		
		@SuppressWarnings("unchecked")
		List<PhotoListItem> photoListItems = (List<PhotoListItem>) modelMap.get("listOfPhotoItems");
		assertEquals(2, photoListItems.size());
		
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

		Album album = albumFactoryForTest.newAlbum();
		
		Exception exception = new RuntimeException("test exception");
		when(albumService.create(album)).thenThrow(exception);
		
		// When
		String viewName = albumController.create(album, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("album/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(album, (Album) modelMap.get("album"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/album/create", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "album.error.create", exception);
		
		@SuppressWarnings("unchecked")
		List<AppuserListItem> appuserListItems = (List<AppuserListItem>) modelMap.get("listOfAppuserItems");
		assertEquals(2, appuserListItems.size());
		
		@SuppressWarnings("unchecked")
		List<PhotoListItem> photoListItems = (List<PhotoListItem>) modelMap.get("listOfPhotoItems");
		assertEquals(2, photoListItems.size());
		
	}

	@Test
	public void updateOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		Album album = albumFactoryForTest.newAlbum();
		Long id = album.getId();

		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		Album albumSaved = new Album();
		albumSaved.setId(id);
		when(albumService.update(album)).thenReturn(albumSaved); 
		
		// When
		String viewName = albumController.update(album, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/album/form/"+album.getId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(albumSaved, (Album) modelMap.get("album"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void updateBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Album album = albumFactoryForTest.newAlbum();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = albumController.update(album, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("album/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(album, (Album) modelMap.get("album"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/album/update", modelMap.get("saveAction"));
		
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

		Album album = albumFactoryForTest.newAlbum();
		
		Exception exception = new RuntimeException("test exception");
		when(albumService.update(album)).thenThrow(exception);
		
		// When
		String viewName = albumController.update(album, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("album/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(album, (Album) modelMap.get("album"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/album/update", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "album.error.update", exception);
		
		@SuppressWarnings("unchecked")
		List<AppuserListItem> appuserListItems = (List<AppuserListItem>) modelMap.get("listOfAppuserItems");
		assertEquals(2, appuserListItems.size());
		
	}
	

	@Test
	public void deleteOK() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		Album album = albumFactoryForTest.newAlbum();
		Long id = album.getId();
		
		// When
		String viewName = albumController.delete(redirectAttributes, id);
		
		// Then
		verify(albumService).delete(id);
		assertEquals("redirect:/album", viewName);
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"delete.ok"));
	}

	@Test
	public void deleteException() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		Album album = albumFactoryForTest.newAlbum();
		Long id = album.getId();
		
		Exception exception = new RuntimeException("test exception");
		doThrow(exception).when(albumService).delete(id);
		
		// When
		String viewName = albumController.delete(redirectAttributes, id);
		
		// Then
		verify(albumService).delete(id);
		assertEquals("redirect:/album", viewName);
		Mockito.verify(messageHelper).addException(redirectAttributes, "album.error.delete", exception);
	}
	
	
}
