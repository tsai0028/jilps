package jilps.cop4j.algebra;

/**
 * @author Sebastian Riedel
 */
public class Cop4JLinAlgPackageDense implements Cop4JLinAlgPackage {
  public Cop4JVector createVector(int size) {
    return new Cop4JVectorDense(size);
  }

  public Cop4JMatrix createMatrix(int rows, int columns) {
    return new Cop4JMatrixDense(rows,columns);
  }
}
