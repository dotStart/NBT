package io.github.lordakkarin.nbt.tree;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

import javax.annotation.Nonnegative;
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

    public void clear() {
        this.map.clear();
    }

    public void computeIfAbsent(@Nonnull String key, @Nonnull Function<String, ? extends Tag> factory) {
        this.map.computeIfAbsent(key, factory);
    }

    public boolean containsKey(@Nonnull String key) {
        return this.map.containsKey(key);
    }

    public boolean containsKey(@Nonnull String key, @Nonnull Class<? extends Tag> type) {
        Tag tag = this.get(key);

        if (tag == null) {
            return false;
        }

        return type.isInstance(tag);
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

    @Nullable
    public byte[] getByteArray(@Nonnull String key) {
        return Optional.ofNullable((ByteArrayTag) this.get(key)).map(ByteArrayTag::getValue).orElse(null);
    }

    public byte getByte(@Nonnull String key) {
        return Optional.ofNullable((ByteTag) this.get(key)).map(ByteTag::getValue).orElse((byte) 0);
    }

    public double getDouble(@Nonnull String key) {
        return Optional.ofNullable((DoubleTag) this.get(key)).map(DoubleTag::getValue).orElse(0d);
    }

    public float getFloat(@Nonnull String key) {
        return Optional.ofNullable((FloatTag) this.get(key)).map(FloatTag::getValue).orElse(0f);
    }

    @Nullable
    public int[] getIntegerArray(@Nonnull String key) {
        return Optional.ofNullable((IntegerArrayTag) this.get(key)).map(IntegerArrayTag::getValue).orElse(null);
    }

    public int getInteger(@Nonnull String key) {
        return Optional.ofNullable((IntegerTag) this.get(key)).map(IntegerTag::getValue).orElse(0);
    }

    public long getLong(@Nonnull String key) {
        return Optional.ofNullable((IntegerTag) this.get(key)).map(IntegerTag::getValue).orElse(0);
    }

    public short getShort(@Nonnull String key) {
        return Optional.ofNullable((ShortTag) this.get(key)).map(ShortTag::getValue).orElse((short) 0);
    }

    @Nullable
    public String getString(@Nonnull String key) {
        return Optional.ofNullable((StringTag) this.get(key)).map(StringTag::getValue).orElse(null);
    }

    public void setByteArray(@Nonnull String key, @Nonnull byte[] value) {
        this.getOrCreate(key, ByteArrayTag.class).setValue(value);
    }

    public void setByte(@Nonnull String key, byte value) {
        this.getOrCreate(key, ByteTag.class).setValue(value);
    }

    public void setDouble(@Nonnull String key, double value) {
        this.getOrCreate(key, DoubleTag.class).setValue(value);
    }

    public void setFloat(@Nonnull String key, float value) {
        this.getOrCreate(key, FloatTag.class).setValue(value);
    }

    public void setInteger(@Nonnull String key, int value) {
        this.getOrCreate(key, IntegerTag.class).setValue(value);
    }

    public void setIntegerArray(@Nonnull String key, @Nonnull int[] value) {
        this.getOrCreate(key, IntegerArrayTag.class).setValue(value);
    }

    public void setLong(@Nonnull String key, long value) {
        this.getOrCreate(key, LongTag.class).setValue(value);
    }

    public void setShort(@Nonnull String key, short value) {
        this.getOrCreate(key, ShortTag.class).setValue(value);
    }

    public void setString(@Nonnull String key, @Nonnull String value) {
        this.getOrCreate(key, StringTag.class).setValue(value);
    }

    /**
     * Retrieves a key of a certain type or creates it.
     * @param key a key.
     * @param type a type.
     * @param <T> a type.
     * @return a tag.
     */
    @Nonnull
    private <T extends Tag> T getOrCreate(@Nonnull String key, @Nonnull Class<T> type) {
        if (!this.containsKey(key, type)) {
            try {
                T instance = type.newInstance();
                this.put(key, instance);
                return instance;
            } catch (InstantiationException | IllegalAccessException ex) {
                throw new RuntimeException("Could not construct tag: " + ex.getMessage(), ex);
            }
        }

        // noinspection ConstantConditions
        return this.get(key);
    }

    @Nonnegative
    public int size() {
        return this.map.size();
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
