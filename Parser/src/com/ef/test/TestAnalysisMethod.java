package com.ef.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.ef.AnalysisMethod;

public class TestAnalysisMethod extends TestBase {

	String defaultStartDate = "2017-01-01.13:00:00";
	int defaultThreshold = 100;
	AnalysisMethod hourlyMethod = AnalysisMethod.createHourlyMethod(defaultStartDate, defaultThreshold);
	AnalysisMethod dailyMethod = AnalysisMethod.createDailyMethod(defaultStartDate, defaultThreshold);

	@Before
	public void before() throws Exception {
		super.before();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testHourlyMethodWhenLogTimeIsGreatherThanStartDateIsLowerThanFinalDateThenTimeRangeIsTrue()
			throws IOException {
		printer.println("testHourlyMethodWhenTimeRangeIsTrue");
		String logDateTime = "2017-01-01 13:59:59.000";
		assertTrue(hourlyMethod.isInTimeRange(logDateTime));
	}

	@Test
	public void testHourlyMethodWhenLogTimeIsGreatherThanFinalDateThenTimeRangeIsFalse() throws IOException {
		printer.println("testHourlyMethodWhenLogTimeIsGreatherThanFinalDateThenTimeRangeIsFalse");
		String logDateTime = "2017-01-01 14:00:01.000";
		assertFalse(hourlyMethod.isInTimeRange(logDateTime));
	}

	@Test
	public void testHourlyMethodWhenLogTimeIsLowerThanStartDateThenTimeRangeIsFalse() throws IOException {
		printer.println("testHourlyMethodWhenLogTimeIsLowerThanStartDateThenTimeRangeIsFalse");
		String logDateTime = "2017-01-01 12:59:59.000";
		assertFalse(hourlyMethod.isInTimeRange(logDateTime));
	}

	@Test
	public void testDailyMethodWhenLogTimeIsGreatherThanStartDateIsLowerThanFinalDateThenTimeRangeIsTrue()
			throws IOException {
		printer.println("testHourlyMethodWhenTimeRangeIsTrue");
		String logDateTime = "2017-01-01 20:59:59.000";
		assertTrue(dailyMethod.isInTimeRange(logDateTime));
	}

	@Test
	public void testDailyMethodWhenLogTimeIsGreatherThanFinalDateThenTimeRangeIsFalse() throws IOException {
		printer.println("testHourlyMethodWhenLogTimeIsGreatherThanFinalDateThenTimeRangeIsFalse");
		String logDateTime = "2017-01-02 14:00:01.000";
		assertFalse(dailyMethod.isInTimeRange(logDateTime));
	}

	@Test
	public void testDailyMethodWhenLogTimeIsLowerThanStartDateThenTimeRangeIsFalse() throws IOException {
		printer.println("testHourlyMethodWhenLogTimeIsLowerThanStartDateThenTimeRangeIsFalse");
		String logDateTime = "2017-01-01 12:59:59.000";
		assertFalse(dailyMethod.isInTimeRange(logDateTime));
	}

}
