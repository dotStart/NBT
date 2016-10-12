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
public class ByteArrayTag implements Tag {
    private byte[] value;

    public ByteArrayTag() {
        this.value = new byte[0];
    }

    public ByteArrayTag(@Nonnull byte[] value) {
        this.value = value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void accept(@Nonnull TagVisitor visitor) {
        visitor.visitByteArray(this.value.length);

        for (byte value : this.value) {
            visitor.visitByte(value);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Nonnull
    @Override
    public TagType getType() {
        return TagType.BYTE_ARRAY;
    }

    @Nonnull
    public byte[] getValue() {
        return this.value;
    }

    public void setValue(@Nonnull byte[] value) {
        this.value = value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ByteArrayTag)) return false;

        ByteArrayTag that = (ByteArrayTag) o;
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
