package jilps.cop4j.algebra;

/**
 * @author Sebastian Riedel
 */
public interface Cop4JMatrixView extends Cop4JMatrix {
  void swapRows(int in, int out);
  void swapColumns(int in, int out);

}
