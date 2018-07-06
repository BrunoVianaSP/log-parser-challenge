package com.ef.test;

import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;

public class TestBase {
	protected static final PrintStream printer = System.out;

	public TestBase() {

	}

	@Before
	public void before() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
}
