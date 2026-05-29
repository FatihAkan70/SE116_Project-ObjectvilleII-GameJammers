package objectville.cells.zones;

public class Industrial extends Zone{

    public Industrial(int coordinateX, int coordinateY) {
        super(coordinateX, coordinateY);
    }

    @Override
    public void updateLevel() {

        if (this.receivedElectricity == 0 || this.receivedWater == 0) {
            this.level = 0;
            return;
        }
        // shutdown to 0

        if (this.level == 0 & this.receivedElectricity > 0 & this.receivedInternet > 0 & this.receivedPopulation > 0) {
            this.level = 1;
            return;
        }
        // 0 to 1

        if (this.level == 1 & receivedPopulation == 0) {
            this.level = 0;
            return;
        }
        // 1 to 0

        if (this.level == 1 & this.hasSecurity) {
            this.level = 2;
            return;
        }
        // 1 to 2

        if ((this.level == 2) & (!this.hasSecurity || this.receivedPopulation == 0)) {
            this.level = 1;
            return;
        }
        // 2 to 1

        if (this.level == 2 & this.receivedPopulation > this.calculateOutput() ) {
            this.level = 3;
            return;
        }
        // 2 to 3

        if (this.level == 3 &  this.receivedPopulation <= this.calculateOutput()) {
            this.level = 2;
            return;
        }
        // 3 to 2

    }

    @Override
    public int calculateOutput() {

        if (this.level == 0) {
            return 0;
        }

        int m = Math.min(this.receivedElectricity, this.receivedWater);;

        if (this.level == 1) {
            return m;
        }

            else if (this.level == 2) {
            return 2 * m;
        }

            else if (this.level == 3) {
            return (2 * m) + this.receivedPopulation;
        }

        return 0;
    }
}
