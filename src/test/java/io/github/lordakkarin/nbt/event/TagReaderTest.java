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

import static org.mockito.Mockito.anyByte;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.IOException;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Provides test cases for the {@link TagReader} implementation.
 *
 * @author <a href="mailto:johannesd@torchmind.com">Johannes Donath</a>
 */
public class TagReaderTest {

  /**
   * Tests the tag reader against a bigger test file which contains nested compounds and all value
   * types.
   */
  @Test
  public void testBig() throws IOException {
    TagVisitor visitor = Mockito.mock(TagVisitor.class);
    TagReader reader = new TagReader(TagReaderTest.class.getResourceAsStream("/bigtest.nbt"));
    reader.accept(visitor);

    verify(visitor).visitRoot("Level");

    verify(visitor, times(5)).visitCompound();
    verify(visitor, times(6)).visitCompoundEnd();

    verify(visitor, times(4)).visitKey("name");
    verify(visitor, times(2)).visitKey("value");
    verify(visitor, times(2)).visitKey("created-on");
    verify(visitor, times(2)).visitLong(1264099775885L);

    verify(visitor).visitRoot("Level");
    verify(visitor).visitKey("nested compound test");

    verify(visitor).visitKey("egg");
    verify(visitor).visitString("Eggbert");
    verify(visitor).visitFloat(0.5f);

    verify(visitor).visitKey("ham");
    verify(visitor).visitString("Hampus");
    verify(visitor).visitFloat(0.75f);

    verify(visitor).visitKey("intTest");
    verify(visitor).visitInteger(2147483647);

    verify(visitor).visitKey("byteTest");
    verify(visitor).visitByte((byte) 127);

    verify(visitor).visitKey("stringTest");
    verify(visitor).visitString("HELLO WORLD THIS IS A TEST STRING ÅÄÖ!");

    verify(visitor).visitKey("listTest (long)");
    verify(visitor).visitList(TagType.LONG, 5);
    verify(visitor).visitLong(11);
    verify(visitor).visitLong(12);
    verify(visitor).visitLong(13);
    verify(visitor).visitLong(14);
    verify(visitor).visitLong(15);

    verify(visitor).visitKey("doubleTest");
    verify(visitor).visitDouble(0.49312871321823148d);

    verify(visitor).visitKey("floatTest");
    verify(visitor).visitFloat(0.49823147058486938f);

    verify(visitor).visitKey("longTest");
    verify(visitor).visitLong(9223372036854775807L);

    verify(visitor).visitKey("listTest (compound)");
    verify(visitor).visitList(TagType.COMPOUND, 2);

    verify(visitor).visitString("Compound tag #0");
    verify(visitor).visitString("Compound tag #1");

    verify(visitor).visitKey(
        "byteArrayTest (the first 1000 values of (n*n*255+n*7)%100, starting with n=0 (0, 62, 34, 16, 8, ...))");
    verify(visitor).visitByteArray(1000);
    verify(visitor, times(1001))
        .visitByte(anyByte()); // did you really think I'm going to test this?!

    verify(visitor).visitKey("shortTest");
    verify(visitor).visitShort((short) 32767);
  }

  /**
   * Tests the tag reader against a hello world NBT file.
   */
  @Test
  public void testHelloWorld() throws IOException {
    TagVisitor visitor = Mockito.mock(TagVisitor.class);
    TagReader reader = new TagReader(TagReaderTest.class.getResourceAsStream("/hello_world.nbt"));
    reader.accept(visitor);

    verify(visitor).visitRoot("hello world");
    verify(visitor).visitKey("name");
    verify(visitor).visitString("Bananrama");
    verify(visitor).visitCompoundEnd();
  }
}
