package jilps.cop4j.algebra;

/**
 * @author Sebastian Riedel
 */
public interface Cop4JLinAlgPackage {
  Cop4JVector createVector(int size);
  Cop4JMatrix createMatrix(int rows, int columns);

}
