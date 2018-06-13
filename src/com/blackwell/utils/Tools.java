package com.blackwell.utils;

import java.util.Random;

import com.blackwell.CoordCube;
import com.blackwell.CubieCube;
import com.blackwell.FaceCube;

public class Tools {
	/**
	 * Generates a random cube.
	 * @return A random cube in the string representation. Each cube of the cube space has the same probability.
	 */
	public static String randomCube() {
		CubieCube cc = new CubieCube();
		Random gen = new Random();
		cc.setFlip((short) gen.nextInt(CoordCube.N_FLIP));
		cc.setTwist((short) gen.nextInt(CoordCube.N_TWIST));
		do {
			cc.setURFtoDLB(gen.nextInt(CoordCube.N_URFtoDLB));
			cc.setURtoBR(gen.nextInt(CoordCube.N_URtoBR));
		} while ((cc.edgeParity() ^ cc.cornerParity()) != 0);
		FaceCube fc = cc.toFaceCube();
		return fc.to_String();
	}
}
