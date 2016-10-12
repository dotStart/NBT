package io.github.lordakkarin.nbt.tree;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.NotThreadSafe;

import io.github.lordakkarin.nbt.event.TagType;
import io.github.lordakkarin.nbt.event.TagVisitor;

/**
 * @author <a href="mailto:johannesd@torchmind.com">Johannes Donath</a>
 */
@NotThreadSafe
public class CompoundTag implements Iterable<Map.Entry<String, Tag>>, Tag {
    private final Map<String, Tag> map = new HashMap<>();

    public CompoundTag() {
    }

    public CompoundTag(@Nonnull Map<String, ? extends Tag> elements) {
        this.putAll(elements);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void accept(@Nonnull TagVisitor visitor) {
        visitor.visitCompound();
        this.map.forEach((k, v) -> {
            visitor.visitKey(k);
            v.accept(visitor);
        });
        visitor.visitCompoundEnd();
    }

    /**
     * {@inheritDoc}
     */
    @Nonnull
    @Override
    public TagType getType() {
        return TagType.COMPOUND;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterator<Map.Entry<String, Tag>> iterator() {
        return this.map.entrySet().iterator();
    }

    public void computeIfAbsent(@Nonnull String key, @Nonnull Function<String, ? extends Tag> factory) {
        this.map.computeIfAbsent(key, factory);
    }

    public boolean containsKey(@Nonnull String key) {
        return this.map.containsKey(key);
    }

    @Nullable
    @SuppressWarnings("unchecked")
    public <T extends Tag> T get(@Nonnull String key) {
        return (T) this.map.get(key);
    }

    @Nullable
    @SuppressWarnings("unchecked")
    public <T extends Tag> T getOrDefault(@Nonnull String key, @Nullable T defaultValue) {
        return (T) this.map.getOrDefault(key, defaultValue);
    }

    public void put(@Nonnull String key, @Nonnull Tag tag) {
        this.map.put(key, tag);
    }

    public void putAll(@Nonnull Map<String, ? extends Tag> elements) {
        this.map.putAll(elements);
    }

    public void putIfAbsent(@Nonnull String key, @Nonnull Tag tag) {
        this.map.putIfAbsent(key, tag);
    }

    @Nonnull
    @SuppressWarnings("unchecked")
    public <T extends Tag> T remove(@Nonnull String key) {
        return (T) this.map.remove(key);
    }

    public boolean remove(@Nonnull String key, @Nonnull Tag tag) {
        return this.map.remove(key, tag);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CompoundTag)) return false;

        CompoundTag entries = (CompoundTag) o;
        return Objects.equals(this.map, entries.map);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.map);
    }
}
