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
package org.bitpipeline.lib.owm;

import java.util.Optional;

/**
 * Provides weather data coming from the API version 2.5 of
 * http://openweathermap.org/
 * 
 * @version 1.0
 * @author cs4r
 */
public interface OwmWeatherProvider_V_2_5 {

	/**
	 * Gets the current weather in a given city
	 * 
	 * @param cityId
	 *            id of the city
	 * @return {@link StatusWeatherData} if the id is correct and there is
	 *         weather data for such a city, {@link Optional#empty()} otherwise
	 * @throws IllegalArgumentException
	 *             if cityId is negative or zero
	 */
	Optional<StatusWeatherData> currentWeatherInCity(int cityId);
}
