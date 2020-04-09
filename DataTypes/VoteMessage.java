package DataTypes;

import java.io.Serializable;

public class VoteMessage implements Serializable {

    private boolean isUpVote;
    private Song song;

    /**
     * This is the default constructor for the vote message.
     * Note: DON'T USE THIS.
     */
    public VoteMessage()
    {

    }

    /**
     * This is the constructor for the vote message
     * @param isUpVote
     * @param song
     */
    public VoteMessage(boolean isUpVote, Song song)
    {
        this.isUpVote = isUpVote;
        this.song = song;
    }

    public boolean isUpVote() {
        return isUpVote;
    }

    public Song getSong() {
        return song;
    }
}
