package se.ericthelin.atg.codetest.web.support;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.startsWith;
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
	void abortsWhenUnableToCreateParentDirectories() {
		// Given
		File readOnlyDirectory = new File(root, "a/b/c");
		assertTrue(readOnlyDirectory.mkdirs());
		assertTrue(readOnlyDirectory.setWritable(false));
		File destination = new File(readOnlyDirectory, "d/out.txt");

		// When
		IOException actual = assertThrows(IOException.class, () ->
				subject.write("foo".getBytes(StandardCharsets.UTF_8), destination)
		);

		// Then
		assertThat(actual.getMessage(), allOf(
				startsWith("Could not create directory"),
				containsString(readOnlyDirectory.getAbsolutePath())
		));
	}

	private String contentsOf(File source) throws IOException {
		return Files.readString(source.toPath());
	}
}
