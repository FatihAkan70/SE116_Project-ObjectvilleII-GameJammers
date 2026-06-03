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

        if (this.receivedElectricity == 0 || this.receivedInternet == 0 || this.receivedWater == 0) {
            this.level = 0;
            return;
        }
        // shutdown to 0

        if (this.level == 0 & this.receivedElectricity > 0 & this.receivedInternet > 0 & this.receivedWater > 0) {
            this.level = 1;
            return;
        }
        // 0 to 1

        if (this.level == 1 & this.hasSecurity & this.hasEducation & this.hasHealth) {
            this.level = 2;
            return;
        }
        // 1 to 2

        if ((this.level == 2) & (!this.hasHealth || !this.hasSecurity || !this.hasEducation)) {
            this.level = 1;
            return;
        }
        // 2 to 1

        if (this.level == 2 & this.receivedLifestyle > 0) {
            this.level = 3;
            return;
        }
        // 2 to 3

        if (this.level == 3 & this.receivedLifestyle == 0) {
            this.level = 2;
        }
        // 3 to 2


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
