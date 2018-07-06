package com.ef;

import java.io.IOException;
import java.io.PrintStream;
import java.security.InvalidParameterException;
import java.util.List;
import java.util.Scanner;

import javax.naming.directory.InvalidAttributesException;

public class Parser {
	protected static final PrintStream printer = System.out;

	private static String logFilePath = "./access.log";
	private static List<String> lines;
	private static String startDate;
	private static String duration;
	private static int threshold;
	private static boolean shouldSaveToDatabase = false;
	static Scanner reader = new Scanner(System.in);
	private static DatabaseConnection databaseConn = new DatabaseConnection();

	public static void main(String[] args) {
		printer.println("\n##################################################\n");
		printer.println("Program started.\n");
		try {
			readArguments(args);
//			readLogFilePath();
			askForDatabaseCredentials();
			loadLogFile();

			List<Log> logs = parseLogFile();
			AnalysisMethod hourlyMethod = createAnalysisMethod();
			LogAnalyst analyst = executeAnalysis(logs, hourlyMethod);
			sendToDatabase(logs, analyst);
		} catch (IOException e) {
			printer.println(e.getMessage());
			e.printStackTrace();
		} catch (InvalidAttributesException e) {
			printer.println(e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			printer.println(e.getMessage());
			e.printStackTrace();
		} finally {
			printer.println("\nProgram finished.");
			printer.println("\n##################################################\n");
			reader.close();
		}
	}

	private static LogAnalyst executeAnalysis(List<Log> logs, AnalysisMethod hourlyMethod) {
		printer.println("\nStarting log analysis. Please wait.\n");
		LogAnalyst analyst = new LogAnalyst(hourlyMethod, logs);
		analyst.analyse();
		analyst.printResult();
		return analyst;
	}

	private static void sendToDatabase(List<Log> logs, LogAnalyst analyst) throws Exception {
		if (shouldSaveToDatabase == false) {
			return;
		}
		sendLogFile(logs);
		sendAnalysisResult(analyst);
	}

	private static void sendAnalysisResult(LogAnalyst analyst) throws Exception {
		if (analyst.getResultSize() == 0) {
			return; // Have no results.
		}

		System.out.println(
				"\nWould like to save Analysis Result file to Database (press enter to SKIP)? Please enter (y/n):");
		String change = reader.nextLine();
		if (change.equals("y")) {
			printer.println("\nSending Analysis Result file to Database. Please wait.");
			databaseConn.saveAnalysisResult(analyst.getAnalysisResult());
			printer.println("Analysis Result file saved.");
		} else {
			printer.println("***Won't save Analysis Result file to Database.");
		}
	}

	private static void sendLogFile(List<Log> logs) throws Exception {
		System.out.println("\nWould like to save FULL Log file to Database (press enter to SKIP)? Please enter (y/n):");
		String change = reader.nextLine();
		if (change.equals("y")) {
			printer.println("\nSending FULL Log file to Database. Please wait.");
			databaseConn.saveLogFile(logs);
			printer.println("Full Log file saved.");
		} else {
			printer.println("***Won't save FULL Log file to Database.");
		}
	}

	private static AnalysisMethod createAnalysisMethod() throws InvalidAttributesException {
		if (duration.equals("hourly")) {
			return AnalysisMethod.createHourlyMethod(startDate, threshold);
		} else if (duration.equals("daily")) {
			return AnalysisMethod.createDailyMethod(startDate, threshold);
		}
		throw new InvalidAttributesException("duration: " + duration + " not defined.");
	}

	private static List<Log> parseLogFile() {
		List<Log> logs = LogParser.parse(lines);
		return logs;
	}

	private static void loadLogFile() throws IOException {
		lines = FileTool.readLines(logFilePath);
	}

	private static void readArguments(String[] args) throws ArrayIndexOutOfBoundsException {
		printArguments(args);
		try {
			logFilePath = args[0].substring(args[0].indexOf("=") + 1);
			startDate = args[1].substring(args[1].indexOf("=") + 1);
			duration = args[2].substring(args[2].indexOf("=") + 1);
			threshold = Integer.parseInt(args[3].substring(args[3].indexOf("=") + 1));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new ArrayIndexOutOfBoundsException(
					"ERROR - Needed params:\n--accesslog\n--startDate\n--duration\n--threshold\nError: args list length: "
							+ args.length + ". Some params error are missing.");
		}

	}

	private static void printArguments(String[] args) {
		printer.println("User arguments: ");
		if (args.length == 0) {
			throw new InvalidParameterException("ERROR - user input cannot be empty.");
		}
		for (String string : args) {
			printer.println(string);
		}
	}

	private static void askForDatabaseCredentials() {
		System.out.println("\nWould you like to use Database (press enter to NO)? Please enter (y/n):");
		String input = reader.nextLine();

		if (input.isEmpty() || input.equals("n")) {
			printer.println("\nLogs won't be saved to Database.");
			printer.println("\nContinuing program execution...");
		} else if (input.equals("y")) {
			shouldSaveToDatabase = true;
			showDefaultDatabaseConnection();

			System.out.println(
					"Would like to change database connection (press enter to use default connection)? Please enter (y/n):");
			String change = reader.nextLine();

			if (change.equals("y")) {
				readNewDatabaseConfiguration();
			}

		} else {
			printer.println("***Please enter a valid option: (y == yes) or (n = no)\n");
			askForDatabaseCredentials();
		}
	}

	private static void showDefaultDatabaseConnection() {
		System.out.println("\n******** DEFAULT DATABASE CONNECTION ********");
		System.out.println("User=root");
		System.out.println("Password=123456");
		System.out.println("Host=localhost:3306");
		System.out.println("Database=WALLETHUB_DB");
		System.out.println("*********************************************\n");
	}

	private static void readNewDatabaseConfiguration() {
		System.out.println("Enter User:");
		String user = reader.nextLine();

		System.out.println("Enter Password:");
		String passwd = reader.nextLine();

		System.out.println("Enter Host:");
		String host = reader.nextLine();

		System.out.println("Enter Database:");
		String database = reader.nextLine();

		databaseConn = new DatabaseConnection(user, passwd, host, database);
	}

}
