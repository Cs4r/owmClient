/**
 * Copyright 2015 J. Miguel P. Tavares
 * Copyright 2015 J. Cesar Aguilera
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ***************************************************************************/
package org.bitpipeline.lib.owm.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Matchers.anyMap;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.bitpipeline.lib.owm.impl.util.OverHttpJsonLoader;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * 
 * Unit tests for {@link OwmWeatherProvider_V_2_5_Impl}
 * 
 * @author cs4r
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class OwmWeatherProvider_V_2_5_ImplTest {

	@Mock
	private OverHttpJsonLoader jsonLoader;
	private OwmWeatherProvider_V_2_5_Impl weatherProvider;

	@Before
	public void setUp() {
		weatherProvider = new OwmWeatherProvider_V_2_5_Impl("", jsonLoader);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructWhenApiKeyIsNull() {
		new OwmWeatherProvider_V_2_5_Impl(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCurrentWeatherInCityWhenCityIdIsNegativeThrowsIllegalArgumentException() {
		weatherProvider.currentWeatherInCity(-1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCurrentWeatherInCityWhenCityIdIsZeroThrowsIllegalArgumentException() {
		weatherProvider.currentWeatherInCity(0);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void CurrentWeatherInCityWhenJsonDataIsNotRetrivable() {
		when(jsonLoader.loadJsonFromUrl(anyString(), anyMap())).thenReturn(Optional.empty());
		assertEquals(Optional.empty(), weatherProvider.currentWeatherInCity(1));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void CurrentWeatherInCityWhenJsonDataIsRetrivable() {
		JSONObject jsonObject = mock(JSONObject.class);
		Optional<JSONObject> notEmptyOptional = Optional.of(jsonObject);
		when(jsonLoader.loadJsonFromUrl(anyString(), anyMap())).thenReturn(notEmptyOptional);
		assertNotEquals(Optional.empty(), weatherProvider.currentWeatherInCity(1));
	}

}
