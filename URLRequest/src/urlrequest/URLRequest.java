/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package urlrequest;

import com.prt.URLHelper;

/**
 *
 * @author P-ratt
 */
public class URLRequest {
//URL url = new URL("http://www.omdbapi.com/?apikey=3851964&s=" + answer);

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		URLHelper helper = new URLHelper();
		helper.start();
	}

}
