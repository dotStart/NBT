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
package io.github.lordakkarin.nbt.tree;

import edu.umd.cs.findbugs.annotations.NonNull;
import java.util.Iterator;

/**
 * Provides an iterator implementation which is capable of iterating over arrays directly.
 *
 * @author <a href="mailto:johannesd@torchmind.com">Johannes Donath</a>
 */
class ArrayIterator<E> implements Iterator<E> {

  private final E[] array;
  private int index;

  ArrayIterator(@NonNull E[] array) {
    this.array = array;
  }

  public static <E> ArrayIterator<E> of(@NonNull E[] array) {
    return new ArrayIterator<>(array);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean hasNext() {
    return (this.index + 1) <= this.array.length;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public E next() {
    return this.array[++this.index];
  }
}
