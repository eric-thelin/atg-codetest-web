package se.ericthelin.atg.codetest.web.support;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

class RealFileSystem implements FileSystem {
	@Override
	public void write(byte[] source, File destination) {
		if (!destination.getParentFile().exists() && !destination.getParentFile().mkdirs()) {
			System.err.println("Could not create screenshot directory");
		}
		try {
			Files.write(destination.toPath(), source);
		} catch (IOException e) {
			System.err.println("Could not save screenshot");
		}
	}
}
