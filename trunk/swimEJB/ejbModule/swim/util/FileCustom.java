package swim.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;

/**
 * Estensione di <code>File</code> con supporto all'eliminazione
 * ricorsiva di cartelle e del loro contenuto e alla copia di file
 * @author Giusto - Mazuran
 */
public class FileCustom extends File {

	private static final long serialVersionUID = -2348402858534009138L;
	private static final int BUFFER_SIZE = 1024 * 64;

	public FileCustom(String arg0) {
		super(arg0);
	}

	public FileCustom(URI arg0) {
		super(arg0);
	}

	public FileCustom(String arg0, String arg1) {
		super(arg0, arg1);
	}

	public FileCustom(File arg0, String arg1) {
		super(arg0, arg1);
	}
	
	/**
	 * @see java.io.File#listFiles()
	 */
	public FileCustom[] listFiles() {
		File[] files = super.listFiles();
		FileCustom[] fc = new FileCustom[files.length];
		for(int i = 0; i < files.length; i++) {
			fc[i] = new FileCustom(files[i].getAbsolutePath());
		}
		return fc;
	}
	
	/**
	 * Cancella una cartella e tutti i suoi figli, ricorsivamente
	 * @return <code>true</code> se l'eliminazione ha avuto successo,
	 * 			<code>false</code> altrimenti
	 */
	public boolean deleteDirectory() {
		if(this.exists() && this.isDirectory()) {
			FileCustom[] files = this.listFiles();
			for(FileCustom file: files) {
				if(file.isDirectory()) {
					file.deleteDirectory();
				} else {
					file.delete();
				}
			}
		}
		return this.delete();
	}
	
	public boolean copyTo(File dest) {
		try {
			InputStream in = new FileInputStream(this);
			OutputStream out = new FileOutputStream(dest);
			
			byte[] buf = new byte[BUFFER_SIZE];
			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			in.close();
			out.close();
			return true;
		} catch(Exception e) {
			return false;
		}
	}

}
