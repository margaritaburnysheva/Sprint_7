package order;

public class Track {
    private int track;
    public Track(int track) {
        this.track = track;
    }
    public int getTrack() {
        return track;
    }
    public void setTrack(int track) {
        this.track = track;
    }

    @Override
    public String toString() {
        return "Track{" +
                "track=" + track +
                '}';
    }
}
