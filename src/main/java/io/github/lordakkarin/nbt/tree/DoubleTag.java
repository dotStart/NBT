package io.github.lordakkarin.nbt.tree;

import io.github.lordakkarin.nbt.event.TagType;
import io.github.lordakkarin.nbt.event.TagVisitor;
import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.concurrent.NotThreadSafe;

/**
 * @author <a href="mailto:johannesd@torchmind.com">Johannes Donath</a>
 */
@NotThreadSafe
public class DoubleTag implements Tag {

  private double value;

  public DoubleTag() {
  }

  public DoubleTag(double value) {
    this.value = value;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void accept(@Nonnull TagVisitor visitor) {
    visitor.visitDouble(this.value);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof DoubleTag)) {
      return false;
    }

    DoubleTag doubleTag = (DoubleTag) o;
    return Double.compare(doubleTag.value, this.value) == 0;
  }

  /**
   * {@inheritDoc}
   */
  @Nonnull
  @Override
  public TagType getType() {
    return TagType.DOUBLE;
  }

  public double getValue() {
    return this.value;
  }

  public void setValue(double value) {
    this.value = value;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {
    return Objects.hash(this.value);
  }
}
