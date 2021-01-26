import org.codehaus.groovy.grails.commons.DefaultGrailsDomainClass

class ImportStack {
    String type //agentNouveau, agentUpdate, agentPrioritaire, erreur
    String info //agent en Visite VE par ex si agentPrioritaire
    int row //ligne si erreur
    String erreur //erreur si erreur
    String matricule
    String nom
    String prenom

    static belongsTo = [rapport : Rapport]

    static constraints = {
        row(nullable:true)
        info(nullable:true)
        erreur(nullable:true)
        nom(nullable:true)
        prenom(nullable:true)
    }

    static mapping = {
        sort row : "desc"
    }

    def getDefaultProperties= {
        def domain = new DefaultGrailsDomainClass(this.class)
        def properties = [:]
        domain.persistentProperties.findAll{!it.isAssociation()}.each{
            properties[it.name] = this[it.name]
        }
        properties.id=this.id
        return properties
    }

}
