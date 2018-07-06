package com.ef;

import java.util.ArrayList;
import java.util.List;

public class LogParser {

	public static List<Log> parse(List<String> lines) {
		List<Log> logs = new ArrayList<>();
		Log log = null;
		for (String line : lines) {
			String[] split = line.split("\\|");
			log = new Log();
			log.setDate(split[0]);
			log.setIp(split[1]);
			log.setRequest(split[2]);
			log.setStatus(Integer.parseInt(split[3]));
			log.setUserAgent(split[4]);
			logs.add(log);
		}
		return logs;
	}

}
