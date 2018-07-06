package com.ef;

import java.io.PrintStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public abstract class Chronos {

	protected static final PrintStream printer = System.out;
	protected static final long DAY_IN_MILISECONDS = 1000 * 60 * 60 * 24;
	protected static final long HOUR_IN_MILISECONDS = 1000 * 60 * 60;

	public Chronos() {
		// TODO Auto-generated constructor stub
	}

	public static String now() {
		DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss.SSS");
		DateTime now = new DateTime();
		return now.toString(format);
	}

	public static String daysFromNow(int daysAhead) {
		DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss.SSS");
		DateTime now = new DateTime(System.currentTimeMillis() + DAY_IN_MILISECONDS * daysAhead);
		return now.toString(format);
	}

	public static Duration difference(DateTime now, DateTime ahead) {
		return new Duration(now, ahead);
	}

	public static Duration difference(String now, String ahead) {

		try {
			DateTime dtNow = convertToDateTime(now);
			DateTime dtAhead = convertToDateTime(ahead);
			return difference(dtNow, dtAhead);
		} catch (Exception e) {
			DateTime dtNow = convertToDateTime(now());
			return difference(dtNow, dtNow);
		}
	}

	private static DateTime convertToDateTime(String date) {
		try {

			return tryFormatAsDateTime(date);
		} catch (IllegalArgumentException ex) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			try {
				return new DateTime(formatter.parse(date).getTime());
			} catch (ParseException e) {
				return tryFormatAsDateTime(date);
			}
		}
	}

	private static DateTime tryFormatAsDateTime(String date) {
		DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss.SSS");
		DateTime parsed = format.parseDateTime(date);
		return parsed;
	}

	public static Duration difference(long start, long end) {
		return new Duration(start, end);
	}

	public static String hoursFrom(String date, long plusInMillis) {
		DateTime dateTime = convertToDateTime(date);
		DateTime finalDateTime = new DateTime(dateTime.getMillis() + plusInMillis);
		DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss.SSS");
		return finalDateTime.toString(format);
	}
}
