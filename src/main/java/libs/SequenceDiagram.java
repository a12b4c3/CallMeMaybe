package libs;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/** single class that keeps track of the sequence diagram for the DSL on www.sequencediagram.org
 *
 */
public class SequenceDiagram {
    private static SequenceDiagram instance = null;

    public ArrayList<String> getStringToPaint() {
        return stringToPaint;
    }

    private ArrayList<String> stringToPaint = new ArrayList<String>();
    private Set<String> participants = new HashSet<String>();
    private String title = null;
    private String entry = null;
    private boolean activationOn = false;

    // following fields are used to determine if the previous call has been returned (ie. for
    // both void or parameterized returns)

    public boolean elseStatement = false;

//    private boolean callNotReturned = false;
    private String classA = "";
    private String classB = "";

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
     * if the previous call has not been returned (as it is has a void return method), then
     * this method will be invoked to end the call and reset the accumulators.
     */
//    private void returnVoidCall() {
//        if (this.callNotReturned) {
//            this.stringToPaint.add(String.format("%s-->%s:", this.classB, this.classA));
//            this.resetReturnStatus();
//        }
//    }
//
//    private void resetReturnStatus() {
//        this.callNotReturned = false;
//        this.classA = "";
//        this.classB = "";
//    }

    /**
     * when theres a new call out of the current method (method A) to another method
     *         - BParticipant can be to itself (A) or to some other participant(B)
     * @param AParticipant
     * @param BParticipant
     * @param BMethod
     */
    public void addCallAToB(String AParticipant, String BParticipant, String BMethod, String params) {
        this.stringToPaint.add(String.format("%s->%s:%s(%s)", AParticipant, BParticipant, BMethod, params));
        this.classA = AParticipant;
        this.classB = BParticipant;
    }

    public void addReturn(String AParticipant, String BParticipant, String returnType) {
        if (returnType.equals("void")) {
            this.stringToPaint.add(String.format("%s-->%s:", AParticipant, BParticipant));
        } else {
            this.stringToPaint.add(String.format("%s-->%s: ret %s", AParticipant, BParticipant, " " + returnType));
        }
    }

    /**
     *
     * @param BParticipant
     * @param AParticipant
     */
//    public void addReturnCallBToA(String BParticipant, String AParticipant, String msg) {
//        this.stringToPaint.add(String.format("%s-->%s:%s", BParticipant, AParticipant, msg));
//        this.resetReturnStatus();
//    }

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
        this.stringToPaint.add("loop" + " " + loopCondition);
    }

    /**
     * the following calls are boxed in a rectangle indicating a alt.
     * @param ifCondition the initial condition
     */
    public void beginIf(String ifCondition, boolean start) {
        this.stringToPaint.add("alt" + " " + ifCondition);
        if (start) {
            this.elseStatement = true;
        }
    }

    /**
     * dash-lined section for more conditions
     * @param elseifCondition other conditions, can be empty
     */
    public void moreElseIf(String elseifCondition) {
        this.stringToPaint.add("else" + " " + elseifCondition);
    }

    /**
     * ends loops or if-conditions
     */

    public void endLoop() {
        this.stringToPaint.add("end");
    }

    /**
     * ends loops or if-conditions
     */
    public void endConditions() {
        this.stringToPaint.add("end");
    }

    /**
     * adds a comment into the DSL code that will not be shown on the callgraph
     * @param comment comment string to be added.
     */
    public void addComment(String comment) {
        this.stringToPaint.add("//%s" + comment);
    }

    /**
     * checks if the last input was returned.
     * returns the final string to be used as input for sequencediagram.org
     * @return
     */
    public String finishDiagram() {
        return String.join("\n", this.stringToPaint);
    }

    public void clear() {
        this.participants.clear();
        this.stringToPaint.clear();
        this.entry = null;
        this.title = null;
    }

}
