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
package org.bitpipeline.lib.owm.impl.util;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.util.Map;
import java.util.Optional;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * Loads JSON data from HTTP URLs
 * 
 * <p>
 * <strong>This class is not final for testing purposes</strong>
 * 
 * @author cs4r
 */
public class OverHttpJsonLoader {

	private static final Logger LOGGER = LoggerFactory.getLogger(OverHttpJsonLoader.class);
	private static final int CONTENT_SIZE = 8 * 1024;
	private final HttpClient httpClient;

	/**
	 * Constructs an instance of {@link OverHttpJsonLoader}
	 */
	public OverHttpJsonLoader() {
		httpClient = new DefaultHttpClient();
	}

	/**
	 * Loads JSON data from a given HTTP URLs
	 * 
	 * @param url
	 *            URL to query
	 * @param headers
	 *            HTTP Headers to retrieve the json data (if needed)
	 * @return {@link JSONObject} if json data was successfully retrieve,
	 *         {@link Optional#empty()} otherwise
	 */
	public Optional<JSONObject> loadJsonFromUrl(final String url, final Map<String, String> headers) {
		String responseBody = null;
		HttpGet httpget = new HttpGet(url);
		headers.forEach((k, v) -> httpget.addHeader(k, v));

		HttpResponse response = null;
		JSONObject jsonObject = null;
		try {
			response = httpClient.execute(httpget);
			StatusLine statusLine = response.getStatusLine();
			if (statusLine == null) {
				LOGGER.info("Unable to get a response from server");
			} else {
				int statusCode = statusLine.getStatusCode();
				if (statusCode < HttpStatus.SC_OK && statusCode >= HttpStatus.SC_MULTIPLE_CHOICES) {
					LOGGER.info("Server responded with status code {}: {}", url, statusCode, statusLine);
				} else {
					HttpEntity responseEntity = response.getEntity();
					try (InputStream contentStream = responseEntity.getContent()) {
						Reader isReader = new InputStreamReader(contentStream);
						int contentSize = (int) responseEntity.getContentLength();
						if (contentSize < 0) {
							contentSize = CONTENT_SIZE;
						}
						StringWriter strWriter = new StringWriter(contentSize);
						char[] buffer = new char[CONTENT_SIZE];
						int n = 0;
						while ((n = isReader.read(buffer)) != -1) {
							strWriter.write(buffer, 0, n);
						}
						responseBody = strWriter.toString();
						jsonObject = new JSONObject(responseBody);
					}
				}
			}

		} catch (Exception exception) {
			LOGGER.info("Unable to retrieve json data from URL {}", url, exception);
		} finally {
			if (response != null) {
				httpget.abort();
			}
		}

		return Optional.ofNullable(jsonObject);
	}

}
