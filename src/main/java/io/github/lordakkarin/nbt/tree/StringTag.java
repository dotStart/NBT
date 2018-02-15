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
public class StringTag implements Tag {

  private String value;

  public StringTag() {
    this.value = "";
  }

  public StringTag(String value) {
    this.value = value;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void accept(@Nonnull TagVisitor visitor) {
    visitor.visitString(this.value);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof StringTag)) {
      return false;
    }

    StringTag stringTag = (StringTag) o;
    return Objects.equals(this.value, stringTag.value);
  }

  /**
   * {@inheritDoc}
   */
  @Nonnull
  @Override
  public TagType getType() {
    return TagType.STRING;
  }

  @Nonnull
  public String getValue() {
    return this.value;
  }

  public void setValue(@Nonnull String value) {
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
