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
import java.util.Optional;

/**
 * Provides an abstract visitor which is capable of passing all its values to the next visitor in a
 * chain.
 *
 * @author <a href="mailto:johannesd@torchmind.com">Johannes Donath</a>
 */
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
  @NonNull
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
  public void visitByteArray(int length) {
    if (this.next != null) {
      this.next.visitByteArray(length);
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
  public void visitCompoundEnd() {
    if (this.next != null) {
      this.next.visitCompoundEnd();
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
  public void visitFloat(float value) {
    if (this.next != null) {
      this.next.visitFloat(value);
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
  public void visitIntegerArray(int length) {
    if (this.next != null) {
      this.next.visitIntegerArray(length);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void visitKey(@NonNull String name) {
    if (this.next != null) {
      this.next.visitKey(name);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void visitList(@Nullable TagType type, int length) {
    if (this.next != null) {
      this.next.visitList(type, length);
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
  public void visitRoot(@NonNull String name) {
    if (this.next != null) {
      this.next.visitRoot(name);
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
  public void visitString(@NonNull String value) {
    if (this.next != null) {
      this.next.visitString(value);
    }
  }
}
