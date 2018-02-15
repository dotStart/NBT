/*
 * Copyright 2018 Johannes Donath <johannesd@torchmind.com>
 * and other copyright owners as documented in the project's IP log.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
