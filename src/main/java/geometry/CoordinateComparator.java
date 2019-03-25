package geometry;

import java.util.Comparator;
import java.util.List;

/**
 * Coordinate Comparator: Comparator for two KD objects along a particular axis.
 */
public class CoordinateComparator implements Comparator<Coordinate> {
  private int axis;

  /**
   * The constructor for the comparator.
   * Takes in the axis that the objects will be compared upon.
   *
   * @param axis : The axis to compare on.
   */
  public CoordinateComparator(int axis) {
    this.axis = axis;
  }

  @Override
  public int compare(Coordinate o1, Coordinate o2) {
    List<Double> firstVals = o1.getCoordinates();
    List<Double> secondVals = o2.getCoordinates();
    return Double.compare(firstVals.get(this.axis), secondVals.get(this.axis));
  }
}
