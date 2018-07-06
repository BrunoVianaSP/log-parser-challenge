package com.ef.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.ef.AnalysisMethod;
import com.ef.FileTool;
import com.ef.Log;
import com.ef.LogAnalyst;
import com.ef.LogParser;

public class TestLogFileParse extends TestBase {

	private static final int LOG_FILE_SIZE = 116484;
	String file = "/Users/bruno/Development/projects/wallethub-test-project/access.log";
	List<String> lines;
	LogAnalyst analyst;

	@Before
	public void before() throws Exception {
		super.before();
		lines = FileTool.readLines(file);
		List<Log> logs = LogParser.parse(lines);
		String startDate = "2017-01-01.13:00:00";
		int threshold = 100;
		AnalysisMethod hourlyMethod = AnalysisMethod.createHourlyMethod(startDate, threshold);
		analyst = new LogAnalyst(hourlyMethod, logs);

	}

	@Test
	public void testLoadLogFile() {
		printer.println("testLoadLogFile");
		assertNotNull(lines);
	}

	@Test
	public void testLogFileToListSize() throws IOException {
		printer.println("testLogFileToListSize");
		assertEquals(116484, lines.size());
	}

	@Test
	public void testParseLogFile() throws IOException {
		printer.println("testParseLogFile");
		List<Log> logs = LogParser.parse(lines);
		assertEquals(LOG_FILE_SIZE, logs.size());
	}

	@Test
	public void testHourlyIpFrequencyLogAnalyst() throws IOException {
		printer.println("testHourlyIpFrequencyLogAnalyst");
		analyst.analyse();
		assertEquals(2, analyst.getResultSize());
	}

	@Test
	public void testDailyIpFrequencyLogAnalyst() throws IOException {
		printer.println("testDailyIpFrequencyLogAnalyst");
		AnalysisMethod dailyMethod = AnalysisMethod.createDailyMethod("2017-01-01.13:00:00", 100);
		analyst.setAnalysisMethod(dailyMethod);
		analyst.analyse();
		assertEquals(59, analyst.getResultSize());
	}

	@Test
	public void testHourlyIpFrequencySample() throws IOException {
		printer.println("testHourlyIpFrequencySample");
		AnalysisMethod dailyMethod = AnalysisMethod.createHourlyMethod("2017-01-01.15:00:00", 200);
		analyst.setAnalysisMethod(dailyMethod);
		analyst.analyse();
		assertEquals(2, analyst.getResultSize());
	}

	@Test
	public void testDailyIpFrequencySample() throws IOException {
		printer.println("testDailyIpFrequencySample");
		AnalysisMethod dailyMethod = AnalysisMethod.createDailyMethod("2017-01-01.00:00:00", 500);
		analyst.setAnalysisMethod(dailyMethod);
		analyst.analyse();
		assertEquals(15, analyst.getResultSize());
	}
}
