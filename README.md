<h1 align="center">
    <img src="https://raw.githubusercontent.com/dmitryblackwell/RubiksCubeSolver/master/src/res/rubik.png" alt="rubik">
	RubiksCubeSolver
</h1>

<p align="center">
   *Implementation of Kociemba algorithm for solving Rubik's cube.*
</p>


## Getting Started

### Installation 
1. Clone project using git panel or console. If you nor familiar with gir, you can simply download ZIP-archive.

```git
git clone https://github.com/dmitryblackwell/RubiksCubeSolver.git
```

1. Setup dependencies (if needed)

Check for jdk setup. It is recommended to use JDK 1.8 or 1.9.
To setup jdk go to
```
File -> Project Structure -> Project -> Project SDK
```

1. Create out folder

Create folder for compiled classed (out by default) and add it to your project structure.
```
File -> Project Structure -> Project -> Project Compiler output
```

### Using .jar file

You can simply run **bin/RubiksCubeSolver.jar** to see how program work. 

## Screenshots

### Solve

To solve just enter your colors to stickers. To do it just click on the color palette and the on sticker. Or just scramble the cube.

<p align="center">
  <img src="https://raw.githubusercontent.com/dmitryblackwell/RubiksCubeSolver/master/img/solve.gif" alt="solve" width="500">
</p>

### Enter

Also you can enter your own rotations. Just click enter and type your rotations with capital letters. If you want to get solved cube, just left field empty.

<p align="center">
  <img src="https://raw.githubusercontent.com/dmitryblackwell/RubiksCubeSolver/master/img/enter.gif" alt="enter" width="500">
</p>

## How to use?

### Button definition

To solve your cube just enter facelets in the GUI. First click a button on color palette and after a sticker, that you want to pain.
If you want to scramble cube, click that button. To enter rotations, click enter end type your rotation sequins using spaces. 
If you left field empty - cube will be solved.  
 
### Side definition

1. ![#FFFFFF](https://placehold.it/15/FFFFFF/000000?text=+) U - up
1. ![#008000](https://placehold.it/15/008000/000000?text=+) F - face
1. ![#FF0000](https://placehold.it/15/FF0000/000000?text=+) R - right
1. ![#FFFF00](https://placehold.it/15/FFFF00/000000?text=+) D - down
1. ![#FFA500](https://placehold.it/15/FFA500/000000?text=+) L - left
1. ![#0000FF](https://placehold.it/15/0000FF/000000?text=+) B - back

## How algorithm works?

All cube solving process split into two subgroups. In each phase only special moves allowed.
*Simply program just move cube from start to middle state, and after to solve state, using allowed below moves.*

```
    G0 = <U, D, L, R, F, B>
    G1 = <U, D, L2, R2, F2, B2>
```

In phase 1, the algorithm looks for maneuvers which will transform a scrambled cube to G1.
That is, the orientations of corners and edges have to be constrained and the edges of the UD-slice have to be transferred into that slice.

In phase 2 the algorithm restores the cube in the subgroup G1, using only moves of this subgroup. 
It restores the permutation of the 8 corners, the permutation of the 8 edges of the U-face and D-face and the permutation of the 4 UD-slice edges. 
 
The algorithm does not stop when a first solution is found but continues to search for shorter solutions by carrying out phase 2 from suboptimal solutions of phase 1. 

> [origin](http://kociemba.org/cube.htm)

## Running the tests

In `junit` folder you can find simple test for Search algorithm. It is randomly scramble cube and then solve it.
Only if cube in it is final state, test is complete successfully.

## Code Example

Simple example of how searching algorithm is called and where.

```java
public class Main extends JFrame {
    
    // ...
    
    private void solveCube() {
    
        String cubeString = getCubeInput();
        System.out.println(cubeString);
    
        String result = Search.solution(cubeString);
        resultText.setText(result);
    
        rotations = result.split(" ");
        index = 0;
        System.out.println(Arrays.toString(rotations));
    
    }
}
```

## Versioning
We use [SemVer](http://semver.org/) for versioning. 

Current version is **1.0.1**

## Author
* **Dmitry Blackwell** - *Initial work, Algorithm, GUI.* - [@dmitryblackwell](https://github.com/dmitryblackwell)


## License
This project is licensed under the Apache License 2.0 - see the [LICENSE.md](LICENSE.md) file for details.
Apache License 2.0 © [@dmitryblackwell](https://github.com/dmitryblackwell)

