package com.ef;

public class DailyAnalysisMethod extends AnalysisMethod {

	@Override
	public long getMaxTimeRange() {
		return 3600 * 1000 * 24;
	}

	@Override
	public String getDuration() {
		return "daily";
	}
}
