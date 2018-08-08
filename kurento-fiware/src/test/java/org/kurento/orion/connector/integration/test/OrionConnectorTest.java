package org.kurento.orion.connector.integration.test;

import static org.junit.jupiter.api.Assertions.*;
import static org.slf4j.LoggerFactory.getLogger;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.apache.http.client.utils.URIBuilder;
import org.junit.jupiter.api.Test;
import org.kurento.orion.connector.OrionConnector;
import org.kurento.orion.connector.OrionConnectorConfiguration;
import org.kurento.orion.connector.OrionConnectorException;
import org.kurento.orion.connector.entities.OrionEntity;
import org.slf4j.Logger;

class OrionConnectorTest {
	
	private static final Logger log = getLogger(OrionConnectorTest.class);

	class OrionEntityTest implements OrionEntity {
		String id; 
		String type;
		String description;
		
		public OrionEntityTest (String description) {
			this.id = "Test_"+System.currentTimeMillis(); 
			this.description = description;
			this.type="TEST";
		}
		
		@Override
		public String getId() {
			return id;
		}

		@Override
		public String getType() {
			return type;
		}

		public String getDescription() {
			return description;
		}
		@Override
		public void setId(String id) {
			this.id = id;
		}

		@Override
		public void setType(String type) {	
			this.type=type;
		}
		
		public void setDescription (String description) {
			this.description = description;
		}	
	}
	
	@Test
	void testInit() {
		log.info("[testInit] INI");
		try {
			OrionConnectorConfiguration occ =  new OrionConnectorConfiguration();
			OrionConnector <OrionEntityTest> oc = new OrionConnector<OrionEntityTest>(occ,OrionEntityTest.class){};
			URI expected = new URIBuilder()
					.setScheme(occ.getOrionScheme())
					.setHost(occ.getOrionHost()).setPort(occ.getOrionPort())
					.build();
			assertEquals(oc.getOrionAddr(), expected, "[ERROR]Bad formed URI expected:"+expected.toString()+" generated:"+oc.getOrionAddr().toString());

		}catch (OrionConnectorException oce) {
			log.info("[testInit] END KO OrionConnectorException::"+oce.getLocalizedMessage());
			fail("[ERROR OrionConnectorException]" + oce.getLocalizedMessage());
			
		} catch (URISyntaxException use) {
			log.info("[testInit] END KO URISyntaxException::"+use.getLocalizedMessage());
			fail("[ERROR URISyntaxException]" + use.getLocalizedMessage());
		}
		
		log.info("[testInit] END OK");

	}
	
	@Test
	void testConnectionAndEntities() {
		log.info("[testConnectionAndEntities] INI");
		try {
			OrionConnectorConfiguration occ =  new OrionConnectorConfiguration();
			OrionConnector <OrionEntityTest> oc = new OrionConnector<OrionEntityTest>(occ,OrionEntityTest.class){};
			URI expected = new URIBuilder()
					.setScheme(occ.getOrionScheme())
					.setHost(occ.getOrionHost()).setPort(occ.getOrionPort())
					.build();
			assertEquals(oc.getOrionAddr(), expected, "[ERROR]Bad formed URI expected:"+expected.toString()+" generated:"+oc.getOrionAddr().toString());

			List<String> entities_types = oc.getEntityTypes();
			
			assertNotNull(entities_types, "[ERROR]null entities" );
			
		}catch (OrionConnectorException oce) {
			log.info("[testConnectionAndEntities] END KO OrionConnectorException::"+oce.getLocalizedMessage());
			fail("[ERROR OrionConnectorException]" + oce.getLocalizedMessage());
			
		} catch (URISyntaxException use) {
			log.info("[testConnectionAndEntities] END KO URISyntaxException::"+use.getLocalizedMessage());
			fail("[ERROR URISyntaxException]" + use.getLocalizedMessage());
		} catch (Exception e) {
			log.info("[testConnectionAndEntities] END KO Unknown "+ e.getClass()+"::"+e.getLocalizedMessage());
			fail("[ERROR Unknown]" + e.getLocalizedMessage());
		}
		
		log.info("[testConnectionAndEntities] END OK");
	}
	
	@Test 
	void testCreateNewEntityAndRetrieveEntity() {
		
		log.info("[testCreateNewEntityAndRetrieveEntity] INI");
		try {
			OrionConnectorConfiguration occ =  new OrionConnectorConfiguration();
			OrionConnector <OrionEntityTest> oc = new OrionConnector<OrionEntityTest>(occ,OrionEntityTest.class){};
			URI expected = new URIBuilder()
					.setScheme(occ.getOrionScheme())
					.setHost(occ.getOrionHost()).setPort(occ.getOrionPort())
					.build();
			assertEquals(oc.getOrionAddr(), expected, "[ERROR]Bad formed URI expected:"+expected.toString()+" generated:"+oc.getOrionAddr().toString());

			//create new Entity
			OrionEntityTest oet = new OrionEntityTest("testCreateNewEntityAndRetrieveEntity");
			
			oc.createNewEntity(oet, true);
			
			OrionEntity toe = oc.readEntity(oet.getId());
			
			assertNotNull(toe, "[ERROR]Entity not retrieved");
			assertTrue(toe.getId().equals(oet.getId()), "[ERROR]Unexpected retrieved entity");			
			
		}catch (OrionConnectorException oce) {
			log.info("[testCreateNewEntityAndRetrieveEntity] END KO OrionConnectorException::"+oce.getLocalizedMessage());
			fail("[ERROR OrionConnectorException]" + oce.getLocalizedMessage());
			
		} catch (URISyntaxException use) {
			log.info("[testCreateNewEntityAndRetrieveEntity] END KO URISyntaxException::"+use.getLocalizedMessage());
			fail("[ERROR URISyntaxException]" + use.getLocalizedMessage());
		} catch (Exception e) {
			log.info("[testCreateNewEntityAndRetrieveEntity] END KO Unknown "+ e.getClass()+"::"+e.getLocalizedMessage());
			fail("[ERROR Unknown]" + e.getLocalizedMessage());
		}
		
		log.info("[testCreateNewEntityAndRetrieveEntity] END OK");
	}
	
	@Test
	void testCreateNewEntityAndEntityList() {
		log.info("[testCreateNewEntityAndEntityList] INI");
		try {
			OrionConnectorConfiguration occ =  new OrionConnectorConfiguration();
			OrionConnector <OrionEntityTest> oc = new OrionConnector<OrionEntityTest>(occ,OrionEntityTest.class){};
			URI expected = new URIBuilder()
					.setScheme(occ.getOrionScheme())
					.setHost(occ.getOrionHost()).setPort(occ.getOrionPort())
					.build();
			assertEquals(oc.getOrionAddr(), expected, "[ERROR]Bad formed URI expected:"+expected.toString()+" generated:"+oc.getOrionAddr().toString());

			//create new Entity
			OrionEntityTest oet = new OrionEntityTest("testCreateNewEntityAndEntityList");
			
			oc.createNewEntity(oet, true);
			
			List<OrionEntityTest> lstoe = oc.readEntityList(oet.getType());
			int r = oc.getEntityCount("TEST");
			
			assertEquals(lstoe.size(),r, "[ERROR]Incomplete list");
			boolean found = false;
			
			for(int i = 0; i<lstoe.size() && !found; found = lstoe.get(i).getId().equals(oet.getId()), i++);
					
			assertTrue(found,"[ERROR]Entity not in the list" );
			
		}catch (OrionConnectorException oce) {
			log.info("[testCreateNewEntityAndEntityList] END KO OrionConnectorException::"+oce.getLocalizedMessage());
			fail("[ERROR OrionConnectorException]" + oce.getLocalizedMessage());
			
		} catch (URISyntaxException use) {
			log.info("[testCreateNewEntityAndEntityList] END KO URISyntaxException::"+use.getLocalizedMessage());
			fail("[ERROR URISyntaxException]" + use.getLocalizedMessage());
		} catch (Exception e) {
			log.info("[testCreateNewEntityAndEntityList] END KO Unknown "+ e.getClass()+"::"+e.getLocalizedMessage());
			fail("[ERROR Unknown]" + e.getLocalizedMessage());
		}
		
		log.info("[testCreateNewEntityAndEntityList] END OK");
	}

	@Test
	void testCountEntities() {
		log.info("[testCountEntities] INI");
		int r=-1;
		try {
			OrionConnectorConfiguration occ =  new OrionConnectorConfiguration();
			OrionConnector <OrionEntityTest> oc = new OrionConnector<OrionEntityTest>(occ,OrionEntityTest.class){};
			URI expected = new URIBuilder()
					.setScheme(occ.getOrionScheme())
					.setHost(occ.getOrionHost()).setPort(occ.getOrionPort())
					.build();
			assertEquals(oc.getOrionAddr(), expected, "[ERROR]Bad formed URI expected:"+expected.toString()+" generated:"+oc.getOrionAddr().toString());
			
			r = oc.getEntityCount("TEST");
			assertTrue(r > -1, "[ERROR]Bad result expected count > 1");
			
		}catch (OrionConnectorException oce) {
			log.info("[testCountEntities] END KO OrionConnectorException::"+oce.getLocalizedMessage());
			fail("[ERROR OrionConnectorException]" + oce.getLocalizedMessage());
			
		} catch (URISyntaxException use) {
			log.info("[testCountEntities] END KO URISyntaxException::"+use.getLocalizedMessage());
			fail("[ERROR URISyntaxException]" + use.getLocalizedMessage());
		} catch (Exception e) {
			log.info("[testCountEntities] END KO Unknown "+ e.getClass()+"::"+e.getLocalizedMessage());
			fail("[ERROR Unknown]" + e.getLocalizedMessage());
		}
		log.info("[testCountEntities] END OK ({})",r);
	}
	
	@Test
	void testDeleteEntity() {
		log.info("[testDeleteEntity] INI");
		int countBefore=-1;
		int countAfter = -1;
		try {
			OrionConnectorConfiguration occ =  new OrionConnectorConfiguration();
			OrionConnector <OrionEntityTest> oc = new OrionConnector<OrionEntityTest>(occ,OrionEntityTest.class){};
			URI expected = new URIBuilder()
					.setScheme(occ.getOrionScheme())
					.setHost(occ.getOrionHost()).setPort(occ.getOrionPort())
					.build();
			assertEquals(oc.getOrionAddr(), expected, "[ERROR]Bad formed URI expected:"+expected.toString()+" generated:"+oc.getOrionAddr().toString());
			
			countBefore = oc.getEntityCount("TEST");
			//if there is already some TEST entity we use them to test the delete, no need to create another one
			if (countBefore<1) {
				new OrionEntityTest("testDeleteEntity");
			}
			countBefore = oc.getEntityCount("TEST");
			assertTrue(countBefore > 0, "[ERROR]Bad result expected count > 0");

			//get first "TEST" entity in orion
			List<OrionEntityTest> lstoe = oc.readEntityList("TEST", 1, 0);
			String idToDelete =lstoe.get(0).getId();
			oc.deleteOneEntity(idToDelete);
			countAfter = oc.getEntityCount("TEST");
			
			assertTrue((countBefore - countAfter)==1, "[ERROR]countBefore("+countBefore+") countAfter("+countAfter+")");
			
			lstoe = oc.readEntityList("TEST");
			boolean found = false;
			
			for(int i = 0; i<lstoe.size() && !found;  found = lstoe.get(i).getId().equals(idToDelete), i++);
			
			assertTrue(!found,"[ERROR]Entity still in the list, another entity must have been deleted" );
			
		}catch (OrionConnectorException oce) {
			log.info("[testDeleteEntity] END KO OrionConnectorException::"+oce.getLocalizedMessage());
			fail("[ERROR OrionConnectorException]" + oce.getLocalizedMessage());
			
		} catch (URISyntaxException use) {
			log.info("[testDeleteEntity] END KO URISyntaxException::"+use.getLocalizedMessage());
			fail("[ERROR URISyntaxException]" + use.getLocalizedMessage());
		} catch (Exception e) {
			log.info("[testDeleteEntity] END KO Unknown "+ e.getClass()+"::"+e.getLocalizedMessage());
			fail("[ERROR Unknown]" + e.getLocalizedMessage());
		}
		log.info("[testDeleteEntity] END OK ({})",countBefore);
	}
	
	@Test
	void testUpdateEntity() {
		log.info("[testUpdateEntity] INI");
		int countBefore=-1;
		try {
			OrionConnectorConfiguration occ =  new OrionConnectorConfiguration();
			OrionConnector <OrionEntityTest> oc = new OrionConnector<OrionEntityTest>(occ,OrionEntityTest.class){};
			URI expected = new URIBuilder()
					.setScheme(occ.getOrionScheme())
					.setHost(occ.getOrionHost()).setPort(occ.getOrionPort())
					.build();
			assertEquals(oc.getOrionAddr(), expected, "[ERROR]Bad formed URI expected:"+expected.toString()+" generated:"+oc.getOrionAddr().toString());
			
			countBefore = oc.getEntityCount("TEST");
			//if there is already some TEST entity we use them to test the delete, no need to create another one
			if (countBefore<1) {
				new OrionEntityTest("testUpdateEntity");
			}
			countBefore = oc.getEntityCount("TEST");
			assertTrue(countBefore > 0, "[ERROR]Bad result expected count > 0");

			//get first "TEST" entity in orion
			List<OrionEntityTest> lstoe = oc.readEntityList("TEST", 1, 0);
			OrionEntityTest updateEntity =lstoe.get(0);
			String updatedId = updateEntity.getId();
			
			updateEntity.setDescription("UPDATED description");
			
			oc.updateEntity(updateEntity);
						
			OrionEntityTest updatedEntity = oc.readEntity(updatedId);
			
			assertEquals(updateEntity.getDescription(), updatedEntity.getDescription(),"[ERROR]Entity description not updated" );
			
		}catch (OrionConnectorException oce) {
			log.info("[testUpdateEntity] END KO OrionConnectorException::"+oce.getLocalizedMessage());
			fail("[ERROR OrionConnectorException]" + oce.getLocalizedMessage());
			
		} catch (URISyntaxException use) {
			log.info("[testUpdateEntity] END KO URISyntaxException::"+use.getLocalizedMessage());
			fail("[ERROR URISyntaxException]" + use.getLocalizedMessage());
		} catch (Exception e) {
			log.info("[testUpdateEntity] END KO Unknown "+ e.getClass()+"::"+e.getLocalizedMessage());
			fail("[ERROR Unknown]" + e.getLocalizedMessage());
		}
		log.info("[testUpdateEntity] END OK ({})",countBefore);
	}
	
	

}
