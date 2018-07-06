package com.ef;

import java.util.Comparator;

public class FrequentIp implements Comparable<FrequentIp> {

	private String ip;
	private int frequency;
	private String comments;

	public FrequentIp(String ip, int frequency, String comments) {
		super();
		this.ip = ip;
		this.frequency = frequency;
		this.setComments(comments);
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getFrequency() {
		return frequency;
	}

	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public class FrequencyComparator implements Comparator<FrequentIp> {
		@Override
		public int compare(FrequentIp o1, FrequentIp o2) {
			return o1.compareTo(o2);
		}
	}

	@Override
	public int compareTo(FrequentIp o) {
		return getFrequency() > o.getFrequency() ? 1 : 0;
	}
}
