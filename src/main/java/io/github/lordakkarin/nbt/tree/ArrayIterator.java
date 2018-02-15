package io.github.lordakkarin.nbt.tree;

import java.util.Iterator;
import edu.umd.cs.findbugs.annotations.NonNull;

/**
 * Provides an iterator implementation which is capable of iterating over arrays directly.
 *
 * @author <a href="mailto:johannesd@torchmind.com">Johannes Donath</a>
 */
class ArrayIterator<E> implements Iterator<E> {

  private final E[] array;
  private int index;

  ArrayIterator(@NonNull E[] array) {
    this.array = array;
  }

  public static <E> ArrayIterator<E> of(@NonNull E[] array) {
    return new ArrayIterator<>(array);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean hasNext() {
    return (this.index + 1) <= this.array.length;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public E next() {
    return this.array[++this.index];
  }
}
