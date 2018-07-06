package com.ef.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.ef.DatabaseConnection;
import com.ef.FileTool;
import com.ef.FrequentIp;
import com.ef.Log;
import com.ef.LogParser;

public class TestDatabase extends TestBase {

	String filePath = "C:\\Users\\Bruno.Barbosa\\Documents\\GitHub\\log-parser-challenge\\Parser\\access.log";
	DatabaseConnection database;

	@Before
	public void before() throws Exception {
		super.before();
		database = new DatabaseConnection();

	}

	@After
	public void tearDown() throws Exception {
		super.tearDown();
	}

	@Test
	public void testSaveAnalysisResult() throws Exception {
		List<FrequentIp> analysisResult = new ArrayList<>();
		analysisResult.add(new FrequentIp("192.168.1.1", 100, "Exceeds threshold limit."));
		database.saveAnalysisResult(analysisResult);
	}

	@Test
	public void testSaveLogFile() throws Exception {
		List<String> lines = FileTool.readLines(filePath);
		List<Log> logs = LogParser.parse(lines);
		database.saveLogFile(logs);
	}

}
