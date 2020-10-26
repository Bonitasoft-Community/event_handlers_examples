
package org.bonitasoft.handler;

import org.bonitasoft.engine.bpm.flownode.ActivityStates;
import org.bonitasoft.engine.business.data.BusinessDataService;
import org.bonitasoft.engine.commons.exceptions.SBonitaException;
import org.bonitasoft.engine.core.contract.data.ContractDataService;
import org.bonitasoft.engine.core.data.instance.TransientDataService;
import org.bonitasoft.engine.core.process.definition.model.SFlowNodeType;
import org.bonitasoft.engine.core.process.instance.api.ProcessInstanceService;
import org.bonitasoft.engine.core.process.instance.api.states.FlowNodeState;
import org.bonitasoft.engine.core.process.instance.model.SActivityInstance;
import org.bonitasoft.engine.core.process.instance.model.SFlowNodeInstance;
import org.bonitasoft.engine.core.process.instance.model.SUserTaskInstance;
import org.bonitasoft.engine.data.instance.api.DataInstanceContainer;
import org.bonitasoft.engine.data.instance.model.SDataInstance;
import org.bonitasoft.engine.events.model.SEvent;
import org.bonitasoft.engine.events.model.SHandler;
import org.bonitasoft.engine.events.model.SHandlerExecutionException;
import org.bonitasoft.engine.execution.ProcessExecutor;
import org.bonitasoft.engine.log.technical.TechnicalLogger;
import org.bonitasoft.engine.service.TenantServiceAccessor;
import org.bonitasoft.engine.service.impl.ServiceAccessorFactory;

import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;



public class UserAssignedHandler implements SHandler {
    Logger logger = Logger.getLogger("org.bonitasoft");
    public long tenantId;
    private transient ProcessExecutor processExecutor;
    private transient TransientDataService transientDataInstanceService;

    public UserAssignedHandler(long tenantId){
        this.tenantId = tenantId;
    }
    public void execute(SEvent event) throws SHandlerExecutionException{

        //try {
        Object eventObject = event.getObject();
        if (eventObject instanceof SUserTaskInstance) {
            SUserTaskInstance taskInstance = (SUserTaskInstance) eventObject;
            logger.info(String.format("******* Event fired: %s - %s", this.getClass().getName(), taskInstance.getStateName()));
        }

    }

    public boolean isInterested(SEvent event){
        //boolean isInterested = false;

        //isInterested = (activityInstance.getName().equalsIgnoreCase("Task eventHandler"));


        return true;
    }


    public String getIdentifier(){
        return UUID.randomUUID().toString();
    }
}
