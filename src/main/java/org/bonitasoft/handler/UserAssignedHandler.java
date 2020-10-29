
package org.bonitasoft.handler;


import org.bonitasoft.engine.core.data.instance.TransientDataService;
import org.bonitasoft.engine.core.process.instance.model.SUserTaskInstance;
import org.bonitasoft.engine.events.model.SEvent;
import org.bonitasoft.engine.events.model.SHandler;
import org.bonitasoft.engine.events.model.SHandlerExecutionException;
import org.bonitasoft.engine.execution.ProcessExecutor;
import java.util.UUID;
import java.util.logging.Logger;



public class UserAssignedHandler implements SHandler {
    Logger logger = Logger.getLogger("org.bonitasoft.handlers");
    public long tenantId;
    private transient ProcessExecutor processExecutor;
    private transient TransientDataService transientDataInstanceService;

    public UserAssignedHandler(long tenantId){
        this.tenantId = tenantId;
    }
    public void execute(SEvent event) throws SHandlerExecutionException{

        Object eventObject = event.getObject();
        if (eventObject instanceof SUserTaskInstance) {
            SUserTaskInstance taskInstance = (SUserTaskInstance) eventObject;
            logger.info(String.format("******* Event handler %s fired. The task: %s id:%s is now assigned to user: %s", this.getClass().getName(), taskInstance.getDisplayName(),taskInstance.getId(), taskInstance.getAssigneeId()));
        }
    }

    public boolean isInterested(SEvent event){
        //We want to deal with all the events of type HUMAN_TASK_INSTANCE_ASSIGNEE_UPDATED.
        // Since this filter is already set in the bonita-tenant-sp-custom.xml we can just return true
        return true;
    }


    public String getIdentifier(){
        return UUID.randomUUID().toString();
    }
}
