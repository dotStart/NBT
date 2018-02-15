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
import io.github.lordakkarin.nbt.event.TagVisitor;
import java.util.Map;
import java.util.Objects;

/**
 * Provides a root NBT tag which acts as the implied compound at the heart of the file.
 *
 * @author <a href="mailto:johannesd@torchmind.com">Johannes Donath</a>
 */
public class RootTag extends CompoundTag {

  private String name;

  public RootTag(@NonNull String name) {
    super();
    this.name = name;
  }

  public RootTag(@NonNull String name, @NonNull Map<String, ? extends Tag> elements) {
    super(elements);
    this.name = name;
  }

  @Override
  public void accept(@NonNull TagVisitor visitor) {
    visitor.visitKey(this.name);
    super.accept(visitor);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof RootTag)) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }

    RootTag entries = (RootTag) o;
    return Objects.equals(this.name, entries.name);
  }

  @NonNull
  public String getName() {
    return this.name;
  }

  public void setName(@NonNull String name) {
    this.name = name;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), this.name);
  }
}
