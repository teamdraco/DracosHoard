package coda.weecore;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

public class FunnyUUIDThing {

    public static void main(String[] args) {
        writeId(UUID.fromString(args[0]));
    }

    public static void writeId(UUID id) {
        Path path = Paths.get("supporter_ids.bin");
        try (
                OutputStream fileOut = Files.newOutputStream(path);
                DataOutputStream output = new DataOutputStream(fileOut);
        ) {
            //for (UUID id : ids) {
                output.writeLong(id.getLeastSignificantBits());
                output.writeLong(id.getMostSignificantBits());
            //}
        } catch (IOException exception) {
            // Can't open file sadge
        }
    }
}
