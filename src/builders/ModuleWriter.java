package builders;

import java.io.BufferedWriter;
import java.io.IOException;

public interface ModuleWriter {
    default void writeRow(BufferedWriter writer, String text) throws IOException {
        writer.write(text);
        writer.newLine();
    }
}
