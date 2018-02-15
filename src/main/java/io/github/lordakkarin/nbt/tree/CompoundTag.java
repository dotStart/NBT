package io.github.lordakkarin.nbt.tree;

import edu.umd.cs.findbugs.annotations.NonNull;
import edu.umd.cs.findbugs.annotations.Nullable;
import io.github.lordakkarin.nbt.event.TagType;
import io.github.lordakkarin.nbt.event.TagVisitor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

/**
 * Represents an NBT tag which contains multiple named tags (e.g. it acts like a dictionary of
 * values).
 *
 * @author <a href="mailto:johannesd@torchmind.com">Johannes Donath</a>
 */
public class CompoundTag implements Iterable<Map.Entry<String, Tag>>, Tag {

  private final Map<String, Tag> map = new HashMap<>();

  public CompoundTag() {
  }

  public CompoundTag(@NonNull Map<String, ? extends Tag> elements) {
    this.putAll(elements);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void accept(@NonNull TagVisitor visitor) {
    visitor.visitCompound();
    this.map.forEach((k, v) -> {
      visitor.visitKey(k);
      v.accept(visitor);
    });
    visitor.visitCompoundEnd();
  }

  public void clear() {
    this.map.clear();
  }

  public void computeIfAbsent(@NonNull String key,
      @NonNull Function<String, ? extends Tag> factory) {
    this.map.computeIfAbsent(key, factory);
  }

  public boolean containsKey(@NonNull String key) {
    return this.map.containsKey(key);
  }

  public boolean containsKey(@NonNull String key, @NonNull Class<? extends Tag> type) {
    Tag tag = this.get(key);

    if (tag == null) {
      return false;
    }

    return type.isInstance(tag);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof CompoundTag)) {
      return false;
    }

    CompoundTag entries = (CompoundTag) o;
    return Objects.equals(this.map, entries.map);
  }

  @Nullable
  @SuppressWarnings("unchecked")
  public <T extends Tag> T get(@NonNull String key) {
    return (T) this.map.get(key);
  }

  public byte getByte(@NonNull String key) {
    return Optional.ofNullable((ByteTag) this.get(key)).map(ByteTag::getValue).orElse((byte) 0);
  }

  @Nullable
  public byte[] getByteArray(@NonNull String key) {
    return Optional.ofNullable((ByteArrayTag) this.get(key)).map(ByteArrayTag::getValue)
        .orElse(null);
  }

  public double getDouble(@NonNull String key) {
    return Optional.ofNullable((DoubleTag) this.get(key)).map(DoubleTag::getValue).orElse(0d);
  }

  public float getFloat(@NonNull String key) {
    return Optional.ofNullable((FloatTag) this.get(key)).map(FloatTag::getValue).orElse(0f);
  }

  public int getInteger(@NonNull String key) {
    return Optional.ofNullable((IntegerTag) this.get(key)).map(IntegerTag::getValue).orElse(0);
  }

  @Nullable
  public int[] getIntegerArray(@NonNull String key) {
    return Optional.ofNullable((IntegerArrayTag) this.get(key)).map(IntegerArrayTag::getValue)
        .orElse(null);
  }

  public long getLong(@NonNull String key) {
    return Optional.ofNullable((LongTag) this.get(key)).map(LongTag::getValue).orElse(0L);
  }

  /**
   * Retrieves a key of a certain type or creates it.
   *
   * @param key a key.
   * @param type a type.
   * @param <T> a type.
   * @return a tag.
   */
  @NonNull
  private <T extends Tag> T getOrCreate(@NonNull String key, @NonNull Class<T> type) {
    if (!this.containsKey(key, type)) {
      try {
        T instance = type.getConstructor().newInstance();
        this.put(key, instance);
        return instance;
      } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException ex) {
        throw new RuntimeException("Could not construct tag: " + ex.getMessage(), ex);
      }
    }

    // noinspection ConstantConditions
    return this.get(key);
  }

  @Nullable
  @SuppressWarnings("unchecked")
  public <T extends Tag> T getOrDefault(@NonNull String key, @Nullable T defaultValue) {
    return (T) this.map.getOrDefault(key, defaultValue);
  }

  public short getShort(@NonNull String key) {
    return Optional.ofNullable((ShortTag) this.get(key)).map(ShortTag::getValue).orElse((short) 0);
  }

  @Nullable
  public String getString(@NonNull String key) {
    return Optional.ofNullable((StringTag) this.get(key)).map(StringTag::getValue).orElse(null);
  }

  /**
   * {@inheritDoc}
   */
  @NonNull
  @Override
  public TagType getType() {
    return TagType.COMPOUND;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {
    return Objects.hash(this.map);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Iterator<Map.Entry<String, Tag>> iterator() {
    return this.map.entrySet().iterator();
  }

  public void put(@NonNull String key, @NonNull Tag tag) {
    this.map.put(key, tag);
  }

  public void putAll(@NonNull Map<String, ? extends Tag> elements) {
    this.map.putAll(elements);
  }

  public void putIfAbsent(@NonNull String key, @NonNull Tag tag) {
    this.map.putIfAbsent(key, tag);
  }

  @NonNull
  @SuppressWarnings("unchecked")
  public <T extends Tag> T remove(@NonNull String key) {
    return (T) this.map.remove(key);
  }

  public boolean remove(@NonNull String key, @NonNull Tag tag) {
    return this.map.remove(key, tag);
  }

  public void setByte(@NonNull String key, byte value) {
    this.getOrCreate(key, ByteTag.class).setValue(value);
  }

  public void setByteArray(@NonNull String key, @NonNull byte[] value) {
    this.getOrCreate(key, ByteArrayTag.class).setValue(value);
  }

  public void setDouble(@NonNull String key, double value) {
    this.getOrCreate(key, DoubleTag.class).setValue(value);
  }

  public void setFloat(@NonNull String key, float value) {
    this.getOrCreate(key, FloatTag.class).setValue(value);
  }

  public void setInteger(@NonNull String key, int value) {
    this.getOrCreate(key, IntegerTag.class).setValue(value);
  }

  public void setIntegerArray(@NonNull String key, @NonNull int[] value) {
    this.getOrCreate(key, IntegerArrayTag.class).setValue(value);
  }

  public void setLong(@NonNull String key, long value) {
    this.getOrCreate(key, LongTag.class).setValue(value);
  }

  public void setShort(@NonNull String key, short value) {
    this.getOrCreate(key, ShortTag.class).setValue(value);
  }

  public void setString(@NonNull String key, @NonNull String value) {
    this.getOrCreate(key, StringTag.class).setValue(value);
  }

  public int size() {
    return this.map.size();
  }
}
