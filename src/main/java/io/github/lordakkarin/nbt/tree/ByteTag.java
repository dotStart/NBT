package io.github.lordakkarin.nbt.tree;

import io.github.lordakkarin.nbt.event.TagType;
import io.github.lordakkarin.nbt.event.TagVisitor;
import java.util.Objects;
import edu.umd.cs.findbugs.annotations.NonNull;

/**
 * @author <a href="mailto:johannesd@torchmind.com">Johannes Donath</a>
 */
public class ByteTag implements Tag {

  private byte value;

  public ByteTag() {
  }

  public ByteTag(byte value) {
    this.value = value;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void accept(@NonNull TagVisitor visitor) {
    visitor.visitByte(this.value);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof ByteTag)) {
      return false;
    }

    ByteTag byteTag = (ByteTag) o;
    return this.value == byteTag.value;
  }

  /**
   * {@inheritDoc}
   */
  @NonNull
  @Override
  public TagType getType() {
    return TagType.BYTE;
  }

  public byte getValue() {
    return this.value;
  }

  public void setValue(byte value) {
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
