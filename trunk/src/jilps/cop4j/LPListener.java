package jilps.cop4j;

import java.util.List;

/**
 * @author Sebastian Riedel
 */
public interface LPListener {
  void columnAdded(LPColumn column);
  void columnsAdded(List<LPColumn> columns);
  void rowAdded(LPRow row);
  void rowsAdded(List<LPRow> rows);
}
