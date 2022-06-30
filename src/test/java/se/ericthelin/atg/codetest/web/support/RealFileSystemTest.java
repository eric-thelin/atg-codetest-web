package se.ericthelin.atg.codetest.web.support;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RealFileSystemTest {

	@TempDir
	private File root;
	private final RealFileSystem subject = new RealFileSystem();

	@Test
	void writesBytesToDestination() throws IOException {
		// Given
		File destination = new File(root, "out.txt");

		// When
		subject.write("foo".getBytes(StandardCharsets.UTF_8), destination);

		// Then
		assertTrue(destination.exists());
		assertEquals("foo", contentsOf(destination));
	}

	private String contentsOf(File source) throws IOException {
		return Files.readString(source.toPath());
	}
}
