/**
 * Provides a modern NBT parser/writer implementation capable of running in tree or event mode to
 * improve performance where possible.
 *
 * @author <a href="mailto:johannesd@torchmind.com">Johannes Donath</a>
 */
module io.github.lordakkarin.nbt {
  exports io.github.lordakkarin.nbt.event;
  exports io.github.lordakkarin.nbt.tree;

  requires static com.github.spotbugs.annotations;
  requires io.netty.buffer;
}
