
public class Device {

    String nameDevice;
    volatile boolean busy = false;

    public Device(String nameDevice) {
        this.nameDevice = nameDevice;
    }

    public boolean isBusy() {
        return busy;
    }

    public void setBusy(boolean busy) {
        this.busy = busy;
    }
}
