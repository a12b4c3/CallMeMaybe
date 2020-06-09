package libs;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/** single class that keeps track of the sequence diagram for the DSL on www.sequencediagram.org
 *
 */
public class SequenceDiagram {
    private static SequenceDiagram instance = null;
    private ArrayList<String> stringToPaint = new ArrayList<String>();
    private Set<String> participants = new HashSet<String>();
    private String title = null;
    private String entry = null;
    private boolean activationOn = false;

    private SequenceDiagram() {

    }

    public static SequenceDiagram getSequenceDiagram() {
        if (instance == null) {
            instance = new SequenceDiagram();
        }
        return instance;
    }

    /**
     * when theres a new participant (eg. class), call this to add a new lifeline to the call graph.
     *         - only adds a participant once.
     *         - ignores multiple identical participants being added
     * @param participant the title of the new lifeline
     */
    public void addParticipant(String participant) {
        if (!participants.contains(participant)) {
            participants.add(participant);
            this.stringToPaint.add("participant " + participant);
        }
    }

    /**
     * when theres a new call out of the current method (method A) to another method
     *         - BParticipant can be to itself (A) or to some other participant(B)
     * @param AParticipant
     * @param BParticipant
     * @param BMethod
     */
    public void addVoidCallAToB(String AParticipant, String BParticipant, String BMethod) {
        // todo
    }

    /**
     *
     * @param BParticipant
     * @param AParticipant
     */
    public void addReturnCallBToA(String BParticipant, String AParticipant) {
        // todo
    }

    /**
     *
     * @param className
     * @param methodName
     */
    public void addEntryPoint(String className, String methodName) {
        if (this.entry != null) {
            throw new RuntimeException("multiple entry point calls detected");
        }
        this.entry = methodName;
        this.stringToPaint.add(String.format("[->%s:%s()", className, methodName));
    }

    /**
     *  Adds a title to the callgraph
     * @param title the title of the callgraph
     */
    public void addTitle(String title) {
        if (this.title != null) {
            throw new RuntimeException(String.format("multiple title calls detected, previous:%s, current:%s",
                    this.title,
                    title));
        }
        this.title = title;
        this.stringToPaint.add("title " + title);
    }

    /**
     * turns on autoactivation, shows a rectangle around the running method.
     */
    public void autoActivationOn() {
        if (this.activationOn) {
            throw new RuntimeException("Autoactivation is already on");
        }
        this.activationOn = true;
        this.stringToPaint.add("autoactivation on");
    }

    /**
     *  turns off autoactivation.
     */
    public void autoActivationOff() {
        if (!this.activationOn) {
            throw new RuntimeException("Autoactivation is already off");
        }
        this.activationOn = false;
        this.stringToPaint.add("autoactivation off");
    }

    /**
     * the following calls are boxed in a rectangle indicating a loop.
     * @param loopCondition may be left empty, include the continue condition.
     */
    public void beginLoop(String loopCondition) {
        this.stringToPaint.add("loop " + loopCondition);
    }

    /**
     * calls preceding this are in a loop, calls following this ar no longer looping.
     */
    public void endLoop() {
        this.stringToPaint.add("end");
    }

    public void clear() {
        this.participants.clear();
        this.stringToPaint.clear();
        this.entry = null;
        this.title = null;
    }
}
