
import java.util.regex.Matcher
import java.util.regex.Pattern
import java.text.SimpleDateFormat
import MessageService
import groovy.json.JsonSlurper

/*
 * Liste des status de bs
 * {statut : 'créé - programmé - convoqué - absent - effectué'}
 */
class BSController {
    
    def messageService
	
    def getAll(){
        if(session.user){
            if(params.max && params.offset && params.sort && params.filter){
                def slurper = new JsonSlurper()
                def offset = Integer.valueOf(params.offset)
		def max = Integer.valueOf(params.max)
                def sort = slurper.parseText(params.sort)
                def filter = slurper.parseText(params.filter)
                def sortIndex = 'datePassage'
		def sortOrder  = 'asc'
                sort.each() { key, value ->
                    sortIndex=key
                    sortOrder=value
                }
                def bss = BS.createCriteria().list(max: max, offset: offset) {
                    if (filter.id){
                        eq('id', Long.valueOf(filter.id))
                    }
                    if (filter.lieu){
                        ilike('lieu', "${filter.lieu}%")
                    }
                    if (filter.type){
                        ilike('type', "${filter.type}%")
                    }
                    if (filter.datePrescription){
                        ilike('sDatePrescription', "%${filter.datePrescription}%")
                    }
                    if (filter.dateConvocation){
                        ilike('sDateConvocation', "%${filter.dateConvocation}%")
                    }
                    if (filter.datePassage){
                        ilike('sDatePassage', "%${filter.datePassage}%")
                    }
                    if(params.statut){
                        def statut = params.statut.split('-')
                        'in'('statut',statut)
                    }
                    and{
                        if(filter.unite || session.user.profil == "GU"){
                            agent{
                                unite{
                                    eq('nom', "${ session.user.profil == "GU" ? session.user.unite.nom : filter.unite }")
                                }
                            }
                        }

                        agent{
                            eq('archive', false)
                        }

                        if(filter.nom){
                            agent{
                                ilike('nom', "${filter.nom}%")
                            }
                        }
                        if(filter.prenom){
                            agent{
                                ilike('prenom', "${filter.prenom}%")
                            }
                        }
                        if(filter.matricule){
                            agent{
                                ilike('matricule', "${filter.matricule}%")
                            }
                        }
                    }
                    switch (sortIndex){
                        case "unite":
                            agent{unite{order("nom",sortOrder)}}
                            break
                        case "site":
                            agent{unite{order("site",sortOrder)}}
                            break
                        case "nom":
                            agent{order("nom",sortOrder)}
                            break
                        case "prenom":
                            agent{order("prenom",sortOrder)}
                            break
                        case "matricule":
                            agent{order("matricule",sortOrder)}
                            break
                        default : 
                            order(sortIndex, sortOrder)
                            break
                    }
                }
                if(bss){
                    def listbs = bss.collect{bs ->
                        return bs.getDefaultProperties()
                    }
                    return [status:200, data:[bss:listbs,total: bss.totalCount]]
                }
                return [status : 204]
            }
            return [status : 415]
        }
        return [status : 401]
    }
	
    def get() {
        if(session.user){
            def bs = BS.get(params.id)
            if (bs){
                def properties = bs.getDefaultProperties()
                properties.remarques = bs.getRemarquesProperties()
                if(properties){
                    return [status:200, data:properties]
                }
                return [status:204]
            }
            return [status : 204]
        
        }
        return [status : 401]
    }
	
    def post(){
        if(session.user){
            def data = request.JSON
            if(data && data.idVm && data.type){
                def vm = VM.get(data.idVm)
                if ( vm ){
                    if ( vm.bs == null ){
                        def bs = new BS(vm : vm , type : data.type , agent : vm.agent , datePrescription : new Date() , statut : "créé")
                        if(data.remarques && data.remarques.length()>0){
                            data.remarques.each(){
                                def obs = new Observation(text : it.text)
                                obs.save()
                                bs.addToRemarques(obs)
                            }
                        }
                        vm.bs = bs
                        if(bs.validate() && vm.validate()){
                            vm.save()
                            new Journal(ref:bs.id,date:new Date(), operation:"Création", typeObject:"BS", nom:bs.agent.nom, prenom:bs.agent.prenom, matricule:bs.agent.matricule, unite:bs.agent.unite.nom, user:session.user.identifiant).save()
                            log.info("création du bs "+bs.id+" pour l'agent : "+ bs.agent.matricule)
                            return [status : 200, data : bs]
                        }
                        vm.errors.allErrors.each{ println it }
                        return [status : 415]
                    }
                    return [status : 409]
                }
                return [status : 204]
            }
            return [status : 415]
        }
        return [status : 401];
    }
    
    def addDate(){
        if(session.user){
            def data = request.JSON//méthode qui récupère du json
            if(data && data.creneau && data.creneau.info1 && data.creneau.valeur && 
                data.site && data.site.id && data.site.valeur && data.date && data.lieu && data.lieu.id) {
                def lieu = Params.get(data.lieu.id)
                def creneau = data.creneau.valeur.toString()
                def duree = Integer.parseInt(data.creneau.info1.toString())
                def nbCreneau = this.calculNbCreneau(creneau, duree)
                def listBs = BS.executeQuery("SELECT b FROM BS AS b WHERE b.agent.site = :site AND b.statut = :statut ", [site : data.site.valeur, statut : "créé"])
                if(!listBs){
                    return [status : 204]
                }
                if(!lieu){
                    return [status : 415]
                }
                def now = new Date()
                if (data.date.periode == "true"){
                    if(data.date.debut && data.date.fin && nbCreneau){
                        def dateDebut = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(data.date.debut.substring(0, 24))
                        def dateFin = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(data.date.fin.substring(0, 24))
                        def i = 0
                        while(dateDebut<=dateFin+1){
                            if(dateDebut.format('E') != "sam." && dateDebut.format('E') != "dim."){
                                for (i ; i < nbCreneau && i < listBs.size() ; i++){
                                    if(listBs[i].agent.site == data.site.valeur && listBs[i].agent.dateArretTravail < now){
                                        listBs[i].datePassage = date
                                        listBs[i].heureDebut = creneau.substring(0,5)
                                        listBs[i].heureFin = creneau.substring(8,13)                            
                                        listBs[i].statut = "programmé"
                                        listBs[i].lieu = lieu.valeur
                                        if(listBs[i].validate()){
                                            listBs[i].save()
                                            new Journal(ref:listBs[i].id,date:new Date(), operation:"Modification", typeObject:"BS", nom:listBs[i].agent.nom, prenom:listBs[i].agent.prenom, matricule:listBs[i].agent.matricule, unite:listBs[i].agent.unite.nom, user:session.user.identifiant).save()
                                            log.info("Ajout d'une date au bs "+listBs[i].id+" pour l'agent : "+ listBs[i].agent.matricule)
                                        }else{
                                            listBs[i].errors.allErrors.each{ println it }
                                            log.info("Erreur de création d'un bs")
                                        }
                                    }
                                }
                            }
                            dateDebut = dateDebut.next()
                        }
                        if(listBs){
                            return [status:200, data:listBs]
                        }
                        return [status:204]
                    }
                    return [status : 415]
                }else{
                    if(data.date.value && nbCreneau ){
                        def date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(data.date.value.substring(0, 24))
                        //attribution des bs
                        for (int i = 0 ; i < nbCreneau && i < listBs.size() ; i++){
                            if(listBs[i].agent.site == data.site.valeur && listBs[i].agent.dateArretTravail < now){
                                listBs[i].datePassage = date
                                listBs[i].heureDebut = creneau.substring(0,5)
                                listBs[i].heureFin = creneau.substring(8,13)                            
                                listBs[i].statut = "programmé"
                                listBs[i].lieu = lieu.valeur
                                if(listBs[i].validate()){
                                    listBs[i].save()
                                    new Journal(ref:listBs[i].id,date:new Date(), operation:"Modification", typeObject:"BS", nom:listBs[i].agent.nom, prenom:listBs[i].agent.prenom, matricule:listBs[i].agent.matricule, unite:listBs[i].agent.unite.nom, user:session.user.identifiant).save()
                                    log.info("Ajout d'une date au bs "+listBs[i].id+" pour l'agent : "+ listBs[i].agent.matricule)
                                }
                                listBs[i].errors.allErrors.each{ println it }
                                log.info("Erreur de création d'un bs")
                            }
                        }
                        if(listBs){
                            return [status:200, data:listBs]
                        }
                        return [status:204]
                    }
                    return [status : 415]
                }
            }
            return [status : 415]
        }
        return [status: 401]
    }
	
    def update() {
        if(session.user){
            def data = request.JSON
            if(data && data.id && data.type){
                def bs = BS.get(data.id)
                if(bs){
                    bs.type = data.type
                    if(bs.validate()){
                        bs.save()
                        log.info("mise à jour du bs "+bs.id)
                        new Journal(ref:bs.id,date:new Date(), operation:"Modification", typeObject:"BS", nom:bs.agent.nom, prenom:bs.agent.prenom, matricule:bs.agent.matricule, unite:bs.agent.unite.nom, user:session.user.identifiant).save()
                        return [status : 200, data : bs]
                    }
                    bs.errors.allErrors.each{ println it }
                    return [status : 500]
                }
                return [status : 204]
            }
            return [status : 415]
        }
        return [status: 401]
    }
	
    def delete() {
        if(session.user){
            if(params.id){
                def bs = BS.get(params.id)
                if(bs) {
                    new Journal(ref:bs.id,date:new Date(), operation:"Supprimer", typeObject:"BS", nom:bs.agent.nom, prenom:bs.agent.prenom, matricule:bs.agent.matricule, unite:bs.agent.unite.nom, user:session.user.identifiant).save()
                    bs.vm.bs = null
                    bs.vm.save()
                    log.info("suppression du bs "+bs.id)
                    bs.delete(flush:true)
                    return [status : 200]
                }
                return [status : 204]
            }
            return [status : 415]
        }
        return [status : 401];
    }
    
    def convoquer(){
        if(session.user){
            def data = request.JSON
            if(data && data.id){
                def bs = BS.get(data.id)
                if(bs){
                    bs.statut = "convoqué"
                    bs.dateConvocation = new Date()
                    if(bs.validate()){
                        bs.save()
                        def emails = [bs.agent.courriel, "medtlse.medtlse@inra.fr"]
                        if(bs.agent.gu && bs.agent.gu.email){
                            emails.push(bs.agent.gu.email)
                        }
                        messageService.send(messageService.prepareMessage(session.user, emails, "INRA - Convocation de "+ bs.agent.nom + " " + bs.agent.prenom +" au bilan sanguin le "+ bs.sDatePassage, messageService.bsConvocation(bs)))
                        log.info(" convocation de l'agent "+bs.agent.matricule+" le "+bs.datePassage.format('dd/MM/yyyy'))
                        return [status : 200, data : bs.getDefaultProperties()]
                    }
                    return [status : 500]
                }
                return [status : 204]
            }
            return [status : 415]
        }
        return [status : 401];
    }
    
    def effectuer(){
        if(session.user){
            def data = request.JSON
            if( data && data.length() > 0 ){
                data.each(){
                    def bs = BS.get(it.id)
                    if(bs){
                        bs.statut = "effectué"
                        bs.save()
                        new Journal(ref:bs.id,date:new Date(), operation:"effectué", typeObject:"BS", nom:bs.agent.nom, prenom:bs.agent.prenom, matricule:bs.agent.matricule, unite:bs.agent.unite.nom, user:session.user.identifiant).save()
                    }
                }
                return [status : 200]
            }
            return [status : 415]
        }
        return [status : 401];
    }
    
    def initialiser(){
        if(session.user){
            def data = request.JSON
            if( data.id ){
                def bs = BS.get(data.id)
                if(bs){
                    bs.dateConvocation = null
                    bs.datePassage = null
                    bs.heureDebut = null
                    bs.heureFin = null
                    bs.statut = "créé"
                    if(bs.validate()){
                        bs.save()
                        def properties = bs.getDefaultProperties()
                        properties.remarques = bs.getRemarquesProperties()
                        return [status : 200, data : properties]
                    }
                    return [status : 500]
                }
                return [status : 204]
            }
            return [status : 415]
        }
        return [status : 401]
    }
    
    def absent(){
        if(session.user){
            def data = request.JSON
            if(data && data.id){
                def bs = BS.get(data.id)
                if(bs){
                    bs.statut = "absent"
                    def newBs = new BS(vm : bs.vm , type : bs.type , agent : bs.agent , datePrescription : bs.datePrescription , lieu : bs.lieu , statut : "créé")
                    bs.remarques.each(){
                        newBs.addToRemarques(it)
                    }
                    bs.vm.bs = newBs
                    if(bs.validate() && bs.vm.validate() && newBs.validate()){
                        newBs.save()
                        bs.vm.save()
                        bs.save()
                        return [status : 200, data : bs.getDefaultProperties()]
                    }
                    return [status : 500]
                }
                return [status : 204]
            }
            return [status : 415]
        }
        return [status : 401]
    }
    
        def listeExamenCvs = {
        println "listeExamenCvs"
        if(session.user && session.user.profil && session.user.profil == "SP"){
            if( params.site ){
                def result = []
                def bs = BS.findAllWhere( statut : "convoqué")
                println bs
                if(bs){
                    bs.each{
                        if(it.vm.site && it.vm.site == params.site){
                            result.add(it.getDefaultProperties())
                        }
                    }
                    
                    println result.size()
                    
                    if(result.size()>0){
                        return [status : 200, data:result]
                        //envoi au format csv nécéssite : def exportService
//                        response.contentType = 'text/csv'
//                        response.setHeader("Content-disposition", "attachment; filename=${params.site}.csv")
//                        exportService.export('csv', response.outputStream, result, [:],[:])
                    }
                    return [status : 204]
                }
            }
            return [status : 415]
        }
        return [status : 401]
    }
    
    def calculNbCreneau = { creneau , duree ->
        if(creneau && duree && creneau ==~ /[0-9]{2}[h|H][0-9]{2} - [0-9]{2}[h|H][0-9]{2}/ ){
            def heureDebut  = Integer.parseInt(creneau.substring(0,2))
            def heureFin = Integer.parseInt(creneau.substring(8,10))
            def minuteDebut = Integer.parseInt(creneau.substring(3,5))
            def minuteFin = Integer.parseInt(creneau.substring(11,13))
            return ( (heureFin - heureDebut) * 60 - minuteDebut + minuteFin ) / duree
        }
        return false
    }
	
}
