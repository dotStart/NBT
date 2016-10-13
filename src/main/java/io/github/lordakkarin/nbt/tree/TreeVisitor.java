package io.github.lordakkarin.nbt.tree;

import java.util.Stack;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.NotThreadSafe;

import io.github.lordakkarin.nbt.event.AbstractTagVisitor;
import io.github.lordakkarin.nbt.event.TagType;
import io.github.lordakkarin.nbt.event.TagVisitor;

/**
 * Provides a visitor which is capable of converting the inputs from types like {@link
 * io.github.lordakkarin.nbt.event.TagReader} into a complete tree of object representations.
 *
 * @author <a href="mailto:johannesd@torchmind.com">Johannes Donath</a>
 */
@NotThreadSafe
public class TreeVisitor extends AbstractTagVisitor {
    private final Stack<ParentNode> stack = new Stack<>();
    private RootTag root;

    public TreeVisitor() {
        this(null);
    }

    public TreeVisitor(@Nullable TagVisitor next) {
        super(next);
    }

    /**
     * Pushes a stack to the correct location.
     *
     * @param tag a tag.
     */
    @SuppressWarnings("unchecked")
    private void pushTag(@Nonnull Tag tag) {
        if (this.stack.isEmpty()) {
            return;
        }

        ParentNode node = this.stack.peek();
        node.push(tag);

        if (node.needsPop()) {
            this.stack.pop();
        }
    }

    @Nullable
    public RootTag getRoot() {
        return this.root;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visitByte(byte value) {
        this.pushTag(new ByteTag(value));

        super.visitByte(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visitShort(short value) {
        this.pushTag(new ShortTag(value));

        super.visitShort(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visitInteger(int value) {
        this.pushTag(new IntegerTag(value));

        super.visitInteger(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visitLong(long value) {
        this.pushTag(new LongTag(value));

        super.visitLong(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visitFloat(float value) {
        this.pushTag(new FloatTag(value));

        super.visitFloat(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visitDouble(double value) {
        this.pushTag(new DoubleTag(value));

        super.visitDouble(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visitByteArray(@Nonnegative int length) {
        ByteArrayTag tag = new ByteArrayTag(new byte[length]);

        this.pushTag(tag);
        this.stack.push(new ByteArrayParentNode(tag));

        super.visitByteArray(length);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visitIntegerArray(@Nonnegative int length) {
        IntegerArrayTag tag = new IntegerArrayTag(new int[length]);

        this.pushTag(tag);
        this.stack.push(new IntegerArrayParentNode(tag));

        super.visitIntegerArray(length);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visitString(@Nonnull String value) {
        this.pushTag(new StringTag(value));

        super.visitString(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visitList(@Nullable TagType type, @Nonnegative int length) {
        ListTag tag = new ListTag((type == null ? TagType.END : type));

        this.pushTag(tag);
        this.stack.push(new ListParentNode(length, tag));

        super.visitList(type, length);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visitCompound() {
        CompoundTag tag = new CompoundTag();

        this.pushTag(tag);
        this.stack.push(new CompoundParentNode(tag));

        super.visitCompound();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visitRoot(@Nonnull String name) {
        this.root = new RootTag(name);
        this.stack.push(new CompoundParentNode(this.root));

        super.visitRoot(name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visitKey(@Nonnull String name) {
        if (!this.stack.isEmpty()) {
            ParentNode node = this.stack.peek();

            if (node instanceof CompoundParentNode) {
                ((CompoundParentNode) node).key = name;
            }
        }

        super.visitKey(name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visitCompoundEnd() {
        if (!this.stack.isEmpty()) {
            this.stack.pop();
        }

        super.visitCompoundEnd();
    }

    /**
     * Provides a wrapper interface which handles the addition of elements to a compound, list or
     * array.
     */
    private interface ParentNode {

        void push(@Nonnull Tag tag);

        boolean needsPop();
    }

    private abstract class ArrayParentNode implements ParentNode {
        final int length;
        int index;

        public ArrayParentNode(int length) {
            this.length = length;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void push(@Nonnull Tag tag) {
            ++this.index;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean needsPop() {
            return this.index >= this.length;
        }
    }

    private class ByteArrayParentNode extends ArrayParentNode {
        private final ByteArrayTag tag;

        public ByteArrayParentNode(@Nonnull ByteArrayTag tag) {
            super(tag.getLength());
            this.tag = tag;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void push(@Nonnull Tag tag) {
            if (!(tag instanceof ByteTag)) {
                throw new IllegalStateException("Could not push " + tag.getClass() + " to end of byte array: Invalid tag type");
            }

            this.tag.set(this.index, ((ByteTag) tag).getValue());
            super.push(tag);
        }
    }

    private class CompoundParentNode implements ParentNode {
        private final CompoundTag tag;
        private String key = null;

        public CompoundParentNode(@Nonnull CompoundTag tag) {
            this.tag = tag;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void push(@Nonnull Tag tag) {
            if (this.key == null) {
                throw new IllegalArgumentException("Could not push " + tag.getClass() + " to end of compound: No key specified");
            }

            this.tag.put(this.key, tag);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean needsPop() {
            return false;
        }
    }

    private class IntegerArrayParentNode extends ArrayParentNode {
        private final IntegerArrayTag tag;

        public IntegerArrayParentNode(@Nonnull IntegerArrayTag tag) {
            super(tag.getLength());
            this.tag = tag;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void push(@Nonnull Tag tag) {
            if (!(tag instanceof IntegerTag)) {
                throw new IllegalArgumentException("Could not push " + tag.getClass() + " to end of array: Invalid tag type");
            }

            this.tag.set(this.index, ((IntegerTag) tag).getValue());
            super.push(tag);
        }
    }

    private class ListParentNode extends ArrayParentNode {
        private final ListTag tag;

        public ListParentNode(@Nonnegative int length, @Nonnull ListTag tag) {
            super(length);
            this.tag = tag;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        @SuppressWarnings("unchecked")
        public void push(@Nonnull Tag tag) {
            this.tag.add(tag);
            super.push(tag);
        }
    }
}
