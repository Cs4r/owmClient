# Introduction

This project is a fork of [owmClient](https://github.com/migtavares/owmClient): A Java library to retrieve weather information and forecasts from [Open Weather Map](http://http://openweathermap.org/).

# Features

## Implemented

+ getting weather forecast for a city by
	+ OpenWeatherMap city id

## Planned

+ getting current weather at
	+ around a geographic coordinate
	+ in a circle
	+ in a bounding box
	+ city by
	  + geographic coordinate
	  + OpenWeatherMap city id
	  + city name
	  + city name and country code
	  + city(ies) within a bounding box
	  + city(ies) in a circle
+ getting weather forecast for a city by
	+ OpenWeatherMap city id
	+ city name
+ getting weather history
	+ from a city id
	+ from a station id (partial)
+ using an OpenWeatherMap APPID


# Usage

*TODO*

## Dependencies

To use this library in a environment that doesn't include Apache `httpclient` from `httpcommons` you must include the dependency in the final project.

The same for the json parser `org.json`.

With maven that can be accomplished with:

	<dependency>
		<groupId>org.apache.httpcomponents</groupId>
		<artifactId>httpclient</artifactId>
		<version>4.2.3</version>
	</dependency>

	<dependency>
		<groupId>org.json</groupId>
		<artifactId>json</artifactId>
		<version>20090211</version>
	</dependency>

*TO BE COMPLETED*

## Note of caution
There's almost no validation build in this library and the fetching of data from the JSON OpenWeatherMap API tries to survive without making much of a fuss.

This means that if you need some value you should use the `has` methods to check if the value was received (and understood) or not.

For enumerations there's a special value `UNKNOWN` that means that although a value was received at the JSON interface it was not one that the library knows about.

## Simple Example

*TODO*

# Class Diagrams
## Basic Weather Data Structure

	                        +---------------------+
	                        | AbstractWeatherData |
	                        +------·-------·------+
				                  /_\     /_\
						           |       |
				           +-------+       +------+
			               |                      |
	                 +-------------+    +--------------------+
	                 | WeatherData |    | SampledWeatherData |
	                 +-----·-------+    +--------------------+
	                      /_\
                           |
                           |
	            +----------------------+
	            | LocalizedWeatherData |
	            +---·--------------·---+
                   /_\            /_\
				    |              |
               +----+              +----+
	           |                        |
	+---------------------+   +-------------------+
	| ForecastWeatherData |   | StatusWeatherData |
	+---------------------+   +-------------------+

## Open Weather Map responses classes

+ `WeatherForecastResponse` - list of `ForecastWeatherData`
+ `WeatherHistoryCityResponse` - list of `WeatherData`
+ `WeatherHistoryStationResponse` - list of `AbstractWeatherData`, using the implementation `WeatherData` for responses to `TICK` history and `SampledWeatherData` for `HOUR` and `DAY`
+ `WeatherStatusResponse` - list of `StatusWeatherData`

# License												
Copyright 2015 J. Miguel P. Tavares

Copyright 2015 Cesar Aguilera

Licensed under the Apache License, Version 2.0 (the "License")
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
