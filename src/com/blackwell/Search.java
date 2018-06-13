package com.blackwell;

//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++


/**
 * Class Search implements the Two-Phase-Algorithm.
 */
class Search {

	private static int[] ax = new int[31]; // The axis of the move
	private static int[] po = new int[31]; // The power of the move

	private static int[] flip = new int[31]; // phase1 coordinates
	private static int[] twist = new int[31];
	private static int[] slice = new int[31];

	private static int[] parity = new int[31]; // phase2 coordinates
	private static int[] URFtoDLF = new int[31];
	private static int[] FRtoBR = new int[31];
	private static int[] URtoUL = new int[31];
	private static int[] UBtoDF = new int[31];
	private static int[] URtoDF = new int[31];

	private static int[] minDistPhase1 = new int[31]; // IDA* distance do goal estimations
	private static int[] minDistPhase2 = new int[31];

	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	// generate the solution string from the array data
	private static String solutionToString(int length) {
		StringBuilder s = new StringBuilder();
		for (int i = 0; i < length; i++) {

			switch (ax[i]) {
                case 0:	s.append("U"); break;
                case 1:	s.append("R"); break;
                case 2:	s.append("F"); break;
                case 3: s.append("D"); break;
                case 4:	s.append("L"); break;
                case 5:	s.append("B"); break;
			}

			switch (po[i]) {
				case 1:	s.append(" "); break;
				case 2: s.append("2 ");	break;
				case 3: s.append("' "); break;
			}
		}
		return s.toString();
	}

	private static void axisInit(String facelets){
        FaceCube fc = new FaceCube(facelets);
        CubieCube cc = fc.toCubieCube();
        CoordCube cube = new CoordCube(cc);

        po[0] = 0;
        ax[0] = 0;
        flip[0] = cube.flip;
        twist[0] = cube.twist;
        parity[0] = cube.parity;
        slice[0] = cube.FRtoBR / 24;
        URFtoDLF[0] = cube.URFtoDLF;
        FRtoBR[0] = cube.FRtoBR;
        URtoUL[0] = cube.URtoUL;
        UBtoDF[0] = cube.UBtoDF;
    }

	static synchronized String solution(String facelets) {
	    axisInit(facelets);

		minDistPhase1[1] = 1;// else failure for depth=1, n=0
		int mv, n = 0;
		boolean busy = false;
		int depthPhase1 = 1;
		// +++++++++++++++++++ com.blackwell.Main loop ++++++++++++++++++++++++++++++++++++++++++
		do {
			do {
				if ((depthPhase1 - n > minDistPhase1[n + 1]) && !busy) {

                    // Initialize next move
					if (ax[n] == 0 || ax[n] == 3)
						ax[++n] = 1;
					else
					    ax[++n] = 0;
					po[n] = 1;

				} else if (++po[n] > 3) {

					do {// increment axis
						if (++ax[n] > 5) {
							if (n == 0) {
                                depthPhase1++;
                                ax[n] = 0;
                                po[n] = 1;
                                busy = false;
                                break;
							} else {
								n--;
								busy = true;
								break;
							}
						} else {
							po[n] = 1;
							busy = false;
						}
					} while (n != 0 && (ax[n - 1] == ax[n] || ax[n - 1] - 3 == ax[n]));

				} else
					busy = false;
			} while (busy);

			// +++++++++++++ compute new coordinates and new minDistPhase1 ++++++++++
			// if minDistPhase1 =0, the H subgroup is reached
			mv = 3 * ax[n] + po[n] - 1;
			flip[n + 1] = CoordCube.flipMove[flip[n]][mv];
			twist[n + 1] = CoordCube.twistMove[twist[n]][mv];
			slice[n + 1] = CoordCube.FRtoBR_Move[slice[n] * 24][mv] / 24;
			minDistPhase1[n + 1] = Math.max(CoordCube.getPruning(CoordCube.Slice_Flip_Prun, CoordCube.N_SLICE1 * flip[n + 1]
					+ slice[n + 1]), CoordCube.getPruning(CoordCube.Slice_Twist_Prun, CoordCube.N_SLICE1 * twist[n + 1]
					+ slice[n + 1]));
			// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

			if (minDistPhase1[n + 1] == 0 && n >= depthPhase1 - 5) {
				minDistPhase1[n + 1] = 10;// instead of 10 any value >5 is possible
                int s;
				if (n == depthPhase1 - 1 && (s = totalDepth(depthPhase1)) >= 0) {
					if (s == depthPhase1
							|| (ax[depthPhase1 - 1] != ax[depthPhase1] && ax[depthPhase1 - 1] != ax[depthPhase1] + 3))
						return solutionToString(s);
				}
			}
		} while (true);
	}

	// Apply phase2 of algorithm and return the combined
	// phase1 and phase2 depth. In phase2, only the moves
	// U,D,R2,F2,L2 and B2 are allowed.
	private synchronized static int totalDepth(int depthPhase1) {
		int mv, d1, d2;
		int maxDepthPhase2 = 10; // Allow only max 10 moves in phase2
		for (int i = 0; i < depthPhase1; i++) {
			mv = 3 * ax[i] + po[i] - 1;
			URFtoDLF[i + 1] = CoordCube.URFtoDLF_Move[URFtoDLF[i]][mv];
			FRtoBR[i + 1] = CoordCube.FRtoBR_Move[FRtoBR[i]][mv];
			parity[i + 1] = CoordCube.parityMove[parity[i]][mv];
		}

		if ((d1 = CoordCube.getPruning(CoordCube.Slice_URFtoDLF_Parity_Prun,
				(CoordCube.N_SLICE2 * URFtoDLF[depthPhase1] + FRtoBR[depthPhase1]) * 2 + parity[depthPhase1])) > maxDepthPhase2)
			return -1;

		for (int i = 0; i < depthPhase1; i++) {
			mv = 3 * ax[i] + po[i] - 1;
			URtoUL[i + 1] = CoordCube.URtoUL_Move[URtoUL[i]][mv];
			UBtoDF[i + 1] = CoordCube.UBtoDF_Move[UBtoDF[i]][mv];
		}
		URtoDF[depthPhase1] = CoordCube.MergeURtoULandUBtoDF[URtoUL[depthPhase1]][UBtoDF[depthPhase1]];

		if ((d2 = CoordCube.getPruning(CoordCube.Slice_URtoDF_Parity_Prun,
				(CoordCube.N_SLICE2 * URtoDF[depthPhase1] + FRtoBR[depthPhase1]) * 2 + parity[depthPhase1])) > maxDepthPhase2)
			return -1;

		if ((minDistPhase2[depthPhase1] = Math.max(d1, d2)) == 0)// already solved
			return depthPhase1;

		// now set up search

		int depthPhase2 = 1;
		int n = depthPhase1;
		boolean busy = false;
		po[depthPhase1] = 0;
		ax[depthPhase1] = 0;
		minDistPhase2[n + 1] = 1;// else failure for depthPhase2=1, n=0
		// +++++++++++++++++++ end initialization +++++++++++++++++++++++++++++++++
		do {
			do {
				if ((depthPhase1 + depthPhase2 - n > minDistPhase2[n + 1]) && !busy) {

					if (ax[n] == 0 || ax[n] == 3)// Initialize next move
					{
						ax[++n] = 1;
						po[n] = 2;
					} else {
						ax[++n] = 0;
						po[n] = 1;
					}
				} else if ((ax[n] == 0 || ax[n] == 3) ? (++po[n] > 3) : ((po[n] = po[n] + 2) > 3)) {
					do {// increment axis
						if (++ax[n] > 5) {
							if (n == depthPhase1) {
								if (depthPhase2 >= maxDepthPhase2)
									return -1;
								else {
									depthPhase2++;
									ax[n] = 0;
									po[n] = 1;
									busy = false;
									break;
								}
							} else {
								n--;
								busy = true;
								break;
							}

						} else {
							if (ax[n] == 0 || ax[n] == 3)
								po[n] = 1;
							else
								po[n] = 2;
							busy = false;
						}
					} while (n != depthPhase1 && (ax[n - 1] == ax[n] || ax[n - 1] - 3 == ax[n]));
				} else
					busy = false;
			} while (busy);
			// +++++++++++++ compute new coordinates and new minDist ++++++++++
			mv = 3 * ax[n] + po[n] - 1;

			URFtoDLF[n + 1] = CoordCube.URFtoDLF_Move[URFtoDLF[n]][mv];
			FRtoBR[n + 1] = CoordCube.FRtoBR_Move[FRtoBR[n]][mv];
			parity[n + 1] = CoordCube.parityMove[parity[n]][mv];
			URtoDF[n + 1] = CoordCube.URtoDF_Move[URtoDF[n]][mv];

			minDistPhase2[n + 1] = Math.max(CoordCube.getPruning(CoordCube.Slice_URtoDF_Parity_Prun, (CoordCube.N_SLICE2
					* URtoDF[n + 1] + FRtoBR[n + 1])
					* 2 + parity[n + 1]), CoordCube.getPruning(CoordCube.Slice_URFtoDLF_Parity_Prun, (CoordCube.N_SLICE2
					* URFtoDLF[n + 1] + FRtoBR[n + 1])
					* 2 + parity[n + 1]));
			// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

		} while (minDistPhase2[n + 1] != 0);
		return depthPhase1 + depthPhase2;
	}


}
