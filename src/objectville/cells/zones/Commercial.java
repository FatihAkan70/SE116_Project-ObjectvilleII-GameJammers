package objectville.cells.zones;

public class Commercial extends Zone {

    public Commercial(int coordinateX, int coordinateY) {
        super(coordinateX, coordinateY);

        needsPopulation = true;
        needsGoods = true;

        needsElectricity = true;
        needsWater = true;
        needsInternet = true;

        needsSecurity = false;
        needsEducation = false;
        needsHealth = false;

        needsExcessLifestyle = false;
        needsExcessGoods = false;
    }

    @Override
    public void updateLevel() {

        if (this.receivedElectricity == 0 || this.receivedWater == 0 || this.receivedInternet == 0) {
            //shutdown
            this.level = 0;
        }
        else {
            if (this.level == 0) {
                if (this.receivedPopulation > 0 && this.receivedGoods > 0) {
                    // 0 to 1
                    this.level = 1;
                }
            }
            else if (this.level == 1) {
                if (this.receivedPopulation == 0 || this.receivedGoods == 0) {
                    // 1 to 0
                    this.level = 0;
                }
                else if (this.hasSecurity) {
                    // 1 to 2
                    this.level = 2;
                }
            }
            else if (this.level == 2) {

                if (!this.hasSecurity || this.receivedPopulation == 0 || this.receivedGoods == 0) {
                    // 2 to 1
                    this.level = 1;
                }

                else if (this.receivedPopulation > this.computeOutput() && this.receivedGoods > this.computeOutput()) {
                    // 2 to 3
                    this.level = 3;
                }
            }
            else if (this.level == 3) {
                // 3 to 2
                if (!this.hasSecurity || this.receivedPopulation <= this.computeOutput() || this.receivedGoods <= this.computeOutput()) {
                    this.level = 2;
                }
            }
        }
        if (this.getLevel() == 0 || this.getLevel() == 1)
        {
            needsPopulation = true;
            needsGoods = true;

            needsElectricity = true;
            needsWater = true;
            needsInternet = true;
        }

        if (this.getLevel() == 2)
        {
            needsPopulation = true;
            needsGoods = true;

            needsElectricity = true;
            needsWater = true;
            needsInternet = true;

            // Level 2 needs
            needsSecurity = true;

        }

        if (this.getLevel() == 3)
        {
            needsPopulation = true;
            needsGoods = true;

            needsElectricity = true;
            needsWater = true;
            needsInternet = true;

            needsSecurity = true;

            // Level 3 needs
            needsExcessLifestyle = true;
            needsExcessGoods = true;
        }
    }

    @Override
    public int computeOutput() {

        if (this.level == 0) {
            this.output = 0;
            return 0;
        }

        int m = Math.min(this.receivedElectricity, Math.min(this.receivedWater, this.receivedInternet));

        if (this.level == 1) {
            this.output = m;
        }
        else if (this.level == 2) {
            this.output = 2 * m;
        }
        else if (this.level == 3) {
            int minResource = Math.min(this.receivedPopulation, this.receivedGoods);
            this.output = (2 * m) + minResource;
        }

        return this.output;
    }
}
