
import org.codehaus.groovy.grails.commons.DefaultGrailsDomainClass

class Groupe {
    
    String nom
    String libelle
    String email

    static hasMany = [agents : Agent]
    
    static constraints = {
        libelle (nullable:true)
    }
    
    def getDefaultProperties = {
        def domain = new DefaultGrailsDomainClass(this.class)
        def properties = [:]
        domain.persistentProperties.findAll{!it.isAssociation()}.each{
            properties[it.name] = this[it.name]
        }
        properties.id = this.id
        return properties
    }
    
    def getPropertiesForAll = {
        def properties = this.getDefaultProperties()
        properties.agents = ""
        this.agents.each(){agent ->
            properties.agents += agent.nom + ", "
        }
        return properties
    }
    
    def getPropertiesForOne = {
        def properties = this.getDefaultProperties()
        properties.agents = this.agents.collect{agent ->
            [ id : agent.id , matricule : agent.matricule , nom : agent.nom , prenom : agent.prenom ]
        }
        return properties
    }
    
    static def All = {
        def groups = this.getAll()
        def listGroup = groups.collect{group ->
            def properties = group.getPropertiesForAll()
        }
        return listGroup
    }
    
}
