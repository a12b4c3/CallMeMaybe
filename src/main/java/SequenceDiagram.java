import java.util.ArrayList;

/*  single class that keeps track of the sequence diagram for the DSL on www.sequencediagram.org

 */
public class SequenceDiagram {
    private static SequenceDiagram instance = null;
    private ArrayList<String> stringToPaint = new ArrayList<String>();
    private ArrayList<String> participants = new ArrayList<String>();
    private String title = "Callgraph";
    private String entry = "default entry";
    private SequenceDiagram() {

    }

    public static SequenceDiagram getSequenceDiagram() {
        if (instance == null) {
            instance = new SequenceDiagram();
        }
        return instance;
    }

    /* when theres a new participant (eg. class), call this to add a new lifeline to the call graph.
        - only adds a participant once.
        - throws error if participant is added more than once.
    input:
        participant (string): the title of the new lifeline
    output: none, only make changes via side effect.
     */
    public void addParticipant(String participant) {
        this.stringToPaint.add("participant " + participant);
        // todo
    }

    /*  when theres a new call out of the current method (method A) to another method
        - BParticipant can be to itself (A) or to some other participant(B)
    input:
        A
     */
    public void addVoidCallAToB(String AParticipant, String BMethod, String BParticipant) {
        // todo
    }

    public void addReturnCallBToA(String BParticipant, String AParticipant) {
        // todo
    }

    public void addTitle(String title) {
        // todo
    }
}
