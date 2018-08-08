package fr.ahkrin.rp.utils;

public class Sound {

    private int id;
    private double volume;
    private double pitch;

    public Sound(int id, double volume, double pitch) {
        this.id = id;
        this.volume = volume;
        this.pitch = pitch;
    }

    public int getId() {
        return id;
    }

    public double getVolume() {
        return volume;
    }

    public double getPitch() {
        return pitch;
    }

}
