package io.github.lordakkarin.nbt.tree;

import edu.umd.cs.findbugs.annotations.NonNull;
import io.github.lordakkarin.nbt.event.TagType;
import io.github.lordakkarin.nbt.event.TagVisitor;
import java.util.Arrays;
import java.util.Iterator;

/**
 * Represents an NBT tag which contains a set of integer values.
 *
 * @author <a href="mailto:johannesd@torchmind.com">Johannes Donath</a>
 */
public class IntegerArrayTag implements Iterable<Integer>, Tag {

  private int[] value;

  public IntegerArrayTag() {
    this.value = new int[0];
  }

  public IntegerArrayTag(int[] value) {
    this.value = value;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void accept(@NonNull TagVisitor visitor) {
    visitor.visitIntegerArray(this.value.length);

    for (int value : this.value) {
      visitor.visitInteger(value);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof IntegerArrayTag)) {
      return false;
    }

    IntegerArrayTag that = (IntegerArrayTag) o;
    return Arrays.equals(this.value, that.value);
  }

  public int get(int index) {
    return this.value[index];
  }

  public int getLength() {
    return this.value.length;
  }

  /**
   * {@inheritDoc}
   */
  @NonNull
  @Override
  public TagType getType() {
    return TagType.INTEGER_ARRAY;
  }

  @NonNull
  public int[] getValue() {
    return this.value;
  }

  public void setValue(@NonNull int[] value) {
    this.value = value;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {
    return Arrays.hashCode(this.value);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Iterator<Integer> iterator() {
    Integer[] obj = new Integer[this.value.length];

    for (int i = 0; i < obj.length; ++i) {
      obj[i] = this.value[i];
    }

    return new ArrayIterator<>(obj);
  }

  public void set(int index, int value) {
    this.value[index] = value;
  }
}
