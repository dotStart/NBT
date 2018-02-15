package io.github.lordakkarin.nbt.tree;

import io.github.lordakkarin.nbt.event.TagType;
import io.github.lordakkarin.nbt.event.TagVisitor;
import java.util.Objects;
import edu.umd.cs.findbugs.annotations.NonNull;

/**
 * Represents an NBT tag which contains an arbitrarily sized character sequence.
 *
 * @author <a href="mailto:johannesd@torchmind.com">Johannes Donath</a>
 */
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
  public void accept(@NonNull TagVisitor visitor) {
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
  @NonNull
  @Override
  public TagType getType() {
    return TagType.STRING;
  }

  @NonNull
  public String getValue() {
    return this.value;
  }

  public void setValue(@NonNull String value) {
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
