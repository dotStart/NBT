package io.github.lordakkarin.nbt.event;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import javax.annotation.Nonnull;
import javax.annotation.WillNotClose;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * Reads an NBT encoded (and optionally gzipped) stream of data and passes it to one or more
 * instances of {@link TagVisitor}.
 *
 * @author <a href="mailto:johannesd@torchmind.com">Johannes Donath</a>
 */
public class TagReader {
    private final ByteBuf buffer;

    public TagReader(@Nonnull @WillNotClose ReadableByteChannel channel) throws IOException {
        this.buffer = Unpooled.directBuffer();

        {
            ByteBuffer tmp = ByteBuffer.allocateDirect(128);
            ByteBuf wrapped = Unpooled.wrappedBuffer(tmp);
            int length;

            while ((length = channel.read(tmp)) > 0) {
                tmp.flip();
                this.buffer.writeBytes(wrapped, length);

                wrapped.resetReaderIndex();
                tmp.rewind();
            }
        }
    }

    public TagReader(@Nonnull @WillNotClose InputStream inputStream) throws IOException {
        this(Channels.newChannel(inputStream));
    }

    public TagReader(@Nonnull Path path) throws IOException {
        this(FileChannel.open(path, StandardOpenOption.READ));
    }

    public TagReader(@Nonnull File file) throws IOException {
        this(file.toPath());
    }

    /**
     * Parses the encoded data and passes it to the specified visitor.
     *
     * @param visitor a visitor.
     */
    public void accept(@Nonnull TagVisitor visitor) {
        this.buffer.markReaderIndex();

        try {
            while (this.buffer.isReadable()) {
                TagType tagType = TagType.byTypeId(this.buffer.readByte());
                visitor.visitKey(this.readString());
                this.visitValue(visitor, tagType);
            }
        } finally {
            this.buffer.resetReaderIndex();
        }
    }

    /**
     * Sets the byte order the reader will use for multi-byte values.
     *
     * <strong>Note:</strong> Call this before accessing {@link #accept(TagVisitor)} in case you are
     * working with NBT which is compatible with the mobile version.
     *
     * @param order a byte order.
     */
    public void setOrder(@Nonnull ByteOrder order) {
        this.buffer.order(order);
    }

    /**
     * Reads an UTF-8 encoded string from the buffer.
     *
     * @return a string.
     */
    @Nonnull
    private String readString() {
        int length = this.buffer.readUnsignedShort();

        byte[] encoded = new byte[length];
        this.buffer.readBytes(encoded);

        return new String(encoded, StandardCharsets.UTF_8);
    }

    /**
     * Visits the raw value based on a given type.
     *
     * @param visitor a visitor.
     * @param tagType a tag type.
     */
    private void visitValue(@Nonnull TagVisitor visitor, @Nonnull TagType tagType) {
        switch (tagType) {
            case BYTE:
                visitor.visitByte(this.buffer.readByte());
                break;
            case SHORT:
                visitor.visitShort(this.buffer.readShort());
                break;
            case INTEGER:
                visitor.visitInteger(this.buffer.readInt());
                break;
            case LONG:
                visitor.visitLong(this.buffer.readLong());
                break;
            case FLOAT:
                visitor.visitFloat(this.buffer.readFloat());
                break;
            case DOUBLE:
                visitor.visitDouble(this.buffer.readDouble());
                break;
            case BYTE_ARRAY: {
                int length = this.buffer.readInt();
                visitor.visitByteArray(length);

                for (int i = 0; i < length; ++i) {
                    visitor.visitByte(this.buffer.readByte());
                }

                break;
            }
            case STRING:
                visitor.visitString(this.readString());
                break;
            case LIST: {
                TagType elementType = TagType.byTypeId(this.buffer.readByte());
                int length = this.buffer.readInt();

                visitor.visitList(elementType, length);

                for (int i = 0; i < length; ++i) {
                    this.visitValue(visitor, elementType);
                }

                break;
            }
            case COMPOUND:
                visitor.visitCompound();

                while (true) {
                    TagType elementType = TagType.byTypeId(this.buffer.readByte());

                    if (elementType == TagType.END) {
                        visitor.visitCompoundEnd();
                        break;
                    }

                    visitor.visitKey(this.readString());
                    this.visitValue(visitor, elementType);
                }

                break;
            case INTEGER_ARRAY: {
                int length = this.buffer.readInt();
                visitor.visitIntegerArray(length);

                for (int i = 0; i < length; ++i) {
                    visitor.visitInteger(this.buffer.readInt());
                }
            }
            default:
                throw new IllegalStateException("Did not expected tag of type " + tagType + " here");
        }
    }
}
