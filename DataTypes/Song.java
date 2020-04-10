package DataTypes;

/**
 * This class is intended to be the storage medium used to hold a song and all the info about it.
 */
public class Song {

    private String Title;
    private String Artist;

    private int Upvotes;
    private int Downvotes;
    private int QueuePos;

    //Constructor for a Song
    public Song(String Title, String Artist, int QueuePos)
    {
        this.Artist = Artist;
        this.Title = Title;
        this.QueuePos = QueuePos;

        this.Upvotes = 0;
        this.Downvotes = 0;
    }

    //Constructor for a Song
    public Song(String Title, String Artist)
    {
        this.Artist = Artist;
        this.Title = Title;
        this.QueuePos = -1;

        this.Upvotes = 0;
        this.Downvotes = 0;
    }

    public String getTitle() {
        return Title;
    }

    public String getArtist() {
        return Artist;
    }

    public int getUpvotes() {
        return Upvotes;
    }

    public void addUpvote() {
        Upvotes++;
    }

    public int getDownvotes() {
        return Downvotes;
    }

    public void addDownvote() {
        Downvotes++;
    }

    public int getQueuePos() {
        return QueuePos;
    }

    public void setQueuePos(int queuePos) {
        QueuePos = queuePos;
    }

    //returns the number of upvotes - downvotes
    public int getScore()
    {
        return getUpvotes() - getDownvotes();
    }
}
