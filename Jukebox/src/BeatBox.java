import javax.sound.midi.*;
import java.util.Vector;
import java.awt.Color;

/**
 * The BeatBox is a class that can play rhythm tracks.
 * 
 * The tracks to play are defined by a sequence of calls to the beat box as follows:
 * 
 * <pre>
 *   newTrack  - start the definition of a new track.
 *   addBeat*  - add a beat to the track (call multiple times).
 *   endTrack  - end the definition of a track.
 *   
 *   start     - start playing the defined track.
 *   stop      - stop playing the currently palying track.
 * </pre>
 * 
 * The beat box can also be used to do a single hit on an instrument by using
 * the 'beat(..)' method.
 * 
 * @author M. Kolling
 * @version 2004-11-16
 */
public class BeatBox 
{
    private static final int PROGRAM_CHANGE = 192;       // midi command constants
    private static final int NOTEON = 144;
    private static final int NOTEOFF = 128;
    private static final int END_OF_TRACK = 47;

    private static final int RHYTHM_CHANNEL = 9;  // the midi channel for our instruments
    private static final int VELOCITY = 100;      // how fast we hit the instrument
    
    private static int DEFAULT_TEMPO = 120;  // beats per minute
    
    private Sequencer sequencer = null;
    private Sequence sequence = null;
    private Track track;
    private int trackLength;
    private boolean doLoop;
    private int tempo;
    private boolean playing;
    private boolean definingTrack;
    
    /**
     * Create a beatbox engine.
     */
    public BeatBox()
    {
        try {
            sequencer = MidiSystem.getSequencer();
            sequencer.open();
        } 
        catch (MidiUnavailableException exc) { 
            exc.printStackTrace(); 
        }
        sequencer.addMetaEventListener(new MidiListener());
        doLoop = true;
        tempo = DEFAULT_TEMPO;
        playing = false;
        definingTrack = false;
    }

    /**
     * Do a single hit on a single instrument and stop again.
     * @param instrument The instument constant (see interface Instruments).
     */
    public void beat(int instrument)
    {
        boolean currentLoop = doLoop;
        newTrack(2);
        addBeat(0, instrument);
        endTrack();
        setLoop(false);
        start();
    }
    
    /**
     * Start defining a new track. 'length' is the length of the track in ticks.
     * For length N, valid ticks for beats are [0 .. N-1].
     * After calling newTrack, you can repeatedly call addBeat, and then endTrack to
     * finalise the track. Calling start will then play the track.
     * 
     * @param length  The length of the track in ticks.
     */
    public void newTrack(int length)
    {
        sequence = null;
        try {
            sequence = new Sequence(Sequence.PPQ, 4);
            track = sequence.createTrack();
            addMidiEvent(track, PROGRAM_CHANGE, 1, 0);      // start of track
            addMidiEvent(track, PROGRAM_CHANGE, 1, length); // end of track
            trackLength = length;
            definingTrack = true;
        }
        catch (InvalidMidiDataException exc) {
            exc.printStackTrace();
            sequence = null;
            definingTrack = false;
        }
    }
    

    /**
     * Add a beat to the current beat track. The parameters define the instrument being used, and
     * the tick in the track at which it is hit. Before calling this method, startTrack()
     * must be called. After repeatedly calling this method (befor playing), endTrack()
     * must be called.
     * 
     * @param tick  The tick number of this beat in the track.
     * @param instrument  The instrument type used.
     */
    public void addBeat(int tick, int instrument)
    {
        if(!definingTrack) {
            throw new IllegalStateException("addBeat() called without newTrack()");
        }
        if(tick < 0 || tick >= trackLength) {
            throw new IllegalArgumentException("The tick for a beat must be in [0..trackLength-1]");
        }

        addMidiEvent(track, NOTEON, instrument, tick);
        addMidiEvent(track, NOTEOFF, instrument, tick+1); 
    }
    
    
    /**
     * End the definition of a beat track. 
     * This method may only be called after startTrack() has been called.
     */
    public void endTrack()
    {
        if(!definingTrack) {
            throw new IllegalStateException("endTrack() called without newTrack()");
        }

        try {
            sequencer.setSequence(sequence);
        } 
        catch (InvalidMidiDataException exc) { 
            exc.printStackTrace(); 
        }
        definingTrack = false;
    }


    /**
     * Start playing the current track. The current track must have been completely
     * defined before playing (by calling a sequence of newTrack/{addBeat}/endTrack).
     */
    public void start()
    {
        if(definingTrack) {
            throw new IllegalStateException("start() called before endTrack()");
        }

        sequencer.start();
        sequencer.setTempoInBPM(tempo);
        playing = true;
    }

    /**
     * Stop playing the current track.
     */
    public void stop()
    {
        sequencer.stop();
    }

    /**
     * Set the beat box looping mode: When 'loop' is true, the track is played in a loop
     * indefinitely until stopped. By default, loop is true.
     */
    public void setLoop(boolean loop)
    {
        doLoop = loop;
    }

    /**
     * Set the playback tempo (in beats per minute). The default tempo is 120.
     */
    public void setTempo(int newTempo)
    {
        tempo = newTempo;
    }
    
    /**
     * Tell whether the beat box is currently playing.
     */
    public boolean isPlaying()
    {
        return playing;
    }

    /**
     * Add a midi message (such as NOTEON) to the given track.
     */
    private void addMidiEvent(Track track, int command, int instrument, long tick) 
    {
        ShortMessage message = new ShortMessage();
        try {
            message.setMessage(command, RHYTHM_CHANNEL, instrument, VELOCITY); 
            MidiEvent event = new MidiEvent(message, tick);
            track.add(event);
        } 
        catch (InvalidMidiDataException exc) { 
            exc.printStackTrace(); 
        }
    }

    // private class for listening to end-of-track events from the sequencer
    
    private class MidiListener implements MetaEventListener 
    {
        /**
         * Called when receiving a meta message. We are only interested in END_OF_TRACK.
         */
        public void meta(MetaMessage message) 
        {
            if(message.getType() == END_OF_TRACK) {
                if(doLoop) {
                    if(sequencer != null && sequencer.isOpen()) {
                        sequencer.start();
                        sequencer.setTempoInBPM(tempo);
                    }
                }
                else {
                    playing = false;
                }
            }
        }        
    }

}
