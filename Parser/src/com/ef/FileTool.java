package com.ef;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileTool {

	public FileTool() {

	}

	public static List<String> readLines(String path) throws IOException {
		BufferedReader buffer = null;
		try {
			buffer = new BufferedReader(new FileReader(path));
			return readLines(buffer);
		} finally {
			if (buffer != null) {
				try {
					buffer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static List<String> readLines(BufferedReader fileBuffer) throws IOException {
		List<String> output = new ArrayList<>();
		String line = "";
		while ((line = fileBuffer.readLine()) != null) {
			output.add(line);
		}
		return output;
	}
}// fim classe
