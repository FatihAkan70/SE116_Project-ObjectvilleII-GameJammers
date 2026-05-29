package objectville.cells.zones;

public class Housing extends Zone {

    public Housing(int coordinateX, int coordinateY) {
        super(coordinateX, coordinateY);
    }

    @Override
    public void updateLevel() {
        if (this.receivedElectricity == 0 || this.receivedInternet == 0 || this.receivedWater == 0) {
            this.level = 0;
            return;
        }
        if (this.level == 0 & this.receivedElectricity > 0 & this.receivedInternet > 0 & this.receivedWater > 0) {
            this.level = 1;
            return;
        }
        if (this.level == 1 & this.hasSecurity & this.hasEducation & this.hasHealth) {
            this.level = 2;
            return;
        }
        if ((this.level == 2) & (!this.hasHealth || !this.hasSecurity || !this.hasEducation)) {
            this.level = 1;
            return;
        }
            else if (this.level == 2 & this.receivedLifestyle > 0) {
            this.level = 3;
            return;
            }
        if (this.level == 3 & this.receivedLifestyle == 0) {
            this.level = 2;
            return;
        }
    }

    @Override
    public int calculateOutput() {

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
