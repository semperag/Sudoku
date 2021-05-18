import java.io.IOException;
import java.io.Writer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CodingErrorAction;
import java.nio.file.Files;
import java.util.Arrays;

public class EncoderTest
{
  public static void main(String[] args) throws IOException
  {
    //Create dummy channel
    WritableByteChannel channel = FileChannel.open(Files.createTempFile(null, null));

    //Create an UTF-8 encoder which ignores malformed input
    Charset charset = Charset.forName("UTF-8");
    CharsetEncoder encoder = charset.newEncoder();
    encoder.onMalformedInput(CodingErrorAction.IGNORE);

    //Create a char array which is filled with high surrogates
    char[] charArray = new char[1024 * 1024];
    Arrays.fill(charArray, '\uD834');

    //Create encoder, encode a single char, encode remaining chars.
    Writer enc = Channels.newWriter(channel, encoder, 1);
    enc.write(charArray, 0, 1);
    enc.write(charArray, 0, charArray.length); //BOOM!
  }
}