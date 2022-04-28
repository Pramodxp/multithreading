package com.pramod.performanceOptimization.example1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ThroughputHttpServer {
	public static final String INPUT_FILE = "";

	public static void main(String[] args) throws IOException {
		new String(Files.readAllBytes(Paths.get(INPUT_FILE)));
	}

	public static void startServer(String text) {

	}
}
