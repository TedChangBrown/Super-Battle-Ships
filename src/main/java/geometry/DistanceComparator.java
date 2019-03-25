package geometry;


import java.util.Comparator;

/**
 * Distance Comparator: Class to compare the euclidian
 * distance from a specified target between two nodes.
 * Has the ability to organize
 * by higher or lower euclidian distance.
 *
 * @param <E> : The object contained within the KDNode.
 */
public class DistanceComparator<E extends Coordinate>
        implements Comparator<E> {
  private Coordinate target;
  private Boolean ascending;

  /**
   * Constructor for comparator.
   *
   * @param target    : The target that the distance of the nodes
   *                  will be measured by.
   * @param ascending : Whether or not the comparator will favor
   *                  lower or higher distances from the target.
   */
  public DistanceComparator(Coordinate target, Boolean ascending) {
    this.target = target;
    this.ascending = ascending;
  }

  @Override
  public int compare(E o1, E o2) {
    if (this.ascending) {
      return Double.compare(o1.getEuclidianDistance(this.target),
              o2.getEuclidianDistance(this.target));
    } else {
      return Double.compare(o2.getEuclidianDistance(this.target),
              o1.getEuclidianDistance(this.target));
    }

  }
}
