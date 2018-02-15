package io.github.lordakkarin.nbt.tree;

import edu.umd.cs.findbugs.annotations.NonNull;
import io.github.lordakkarin.nbt.event.TagType;
import io.github.lordakkarin.nbt.event.TagVisitor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * Represents an NBT tag which contains an arbitrarily sized list of another NBT value type.
 *
 * @author <a href="mailto:johannesd@torchmind.com">Johannes Donath</a>
 */
public class ListTag<T extends Tag> implements Iterable<T>, Tag {

  private final List<T> elements = new ArrayList<>();
  private final List<T> view = Collections.unmodifiableList(this.elements);
  private TagType type;

  public ListTag() {
    this(TagType.END);
  }

  public ListTag(@NonNull TagType type) {
    this.type = type;
  }

  public ListTag(@NonNull TagType type, @NonNull T... elements) {
    this(type);

    this.addAll(Arrays.asList(elements));
  }

  public ListTag(@NonNull TagType type, @NonNull Collection<? extends T> elements) {
    this(type);

    this.addAll(elements);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void accept(@NonNull TagVisitor visitor) {
    visitor.visitList(this.type, this.elements.size());
    this.elements.forEach((e) -> e.accept(visitor));
  }

  public void add(@NonNull T element) {
    if (this.type == TagType.END) {
      this.type = element.getType();
    }

    if (this.type != element.getType()) {
      throw new IllegalArgumentException(
          "Cannot add tag of type " + element.getType() + " to " + this.type + " list");
    }

    this.elements.add(element);
  }

  public void addAll(@NonNull T... elements) {
    this.addAll(Arrays.asList(elements));
  }

  public void addAll(@NonNull Collection<? extends T> elements) {
    elements.forEach(this::add);
  }

  private void cleanup() {
    if (this.elements.size() == 0) {
      this.type = TagType.END;
    }
  }

  public void clear() {
    this.elements.clear();
    this.type = TagType.END;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof ListTag)) {
      return false;
    }

    ListTag<?> listTag = (ListTag<?>) o;
    return this.type == listTag.type &&
        Objects.equals(this.elements, listTag.elements);
  }

  @NonNull
  public T get(int index) {
    return this.elements.get(index);
  }

  /**
   * {@inheritDoc}
   */
  @NonNull
  @Override
  public TagType getType() {
    return TagType.LIST;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {
    return Objects.hash(this.type, this.elements);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Iterator<T> iterator() {
    return this.view.iterator();
  }

  public void remove(int index) {
    this.elements.remove(index);
    this.cleanup();
  }

  public void remove(@NonNull T element) {
    this.elements.remove(element);
    this.cleanup();
  }

  public void removeAll(@NonNull T... elements) {
    this.removeAll(Arrays.asList(elements));
  }

  public void removeAll(@NonNull Collection<? extends T> elements) {
    this.elements.removeAll(elements);
    this.cleanup();
  }

  public int size() {
    return this.elements.size();
  }
}
