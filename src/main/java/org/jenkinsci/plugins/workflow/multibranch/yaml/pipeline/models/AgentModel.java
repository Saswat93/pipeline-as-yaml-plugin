package org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.models;

import lombok.Getter;
import lombok.Setter;
import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.interfaces.ParsableModelInterface;

import java.util.List;


@Getter
@Setter
public class AgentModel extends AbstractModel implements ParsableModelInterface {

    public static final String directive = "agent";
    private String agentType;
    private List<KeyValueModel> agentParameter;

    public AgentModel(String agentType, List<KeyValueModel> agentParameter) {
        this.agentType = this.convertAgentType(agentType);
        this.agentParameter = agentParameter;
    }

    private String convertAgentType(String agentType) {
        if( agentType.equals("label"))
            return "node";
        return agentType;
    }


    @Override
    public String toGroovy() {
        StringBuffer groovyString = new StringBuffer()
                .append(directive);
        if(!this.agentType.equals("none") && !this.agentType.equals("any"))
                groovyString.append(this.getDirectiveOpen());
        else
            groovyString.append(" ");
        groovyString.append(this.agentType);
        if( this.agentParameter.size() > 0) {
            groovyString.append(this.getDirectiveOpen());
            this.agentParameter.forEach(keyValueModel -> groovyString.append(keyValueModel.toGroovy()));
            groovyString.append(this.getDirectiveClose());
        }
        if(!this.agentType.equals("none") && !this.agentType.equals("any"))
            groovyString.append(this.getDirectiveClose());
        else
            groovyString.append("\n");
        return groovyString.toString();
    }
}
