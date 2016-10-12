package io.github.lordakkarin.nbt.tree;

import java.util.Arrays;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.NotThreadSafe;

import io.github.lordakkarin.nbt.event.TagType;
import io.github.lordakkarin.nbt.event.TagVisitor;

/**
 * @author <a href="mailto:johannesd@torchmind.com">Johannes Donath</a>
 */
@NotThreadSafe
public class IntegerArray implements Tag {
    private int[] value;

    public IntegerArray() {
        this.value = new int[0];
    }

    public IntegerArray(int[] value) {
        this.value = value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void accept(@Nonnull TagVisitor visitor) {
        visitor.visitIntegerArray(this.value.length);

        for (int value : this.value) {
            visitor.visitInteger(value);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Nonnull
    @Override
    public TagType getType() {
        return TagType.INTEGER_ARRAY;
    }

    @Nonnull
    public int[] getValue() {
        return this.value;
    }

    public void setValue(@Nonnull int[] value) {
        this.value = value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IntegerArray)) return false;

        IntegerArray that = (IntegerArray) o;
        return Arrays.equals(this.value, that.value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Arrays.hashCode(this.value);
    }
}
