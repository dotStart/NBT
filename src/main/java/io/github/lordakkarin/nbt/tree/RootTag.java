package io.github.lordakkarin.nbt.tree;

import io.github.lordakkarin.nbt.event.TagVisitor;
import java.util.Map;
import java.util.Objects;
import javax.annotation.Nonnull;

/**
 * Provides a root NBT tag which acts as the implied compound at the heart of the file.
 *
 * @author <a href="mailto:johannesd@torchmind.com">Johannes Donath</a>
 */
public class RootTag extends CompoundTag {

  private String name;

  public RootTag(@Nonnull String name) {
    super();
    this.name = name;
  }

  public RootTag(@Nonnull String name, @Nonnull Map<String, ? extends Tag> elements) {
    super(elements);
    this.name = name;
  }

  @Override
  public void accept(@Nonnull TagVisitor visitor) {
    visitor.visitKey(this.name);
    super.accept(visitor);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof RootTag)) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }

    RootTag entries = (RootTag) o;
    return Objects.equals(this.name, entries.name);
  }

  @Nonnull
  public String getName() {
    return this.name;
  }

  public void setName(@Nonnull String name) {
    this.name = name;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), this.name);
  }
}
