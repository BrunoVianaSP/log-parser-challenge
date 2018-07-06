package com.ef;

public class HourlyAnalysisMethod extends AnalysisMethod {

	@Override
	public long getMaxTimeRange() {
		return 3600 * 1000;
	}

	@Override
	public String getDuration() {
		return "hourly";
	}

}
