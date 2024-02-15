package pl.npesystem.services;

import java.text.*;
import java.time.*;
import java.time.format.*;
import java.util.*;
import java.util.regex.*;

public class LogInfo {
	final String regex = "(\\d{1,2}\\.\\d{1,2}\\W\\d{1,2}:\\d{1,2}:\\d{1,2},\\d{1,3})\\s+(Thread-\\d{1,4}\\s+\\(Ac[a-z]+\\.\\.[0-9]+\\)|EJB\\Wasync\\W-\\W[\\w\\.]+\\W-\\W[\\w\\.]+)\\s+(DEBUG|WARN|INFO|TRACE)\\s+(\\S*)\\s+(.*)";
	private LocalDate date;
	private String threadName;
	private LogLevel logLevel;
	private String className;
	private String message;

	public LogInfo(LocalDate date, String threadName, LogLevel logLevel, String className, String message) {
		this.date = date;
		this.threadName = threadName;
		this.logLevel = logLevel;
		this.className = className;
		this.message = message;
	}

	public LogInfo(String row) {


		try {
			final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
			final Matcher matcher = pattern.matcher(row);

//			while (matcher.find()) {
//				System.out.println("Full match: " + matcher.group(0));
//
//				for (int i = 1; i <= matcher.groupCount(); i++) {
//					System.out.println("Group " + i + ": " + matcher.group(i));
//				}
//			}
			if (matcher.find()) {
				SimpleDateFormat sdf = new SimpleDateFormat("dd.MM HH:mm:ss,SSS");
				try {
					Date parse = sdf.parse(matcher.group(1));
					parse.setYear(2024 - 1900);
					this.date = parse.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

				} catch (Exception e) {

				}

				this.threadName = matcher.group(2);
				this.logLevel = LogLevel.valueOf(matcher.group(3));
				this.className = matcher.group(4);
				this.message = matcher.group(5);
			} else {
				System.out.println(row);
			}


		} catch (IllegalStateException e) {
			System.out.println("No match found");
		}

	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getThreadName() {
		return threadName;
	}

	public void setThreadName(String threadName) {
		this.threadName = threadName;
	}

	public LogLevel getLogLevel() {
		return logLevel;
	}

	public void setLogLevel(LogLevel logLevel) {
		this.logLevel = logLevel;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
