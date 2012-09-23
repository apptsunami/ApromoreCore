/**
 * Copyright 2012, Felix Mannhardt
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package org.apromore.canoniser.yawl.internal.impl.handler.yawl.controlflow;

import org.apromore.canoniser.exception.CanoniserException;
import org.apromore.cpf.ANDSplitType;
import org.apromore.cpf.CancellationRefType;
import org.apromore.cpf.DirectionType;
import org.apromore.cpf.MessageType;
import org.apromore.cpf.NodeType;
import org.apromore.cpf.TaskType;
import org.apromore.cpf.TimerType;
import org.apromore.cpf.XORJoinType;
import org.yawlfoundation.yawlschema.TimerTriggerType;

/**
 * Converts a YAWL Timer to CPF.
 *
 * @author <a href="felix.mannhardt@smail.wir.h-brs.de">Felix Mannhardt (Bonn-Rhein-Sieg University oAS)</a>
 *
 */
public class TimerTaskHandler extends BaseTaskHandler {

    /*
     * (non-Javadoc)
     *
     * @see org.apromore.canoniser.yawl.internal.impl.handler.yawl.controlflow.BaseTaskHandler#convert()
     */
    @Override
    public void convert() throws CanoniserException {

        if (isAutomaticTask(getObject())) {
            // Add a Timer as delay before Automatic Task is executed

            // Convert CPF Timer Event -> CPF Task
            final TimerType timerNode = createTimer();
            final NodeType taskNode = createTask(getObject());
            createSimpleEdge(timerNode, taskNode);

            // Link correctly to predecessor and successors separating the routing behavior from the task.
            linkToPredecessors(timerNode);
            linkToSucessors(taskNode);

        } else {
            // Is a Timeout either onEnablement or onStart of the Task
            // So the Task will automatically be completed as soon as the Timer expires.

            if (getObject().getTimer().getTrigger().equals(TimerTriggerType.ON_ENABLED)) {
                final ANDSplitType andSplit = createANDSplit();
                final TimerType timerNode = createTimer();
                final TaskType taskNode = createTask(getObject());
                timerNode.getCancelNodeId().add(createCancellationRegion(taskNode));
                taskNode.getCancelNodeId().add(createCancellationRegion(timerNode));
                createSimpleEdge(andSplit, timerNode);
                createSimpleEdge(andSplit, taskNode);
                final XORJoinType xorJoin = createXORJoin();
                createSimpleEdge(timerNode, xorJoin);
                createSimpleEdge(taskNode, xorJoin);

                // Link correctly to predecessor and successors separating the routing behavior from the task.
                linkToPredecessors(andSplit);
                linkToSucessors(xorJoin);

            } else {
                final ANDSplitType andSplit = createANDSplit();
                final TimerType timerNode = createTimer();
                final MessageType messageNode = createMessage(DirectionType.INCOMING);
                timerNode.getCancelNodeId().add(createCancellationRegion(messageNode));
                messageNode.getCancelNodeId().add(createCancellationRegion(timerNode));
                createSimpleEdge(andSplit, timerNode);
                createSimpleEdge(andSplit, messageNode);
                final XORJoinType xorJoin = createXORJoin();
                createSimpleEdge(timerNode, xorJoin);
                createSimpleEdge(messageNode, xorJoin);

                final TaskType task = createTask(getObject());
                createSimpleEdge(xorJoin, task);

                // Link correctly to predecessor and successors separating the routing behavior from the task.
                linkToPredecessors(andSplit);
                linkToSucessors(task);
            }
        }

        super.convert();
    }

    private CancellationRefType createCancellationRegion(final NodeType node) {
        final CancellationRefType ref = getContext().getCanonicalOF().createCancellationRefType();
        ref.setRefId(node.getId());
        return ref;
    }

    /**
     * Return a TimerType node that was not part of the original YAWL specification. The node is already added to its parent Net.
     *
     * @param element
     * @return the converted TimerType
     */
    protected TimerType createTimer() {
        final TimerType timer = getContext().getCanonicalOF().createTimerType();
        timer.setId(generateUUID());
        timer.setOriginalID(null);
        getConvertedParent().getNode().add(timer);
        return timer;
    }

}