package com.ef;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gson.GsonBuilder;

public class LogAnalyst {

	protected static final PrintStream printer = System.out;

	private List<Log> logs;
	private ArrayList<FrequentIp> result;
	private AnalysisMethod analysisMethod;
	private Map<String, Integer> frequencyTable = new HashMap<>();

	public LogAnalyst() {

	}

	public LogAnalyst(AnalysisMethod analysisMethod, List<Log> logs) {
		super();
		this.logs = logs;
		this.analysisMethod = analysisMethod;
		result = new ArrayList<>();
	}

	public void setAnalysisMethod(AnalysisMethod analysisMethod) {
		this.analysisMethod = analysisMethod;
	}

	public void analyse() {
		calculateFrequencyTable();
		filterByThreshold();
	}

	private void filterByThreshold() {
		for (Entry<String, Integer> entry : frequencyTable.entrySet()) {
			if (analysisMethod.exceedsThreshold(entry.getValue())) {
				String comments = entry.getKey() + " accessed " + entry.getValue()
						+ " times in the time range analysed.";
				result.add(new FrequentIp(entry.getKey(), entry.getValue(), comments));
			}
		}

		sortResult();
	}

	private void sortResult() {
		Collections.sort(result, new Comparator<FrequentIp>() {
			public int compare(FrequentIp o1, FrequentIp o2) {
				return o1.compareTo(o2);
			}
		});
	}

	private void calculateFrequencyTable() {
		frequencyTable.clear();
		for (Log log : logs) {
			if (analysisMethod.isInTimeRange(log.getDate())) {
				frequencyTable.put(log.getIp(), frequencyTable.getOrDefault(log.getIp(), 0).intValue() + 1);
			}
		}
	}

	public int getResultSize() {
		return result.size();
	}

	@Override
	public String toString() {
		return new GsonBuilder().setPrettyPrinting().create().toJson(this);
	}

	public void printResult() {
		printer.println("****************** ANALYSIS RESULT ******************");
		printer.println("Excedding threshold limit IPs: ");
		if (result.isEmpty()) {
			printer.println("No ip exceeded the threshold in the period.");
		}

		for (FrequentIp ip : result) {
			float percentHigher = ((ip.getFrequency() * 100.0f/ analysisMethod.getThreshold()) - 100);
			printer.println(ip.getIp() + " access " + ip.getFrequency() + " times in the time range from "+ analysisMethod.getStartDate() + " to " + analysisMethod.getEndDate() + ". Threshold limit was " + analysisMethod.getThreshold() + ". It's " + percentHigher + "% higher than the limit specified.");
		}
		printer.println("*****************************************************\n");
	}
	
	public List<FrequentIp> getAnalysisResult() {
		return result;
	}
}
