package com.ef.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ TestAnalysisMethod.class, TestLogFileParse.class, TestDatabase.class })

public class TestAll {

	public TestAll() {
		// TODO Auto-generated constructor stub
	}
}
