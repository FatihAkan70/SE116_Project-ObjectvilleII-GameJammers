package objectville.cells.zones;

public class Industrial extends Zone {

    public void setReceivedPopulation(int population) {
        this.receivedPopulation = population;
    }

    public Industrial(int coordinateX, int coordinateY) {
        super(coordinateX, coordinateY);

        needsPopulation = true;

        needsElectricity = true;
        needsWater = true;

        needsSecurity = false;
        needsEducation = false;
        needsHealth = false;

        needsExcessPopulation = false;
    }

    @Override
    public void updateLevel() {

        if (this.receivedElectricity == 0 || this.receivedWater == 0) {
            //shutdown
            this.level = 0;
        } else {
            if (this.level == 0) {
                if (this.receivedPopulation > 0) {
                    // 0 to 1
                    this.level = 1;
                }
            } else if (this.level == 1) {
                if (this.receivedPopulation == 0) {
                    // 1 to 0
                    this.level = 0;
                } else if (this.hasSecurity) {
                    // 1 to 2
                    this.level = 2;
                }
            } else if (this.level == 2) {
                if (!this.hasSecurity || this.receivedPopulation == 0) {
                    // 2 to 1
                    this.level = 1;
                } else if (this.receivedPopulation > this.computeOutput()) {
                    // 2 to 3
                    this.level = 3;
                }
            } else if (this.level == 3) {
                if (!this.hasSecurity || this.receivedPopulation <= this.computeOutput()) {
                    // 3 to 2
                    this.level = 2;
                }
            }
        }

        if (this.getLevel() == 0 || this.getLevel() == 1)
        {
            needsPopulation = true;
            needsElectricity = true;
            needsWater = true;

            // Level 2 needs
            needsSecurity = true;
        }

        if (this.getLevel() == 2)
        {
            needsPopulation = true;
            needsElectricity = true;
            needsWater = true;

            // Level 2 needs
            needsSecurity = true;

            // Level 3 needs
            needsExcessPopulation = true;
        }

        if (this.getLevel() == 3)
        {
            needsPopulation = true;
            needsElectricity = true;
            needsWater = true;

            needsSecurity = true;

            // Level 3 needs
            needsExcessPopulation = true;
        }
    }

    @Override
    public int computeOutput() {

        if (this.level == 0) {
            this.output = 0;
            return 0;
        }

        int m = Math.min(this.receivedElectricity, this.receivedWater);

        if (this.level == 1) {
            this.output = m;
        } else if (this.level == 2) {
            this.output = 2 * m;
        } else if (this.level == 3) {
            this.output = (2 * m) + this.receivedPopulation;
        }

        return this.output;
    }
}