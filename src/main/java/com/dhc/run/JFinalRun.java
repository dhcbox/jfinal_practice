package com.dhc.run;

import com.jfinal.core.JFinal;

public class JFinalRun {

	public static void main(String[] args) {
		JFinal.start("src/main/webapp", 80, "/", 5);
	}

}
