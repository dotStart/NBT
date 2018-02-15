package io.github.lordakkarin.nbt.tree;

import io.github.lordakkarin.nbt.event.TagType;
import io.github.lordakkarin.nbt.event.TagVisitor;
import java.util.Arrays;
import java.util.Iterator;
import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.concurrent.NotThreadSafe;

/**
 * @author <a href="mailto:johannesd@torchmind.com">Johannes Donath</a>
 */
@NotThreadSafe
public class ByteArrayTag implements Iterable<Byte>, Tag {

  private byte[] value;

  public ByteArrayTag() {
    this.value = new byte[0];
  }

  public ByteArrayTag(@Nonnull byte[] value) {
    this.value = value;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void accept(@Nonnull TagVisitor visitor) {
    visitor.visitByteArray(this.value.length);

    for (byte value : this.value) {
      visitor.visitByte(value);
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
    if (!(o instanceof ByteArrayTag)) {
      return false;
    }

    ByteArrayTag that = (ByteArrayTag) o;
    return Arrays.equals(this.value, that.value);
  }

  public byte get(@Nonnegative int index) {
    return this.value[index];
  }

  @Nonnegative
  public int getLength() {
    return this.value.length;
  }

  /**
   * {@inheritDoc}
   */
  @Nonnull
  @Override
  public TagType getType() {
    return TagType.BYTE_ARRAY;
  }

  @Nonnull
  public byte[] getValue() {
    return this.value;
  }

  public void setValue(@Nonnull byte[] value) {
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
  public Iterator<Byte> iterator() {
    Byte[] obj = new Byte[this.value.length];

    for (int i = 0; i < obj.length; ++i) {
      obj[i] = this.value[i];
    }

    return new ArrayIterator<>(obj);
  }

  public void set(@Nonnegative int index, byte value) {
    this.value[index] = value;
  }
}
