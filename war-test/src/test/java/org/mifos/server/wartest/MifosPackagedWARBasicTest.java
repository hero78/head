/*
 * Copyright (c) 2011 Grameen Foundation USA
 * All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * permissions and limitations under the License.
 *
 * See also http://www.apache.org/licenses/LICENSE-2.0.html for an
 * explanation of the license and how it is applied.
 */

package org.mifos.server.wartest;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Test;
import org.mifos.server.WARServerLauncher;

/**
 * Tests launching of a packaged Mifos WAR. All the dependencies / web modules
 * are read from within the WAR in this test. The application is NOT on the
 * (Maven provided) classpath here - only the Jetty web container is.
 * 
 * @author Michael Vorburger
 */
public class MifosPackagedWARBasicTest {

	// TODO When acceptanceTest is WebDriver instead of Selenium-based, and uses
	// server-jetty instead of Cargo for start-up, this test can go into
	// acceptanceTest (and the entire war-test module would no longer needed -
	// be the same thing?)
	
	@Test
	public void testPackagedWARStartup() throws Exception {
	    File testWARFile = new File("../war/target/mifos.war");
	    assertTrue(testWARFile.toString() + " doesn't exist?!", testWARFile.exists());
	    
        WARServerLauncher serverLauncher = new WARServerLauncher(7077, "mifos", testWARFile );
		serverLauncher.startServer();
		serverLauncher.stopServer();
		serverLauncher = null;
	}
}