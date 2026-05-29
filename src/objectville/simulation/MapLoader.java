package objectville.simulation;

import java.io.BufferedReader; //for file reading
import java.io.FileReader;     //
import java.io.IOException;    //for exception throwing
import java.util.ArrayList;

import objectville.grid.Cell;  //Cell[][]

import objectville.cells.infrastructure.Road; //for using later
import objectville.cells.zones.Housing;
import objectville.cells.zones.Industrial;
import objectville.cells.zones.Commercial;

public class MapLoader {

    //no constructor bcz no parameters

    public Cell[][] mapLoad (String filePath) throws IOException {
        ArrayList<String> lines = new ArrayList<>();

        //using bufferedreader to read our string fully
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {

                int trimmed= line.trim().length();
                if (trimmed > 0) {
                    lines.add(line.trim()); }
            }
        }

        // checking for empty file
        if (lines.size() == 0) {
            throw new IllegalArgumentException( " File is empty ! "  ); }

        int tCoordinateY = lines.size(); //total line num is y
        int tCoordinateX = lines.get(0).length(); //the top lines char num is x

        Cell[][] cell = new Cell[tCoordinateY][tCoordinateX];

        //reaching one by one from y to totalY
        for (int coordinateY = 0; coordinateY < tCoordinateY; coordinateY++) {
            String rowString = lines.get(coordinateY);
            for (int coordinateX = 0; coordinateX < tCoordinateX; coordinateX++) {
                char type = rowString.charAt(coordinateX); //character parsing
               // cell[coordinateY][coordinateX] = we need a func there to take our parsed chars and put them on a new cell.
            }
        }

        return cell;
    }

}