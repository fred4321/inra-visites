
import java.sql.SQLException
import org.codehaus.groovy.grails.commons.DefaultGrailsDomainClass
import groovy.json.JsonSlurper
import java.text.SimpleDateFormat
import groovy.sql.Sql
import groovy.time.TimeCategory

class AgentController {
    
    def dataSource
    
    /**
     * Requette executé en pure sql car il est nécéssaire d'afficher les agents avec la dernière vm effectuer
     * tout en triant sur l'agent et sur la dernière vm
     */
    def getAll(){
        if(session.user){
            def result = []
            if(params.max && params.offset && params.sort && params.filter){
                //recupération des valeurs
                def slurper = new JsonSlurper()
                def offset = Integer.valueOf(params.offset)
                def max = Integer.valueOf(params.max)
                def sort = slurper.parseText(params.sort)
                def filter = slurper.parseText(params.filter)
                def sortIndex = 'matricule'
                def sortOrder  = 'asc'
                sort.each() { key, value ->
                    sortIndex=key
                    sortOrder=value
                }
                //creation des conditions
                def where = " where 0=0"+
                (params.prioritaireVM ? " AND a.prioritaireVM = true" : "")+
                (params.inactif ? " AND (a.date_arret_travail IS NULL OR a.date_arret_travail < NOW())" : "" )+
                (filter.nom ? " AND a.nom LIKE '${filter.nom}%'" : "" )+
                (filter.prenom ? " AND a.prenom LIKE '${filter.prenom}%'" : "" )+
                (filter.matricule ? " AND a.matricule LIKE '${filter.matricule}%'" : "" )+
                (filter.insee ? " AND a.insee LIKE '${filter.insee}%'" : "" )+
                (filter.site ? " AND a.site LIKE '${filter.site}%'" : "" )+
                (filter.periodicite ? " AND a.periodicite LIKE '%${filter.periodicite}%'" : "" )+
                (params.archive ? " AND a.archive = " + (params.archive == "true" ? 1 : 0) : "" )+
                (params.archivage == "true" || filter.dateSortie ? " AND a.date_sortie IS NOT NULL" : "" )+
                (params.archivage == "true" ? " AND a.date_sortie < NOW()" : "" )+
                (filter.dateSortie ? " AND s_date_sortie LIKE '%${filter.dateSortie}%'" : "" )+
                (filter.vmID ? " AND v.id = " + Long.parseLong(filter.vmID): "" )+
                (filter.vmDate ? " AND a.s_date_dernierevm LIKE '%${filter.vmDate}%'" : "")+
                (filter.natureVM ? " AND a.natureVM LIKE '%${filter.natureVM}%'" : "")+
                (filter.unite || session.user.profil == "GU" ? " AND u.nom = '${session.user.profil == "GU" ? session.user.unite.nom : filter.unite }'" : "")
                //trie
                def order = " ORDER by ${sortIndex} ${sortOrder}"
                //execution de la requette
                def sql = new Sql(dataSource)
                def requette = "select a.id, a.nom, a.prenom, a.matricule, a.insee, a.natureVM, a.date_sortie as dateSortie, a.periodicite, a.site, "+
                                    "v.naturevm as vmNature, v.id as vmID, a.date_dernierevm as vmDate, v.date_convoc as convocation, u.nom as unite "+
                                    "from agent as a "+
                                    "LEFT JOIN unite as u ON u.id = a.unite_id "+
                                    "LEFT JOIN vm as v ON (v.agent_id = a.id and v.date = (SELECT MAX(date) from vm where agent_id = a.id))"+
                                    (where != " where 0=0" ? where : '')+order
                def rows = sql.rows(requette)
                sql.close()
                //pagination du resultat
                for (int i = offset; i < offset+max; i++) {
                    i<rows.size() ? result.push( rows.get(i) ) : null
                }
                //envoi du resultat
                return [status:200, data:[agents:result,total: rows.size()]]
            }
            return [status:415]
        }
        return [status : 401]
    }
    
    /**
     * 
     * Liste des agents pour l'affectation à des VMs
     */
    def getAllPrioritaireVM(){
        if(session.user){
            if(params.unite){
                def unite = Unite.findByNom(session.user.profil == "GU" ? session.user.unite.nom : params.unite)
                if(unite){
                    def agents = Agent.findAllByUniteAndPrioritaireVM(unite,true,[sort: "nom"])
                    if(agents){
                        def listAgent = agents.collect{agent ->
                            return agent.getDefaultProperties()
                        }
                        return [status:200, data:listAgent]
                    }
                    return [status:204]
                }
                return [status:406]
            }
            return [status:415]
        }
        return [status : 401]
    }
	
    def get() {
        if(session.user){
            if(params.id){
                def agent = Agent.get(params.id)
                if(agent){
                    return [status:200, data:agent.getPlugProperties()]
                }
                return [status:204]
            }
            return [status:415]
        }
        return [status : 401]
    }
	
    def post(){
        if(session.user && session.user.profil == "SP"){
            def data = request.JSON//méthode qui récupère du json
            if(data && data.matricule && data.nom && data.prenom && data.unite){
                if(Agent.findWhere(matricule : data.matricule) == null){
                
                    data.unite = Unite.findByNom(data.unite).id
                    data.gu = User.findByIdentifiant(data.gu)
                    def agent = new Agent(data)
                    agent.dateCreation = new Date()
                    agent.dateModification = agent.dateCreation
                    agent.prioritaireVM = true
                    if(agent.validate()){
                        agent.save()
                        new Journal(date:new Date(), operation:"création", typeObject:"Agent", nom:agent.nom, prenom:agent.prenom, matricule:agent.matricule, unite:agent.unite.nom, user:session.user.identifiant).save()
                        log.info("création de l'agent : "+ agent.matricule)
                        return [status : 200 , data : agent]
                    }
                    return [status : 500]
                }
                return [status : 207]
            }	
            return [status : 415]
        }
        return [status : 401];
    }
	
    def update() {
        if( session.user ){
            def data = request.JSON
            if (data && data.id && data.id != 0 && data.matricule && data.nom && data.unite && data.prenom){
                def agent = Agent.get(data.id)
                if(agent){
                    Long id = data.id
                    if( Agent.find("FROM Agent as a WHERE a.matricule = ? and a.id != ? ", [data.matricule, id ]) == null ){
                        data.vm ? data.remove("vm") : null
                        data.bs ? data.remove("bs") : null
                        data.dateCreation ? data.remove("dateCreation") : null
                        data.derniereVM ? data.remove("derniereVM") : null
                        data.dateModification = new Date()
                        data.unite = Unite.findByNom(data.unite).id
                        data.gu = User.findByIdentifiant(data.gu)
                        agent.properties = data
                        if(agent.validate()){
                            agent.save(flush:true)
                            new Journal(date:new Date(), operation:"Modification", typeObject:"Agent", nom:agent.nom, prenom:agent.prenom, matricule:agent.matricule, unite:agent.unite.nom, user:session.user.identifiant).save()
                            log.info("mise à jour de l'agent : "+ agent.matricule)
                            return [status : 200 , data : agent]
                        }
                        return [status : 500]
                    }
                    return [status :207]
                }
                return [status : 204]
            }
            return [status  : 415]
        }
        return [status : 401];
    }
	
    def delete() {
        if(session.user){
            def agent = Agent.get(params.id)
            def mapResult
            if(agent) {
                agent.properties = params
                if(agent.validate()) {
                    log.info("suppression de l'agent : "+ agent.matricule)
                    def journau = new Journal(date:new Date(), operation:"Suppression", typeObject:"Agent", nom:agent.nom, prenom:agent.prenom, matricule:agent.matricule, unite:agent.unite.nom, user:session.user.identifiant)
                    agent.delete(flush:true)
                    return  [status : 200, data : agent]
                }
                return [status : 500 ]
            }
            return [status : 204]
        }
        return [status : 401];
    }
    
    def archiver(){
        if(session.user){
            def data = request.JSON
            def ok = []
            def ko = []
            if(data && data.length()>0){
                data.each(){
                    def agent = Agent.get(it)
                    agent.archive=true
                    if(agent.validate()){
                        agent.save()
                        new Journal(date:new Date(), operation:"archivage", typeObject:"Agent", nom:agent.nom, prenom:agent.prenom, matricule:agent.matricule, unite:agent.unite.nom, user:session.user.identifiant).save()
                        log.info("archivage de l'agent : "+ agent.matricule)
                        ok.add(it)
                    }else{
                        ko.add(it)
                    }
                }
                return [status:200, data:[ ok : ok , ko : ko ]]
            }
            return [status : 204]
        }
        return [status : 401];
    }
    
    def affectePrioritaire = {
        def today = new Date();
        def agents = Agent.findAllWhere( archive : false , prioritaireVM : false)
        agents.each(){
            def periodicite = "2 ans"
            if(it.periodicite){
                periodicite = it.periodicite
            }
            def definition = periodicite.substring(2,periodicite.length())
            def quantite = Integer.parseInt(periodicite.substring(0,1))
            def m=1
            if(definition == "ans" || definition == "an"){
                m=12
            }
            def delay = quantite*m
            if(it.dateDerniereVM){
                def nextVM
                def dateDerniereVM = it.dateDerniereVM
                use (TimeCategory) {
                    nextVM = dateDerniereVM + delay.month
                }
                
                //println it.matricule + " - " + delay +" : " + today + " -> " + nextVM + " = " +(today >= nextVM)
                if(today >= nextVM){
                    it.prioritaireVM=true
                    it.natureVM = "VPA"
                    it.save()
                }
            }else{
                it.prioritaireVM=true
                it.natureVM = "VE"
                it.save()
            }
        }
        return [status : 200];
    }
    
    def correctifBase = {               //correctif de base de donnée
        println "run patch"
        def listeAgents = Agent.getAll()
        listeAgents.each(){
            println it.id + " : " + it.matricule
            it.dateDerniereVM = it.vm ? it.vm[0].date : null
            it.save(flush:true)
        }
        return [status : 200];
    }
    
    
}