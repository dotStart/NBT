package io.github.lordakkarin.nbt.event;

import edu.umd.cs.findbugs.annotations.NonNull;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Paths;
import org.junit.Assert;
import org.junit.Test;

/**
 * Provides test cases to verify the correct functionality of {@link TagWriter}.
 *
 * @author <a href="mailto:johannesd@torchmind.com">Johannes Donath</a>
 */
public class TagWriterTest {

  @NonNull
  private ByteBuf readResource(@NonNull ReadableByteChannel channel)
      throws IOException {
    ByteBuf target = Unpooled.directBuffer();

    ByteBuffer tmp = ByteBuffer.allocateDirect(128);
    ByteBuf wrapped = Unpooled.wrappedBuffer(tmp);
    int length;

    while ((length = channel.read(tmp)) > 0) {
      tmp.flip();
      target.writeBytes(wrapped, length);

      wrapped.resetReaderIndex();
      tmp.rewind();
    }

    return target;
  }

  /**
   * Tests the generation of a bigger NBT file which contains all known tags.
   */
  @Test
  public void testBig() throws IOException {
    // TODO: We are using a reader here - This means that failure may carry over!
    final TagWriter writer = new TagWriter();

    try (InputStream inputStream = this.getClass().getResourceAsStream("/bigtest.nbt")) {
      TagReader reader = new TagReader(inputStream);
      reader.accept(writer);
    }

    writer.write(Paths.get("test.nbt"));

    try (InputStream inputStream = this.getClass().getResourceAsStream("/bigtest.nbt")) {
      ByteBuf expected = this.readResource(Channels.newChannel(inputStream));
      ByteBuf encoded = writer.getBuffer();

      Assert.assertArrayEquals(this.toArray(expected), this.toArray(encoded));
    }
  }

  /**
   * Tests the generation of a hello world NBT file.
   */
  @Test
  public void testHelloWorld() throws IOException {
    TagWriter writer = new TagWriter();

    writer.visitKey("hello world");
    writer.visitCompound();
    {
      writer.visitKey("name");
      writer.visitString("Bananrama");
    }
    writer.visitCompoundEnd();

    try (InputStream inputStream = this.getClass().getResourceAsStream("/hello_world.nbt")) {
      ByteBuf expected = this.readResource(Channels.newChannel(inputStream));
      ByteBuf encoded = writer.getBuffer();

      Assert.assertArrayEquals(this.toArray(expected), this.toArray(encoded));
    }
  }

  @NonNull
  private byte[] toArray(@NonNull ByteBuf buffer) {
    byte[] array = new byte[buffer.readableBytes()];
    buffer.readBytes(array);

    return array;
  }
}
