package io.github.lordakkarin.nbt.event;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Stack;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.WillNotClose;
import javax.annotation.concurrent.NotThreadSafe;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ReadOnlyByteBuf;
import io.netty.buffer.Unpooled;

/**
 * Accepts the data from a {@link TagReader} or other visitor and turns it into an NBT encoded (and
 * optionally gzipped) stream of binary data.
 *
 * @author <a href="mailto:johannesd@torchmind.com">Johannes Donath</a>
 */
@NotThreadSafe
public class TagWriter extends AbstractTagVisitor {
    private final ByteBuf buffer;
    private Stack<TagType> parentStack = new Stack<>();
    private Stack<Integer> listStack = new Stack<>();
    private String key = null;

    public TagWriter() {
        this(null);
    }

    public TagWriter(@Nullable TagVisitor next) {
        super(next);

        this.buffer = Unpooled.directBuffer();
    }

    /**
     * Clears the internal state.
     */
    public void clear() {
        this.buffer.clear().readerIndex(0).writerIndex(0);
    }

    /**
     * Returns a read-only view of the internal buffer.
     *
     * @return a buffer.
     */
    @Nonnull
    public ByteBuf getBuffer() {
        return new ReadOnlyByteBuf(this.buffer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visitByte(byte value) {
        this.writeType(TagType.BYTE);
        this.buffer.writeByte(value);

        super.visitByte(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visitShort(short value) {
        this.writeType(TagType.SHORT);
        this.buffer.writeShort(value);

        super.visitShort(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visitInteger(int value) {
        this.writeType(TagType.INTEGER);
        this.buffer.writeInt(value);

        super.visitInteger(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visitLong(long value) {
        this.writeType(TagType.LONG);
        this.buffer.writeLong(value);

        super.visitLong(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visitFloat(float value) {
        this.writeType(TagType.FLOAT);
        this.buffer.writeFloat(value);

        super.visitFloat(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visitDouble(double value) {
        this.writeType(TagType.DOUBLE);
        this.buffer.writeDouble(value);

        super.visitDouble(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visitByteArray(@Nonnegative int length) {
        this.writeType(TagType.BYTE_ARRAY);
        this.buffer.writeInt(length);

        this.listStack.push(length);
        this.parentStack.push(TagType.BYTE_ARRAY);

        super.visitByteArray(length);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visitIntegerArray(@Nonnegative int length) {
        this.writeType(TagType.INTEGER_ARRAY);
        this.buffer.writeInt(length);

        this.listStack.push(length);
        this.parentStack.push(TagType.INTEGER_ARRAY);

        super.visitIntegerArray(length);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visitString(@Nonnull String value) {
        this.writeType(TagType.STRING);
        this.writeString(value);

        super.visitString(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visitList(@Nullable TagType type, @Nonnegative int length) {
        this.writeType(TagType.LIST);
        this.buffer.writeByte((type == null ? TagType.END : type).ordinal());
        this.buffer.writeInt(length);

        this.listStack.push(length);
        this.parentStack.push(TagType.LIST);

        super.visitList(type, length);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visitCompound() {
        this.writeType(TagType.COMPOUND);
        this.parentStack.push(TagType.COMPOUND);

        super.visitCompound();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visitRoot(@Nonnull String name) {
        this.writeType(TagType.COMPOUND);
        this.writeString(name);
        this.parentStack.push(TagType.COMPOUND);

        super.visitRoot(name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visitKey(@Nonnull String name) {
        this.key = name;

        super.visitKey(name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visitCompoundEnd() {
        // circumvent the writeType method in order to ensure that this value is never missed even
        // when we are contained in a list
        this.buffer.writeByte(TagType.END.ordinal());
        assert this.parentStack.pop() == TagType.COMPOUND;

        super.visitCompoundEnd();
    }

    /**
     * Writes the serialized tag tree into a channel.
     *
     * @param channel a channel.
     * @throws IOException when writing fails.
     */
    public void write(@Nonnull @WillNotClose WritableByteChannel channel) throws IOException {
        // we are ignoring the first and the last byte in the buffer since they will consist of the
        // implied root compound tag
        ByteBuffer tmp = ByteBuffer.allocate(this.buffer.readableBytes());

        this.buffer.markReaderIndex();

        try {
            this.buffer.readBytes(tmp);
            tmp.flip();
        } finally {
            this.buffer.resetReaderIndex();
        }

        channel.write(tmp);
    }

    /**
     * Writes the serialized tag tree into a stream.
     *
     * @param outputStream a stream.
     * @throws IOException when writing fails.
     */
    public void write(@Nonnull @WillNotClose OutputStream outputStream) throws IOException {
        this.write(Channels.newChannel(outputStream));
    }

    /**
     * Writes the serialized tag tree to a specific path, effectively creating a new file or
     * truncating existing files.
     *
     * @param path a file path.
     * @throws IOException when writing fails.
     */
    public void write(@Nonnull Path path) throws IOException {
        try (FileChannel channel = FileChannel.open(path, StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING)) {
            this.write(channel);
        }
    }

    /**
     * Writes a serialized tag tree to a specific file, effectively creating a new file or
     * truncating existing files.
     *
     * @param file a file.
     * @throws IOException when writing fails.
     */
    public void write(@Nonnull File file) throws IOException {
        this.write(file.toPath());
    }

    /**
     * Writes a UTF-8 encoded string into the bufer.
     *
     * @param value a string value.
     */
    private void writeString(@Nonnull String value) {
        byte[] encoded = value.getBytes(StandardCharsets.UTF_8);

        this.buffer.writeShort(encoded.length);
        this.buffer.writeBytes(encoded);
    }

    /**
     * Writes the raw type identifier to the buffer.
     *
     * @param type a type.
     */
    private void writeType(@Nonnull TagType type) {
        // lists are prefixed with their respective tagId and thus we'll skip writing the tagId
        // until we have fully written the list
        TagType parentType = (this.parentStack.isEmpty() ? TagType.COMPOUND : this.parentStack.peek());
        boolean isList = (parentType == TagType.LIST || parentType == TagType.BYTE_ARRAY || parentType == TagType.INTEGER_ARRAY);

        if (isList && !this.listStack.isEmpty()) {
            int listCounter = this.listStack.pop();

            if (--listCounter > 0) {
                this.listStack.push(listCounter);
            } else {
                this.parentStack.pop();
            }

            return;
        }

        this.buffer.writeByte(type.ordinal());

        // in compound mode we will receive a compound key before the type specific visitor is called
        // to counteract this behavior and order the bytes correctly in our writer, we'll want to
        // store the key and write it right after writing the type specifier
        if (this.key != null) {
            this.writeString(this.key);
            this.key = null;
        }
    }
}
