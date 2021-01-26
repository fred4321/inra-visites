import java.text.SimpleDateFormat
import org.codehaus.groovy.grails.commons.DefaultGrailsDomainClass

/**
 *liste des statuts:
 *initial : la vm n'a pas encore d'unité
 *non affecté : la vm est créer mais aucun agent n'est affecté dessus
 *affecté : vm créé et affecté à un agent
 *convoqué : vm créé, affecté et convocation envoyé
 *absent : l'agent ne c'est pas présenté
 *effectué : vm réalisé mais pas de mail envoyé à l'agent
 *cloturé : vm réalisé et mail récapitulatif envoyé à l'agent
 *****************************************/
class VM {
	
    String creneau
    String lieu
    String site
    String statut
    String refMedecin
    String avis
    String apte
    String natureVM
    
    Date date
    Date dateConvoc
    Date dateEnvoisCourrierAgent
    Date dateEnvoisCourrierDURRH
    Date modification
    
    String sDate
    String sDateConvoc
    String sDateEnvoisCourrierAgent
    String sDateEnvoisCourrierDURRH
    String sModification
    
    BS bs
    
    List observations
    List conclusions
    
    boolean amenagement
    
    static belongsTo = [agent : Agent, unite : Unite]
    static hasMany = [observations : Observation, conclusions : Observation]
        
    static mapping = {
        sort "date"
        amenagement defaultValue : false
        observations cascade: 'all-delete-orphan'
        conclusions cascade: 'all-delete-orphan'
        bs cascade : 'all-delete-orphan'
    }
	
    static constraints = {
        creneau(nullable:true)
        agent(nullable:true)
        bs(nullable:true)
        dateConvoc(nullable:true)
        refMedecin(nullable:true)
        avis(nullable:true)
        apte(nullable:true)
        dateEnvoisCourrierAgent(nullable:true)
        dateEnvoisCourrierDURRH(nullable:true)
        natureVM(nullable:true)
        observations(nullable:true)
        conclusions(nullable:true)
        unite(nullable:true)
        sDate(nullable:true)
        sDateConvoc(nullable:true)
        sDateEnvoisCourrierAgent(nullable:true)
        sDateEnvoisCourrierDURRH(nullable:true)
        sModification(nullable:true)
    }
    
    def beforeValidate() {
        SimpleDateFormat formater = new SimpleDateFormat("dd MMM yyyy")
        if(date){
            date.set(hourOfDay : 12)
            sDate = formater.format(date)
        }
        if(dateConvoc){
            date.set(hourOfDay : 12)
            sDateConvoc = formater.format(dateConvoc)
        }
        if(dateEnvoisCourrierAgent){
            date.set(hourOfDay : 12)
            sDateEnvoisCourrierAgent = formater.format(dateEnvoisCourrierAgent)
        }
        if(dateEnvoisCourrierDURRH){
            date.set(hourOfDay : 12)
            sDateEnvoisCourrierDURRH = formater.format(dateEnvoisCourrierDURRH)
        }
        if(modification){
            date.set(hourOfDay : 12)
            sModification = formater.format(modification)
        }
    }
    
    def getDefaultProperties= {
        def domain = new DefaultGrailsDomainClass(this.class)
        def properties = [:]
        domain.persistentProperties.findAll{!it.isAssociation()}.each{
            it.name !="refMedecin" && it.name !="apte" && it.name !="avis" && it.name !="amenagement" ? properties[it.name] = this[it.name] : null
        }
        properties.uniteAgent = this.agent.unite.nom
        return properties
    }
    
    def getGUProperties = {
        def domain = new DefaultGrailsDomainClass(this.class)
        def properties = [:]
        domain.persistentProperties.findAll{!it.isAssociation()}.each{
            it.name !="refMedecin" && it.name !="apte" && it.name !="avis" && it.name !="amenagement" ? properties[it.name] = this[it.name] : null
        }
        properties.id = this.id
        properties.site = this.site
        properties.unite = this.unite ? this.unite?.nom : null
        if (this.agent){
            properties.periodicite = this.agent.periodicite
            properties.matricule = this.agent.matricule
            properties.nom = this.agent.nom
            properties.prenom = this.agent.prenom
            properties.idAgent = this.agent.id
        }
        return properties
    }
    
    def getAllProperties = {
        def properties = getGUProperties()
        properties.amenagement = this.amenagement
        properties.refMedecin = this.refMedecin
        properties.apte = this.apte
        properties.avis = this.avis
        return properties
    }
    
    static def getMedecinSp = { id ->
        def vm = VM.get(id)
        def p = vm.getDefaultProperties()
        p.id = vm.id
        p.apte = vm.apte
        p.refMedecin = vm.refMedecin
        p.site = vm.site
        p.amenagement = vm.amenagement
        if(vm.agent){
            p.matricule = vm.agent.matricule
            p.nom = vm.agent.nom
            p.prenom = vm.agent.prenom
            p.libEmploi = vm.agent.libEmploi
            p.dateArretTravail = vm.agent.dateArretTravail
            p.dateNaissance = vm.agent.dateNaissance
            p.unite = vm.unite.nom
            p.dateDerniereVM = vm.getLastExecuteVm()
        }
        p.conclusions = vm.conclusions
        p.observations = vm.observations
        p.bs = vm.bs ? vm.bs.id : null
        p.datePrescriptionBs = vm.bs ? vm.bs.datePrescription : null
        p.remarques = vm.bs ? vm.bs.remarques : []
        p.typeBs = vm.bs ? vm.bs.type : null
        return p
    }
    
    def getFullVm = {
        def p = this.getDefaultProperties()
        p.id = this.id
        p.apte = this.apte
        p.refMedecin = this.refMedecin
        p.site = this.site
        p.amenagement = this.amenagement
        if(this.agent){
            p.matricule = this.agent.matricule
            p.nom = this.agent.nom
            p.prenom = this.agent.prenom
            p.libEmploi = this.agent.libEmploi
            p.dateArretTravail = this.agent.dateArretTravail
            p.dateNaissance = this.agent.dateNaissance
            p.dateDerniereVM = this.getLastExecuteVm()
        }
        if(this.unite){
            p.unite = this.unite.nom
        }
        p.conclusions = this.conclusions
        p.observations = this.observations
        p.bs = this.bs ? this.bs.id : null
        p.datePrescriptionBs = this.bs ? this.bs.datePrescription : null
        p.remarques = this.bs ? this.bs.remarques : []
        p.typeBs = this.bs ? this.bs.type : null
        return p
    }
    
    def getLimitVm = {
        def p = this.getDefaultProperties()
        p.id = this.id
        p.apte = this.apte
        p.refMedecin = this.refMedecin
        p.site = this.site
        p.amenagement = this.amenagement
        if(this.agent){
            p.matricule = this.agent.matricule
            p.nom = this.agent.nom
            p.prenom = this.agent.prenom
            p.libEmploi = this.agent.libEmploi
            p.dateArretTravail = this.agent.dateArretTravail
            p.dateNaissance = this.agent.dateNaissance
            p.unite = this.unite.nom
            p.dateDerniereVM = this.getLastExecuteVm()
        }
        p.observations = this.observations
        return p
    }
    
    def getLastExecuteVm = {
        def res = null
        this.agent.vm.each(){
            if(it.statut == "effectué" || it.statut == "cloturé"){
                res = res ? ( res < it.date ? it.date : res ) : it.date
            }
        }
        return res
    }
}
