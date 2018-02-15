package io.github.lordakkarin.nbt.tree;

import io.github.lordakkarin.nbt.event.TagType;
import io.github.lordakkarin.nbt.event.TagVisitor;
import java.util.Objects;
import edu.umd.cs.findbugs.annotations.NonNull;

/**
 * @author <a href="mailto:johannesd@torchmind.com">Johannes Donath</a>
 */
public class LongTag implements Tag {

  private long value;

  public LongTag() {
  }

  public LongTag(long value) {
    this.value = value;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void accept(@NonNull TagVisitor visitor) {
    visitor.visitLong(this.value);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof LongTag)) {
      return false;
    }

    LongTag longTag = (LongTag) o;
    return this.value == longTag.value;
  }

  /**
   * {@inheritDoc}
   */
  @NonNull
  @Override
  public TagType getType() {
    return TagType.LONG;
  }

  public long getValue() {
    return this.value;
  }

  public void setValue(long value) {
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
