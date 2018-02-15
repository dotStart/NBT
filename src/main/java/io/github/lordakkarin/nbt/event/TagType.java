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

/**
 * Provides a list of valid NBT tag types and their respective serialized identifiers.
 *
 * @author <a href="mailto:johannesd@torchmind.com">Johannes Donath</a>
 */
public enum TagType {
  END, // This will never occur in lists
  BYTE,
  SHORT,
  INTEGER,
  LONG,
  FLOAT,
  DOUBLE,
  BYTE_ARRAY,
  STRING,
  LIST,
  COMPOUND,
  INTEGER_ARRAY;

  /**
   * Retrieves a tag type based on its identifier.
   *
   * @param typeId an identifier.
   * @return a tag type.
   * @throws IndexOutOfBoundsException when the specified tagId is out of bounds.
   */
  @NonNull
  public static TagType byTypeId(int typeId) {
    TagType[] types = values();

    if (typeId >= types.length) {
      throw new IndexOutOfBoundsException("No such typeId: " + typeId);
    }

    return types[typeId];
  }
}
