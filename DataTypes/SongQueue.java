package DataTypes;

import java.io.Serializable;
import java.util.ArrayList;

public class SongQueue implements Serializable {

    private ArrayList<Song> MyList;

    //the default constructor that creates a queue. the default length for the list is 20
    public SongQueue()
    {
        MyList = new ArrayList<Song>(20);
    }

    //overloaded constructor that creates a queue. Takes in a default length in case you're expecting a large queue
    public SongQueue(int DefaultListLength)
    {
        MyList = new ArrayList<Song>(DefaultListLength);
    }

    public void addToQueue(Song newSong)
    {
        MyList.add(newSong);
    }

    //returns an array of the songs in the queue
    public Song[] ShowQueue()
    {
        return (Song[]) MyList.toArray();
    }

}
