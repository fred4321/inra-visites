import java.text.SimpleDateFormat
import transfert.Matrice
import transfert.Transfert
import groovy.sql.Sql

/**
 * Transfers l'ancienne base de donnée vers la nouvelle
 */

class TransfertService {
    static transactional = false
    def dataSource
    
    def readDataBase(){
        println new Date()
        Matrice m = Transfert.getMatrice();
        println "creation de la matrice"
        println new Date()
        if(importParams(m)){
            println "import des paramètres"
            println new Date()
            def unites = importUnites(m)
            if(unites){
                println "import des unites"
                println new Date()
                def agents = importAgent(m,unites)
                if(agents){
                    println "import des agents"
                    println new Date()
                    if(importVM(m, unites, agents)){
                        println "import des vms"
                        println new Date()
                        println "Mise a jour de la dernière vm"
                        println new Date()
                        updateLastVM(agents)
                        println new Date()
                        println "Import terminé"
                        
                    }else{
                        println "echec import vm"
                    }
                }else{
                    println "echec import agent"
                }
            }else{
                println "echec import unite"
            }
        }else{
            println "echec import params"
        }
    }
    
    def updateLastVM(agents){
        def ok=0
        def ko=0
        def nul=0
        def sql = new Sql(dataSource)
        agents.each(){key , value ->
            def requette = "select v.date from agent as a LEFT JOIN vm as v ON (v.agent_id = a.id and v.date = (SELECT MAX(date) from vm where agent_id = a.id))"+
            "where a.id = "+value.id
            def rows = sql.rows(requette)
            rows.each { row ->
                if (row.date){
                    value.dateDerniereVM = row.date
                    if(value.validate()){
                        println "ok update agent ${key}"
                        value.save(flush:true)
                        ok++
                    }else{
                        println "ko update agent ${key}"
                        ko++
                    }
                }else{
                    println "pas de vm trouvé pour l'agent ${key}"
                    nul++
                }
            }
        }
        sql.close()
        println "update réussi de "+ok+" date agent"
        println "update échoué de "+ko+" date agent"
        println "date derniere vm null de "+nul+" agent" 
    }
    
    def importVM(Matrice m, unites, agents){
        def today = new Date()
        def totalVMOk = 0
        def totalVMKo = 0
        for(int i = 0 ; i< m.vms.size();i++){
            def vm = new VM()
            def date
            if(m.vms.get(i).get(4)){
                date = m.vms.get(i).get(4).substring(0,10) + " " + (m.vms.get(i).get(1) ? m.vms.get(i).get(1).substring(11,19) : (m.vms.get(i).get(3) ? m.vms.get(i).get(3).substring(11,19) : "12:00:00" ))
            }else{
                date = "1970-12-30 12:00:00"
            }
            vm.date = parseDate(date)
            vm.natureVM = m.vms.get(i).get(7) ? m.vms.get(i).get(7) : "inconnu"
            vm.site = m.vms.get(i).get(6) ? m.vms.get(i).get(6) : "inconnu"
            vm.lieu = m.vms.get(i).get(5) ? m.vms.get(i).get(5) : "inconnu"
            vm.modification =  today
            vm.agent = agents[m.vms.get(i).get(0)]
            vm.unite = unites[m.vms.get(i).get(8)]
            vm.statut = ( vm.date < today ) ? "cloturé" : ( vm.agent ? "affecté" : ( vm.unite ? "non affecté" : "initial" ) )
            if(vm.agent && vm.unite){
                if(vm.save(flush:true)){
                    totalVMOk++
                }else{
                    totalVMKo++
                }
            }else{
                totalVMKo++
                println "import échoué de la VM "+i+" : agent ou unite introuvable"
            }
        }
        def listeAgents = Agent.getAll()
        listeAgents.each(){
            it.dateDerniereVM = it.vm ? it.vm[0].date : null
            it.save(flush:true)
        }
        println "import réussi de "+totalVMOk+" VM"
        println "import échoué de "+totalVMKo+" VM" 
        return true
    }
    
    def importAgent(Matrice m, unites){
        def now = new Date()
        def totalAgentOk = 0
        def totalAgentKo = 0
        def agents = [:]
        for(int i = 0 ; i< m.agents.size();i++){
            if(unites[m.agents.get(i).get(5)]){
                def agent = new Agent()
                agent.matricule = m.agents.get(i).get(0)
                agent.nom = m.agents.get(i).get(1)
                agent.prenom = m.agents.get(i).get(2)
                agent.dateEntree = parseDate(m.agents.get(i).get(3))
                agent.dateSortie = parseDate(m.agents.get(i).get(4))
                agent.unite = unites[m.agents.get(i).get(5)]
                agent.site = m.agents.get(i).get(6)
                agent.genre = m.agents.get(i).get(7)
                agent.libEmploi = m.agents.get(i).get(8)
                agent.dateArretTravail = parseDate(m.agents.get(i).get(11))
                agent.dateCreation = now
                agent.dateModification = now
                agent.archive = agent.dateSortie && agent.dateSortie < now
                if( agent.validate()){
                    agent.save()
                    agents[agent.matricule]=agent
                    totalAgentOk++
                }else{
                    println "agent "+m.agents.get(i).get(0)+" - "+m.agents.get(i).get(1) + " " + m.agents.get(i).get(2) + " non importé - échec de la sauvergarde"
                    totalAgentKo++
                }
            }else{
                println "agent "+m.agents.get(i).get(0)+" - "+m.agents.get(i).get(1) + " " + m.agents.get(i).get(2) + " non importé - aucune unité trouvé"
                totalAgentKo++
            }
        }
        println "import réussi de "+totalAgentOk+" agent"
        println "import échoué de "+totalAgentKo+" agent" 
        return agents
    }
    
    def importUnites(Matrice m){
        def unites = [:]
        def totalUniteOk = 0
        def totalUniteKo = 0
        //Import des types de unites de Rennes
        for(int i = 0 ; i< m.unites.size();i++){
            def unite = new Unite()
            unite.nom = m.unites.get(i).get(1)
            unite.lieu = m.unites.get(i).get(3)
            unite.site = m.unites.get(i).get(2)
            unite.importer = true
            if(unite.validate()){
                unite.save()
                unites[m.unites.get(i).get(0)] = unite
                totalUniteOk++
            }else{
                totalUniteKo++
            }
        }
        //Import des types de unites hors de Rennes
        for(int i = 0 ; i< m.unitesNonRennes.size();i++){
            def unite = new Unite()
            unite.nom = m.unitesNonRennes.get(i).get(1)
            unite.importer = false
            if(unite.validate()){
                unite.save()
                unites[m.unitesNonRennes.get(i).get(0)] = unite
                totalUniteOk++
            }else{
                totalUniteKo++
            }
        }
        println "import réussi de "+totalUniteOk+" unites"
        println "import échoué de "+totalUniteKo+" unites" 
        return unites
    }
    
    def importParams(Matrice m){
        def totalParamsOk = 0
        def totalParamsKo = 0
        //Import des types de vm
        for(int i = 0 ; i< m.typesVm.size();i++){
            def param = new Params()
            param.valeur = m.typesVm.get(i).get(0)
            param.info1 = m.typesVm.get(i).get(1)
            param.groupe = "natureVM"
            if(param.validate()){
                param.save()
                totalParamsOk++
            }else{
                totalParamsKo++
            }
        }
        //Import des types de bs
        for(int i = 0 ; i< m.typesBs.size();i++){
            def param = new Params()
            param.valeur = m.typesBs.get(i).get(0)
            param.groupe = "typeBS"
            if(param.validate()){
                param.save()
                totalParamsOk++
            }else{
                totalParamsKo++
            }
        }
        //Import des lieux
        for(int i = 0 ; i< m.lieux.size();i++){
            def param = new Params()
            param.valeur = m.lieux.get(i).get(0)
            param.info1 = m.lieux.get(i).get(2)
            param.groupe = "lieu"
            if(param.validate()){
                param.save()
                totalParamsOk++
            }else{
                totalParamsKo++
            }
        }
        //Import des sites
        for(int i = 0 ; i< m.sites.size();i++){
            def param = new Params()
            param.valeur = m.sites.get(i).get(0)
            def lieu = Params.findByInfo1(m.sites.get(i).get(1))
            param.info1 = lieu?.id
            param.groupe = "site"
            if(param.validate()){
                param.save()
                totalParamsOk++
            }else{
                totalParamsKo++
            }
        }
        println "import réussi de "+totalParamsOk+" paramètres"
        println "import échoué de "+totalParamsKo+" paramètres" 
        return true
    }
    
    static def parseDate(string){
        if(string){
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(string.substring(0,19))
        }
        return null
    }
}