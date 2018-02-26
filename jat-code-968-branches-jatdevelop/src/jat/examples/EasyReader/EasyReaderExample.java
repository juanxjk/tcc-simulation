package jat.examples.EasyReader;

import jat.core.util.EasyReader;
import jat.core.util.ResourceLoader;

import java.net.URL;

public class EasyReaderExample {

	public static void main(String argv[]) {

		EasyReaderExample ex=new EasyReaderExample();
		URL myURL;
		String fileName = null;
		
		String relative_path="thefile.txt";
		try {

			ResourceLoader c=new ResourceLoader();
			myURL = c.loadURL(ex.getClass(), relative_path);
			fileName = myURL.getPath();


		} catch (Exception e) {
			System.err.println("File not found:" + relative_path);
			System.exit(0);
		}

		EasyReader inFile = new EasyReader(fileName);
		if (inFile.bad()) {
			System.err.println("Can't open " + fileName);
			System.exit(1);
		}

		String firstLine = inFile.readLine();
		if (!inFile.eof()) // or: if (firstLine != null)
			System.out.println("The first line is : " + firstLine);

		inFile.close(); // optional

	}
}
