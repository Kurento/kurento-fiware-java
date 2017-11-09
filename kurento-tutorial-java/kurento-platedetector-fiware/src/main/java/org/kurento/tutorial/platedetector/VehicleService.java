/*
 * (C) Copyright 2017 Kurento (http://kurento.org/)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package org.kurento.tutorial.platedetector;

import java.util.Arrays;
import java.util.Date;
import java.util.Random;

import org.kurento.tutorial.platedetector.orion.models.GeoJson;
import org.kurento.tutorial.platedetector.orion.models.Point;
import org.kurento.tutorial.platedetector.orion.models.datamodels.VehicleDataModel;
import org.springframework.stereotype.Service;

@Service
public class VehicleService {

	// This method return dummy values for demo purposes. Data model taken from
	// http://fiware-datamodels.readthedocs.io/en/latest/Transportation/Vehicle/Vehicle/doc/spec/index.html
	public VehicleDataModel getVehicleFromPlate(String plate) {
		Random random = new Random();
		VehicleDataModel vehicle = new VehicleDataModel();
		vehicle.setId("vehicle:WasteManagement:" + new Date().getTime());
		vehicle.setVehicleType("lorry");
		vehicle.setCategory(Arrays.asList("municipalServices"));

		GeoJson location = new GeoJson();
		Point point = new Point();
		point.setCoordinates(Arrays.asList("-3.164485591715449", "40.62785133667262"));
		location.setValue(point);
		vehicle.setLocation(location);

		vehicle.setName("C Recogida " + new Date().getTime());
		// A random speed is setted for demo purposes
		vehicle.setSpeed(random.nextInt(100 - 50 + 1) + 50);
		// A random cargoWeight is setted for demo purposes
		vehicle.setCargoWeight(new Float(random.nextInt(400 - 300 + 1) + 300));
		vehicle.setServiceStatus("onRoute, garbageCollection");
		vehicle.setAreaServed("Centro");
		vehicle.setRefVehicleModel("vehiclemodel:econic");
		vehicle.setVehiclePlateIdentifier(plate);
		return vehicle;
	}
}
