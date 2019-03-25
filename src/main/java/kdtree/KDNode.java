package kdtree;

import geometry.Coordinate;

/**
 * KDNode: An object that is stored within a KD tree.
 * Has up to two children and stores data about a KD object.
 *
 * @param <T> : A Coordinate that who's data will be stored within the node.
 */
public class KDNode<T extends Coordinate> {
  private KDNode<T> left;
  private KDNode<T> right;
  private T data;
  private int axis;

  /**
   * Constructor for a KD Node.
   *
   * @param data : The KD object to be stored.
   * @param axis : The axis that is significant to the node.
   */
  public KDNode(T data, int axis) {
    this.data = data;
    this.axis = axis;
  }

  /**
   * Getter for KD object.
   *
   * @return KD Object in node
   */
  public T getData() {
    return this.data;
  }

  /**
   * Gets for the left child of the node.
   *
   * @return Left child of the node.
   */
  public KDNode<T> getLeft() {
    return left;
  }

  /**
   * Sets the left child of the node equal to the node passed in.
   *
   * @param left : node to set left child to.
   */
  public void setLeft(KDNode<T> left) {
    this.left = left;
  }

  /**
   * Gets for the right child of the node.
   *
   * @return Right child of the node.
   */
  public KDNode<T> getRight() {
    return right;
  }

  /**
   * Sets the right child of the node equal to the node passed in.
   *
   * @param right node to set right child to.
   */
  public void setRight(KDNode<T> right) {
    this.right = right;
  }

  /**
   * Gets the relevant axis of the node.
   * @return the relevant axis of the node.
   */
  public int getAxis() {
    return this.axis;
  }

}
