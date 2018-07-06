package com.ef;

public abstract class AnalysisMethod {

	private String startDate;
	private int threshold;

	public void setStartDate(String startDate) {
		if (startDate.contains(".")) {
			String[] split = startDate.split("\\.");
			startDate = split[0] + " " + split[1] + ".000";
		}
		this.startDate = startDate;
	}

	public String getStartDate() {
		return startDate;
	}

	public String getEndDate() {
		return Chronos.hoursFrom(getStartDate(), getMaxTimeRange());
	}

	public abstract String getDuration();

	public int getThreshold() {
		return threshold;
	}

	public void setThreshold(int threshold) {
		this.threshold = threshold;
	}

	public abstract long getMaxTimeRange();

	public static AnalysisMethod createHourlyMethod(String date, int threshold) {
		AnalysisMethod method = new HourlyAnalysisMethod();
		method.setStartDate(date);
		method.setThreshold(threshold);
		return method;
	}

	public static AnalysisMethod createDailyMethod(String date, int threshold) {
		AnalysisMethod method = new DailyAnalysisMethod();
		method.setStartDate(date);
		method.setThreshold(threshold);
		return method;
	}

	public boolean exceedsThreshold(Integer count) {
		return count >= threshold;
	}

	public boolean isInTimeRange(String date) {
		long differenceInMillis = Chronos.difference(startDate, date).getMillis();
		return isInTimeRange(differenceInMillis);
	}

	private boolean isInTimeRange(long differenceInMillis) {
		return differenceInMillis >= 0 && differenceInMillis <= getMaxTimeRange();
	}
}
