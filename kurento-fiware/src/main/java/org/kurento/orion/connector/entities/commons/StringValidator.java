/*
 * Copyright 2018 Kurento (https://www.kurento.org)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.kurento.orion.connector.entities.commons;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class StringValidator implements ConstraintValidator<ValidateString, String> {

  private List<String> valueList;

  @Override
  public void initialize(ValidateString constraintAnnotation) {
	valueList = new ArrayList<String>();
	for (String val : constraintAnnotation.acceptedValues()) {
	  valueList.add(val.toUpperCase());
	}
  }

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
	if (!valueList.contains(value.toUpperCase())) {
	  return false;
	}
	return true;
  }

}