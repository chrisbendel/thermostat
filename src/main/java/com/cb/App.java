package com.cb;

import com.cb.model.ThermostatUpdate;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FilenameUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.GZIPInputStream;

public class App {
    public static void main(String[] args) throws IOException {

        if (args.length != 3) {
            System.out.println("Please use the following format and arguments: \n");
            System.out.println("mvn compile exec:java -Dexec.args=\"<fieldName> <filePath> <timestamp>\"\n");
            System.exit(0);
        }

        String fieldName = args[0];
        String filePath = args[1];
        LocalDateTime timeToSearch = LocalDateTime.parse(args[2]);

        ClassLoader classLoader = App.class.getClassLoader();
        File file = new File(Objects.requireNonNull(classLoader.getResource(filePath)).getFile());
        String extension = FilenameUtils.getExtension(file.getName());
        String thermostatJSON;
        if (extension.equals("gz")) {
            thermostatJSON = new String(decompressGzipToBytes(file.toPath()));
        } else {
            thermostatJSON = Files.readString(file.toPath(), StandardCharsets.UTF_8);
        }

        // Read the file IO
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        MappingIterator<ThermostatUpdate> iterator = objectMapper.readerFor(ThermostatUpdate.class).readValues(thermostatJSON);
        List<ThermostatUpdate> updates = new ArrayList<>();

        ThermostatUpdate previousValue = null;
        while (iterator.hasNextValue()) {
            ThermostatUpdate value = iterator.nextValue();
            // Instead of traversing everything later, just build up the models here
            // With the assumption that if nothing has changed
            // the next item will inherit anything previous unless it was updated
            if (previousValue != null) {
                value.merge(previousValue.getUpdate());
            }
            previousValue = value;
            updates.add(value);
        }

        // Finally, we can solve the problem at hand

        // Returns: index of the search key, if it is contained in the array; otherwise, (-(insertion point) - 1)
        int index = Collections.binarySearch(
            updates.stream().map(ThermostatUpdate::getUpdateTime).collect(Collectors.toList()),
            timeToSearch,
            LocalDateTime::compareTo
        );

        // https://stackoverflow.com/a/52435382
        // Depending on if we want to get the result before or after
        int leftIndex = (-index - 2);
        // int rightIndex = (-index - 1);
        ThermostatUpdate nearestUpdate = updates.get(leftIndex);

        System.out.println("Latest value for field " + fieldName + ": " + nearestUpdate.getUpdate().getPropertyValue(fieldName));
    }


    // decompress a Gzip file into a byte array
    // https://mkyong.com/java/how-to-decompress-file-from-gzip-file/
    public static byte[] decompressGzipToBytes(Path source) throws IOException {

        ByteArrayOutputStream output = new ByteArrayOutputStream();

        try (GZIPInputStream gis = new GZIPInputStream(
                new FileInputStream(source.toFile()))) {

            // copy GZIPInputStream to ByteArrayOutputStream
            byte[] buffer = new byte[1024];
            int len;
            while ((len = gis.read(buffer)) > 0) {
                output.write(buffer, 0, len);
            }
        }

        return output.toByteArray();
    }
}
