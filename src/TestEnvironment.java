import com.opencsv.*;
import com.opencsv.exceptions.CsvValidationException;


import java.io.IOException;
import java.net.URISyntaxException;
import java.time.format.DateTimeFormatter;
import java.time.*;
import java.util.stream.Stream;
import java.util.ArrayList;
import java.nio.file.Path;
import java.io.FileReader;
import java.io.Reader;
import java.nio.file.Files;
import java.util.Arrays;
import java.io.File;
import java.nio.file.Paths;

public class TestEnvironment {

    public static void main(String[] args) {

        Path path;
        try {
            path = Paths.get(ClassLoader.getSystemResource("/src/test/resources/Staff_List.csv").toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        ArrayList<String> list = new ArrayList<String>();
        try (Reader reader = Files.newBufferedReader(path)) {
            try (CSVReader csvReader = new CSVReader(reader)) {
                String[] line;
                while ((line = csvReader.readNext()) != null) {
                    list.add(Arrays.toString(line));
                }
            } catch (CsvValidationException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        list.forEach(System.out::println);

//        System.out.println(new File("."). getAbsolutePath());

//        try {
//            File file = new File(TestEnvironment.class.getResource("/src/test/resources/Staff_List.csv").toURI());
//
//            CSVReader reader = new CSVReader(new FileReader(file));
//            String[] nextLine;
//            while((nextLine = reader.readNext()) != null)
//            {
//                if(nextLine != null){
//                    System.out.println(Arrays.toString(nextLine));
//                }
//            }
//        } catch (Exception e){
//            System.out.println(e);
//        }

//        CSVParser parser = new CSVParserBuilder()
//                .withSeparator(',')
//                .withIgnoreQuotations(true)
//                .build();
//
//        CSVReader csvReader = new CSVReaderBuilder(reader)
//                .withSkipLines(0)
//                .withCSVParser(parser)
//                .build();
    }
}
