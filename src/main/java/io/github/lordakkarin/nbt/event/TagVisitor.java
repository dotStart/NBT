package io.github.lordakkarin.nbt.event;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Provides a basic visitor which is capable of receiving events from a {@link TagReader} or another
 * visitor.
 *
 * @author <a href="mailto:johannesd@torchmind.com">Johannes Donath</a>
 */
public interface TagVisitor {

  void visitByte(byte value);

  void visitByteArray(@Nonnegative int length);

  void visitCompound();

  void visitCompoundEnd();

  void visitDouble(double value);

  void visitFloat(float value);

  void visitInteger(int value);

  void visitIntegerArray(@Nonnegative int length);

  void visitKey(@Nonnull String name);

  void visitList(@Nullable TagType type, @Nonnegative int length);

  void visitLong(long value);

  void visitRoot(@Nonnull String name);

  void visitShort(short value);

  void visitString(@Nonnull String value);
}
