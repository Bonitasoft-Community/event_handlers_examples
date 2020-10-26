
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
import org.bonitasoft.engine.data.instance.exception.SDataInstanceException;
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

/*
* This event handler fires when:
* 1 - A new human task is created
* 2 - When a task with a transient data called "status" is executed, and it retrieves it's value
* */


public class TransientTaskDataHandler implements SHandler {
    Logger logger = Logger.getLogger("org.bonitasoft");
    public long tenantId;
    public String transientDataName;
    private transient ProcessExecutor processExecutor;
    private transient TransientDataService transientDataInstanceService;

    public TransientTaskDataHandler(long tenantId, String transientDataName){
        this.tenantId = tenantId;
        this.transientDataName = transientDataName;
    }
    public void execute(SEvent event) throws SHandlerExecutionException{
        initializeTenantServices(tenantId);
        SDataInstance sDataInstance;
        //try {
            Object eventObject = event.getObject();
            if (eventObject instanceof SUserTaskInstance) {
                SUserTaskInstance taskInstance = (SUserTaskInstance) eventObject;
                //logger.info(String.format("******* Event fired: %s - %s", this.getClass().getName(), taskInstance.getStateName()));
                // Check if it's a task instantiation
                if (taskInstance.getStateName().equalsIgnoreCase("ready") && taskInstance.getAssigneeId()==0L){
                    logger.info("****Event handler: A new task is available without assigned********** " + taskInstance.getStateName());
                }
                else {
                    try {
                        sDataInstance = transientDataInstanceService.getDataInstance("status", taskInstance.getId(), DataInstanceContainer.ACTIVITY_INSTANCE.toString());
                        if (sDataInstance != null) {
                            if (taskInstance.getStateName().equalsIgnoreCase("completed"))
                                logger.info("****Event handler: Task executed  Value of status variable: ********** " + sDataInstance.getValue());
                        }
                    } catch (SDataInstanceException e) {
                        // It's possible that the human task doesn't have any status variable, so we don't treat the exception
                    }
                }

            }
    }
    private void initializeTenantServices(long tenantId) throws SHandlerExecutionException{
        TenantServiceAccessor tenantServiceAccessor = getTenantServiceAccessor(tenantId);
        processExecutor = tenantServiceAccessor.getProcessExecutor();
        transientDataInstanceService = tenantServiceAccessor.getTransientDataService();
    }

    TenantServiceAccessor getTenantServiceAccessor(long tenantId) throws SHandlerExecutionException {
        try {
            ServiceAccessorFactory serviceAccessorFactory = ServiceAccessorFactory.getInstance();
            return serviceAccessorFactory.createTenantServiceAccessor(tenantId);
        } catch (Exception e) {
            throw new SHandlerExecutionException(e.getMessage(), null);
        }
    }
    public boolean isInterested(SEvent event){
        //boolean isInterested = false;

           //isInterested = (activityInstance.getName().equalsIgnoreCase("Task eventHandler"));
        /*
        Object eventObject = event.getObject();
        if (eventObject instanceof SUserTaskInstance) {
            SUserTaskInstance taskInstance = (SUserTaskInstance) eventObject;
            isInterested = (taskInstance.getStateName().equalsIgnoreCase("ready") && taskInstance.getAssigneeId()!=0L);
        }

         */
        return true;
    }

    private TenantServiceAccessor getTenantServiceAccessor() throws SHandlerExecutionException{
        try{
            ServiceAccessorFactory serviceAccessorFactory = ServiceAccessorFactory.getInstance();
            return serviceAccessorFactory.createTenantServiceAccessor(tenantId);
        }
        catch(Exception e){
            throw new SHandlerExecutionException(e.getMessage(), null);
        }
    }
    public String getIdentifier(){
        return UUID.randomUUID().toString();
    }
}
