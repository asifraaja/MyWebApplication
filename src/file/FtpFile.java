package file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FtpFile {
	private FileReader fr;
	private FileWriter fw;
	
	public FtpFile(String src, String dest) {
		try {
			fr = new FileReader(src);
			fw = new FileWriter(dest);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public FtpFile(File src, File dest) {
		try {
			fr = new FileReader(src);
			fw = new FileWriter(dest);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void ftpTheFile() {
		BufferedReader br = null;
		BufferedWriter bw = null;
		try {
			br = new BufferedReader(fr);
			bw = new BufferedWriter(fw);
			
			int data;
			while((data=br.read())!=-1) {
				bw.write(data);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				bw.flush();
				bw.close();
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("File Uploaded");
	}
	
	public static void main(String[] args) {
		String src = "/Users/m0a00pf/Documents/Tutorials/Books/Python Book.pdf";
		String dest = "/Users/m0a00pf/Documents/Parse.pdf";
		FtpFile ftp = new FtpFile(src, dest);
		ftp.ftpTheFile();
	}
	
}
