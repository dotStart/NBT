package io.github.lordakkarin.nbt.tree;

import io.github.lordakkarin.nbt.event.TagType;
import io.github.lordakkarin.nbt.event.TagVisitor;
import java.util.Arrays;
import java.util.Iterator;
import edu.umd.cs.findbugs.annotations.NonNull;

/**
 * @author <a href="mailto:johannesd@torchmind.com">Johannes Donath</a>
 */
public class ByteArrayTag implements Iterable<Byte>, Tag {

  private byte[] value;

  public ByteArrayTag() {
    this.value = new byte[0];
  }

  public ByteArrayTag(@NonNull byte[] value) {
    this.value = value;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void accept(@NonNull TagVisitor visitor) {
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

  public byte get(int index) {
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
    return TagType.BYTE_ARRAY;
  }

  @NonNull
  public byte[] getValue() {
    return this.value;
  }

  public void setValue(@NonNull byte[] value) {
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

  public void set(int index, byte value) {
    this.value[index] = value;
  }
}
