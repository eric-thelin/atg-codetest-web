package se.ericthelin.atg.codetest.web.support;

import java.io.File;
import java.io.IOException;

interface FileSystem {
	void write(byte[] source, File destination) throws IOException;
}
