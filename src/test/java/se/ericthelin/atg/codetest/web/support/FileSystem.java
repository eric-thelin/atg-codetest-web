package se.ericthelin.atg.codetest.web.support;

import java.io.File;

interface FileSystem {
	void write(byte[] source, File destination);
}
