/*
 * Created on 28 okt 2015 ( Time 11:01:17 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package org.backpacker4.business.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.backpacker4.bean.UserRoles;
import org.backpacker4.bean.jpa.UserRolesEntity;
import org.backpacker4.business.service.mapping.UserRolesServiceMapper;
import org.backpacker4.persistence.services.jpa.UserRolesPersistenceJPA;
import org.backpacker4.test.UserRolesFactoryForTest;
import org.backpacker4.test.UserRolesEntityFactoryForTest;
import org.backpacker4.test.MockValues;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Test : Implementation of UserRolesService
 */
@RunWith(MockitoJUnitRunner.class)
public class UserRolesServiceImplTest {

	@InjectMocks
	private UserRolesServiceImpl userRolesService;
	@Mock
	private UserRolesPersistenceJPA userRolesPersistenceJPA;
	@Mock
	private UserRolesServiceMapper userRolesServiceMapper;
	
	private UserRolesFactoryForTest userRolesFactoryForTest = new UserRolesFactoryForTest();

	private UserRolesEntityFactoryForTest userRolesEntityFactoryForTest = new UserRolesEntityFactoryForTest();

	private MockValues mockValues = new MockValues();
	
	@Test
	public void findById() {
		// Given
		Integer iduserRoles = mockValues.nextInteger();
		
		UserRolesEntity userRolesEntity = userRolesPersistenceJPA.load(iduserRoles);
		
		UserRoles userRoles = userRolesFactoryForTest.newUserRoles();
		when(userRolesServiceMapper.mapUserRolesEntityToUserRoles(userRolesEntity)).thenReturn(userRoles);

		// When
		UserRoles userRolesFound = userRolesService.findById(iduserRoles);

		// Then
		assertEquals(userRoles.getIduserRoles(),userRolesFound.getIduserRoles());
	}

	@Test
	public void findAll() {
		// Given
		List<UserRolesEntity> userRolesEntitys = new ArrayList<UserRolesEntity>();
		UserRolesEntity userRolesEntity1 = userRolesEntityFactoryForTest.newUserRolesEntity();
		userRolesEntitys.add(userRolesEntity1);
		UserRolesEntity userRolesEntity2 = userRolesEntityFactoryForTest.newUserRolesEntity();
		userRolesEntitys.add(userRolesEntity2);
		when(userRolesPersistenceJPA.loadAll()).thenReturn(userRolesEntitys);
		
		UserRoles userRoles1 = userRolesFactoryForTest.newUserRoles();
		when(userRolesServiceMapper.mapUserRolesEntityToUserRoles(userRolesEntity1)).thenReturn(userRoles1);
		UserRoles userRoles2 = userRolesFactoryForTest.newUserRoles();
		when(userRolesServiceMapper.mapUserRolesEntityToUserRoles(userRolesEntity2)).thenReturn(userRoles2);

		// When
		List<UserRoles> userRolessFounds = userRolesService.findAll();

		// Then
		assertTrue(userRoles1 == userRolessFounds.get(0));
		assertTrue(userRoles2 == userRolessFounds.get(1));
	}

	@Test
	public void create() {
		// Given
		UserRoles userRoles = userRolesFactoryForTest.newUserRoles();

		UserRolesEntity userRolesEntity = userRolesEntityFactoryForTest.newUserRolesEntity();
		when(userRolesPersistenceJPA.load(userRoles.getIduserRoles())).thenReturn(null);
		
		userRolesEntity = new UserRolesEntity();
		userRolesServiceMapper.mapUserRolesToUserRolesEntity(userRoles, userRolesEntity);
		UserRolesEntity userRolesEntitySaved = userRolesPersistenceJPA.save(userRolesEntity);
		
		UserRoles userRolesSaved = userRolesFactoryForTest.newUserRoles();
		when(userRolesServiceMapper.mapUserRolesEntityToUserRoles(userRolesEntitySaved)).thenReturn(userRolesSaved);

		// When
		UserRoles userRolesResult = userRolesService.create(userRoles);

		// Then
		assertTrue(userRolesResult == userRolesSaved);
	}

	@Test
	public void createKOExists() {
		// Given
		UserRoles userRoles = userRolesFactoryForTest.newUserRoles();

		UserRolesEntity userRolesEntity = userRolesEntityFactoryForTest.newUserRolesEntity();
		when(userRolesPersistenceJPA.load(userRoles.getIduserRoles())).thenReturn(userRolesEntity);

		// When
		Exception exception = null;
		try {
			userRolesService.create(userRoles);
		} catch(Exception e) {
			exception = e;
		}

		// Then
		assertTrue(exception instanceof IllegalStateException);
		assertEquals("already.exists", exception.getMessage());
	}

	@Test
	public void update() {
		// Given
		UserRoles userRoles = userRolesFactoryForTest.newUserRoles();

		UserRolesEntity userRolesEntity = userRolesEntityFactoryForTest.newUserRolesEntity();
		when(userRolesPersistenceJPA.load(userRoles.getIduserRoles())).thenReturn(userRolesEntity);
		
		UserRolesEntity userRolesEntitySaved = userRolesEntityFactoryForTest.newUserRolesEntity();
		when(userRolesPersistenceJPA.save(userRolesEntity)).thenReturn(userRolesEntitySaved);
		
		UserRoles userRolesSaved = userRolesFactoryForTest.newUserRoles();
		when(userRolesServiceMapper.mapUserRolesEntityToUserRoles(userRolesEntitySaved)).thenReturn(userRolesSaved);

		// When
		UserRoles userRolesResult = userRolesService.update(userRoles);

		// Then
		verify(userRolesServiceMapper).mapUserRolesToUserRolesEntity(userRoles, userRolesEntity);
		assertTrue(userRolesResult == userRolesSaved);
	}

	@Test
	public void delete() {
		// Given
		Integer iduserRoles = mockValues.nextInteger();

		// When
		userRolesService.delete(iduserRoles);

		// Then
		verify(userRolesPersistenceJPA).delete(iduserRoles);
		
	}

}
