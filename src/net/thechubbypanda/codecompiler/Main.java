package net.thechubbypanda.codecompiler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Main {

	private Main(String path) {
		File srcDir = new File(path);
		ArrayList<File> allFiles = getFilesInDir(srcDir);

		Collections.sort(allFiles, new Comparator<File>() {
			public int compare(File f1, File f2) {
				return f1.getName().compareTo(f2.getName());
			}
		});

		File outputFile = new File("compiledCode.txt");

		try {
			FileOutputStream os = new FileOutputStream(outputFile);
			FileInputStream is;
			byte[] newLine = System.getProperty("line.separator").getBytes();
			for (File file : allFiles) {
				is = new FileInputStream(file);
				System.out.println(file.getName());
				os.write(file.getName().getBytes());
				os.write(newLine);
				os.write(newLine);
				byte b;
				while ((b = (byte) is.read()) != -1) {
					os.write(b);
				}
				os.write(newLine);
				os.write(newLine);
			}

			os.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("\nWritten all files successfully!\nOutput file: " + outputFile.getAbsolutePath());
	}

	private ArrayList<File> getFilesInDir(File dir) {
		ArrayList<File> output = new ArrayList<File>();
		File[] files = dir.listFiles();
		for (File file : files) {
			if (!file.isDirectory()) {
				output.add(file);
			} else {
				output.addAll(getFilesInDir(file));
			}
		}
		return output;
	}

	public static void main(String[] args) {
		if (args.length > 0) {
			new Main(args[0]);
		} else {
			System.err.println("Please provide absolute path to code directory e.g: \njava -jar codeCompile.jar \"C:/dev/prject/src\"");
		}
	}
}
