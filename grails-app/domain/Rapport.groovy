import java.text.SimpleDateFormat
import org.codehaus.groovy.grails.commons.DefaultGrailsDomainClass

class Rapport {
	
    Date date
    String sDate
    String type
    String statut

    static hasMany = [stack : ImportStack]
    
    static constraints = {
        statut(nullable:true)
        stack(nullable:true)
    }
    
    static mapping = {
        stack sort: 'row', order: 'asc'
    }

    def beforeValidate() {
        sDate = new SimpleDateFormat("dd MMM yyyy").format(date)
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
    
    def getRapportImport = {
        def result = this.getDefaultProperties()
        result.nouveau = []
        result.modifier = []
        result.prioritaire = []
        result.totalTypePrioritaire = [:]
        result.erreur = []
        this.stack.each(){
            switch (it.type){
            case "agentPrioritaire":
                result.prioritaire.add(it.getDefaultProperties())
                if(result.totalTypePrioritaire[it.info]){
                    result.totalTypePrioritaire[it.info]++
                }else{
                    result.totalTypePrioritaire[it.info]=1
                }
                break
            case "agentUpdate":
                result.modifier.add(it.getDefaultProperties())
                break
            case "agentNouveau":
                result.nouveau.add(it.getDefaultProperties())
                break
            case "erreur":
                result.erreur.add(it.getDefaultProperties())
                break
            }
        }
        return result
    }
}
