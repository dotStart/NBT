package io.github.lordakkarin.nbt.tree;

import io.github.lordakkarin.nbt.event.TagType;
import io.github.lordakkarin.nbt.event.TagVisitor;
import edu.umd.cs.findbugs.annotations.NonNull;

/**
 * Represents a basic NBT tag.
 *
 * @author <a href="mailto:johannesd@torchmind.com">Johannes Donath</a>
 */
public interface Tag {

  /**
   * Passes the entirety of a tag and all of its children to a visitor.
   *
   * @param visitor a visitor.
   */
  void accept(@NonNull TagVisitor visitor);

  /**
   * Retrieves the tag's type.
   *
   * @return a type.
   */
  @NonNull
  TagType getType();
}
