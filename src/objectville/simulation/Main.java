package objectville.simulation;

public class Main {

    public static void main(String[] args) {

        //checking if both the
        //filename:(where the map data is stored) and the
        //tick count:(how long the simulation runs) are provided or not
        if (args.length < 2) {
            //Fail Fast mechanism

            System.out.println( "Error: Missing parameters! " );
            return; }


        int tickCount = 0; //to count tours
        String fileName = args[0];


        //parsing if the type is not int to avoid problems
        try {
            tickCount = Integer.parseInt(args[1]); }


        catch (NumberFormatException e) {
            System.out.println( " Error: Tick count must be a number! " );
            return; }


        catch (Exception e) {
            System.out.println( " Error: Invalid tick count! " );
            return; }
    }
}