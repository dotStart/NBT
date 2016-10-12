package io.github.lordakkarin.nbt.event;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;

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
     *
     * @throws IndexOutOfBoundsException when the specified tagId is out of bounds.
     */
    @Nonnull
    public static TagType byTypeId(@Nonnegative int typeId) {
        TagType[] types = values();

        if (typeId >= types.length) {
            throw new IndexOutOfBoundsException("No such typeId: " + typeId);
        }

        return types[typeId];
    }
}
