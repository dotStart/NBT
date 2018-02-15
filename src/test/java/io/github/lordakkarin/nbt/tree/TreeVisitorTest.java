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

import io.github.lordakkarin.nbt.event.TagReader;
import io.github.lordakkarin.nbt.event.TagType;
import java.io.IOException;
import org.junit.Assert;
import org.junit.Test;

/**
 * Provides test cases for {@link TreeVisitor}.
 *
 * @author <a href="mailto:johannesd@torchmind.com">Johannes Donath</a>
 */
public class TreeVisitorTest {

  @Test
  public void testBig() throws IOException {
    TreeVisitor visitor = new TreeVisitor();
    TagReader reader = new TagReader(TreeVisitorTest.class.getResourceAsStream("/bigtest.nbt"));
    reader.accept(visitor);

    Assert.assertNotNull(visitor.getRoot());
    Assert.assertEquals(TagType.COMPOUND, visitor.getRoot().getType());

    {
      RootTag root = visitor.getRoot();

      Assert.assertEquals(11, root.size());
      Assert.assertTrue(root.containsKey("nested compound test"));
      Assert.assertTrue(root.containsKey("intTest"));
      Assert.assertTrue(root.containsKey("byteTest"));
      Assert.assertTrue(root.containsKey("stringTest"));
      Assert.assertTrue(root.containsKey("listTest (long)"));
      Assert.assertTrue(root.containsKey("doubleTest"));
      Assert.assertTrue(root.containsKey("floatTest"));
      Assert.assertTrue(root.containsKey("longTest"));
      Assert.assertTrue(root.containsKey("listTest (compound)"));
      Assert.assertTrue(root.containsKey(
          "byteArrayTest (the first 1000 values of (n*n*255+n*7)%100, starting with n=0 (0, 62, 34, 16, 8, ...))"));
      Assert.assertTrue(root.containsKey("shortTest"));

      Assert.assertEquals(2147483647, root.getInteger("intTest"));
      Assert.assertEquals(127, root.getByte("byteTest"));
      Assert.assertEquals("HELLO WORLD THIS IS A TEST STRING ÅÄÖ!", root.getString("stringTest"));
      Assert.assertEquals(0.49312871321823148d, root.getDouble("doubleTest"), 0.001);
      Assert.assertEquals(0.49823147058486938f, root.getFloat("floatTest"), 0.001);
      Assert.assertEquals(9223372036854775807L, root.getLong("longTest"));
      Assert.assertEquals(32767, root.getShort("shortTest"));

      {
        CompoundTag nested = root.get("nested compound test");

        Assert.assertEquals(2, nested.size());
        Assert.assertTrue(nested.containsKey("egg"));
        Assert.assertTrue(nested.containsKey("ham"));

        {
          CompoundTag value = nested.get("egg");

          Assert.assertEquals(2, value.size());
          Assert.assertTrue(value.containsKey("name"));
          Assert.assertTrue(value.containsKey("value"));

          Assert.assertEquals("Eggbert", value.getString("name"));
          Assert.assertEquals(0.5f, value.getFloat("value"), 0.01);
        }

        {
          CompoundTag value = nested.get("ham");

          Assert.assertEquals(2, value.size());
          Assert.assertTrue(value.containsKey("name"));
          Assert.assertTrue(value.containsKey("value"));

          Assert.assertEquals("Hampus", value.getString("name"));
          Assert.assertEquals(0.75f, value.getFloat("value"), 0.01);
        }
      }

      {
        ListTag list = root.get("listTest (long)");

        Assert.assertEquals(5, list.size());
        Assert.assertEquals(new LongTag(11), list.get(0));
        Assert.assertEquals(new LongTag(12), list.get(1));
        Assert.assertEquals(new LongTag(13), list.get(2));
        Assert.assertEquals(new LongTag(14), list.get(3));
        Assert.assertEquals(new LongTag(15), list.get(4));
      }

      {
        ListTag<CompoundTag> list = root.get("listTest (compound)");

        Assert.assertEquals(2, list.size());

        {
          CompoundTag tag = list.get(0);

          Assert.assertEquals(2, tag.size());
          Assert.assertTrue(tag.containsKey("created-on"));
          Assert.assertTrue(tag.containsKey("name"));
          Assert.assertEquals(1264099775885L, tag.getLong("created-on"));
          Assert.assertEquals("Compound tag #0", tag.getString("name"));
        }

        {
          CompoundTag tag = list.get(1);

          Assert.assertEquals(2, tag.size());
          Assert.assertTrue(tag.containsKey("created-on"));
          Assert.assertTrue(tag.containsKey("name"));
          Assert.assertEquals(1264099775885L, tag.getLong("created-on"));
          Assert.assertEquals("Compound tag #1", tag.getString("name"));
        }
      }
    }
  }

  @Test
  public void testHelloWorld() throws IOException {
    TreeVisitor visitor = new TreeVisitor();
    TagReader reader = new TagReader(TreeVisitorTest.class.getResourceAsStream("/hello_world.nbt"));

    reader.accept(visitor);

    Assert.assertNotNull(visitor.getRoot());
    Assert.assertEquals(TagType.COMPOUND, visitor.getRoot().getType());

    RootTag tag = visitor.getRoot();
    Assert.assertEquals(1, tag.size());
    Assert.assertTrue(tag.containsKey("name"));
    Assert.assertTrue(tag.containsKey("name", StringTag.class));

    Tag valueTag = tag.get("name");
    Assert.assertEquals(StringTag.class, valueTag.getClass());
    Assert.assertEquals(TagType.STRING, valueTag.getType());

    StringTag stringTag = (StringTag) valueTag;
    Assert.assertEquals("Bananrama", stringTag.getValue());
  }
}
