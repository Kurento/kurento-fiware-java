package org.kurento.orion.connector.integration.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.slf4j.LoggerFactory.getLogger;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.kurento.orion.connector.OrionConnector;
import org.kurento.orion.connector.OrionConnectorConfiguration;
import org.kurento.orion.connector.OrionConnectorException;
import org.kurento.orion.connector.entities.OrionEntity;
import org.slf4j.Logger;

class OrionConnectorTest {

  private static final Logger					   log				  = getLogger(OrionConnectorTest.class);

  private static List<String>					   published_test_ids = new ArrayList<String>();

  private static final OrionConnectorConfiguration occ				  = new OrionConnectorConfiguration();
  private static OrionConnector<OrionEntityTest>   oc;

  class OrionEntityTest implements OrionEntity {
	String id;
	String type;
	String description;

	public OrionEntityTest(String description) {
	  this.id = "Test_" + System.currentTimeMillis();
	  this.description = description;
	  this.type = "TEST";
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
	  this.type = type;
	}

	public void setDescription(String description) {
	  this.description = description;
	}
  }

  @BeforeAll
  public static void initialization() {
	oc = new OrionConnector<OrionEntityTest>(occ, OrionEntityTest.class) {
	};
  }

  @AfterAll
  public static void cleanInsert() {
	for (String id : published_test_ids) {
	  try {
		oc.deleteOneEntity(id);
		// published_test_ids.remove(id);
	  } catch (OrionConnectorException e) {
		log.error("Entity {} could't be deleted... SKIPPING", id);
	  }
	}
  }

  @Test
  void testConnectionAndEntities() {
	log.info("[testConnectionAndEntities] INI");
	try {
	  List<String> entities_types = oc.getEntityTypes();

	  assertNotNull(entities_types, "[ERROR]null entities");

	} catch (OrionConnectorException oce) {
	  log.info("[testConnectionAndEntities] END KO OrionConnectorException::" + oce.getLocalizedMessage());
	  fail("[ERROR OrionConnectorException]" + oce.getLocalizedMessage());

	} catch (Exception e) {
	  log.info("[testConnectionAndEntities] END KO Unknown " + e.getClass() + "::" + e.getLocalizedMessage());
	  fail("[ERROR Unknown]" + e.getLocalizedMessage());
	}

	log.info("[testConnectionAndEntities] END OK");
  }

  @Test
  void testCreateNewEntityAndRetrieveEntity() {

	log.info("[testCreateNewEntityAndRetrieveEntity] INI");
	try {

	  // create new Entity
	  OrionEntityTest oet = new OrionEntityTest("testCreateNewEntityAndRetrieveEntity");

	  oc.createNewEntity(oet, true);
	  published_test_ids.add(oet.getId());

	  OrionEntity toe = oc.readEntity(oet.getId());

	  assertNotNull(toe, "[ERROR]Entity not retrieved");
	  assertTrue(toe.getId().equals(oet.getId()), "[ERROR]Unexpected retrieved entity");

	} catch (OrionConnectorException oce) {
	  log.info("[testCreateNewEntityAndRetrieveEntity] END KO OrionConnectorException::" + oce.getLocalizedMessage());
	  fail("[ERROR OrionConnectorException]" + oce.getLocalizedMessage());

	} catch (Exception e) {
	  log.info(
	      "[testCreateNewEntityAndRetrieveEntity] END KO Unknown " + e.getClass() + "::" + e.getLocalizedMessage());
	  fail("[ERROR Unknown]" + e.getLocalizedMessage());
	}

	log.info("[testCreateNewEntityAndRetrieveEntity] END OK");
  }

  @Test
  void testCreateNewEntityAndEntityList() {
	log.info("[testCreateNewEntityAndEntityList] INI");
	try {

	  // create new Entity
	  OrionEntityTest oet = new OrionEntityTest("testCreateNewEntityAndEntityList");

	  oc.createNewEntity(oet, true);
	  published_test_ids.add(oet.getId());

	  List<OrionEntityTest> lstoe = oc.readEntityList(oet.getType());
	  int r = oc.getEntityCount("TEST");

	  assertEquals(lstoe.size(), r, "[ERROR]Incomplete list");
	  boolean found = false;

	  for (int i = 0; i < lstoe.size() && !found; found = lstoe.get(i).getId().equals(oet.getId()), i++)
		;

	  assertTrue(found, "[ERROR]Entity not in the list");

	} catch (OrionConnectorException oce) {
	  log.info("[testCreateNewEntityAndEntityList] END KO OrionConnectorException::" + oce.getLocalizedMessage());
	  fail("[ERROR OrionConnectorException]" + oce.getLocalizedMessage());

	} catch (Exception e) {
	  log.info("[testCreateNewEntityAndEntityList] END KO Unknown " + e.getClass() + "::" + e.getLocalizedMessage());
	  fail("[ERROR Unknown]" + e.getLocalizedMessage());
	}

	log.info("[testCreateNewEntityAndEntityList] END OK");
  }

  @Test
  void testCountEntities() {
	log.info("[testCountEntities] INI");
	int r = -1;
	try {

	  r = oc.getEntityCount("TEST");
	  assertTrue(r > -1, "[ERROR]Bad result expected count > 1");

	} catch (OrionConnectorException oce) {
	  log.info("[testCountEntities] END KO OrionConnectorException::" + oce.getLocalizedMessage());
	  fail("[ERROR OrionConnectorException]" + oce.getLocalizedMessage());

	} catch (Exception e) {
	  log.info("[testCountEntities] END KO Unknown " + e.getClass() + "::" + e.getLocalizedMessage());
	  fail("[ERROR Unknown]" + e.getLocalizedMessage());
	}
	log.info("[testCountEntities] END OK ({})", r);
  }

  @Test
  void testDeleteEntity() {
	log.info("[testDeleteEntity] INI");
	int countBefore = -1;
	int countAfter = -1;
	try {

	  countBefore = oc.getEntityCount("TEST");
	  // if there is already some TEST entity we use them to test the delete, no need
	  // to create another one
	  if (countBefore < 1) {
		OrionEntityTest oet = new OrionEntityTest("testDeleteEntity");
		oc.createNewEntity(oet, true);
	  }
	  countBefore = oc.getEntityCount("TEST");
	  assertTrue(countBefore > 0, "[ERROR]Bad result expected count > 0");

	  // get first "TEST" entity in orion
	  List<OrionEntityTest> lstoe = oc.readEntityList("TEST", 1, 0);
	  String idToDelete = lstoe.get(0).getId();
	  oc.deleteOneEntity(idToDelete);
	  countAfter = oc.getEntityCount("TEST");

	  assertTrue((countBefore - countAfter) == 1,
	      "[ERROR]countBefore(" + countBefore + ") countAfter(" + countAfter + ")");

	  lstoe = oc.readEntityList("TEST");
	  boolean found = false;

	  for (int i = 0; i < lstoe.size() && !found; found = lstoe.get(i).getId().equals(idToDelete), i++)
		;

	  assertTrue(!found, "[ERROR]Entity still in the list, another entity must have been deleted");

	} catch (OrionConnectorException oce) {
	  log.info("[testDeleteEntity] END KO OrionConnectorException::" + oce.getLocalizedMessage());
	  fail("[ERROR OrionConnectorException]" + oce.getLocalizedMessage());

	} catch (Exception e) {
	  log.info("[testDeleteEntity] END KO Unknown " + e.getClass() + "::" + e.getLocalizedMessage());
	  fail("[ERROR Unknown]" + e.getLocalizedMessage());
	}
	log.info("[testDeleteEntity] END OK ({})", countBefore);
  }

  @Test
  void testUpdateEntity() {
	log.info("[testUpdateEntity] INI");
	int countBefore = -1;
	try {
	  countBefore = oc.getEntityCount("TEST");
	  // if there is already some TEST entity we use them to test the delete, no need
	  // to create another one
	  if (countBefore < 1) {
		OrionEntityTest oet = new OrionEntityTest("testUpdateEntity");
		oc.createNewEntity(oet, true);
		published_test_ids.add(oet.getId());
	  }
	  countBefore = oc.getEntityCount("TEST");
	  assertTrue(countBefore > 0, "[ERROR]Bad result expected count > 0");

	  // get first "TEST" entity in orion
	  List<OrionEntityTest> lstoe = oc.readEntityList("TEST", 1, 0);
	  OrionEntityTest updateEntity = lstoe.get(0);
	  String updatedId = updateEntity.getId();

	  updateEntity.setDescription("UPDATED description");

	  oc.updateEntity(updateEntity);

	  OrionEntityTest updatedEntity = oc.readEntity(updatedId);

	  assertEquals(updateEntity.getDescription(), updatedEntity.getDescription(),
	      "[ERROR]Entity description not updated");

	} catch (OrionConnectorException oce) {
	  log.info("[testUpdateEntity] END KO OrionConnectorException::" + oce.getLocalizedMessage());
	  fail("[ERROR OrionConnectorException]" + oce.getLocalizedMessage());

	} catch (Exception e) {
	  log.info("[testUpdateEntity] END KO Unknown " + e.getClass() + "::" + e.getLocalizedMessage());
	  fail("[ERROR Unknown]" + e.getLocalizedMessage());
	}
	log.info("[testUpdateEntity] END OK ({})", countBefore);
  }

}
