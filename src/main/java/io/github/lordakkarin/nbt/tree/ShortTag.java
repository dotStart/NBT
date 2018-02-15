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
public class ShortTag implements Tag {

  private short value;

  public ShortTag() {
  }

  public ShortTag(short value) {
    this.value = value;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void accept(@Nonnull TagVisitor visitor) {
    visitor.visitShort(this.value);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof ShortTag)) {
      return false;
    }

    ShortTag shortTag = (ShortTag) o;
    return this.value == shortTag.value;
  }

  /**
   * {@inheritDoc}
   */
  @Nonnull
  @Override
  public TagType getType() {
    return TagType.SHORT;
  }

  public short getValue() {
    return this.value;
  }

  public void setValue(short value) {
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
