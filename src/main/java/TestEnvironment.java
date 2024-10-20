import com.opencsv.*;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvValidationException;


import java.io.*;
import java.net.URISyntaxException;
import java.time.format.DateTimeFormatter;
import java.time.*;
import java.util.stream.Stream;
import java.util.ArrayList;
import java.nio.file.Path;
import java.nio.file.Files;
import java.util.Arrays;
import java.nio.file.Paths;

public class TestEnvironment {

    public static void main(String[] args) throws IOException, CsvException {

        // getResourceAsStream tries to look for the file in the directory of the class it is invoked from, in this case, src
        // so append test/resources/<name of file>.csv to properly locate the directory and file location
        InputStream inputStream = TestEnvironment.class.getClassLoader().getResourceAsStream("Staff_List.csv");
        // System.out.println(inputStream == null); // if false, means the file is successfully located
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        ArrayList<String> staffList = new ArrayList<String>();

        CSVParser parser = new CSVParserBuilder()
                .withSeparator(',')
                .withIgnoreQuotations(true)
                .build();

        CSVReader csvReader = new CSVReaderBuilder(reader)
                .withSkipLines(1)       // set withSkipLines to 1 to skip the header row
                .withCSVParser(parser)
                .build();

        try (csvReader) {
            String[] line;
            while ((line = csvReader.readNext()) != null) {
                staffList.add(Arrays.toString(line));
            }
        }

        staffList.forEach(System.out::println);
    }
}
