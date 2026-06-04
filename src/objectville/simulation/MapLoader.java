package objectville.simulation;

import java.io.BufferedReader; //for file reading
import java.io.FileReader;     //
import java.io.IOException;    //for exception throwing
import java.util.ArrayList;
import java.util.HashMap;

import objectville.cells.providers.services.Hospital;
import objectville.cells.providers.services.PoliceStation;
import objectville.cells.providers.services.School;
import objectville.cells.providers.utilities.InternetHub;
import objectville.cells.providers.utilities.PowerPlant;
import objectville.cells.providers.utilities.WaterPumpingStation;
import objectville.grid.Cell;  //Cell[][]

import objectville.cells.infrastructure.* ; //for using later
import objectville.cells.zones.* ;
import objectville.grid.EmptyCell;

public class MapLoader {

    //no constructor bcz no parameters

    public Cell[][] mapLoad (String filePath) throws IOException {
        ArrayList<String> lines = new ArrayList<>();

        //using bufferedreader to read our string fully
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {

                int trimmed = line.trim().length();
                if (trimmed > 0) {
                    lines.add(line.trim());
                }
            }
        }

        // checking for empty file
        if (lines.size() == 0) {
            throw new IllegalArgumentException(" File is empty ! ");
        }

        int tCoordinateY = lines.size(); //total line num is y
        int tCoordinateX = lines.get(0).length(); //the top lines char num is x

        Cell[][] cell = new Cell[tCoordinateX][tCoordinateY];

        HashMap<String, Object> objectHashMap = new HashMap<>(10);

        //reaching one by one from y to totalY
        for (int coordinateY = 0; coordinateY < tCoordinateY; coordinateY++) {
            String rowString = lines.get(coordinateY);
            for (int coordinateX = 0; coordinateX < tCoordinateX; coordinateX++) {
                char type = rowString.charAt(coordinateX); //character parsing
                switch (type) {

                    // Zones
                    case 'H':
                        cell[coordinateX][coordinateY] = new Housing(coordinateX, coordinateY);
                        break;

                    case 'C':
                        cell[coordinateX][coordinateY] = new Commercial(coordinateX, coordinateY);
                        break;

                    case 'I':
                        cell[coordinateX][coordinateY] = new Industrial(coordinateX, coordinateY);
                        break;

                    // Services
                    case 'S':
                        cell[coordinateX][coordinateY] = new School(coordinateX, coordinateY);
                        break;

                    case 'F':
                        cell[coordinateX][coordinateY] = new PoliceStation(coordinateX, coordinateY);
                        break;

                    case 'D':
                        cell[coordinateX][coordinateY] = new Hospital(coordinateX, coordinateY);
                        break;

                    // Utilities
                    case 'T':
                        cell[coordinateX][coordinateY] = new InternetHub(100, coordinateX, coordinateY);
                        break;

                    case 'W':
                        cell[coordinateX][coordinateY] = new WaterPumpingStation(100, coordinateX, coordinateY);
                        break;

                    case 'P':
                        cell[coordinateX][coordinateY] = new PowerPlant(100, coordinateX, coordinateY);
                        break;

                    // Grids
                    case 'R':
                        cell[coordinateX][coordinateY] = new Road(coordinateX, coordinateY);
                        break;

                    case 'E':
                        cell[coordinateX][coordinateY] = new EmptyCell(coordinateX,coordinateY);
                        break;
                    default:
                        throw new IllegalArgumentException(
                                "Unknown map character: " + type +
                                        " at (" + coordinateX + "," + coordinateY + ")"
                        );
                }
            }

        }
        return cell;
    }
}