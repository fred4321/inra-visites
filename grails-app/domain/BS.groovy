
import java.text.SimpleDateFormat
import org.codehaus.groovy.grails.commons.DefaultGrailsDomainClass


/*
 * Liste des status de bs
 * {statut : 'créé - programmé - convoqué - absent - effectué'}
 */
class BS {
    String lieu
    String type
    boolean absent
    Date datePrescription
    Date dateConvocation
    Date datePassage
    
    String sDatePrescription
    String sDateConvocation
    String sDatePassage
    
    String heureDebut
    String heureFin
    String statut
    List remarques
		
    static belongsTo = [agent : Agent, vm : VM]
    static hasMany = [remarques : Observation]
    
    static mapping = {
        remarques cascade: 'all-delete-orphan'
        type type: 'text'
        absent defaultValue : false
        statut defaultValue : "'créé'"
    }

    static constraints = {
        statut(nullble:true)
        lieu(nullable:true)
        dateConvocation(nullable:true)
        datePassage(nullable:true)
        heureDebut(nullable:true)
        heureFin(nullable:true)
        remarques(nullable:true)
        sDatePrescription(nullable:true)
        sDateConvocation(nullable:true)
        sDatePassage(nullable:true)
    }
    
    def beforeValidate() {
        SimpleDateFormat formater = new SimpleDateFormat("dd MMM yyyy")
        if(datePrescription){
            datePrescription.set(hourOfDay : 12)
            sDatePrescription = formater.format(datePrescription)
        }
        if(dateConvocation){
            datePrescription.set(hourOfDay : 12)
            sDateConvocation = formater.format(dateConvocation)
        }
        if(datePassage){
            datePrescription.set(hourOfDay : 12)
            sDatePassage = formater.format(datePassage)
        }
    }
    
    def getDefaultProperties = {
        def domain = new DefaultGrailsDomainClass(this.class)
        def properties = [:]
        domain.persistentProperties.findAll{!it.isAssociation()}.each{
            properties[it.name] = this[it.name]
        }
        properties.idAgent = this.agent.id
        properties.matricule = this.agent.matricule
        properties.nom = this.agent.nom
        properties.prenom = this.agent.prenom
        properties.dateNaissance = this.agent.dateNaissance
        properties.posteTravail = this.agent.libEmploi
        properties.unite = this.agent.unite.nom
        properties.site = this.agent.site
        properties.id = this.id
        return properties
    }
    
    def getRemarquesProperties = {
        def remarques = []
        this.remarques.each(){
            remarques.add([ id : it.id , text : it.text])
        }
        return remarques
    }
    
}
