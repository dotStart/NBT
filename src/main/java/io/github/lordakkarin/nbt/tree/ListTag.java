package io.github.lordakkarin.nbt.tree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.concurrent.NotThreadSafe;

import io.github.lordakkarin.nbt.event.TagType;
import io.github.lordakkarin.nbt.event.TagVisitor;

/**
 * @author <a href="mailto:johannesd@torchmind.com">Johannes Donath</a>
 */
@NotThreadSafe
public class ListTag<T extends Tag> implements Iterable<T>, Tag {
    private final List<T> elements = new ArrayList<>();
    private final List<T> view = Collections.unmodifiableList(this.elements);
    private TagType type;

    public ListTag() {
        this(TagType.END);
    }

    public ListTag(@Nonnull TagType type) {
        this.type = type;
    }

    public ListTag(@Nonnull TagType type, @Nonnull T... elements) {
        this(type);

        this.addAll(Arrays.asList(elements));
    }

    public ListTag(@Nonnull TagType type, @Nonnull Collection<? extends T> elements) {
        this(type);

        this.addAll(elements);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void accept(@Nonnull TagVisitor visitor) {
        visitor.visitList(this.type, this.elements.size());
        this.elements.forEach((e) -> e.accept(visitor));
    }

    /**
     * {@inheritDoc}
     */
    @Nonnull
    @Override
    public TagType getType() {
        return TagType.LIST;
    }

    public void add(@Nonnull T element) {
        if (this.type == TagType.END) {
            this.type = element.getType();
        }

        if (this.type != element.getType()) {
            throw new IllegalArgumentException("Cannot add tag of type " + element.getType() + " to " + this.type + " list");
        }

        this.elements.add(element);
    }

    public void addAll(@Nonnull T... elements) {
        this.addAll(Arrays.asList(elements));
    }

    public void addAll(@Nonnull Collection<? extends T> elements) {
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

    @Nonnull
    public T get(@Nonnegative int index) {
        return this.elements.get(index);
    }

    public void remove(@Nonnegative int index) {
        this.elements.remove(index);
        this.cleanup();
    }

    public void remove(@Nonnull T element) {
        this.elements.remove(element);
        this.cleanup();
    }

    public void removeAll(@Nonnull T... elements) {
        this.removeAll(Arrays.asList(elements));
    }

    public void removeAll(@Nonnull Collection<? extends T> elements) {
        this.elements.removeAll(elements);
        this.cleanup();
    }

    @Nonnegative
    public int size() {
        return this.elements.size();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterator<T> iterator() {
        return this.view.iterator();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ListTag)) return false;

        ListTag<?> listTag = (ListTag<?>) o;
        return this.type == listTag.type &&
                Objects.equals(this.elements, listTag.elements);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.type, this.elements);
    }
}
