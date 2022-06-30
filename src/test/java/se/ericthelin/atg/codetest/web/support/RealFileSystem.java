package se.ericthelin.atg.codetest.web.support;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

class RealFileSystem implements FileSystem {
	@Override
	public void write(byte[] source, File destination) throws IOException {
		if (!destination.getParentFile().exists() && !destination.getParentFile().mkdirs()) {
			throw new IOException(String.format(
					"Could not create screenshot directory \"%s\"",
					destination.getParentFile()
			));
		}
		Files.write(destination.toPath(), source);
	}
}
