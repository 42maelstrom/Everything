import java.awt.event.ActionEvent;


/**
 * Write a description of class TestBeat here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class TestBeat
{
    public BeatBox box;

    /**
     * Constructor for objects of class TestBeat
     */
    public TestBeat()
    {
        box = new BeatBox();
    }

    /**
     * A very simple beat.
     */
	public void testBeat1()
	{
	    // standard 16 tick track
		box.newTrack(16);

		// straight base line
		box.addBeat(0, 35);
		box.addBeat(4, 35);
		box.addBeat(8, 35);
		box.addBeat(12, 35);
		
		// clapping on the off-beat...
		box.addBeat(6, 39);
		box.addBeat(10, 39);
		box.addBeat(11, 39);

		box.addBeat(2, 81);
		
		box.endTrack();
		box.start();
	}

    /**
     * A standard rock beat.
     */
	public void testBeat2()
	{
	    // standard 16 tick (4 beat) track
		box.newTrack(16);

		// base drum
		box.addBeat(0, 35);
		box.addBeat(2, 35);
		box.addBeat(3, 35);
		box.addBeat(7, 35);
		box.addBeat(9, 35);
		box.addBeat(10, 35);
		box.addBeat(15, 35);
		
		// pedal hi-hat straight every second tick
		for(int i=0; i<16; i+=2) {
		    box.addBeat(i, 44);
		}

		// cymbal straight on every beat
		for(int i=0; i<16; i+=4) {
		    box.addBeat(i, 49);
		}

        // and an acoustic snare at the off beats...
   	    box.addBeat(4, 38);
   	    box.addBeat(12, 38);
		
		box.endTrack();
		box.start();
	}

	public void testBeat3()
	{
	    // standard 16 tick (4 beat) track
		box.newTrack(16);

		// base drum
		box.addBeat(0, 36);
		box.addBeat(3, 36);
		box.addBeat(6, 36);
		box.addBeat(9, 36);
		box.addBeat(15, 36);
		
		// pedal hi-hat on the counter beat
		for(int i=2; i<16; i+=4) {
		    box.addBeat(i, 44);
		}

		// clap your hands
   	    box.addBeat(4, 39);
   	    box.addBeat(12, 39);
		
   	    box.addBeat(13, 50);  // high tom
   	    box.addBeat(14, 45);  // low tom

        // and a few hits on the old cow bell
   	    box.addBeat(0, 56);
   	    box.addBeat(2, 56);
   	    box.addBeat(6, 56);
   	    box.addBeat(10, 56);
   	    box.addBeat(12, 56);

		box.endTrack();
		box.start();
	}

    public void stop()
    {
        box.stop();
    }

    public void setTempo(int newTempo)
    {
        box.setTempo(newTempo);
    }
    
    public static void main(String[] args) 
    {
        TestBeat test = new TestBeat();
        test.testBeat3();
    }
}
