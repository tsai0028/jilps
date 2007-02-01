package jilps.cop4j.algebra;

/**
 * @author Sebastian Riedel
 */
public interface Cop4JOperator {
  void visitDenseVector(Cop4JVectorDense vector, int argumentIndex);
}
