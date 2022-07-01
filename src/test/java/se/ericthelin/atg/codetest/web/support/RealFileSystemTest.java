package se.ericthelin.atg.codetest.web.support;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RealFileSystemTest {

	@TempDir
	private File root;
	private final RealFileSystem subject = new RealFileSystem();

	@ParameterizedTest
	@ValueSource(strings = {
			"out.txt",
			"a/b/c/d/bar.png"
	})
	void writesBytesToDestination(String fileName) throws IOException {
		// Given
		File destination = new File(root, fileName);

		// When
		subject.write("foo".getBytes(StandardCharsets.UTF_8), destination);

		// Then
		assertTrue(destination.exists());
		assertEquals("foo", contentsOf(destination));
	}

	@Test
	void abortsWhenUnableToCreateParentDirectories() throws IOException {
		// Given
		File readOnlyDirectory = new File(root, "a/b/c");
		assertTrue(readOnlyDirectory.mkdirs());
		assertTrue(readOnlyDirectory.setWritable(false));
		File destination = new File(readOnlyDirectory, "d/out.txt");

		// When
		String message = assertThrows(IOException.class, () ->
				subject.write("foo".getBytes(StandardCharsets.UTF_8), destination)
		).getMessage();

		// Then
		assertAll("Unexpected message: " + message,
				() -> assertTrue(message.startsWith("Could not create directory")),
				() -> assertTrue(message.contains(readOnlyDirectory.getAbsolutePath()))
		);
	}

	private String contentsOf(File source) throws IOException {
		return Files.readString(source.toPath());
	}
}
