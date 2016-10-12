package io.github.lordakkarin.nbt.event;

import java.util.Optional;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.ThreadSafe;

/**
 * Provides an abstract visitor which is capable of passing all its values to the next visitor in a
 * chain.
 *
 * @author <a href="mailto:johannesd@torchmind.com">Johannes Donath</a>
 */
@Immutable
@ThreadSafe
public abstract class AbstractTagVisitor implements TagVisitor {
    private final TagVisitor next;

    public AbstractTagVisitor(@Nullable TagVisitor next) {
        this.next = next;
    }

    /**
     * Retrieves the next visitor (if any).
     *
     * @return a visitor.
     */
    @Nonnull
    public Optional<TagVisitor> getNext() {
        return Optional.ofNullable(this.next);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visitByte(byte value) {
        if (this.next != null) {
            this.next.visitByte(value);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visitShort(short value) {
        if (this.next != null) {
            this.next.visitShort(value);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visitInteger(int value) {
        if (this.next != null) {
            this.next.visitInteger(value);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visitLong(long value) {
        if (this.next != null) {
            this.next.visitLong(value);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visitFloat(float value) {
        if (this.next != null) {
            this.next.visitFloat(value);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visitDouble(double value) {
        if (this.next != null) {
            this.next.visitDouble(value);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visitByteArray(@Nonnegative int length) {
        if (this.next != null) {
            this.next.visitByteArray(length);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visitIntegerArray(@Nonnegative int length) {
        if (this.next != null) {
            this.next.visitIntegerArray(length);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visitString(@Nonnull String value) {
        if (this.next != null) {
            this.next.visitString(value);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visitList(@Nullable TagType type, @Nonnegative int length) {
        if (this.next != null) {
            this.next.visitList(type, length);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visitCompound() {
        if (this.next != null) {
            this.next.visitCompound();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visitKey(@Nonnull String name) {
        if (this.next != null) {
            this.next.visitKey(name);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visitCompoundEnd() {
        if (this.next != null) {
            this.next.visitCompoundEnd();
        }
    }
}
