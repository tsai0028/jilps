package jilps.cop4j.algebra;

/**
 * @author Sebastian Riedel
 */
public interface Cop4JMatrixVisitor {

  void visitDenseMatrix(Cop4JMatrixDense matrix);

}
