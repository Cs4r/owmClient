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

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import org.bitpipeline.lib.owm.OwmWeatherProvider_V_2_5;
import org.bitpipeline.lib.owm.StatusWeatherData;
import org.bitpipeline.lib.owm.impl.util.OverHttpJsonLoader;

/**
 * Implementation of {@link OwmWeatherProvider_V_2_5}
 * 
 * @author cs4r
 */
public class OwmWeatherProvider_V_2_5_Impl implements OwmWeatherProvider_V_2_5 {
	private static final String APPID_HEADER = "x-api-key";
	private static final String BASE_URL = "http://api.openweathermap.org/data/2.5/";
	private final Map<String, String> headers = new HashMap<>();
	private final OverHttpJsonLoader jsonLoader;

	/**
	 * Constructs an instance of {@link OwmWeatherProvider_V_2_5_Impl}
	 * 
	 * @param apiKey
	 *            Api key for http://openweathermap.org/
	 */
	public OwmWeatherProvider_V_2_5_Impl(String apiKey) {
		checkNotNull(apiKey, "apiKey");
		jsonLoader = new OverHttpJsonLoader();
		headers.put(APPID_HEADER, apiKey);
	}

	/**
	 * Protected for testing purposes
	 */
	protected OwmWeatherProvider_V_2_5_Impl(String apiKey, OverHttpJsonLoader overHttpJsonLoader) {
		checkNotNull(apiKey, "apiKey");
		jsonLoader = overHttpJsonLoader;
		headers.put(APPID_HEADER, apiKey);
	}

	private <T> T checkNotNull(T fieldToCheck, String fieldName) {
		if (fieldToCheck == null) {
			throw new IllegalArgumentException("Can't construct a " + OwmWeatherProvider_V_2_5_Impl.class.getName()
					+ " object with a null " + fieldName);
		}
		return fieldToCheck;
	}

	@Override
	public Optional<StatusWeatherData> currentWeatherInCity(int cityId) {
		if (cityId < 1) {
			throw new IllegalArgumentException("cityId cannot be neither negative nor zero");
		}
		String subUrl = String.format(Locale.ROOT, "weather?id=%d", Integer.valueOf(cityId));
		return jsonLoader.loadJsonFromUrl(BASE_URL + subUrl, headers)
				.map(json -> Optional.of(new StatusWeatherData(json))).orElse(Optional.empty());
	}

}
