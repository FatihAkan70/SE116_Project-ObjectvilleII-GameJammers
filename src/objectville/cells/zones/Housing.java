package objectville.cells.zones;

public class Housing extends Zone {

    public Housing(int coordinateX, int coordinateY) {
        super(coordinateX, coordinateY);

        needsElectricity = true;
        needsWater = true;
        needsInternet = true;

        needsSecurity = false;
        needsEducation = false;
        needsHealth = false;
    }

    @Override
    public void updateLevel() {

        if (this.receivedElectricity == 0 || this.receivedWater == 0 || this.receivedInternet == 0) {
            // shutdown
            this.level = 0;
        }
        else {
            if (this.level == 0) {
                // 0 to 1
                this.level = 1;
            }
            else if (this.level == 1) {

                if (this.hasSecurity && this.hasHealth && this.hasEducation) {
                    // 1 to 2
                    this.level = 2;
                }
            }
            else if (this.level == 2) {

                if (!this.hasSecurity || !this.hasHealth || !this.hasEducation) {
                    // 2 to 1
                    this.level = 1;
                }

                else if (this.receivedLifestyle > 0) {
                    // 2 to 3
                    this.level = 3;
                }
            }
            else if (this.level == 3) {

                if (this.receivedLifestyle <= 0 || !this.hasSecurity || !this.hasHealth || !this.hasEducation) {
                    // 3 to 2
                    this.level = 2;
                }
            }
        }

        if (this.getLevel() == 0 || this.getLevel() == 1)
        {
            needsElectricity = true;
            needsWater = true;
            needsInternet = true;
        }

        if (this.getLevel() == 2)
        {
            needsElectricity = true;
            needsWater = true;
            needsInternet = true;

            // Level 2 needs
            needsSecurity = true;
            needsHealth = true;
            needsEducation = true;
        }

        if (this.getLevel() == 3)
        {
            needsElectricity = true;
            needsWater = true;
            needsInternet = true;

            needsSecurity = true;
            needsHealth = true;
            needsEducation = true;

            // Level 3 needs
            needsLifestyle = true;
        }
    }

    @Override
    public int computeOutput() {

        if (this.level == 0) {
            return 0;
        }

        int m = Math.min(this.receivedElectricity, Math.min(this.receivedWater, this.receivedInternet));

        if (this.level == 1) {
            return m; //
        }

            else if (this.level == 2) {
            return 2 * m; //
        }

            else if (this.level == 3) {
            return (2 * m) + this.receivedLifestyle; //
        }

        return 0;
    }
}
