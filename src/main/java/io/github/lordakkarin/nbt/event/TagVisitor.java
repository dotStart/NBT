package io.github.lordakkarin.nbt.event;

import edu.umd.cs.findbugs.annotations.NonNull;
import edu.umd.cs.findbugs.annotations.Nullable;

/**
 * Provides a basic visitor which is capable of receiving events from a {@link TagReader} or another
 * visitor.
 *
 * @author <a href="mailto:johannesd@torchmind.com">Johannes Donath</a>
 */
public interface TagVisitor {

  void visitByte(byte value);

  void visitByteArray(int length);

  void visitCompound();

  void visitCompoundEnd();

  void visitDouble(double value);

  void visitFloat(float value);

  void visitInteger(int value);

  void visitIntegerArray(int length);

  void visitKey(@NonNull String name);

  void visitList(@Nullable TagType type, int length);

  void visitLong(long value);

  void visitRoot(@NonNull String name);

  void visitShort(short value);

  void visitString(@NonNull String value);
}
